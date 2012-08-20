package com.pwrd.war.gameserver.quest;

import java.sql.Timestamp;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pwrd.war.common.LogReasons;
import com.pwrd.war.common.LogReasons.CurrencyLogReason;
import com.pwrd.war.common.LogReasons.ItemGenLogReason;
import com.pwrd.war.common.LogReasons.MoneyLogReason;
import com.pwrd.war.common.LogReasons.QuestLogReason;
import com.pwrd.war.common.LogReasons.TaskLogReason;
import com.pwrd.war.common.model.quest.QuestDestInfo;
import com.pwrd.war.common.model.quest.QuestInfo;
import com.pwrd.war.core.util.KeyUtil;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.common.event.StoryShowEvent;
import com.pwrd.war.gameserver.common.i18n.constants.QuestLangConstants_40000;
import com.pwrd.war.gameserver.currency.Currency;
import com.pwrd.war.gameserver.currency.CurrencyCostType;
import com.pwrd.war.gameserver.human.Human;
import com.pwrd.war.gameserver.item.ItemParam;
import com.pwrd.war.gameserver.quest.DoingQuest.QuestStatus;
import com.pwrd.war.gameserver.quest.IQuest.QuestType;
import com.pwrd.war.gameserver.quest.bonus.IQuestBonus;
import com.pwrd.war.gameserver.quest.condition.IQuestCondition;
import com.pwrd.war.gameserver.quest.condition.IQuestCondition.QuestConditionType;
import com.pwrd.war.gameserver.quest.template.QuestTemplate;
import com.pwrd.war.gameserver.story.StoryEventType;

/**
 * 任务模板骨架实现
 * 
 * 
 * 
 */
public abstract class AbstractQuest {
	/** 任务模板 */
	protected QuestTemplate template;
	
	/** 前置任务列表 */
	protected List<AbstractQuest> preQuests = new ArrayList<AbstractQuest>();
	
	/** 后续任务列表 */
	protected List<AbstractQuest> nextQuests = new ArrayList<AbstractQuest>();
	
	protected AbstractQuest() {
	}
	
	public AbstractQuest(QuestTemplate template) {
		this.template = template;
	}
	
	/**
	 * 获取前置任务列表
	 * @return
	 */
	public List<AbstractQuest> getPreQuests() {
		return preQuests;
	}
	
	/**
	 * 加入一个任务到前置任务列表中，仅加载任务配置时调用
	 * @param next
	 */
	public void addPreQuest(AbstractQuest next) {
		preQuests.add(next);
	}
	
	/**
	 * 获取后续任务列表
	 * @return
	 */
	public List<AbstractQuest> getNextQuests() {
		return nextQuests;
	}
	
	/**
	 * 加入一个任务到后续任务列表中，仅加载任务配置时调用
	 * @param next
	 */
	public void addNextQuest(AbstractQuest next) {
		nextQuests.add(next);
	}

	/**
	 * 判断任务是否可以接受，仅检查等级和任务接受条件是否满足
	 * @param human
	 * @param showError
	 * @return
	 */
	public final boolean canAccept(Human human, boolean showError) {
		//角色等级未达到最低等级要求时不可接受
		if (human.getLevel() < getAcceptMinLevel()) {
			if (showError) {
				human.getPlayer().sendErrorPromptMessage(QuestLangConstants_40000.QUEST_LEVEL_NOT_REACH, getAcceptMinLevel());
			}
			return false;
		}
		
		//任务在进行中，不能再次接受
		QuestDiary qd = human.getQuestDiary();
		if (qd.isDoingQuest(getId())) {
			if (showError) {
				human.getPlayer().sendErrorPromptMessage(QuestLangConstants_40000.QUEST_IN_PROCESS, getQuestTitle());
			}
			return false;
		}
		
		//检查前置任务是否完成
		for (AbstractQuest quest : preQuests) {
			if (!qd.hasFinishedQuest(quest.getId())) {
				if (showError) {
					human.getPlayer().sendErrorPromptMessage(QuestLangConstants_40000.QUEST_PREQUEST_NOT_FINISHED, getQuestTitle());
				}
				return false;
			}
		}
		
		//逐个检查任务接受条件
		for (IQuestCondition c : getAcceptConditionList()) {
			if (!c.isMeet(human, getId(), showError)) {
				return false;
			}
		}
		
		//检查扩展条件
		return canAcceptImpl(human, showError);
	}
	
	/**
	 * 判断任务是否可以接受，子类扩展条件
	 * @param human
	 * @return
	 */
	protected abstract boolean canAcceptImpl(Human human, boolean showError);
	
	/**
	 * 任务接受处理
	 * @param human
	 * @param showError
	 * @return
	 */
	public final boolean accept(Human human) {
		//首先判断任务必须可以接受
		if (!canAccept(human, true)) {
			return false;
		}
		
		//调用扩展处理
		if (!acceptImpl(human)) {
			return false;
		}
		
		//接受任务后的各个接受条件的处理
		for (IQuestCondition c : getAcceptConditionList()) {
			c.onAccept(human);
		}
		
		//接受任务成功，将当前任务状态加入正在进行任务数据中
		DoingQuest doing = new DoingQuest(human, this);
		doing.setDbId(KeyUtil.UUIDKey());
		doing.setInDb(false);
		doing.setStartTime(new Timestamp(Globals.getTimeService().now()));
		doing.initParamFromTemplate();
		doing.getLifeCycle().activate();
		doing.setModified();
		
		//向已接任务列表中添加
		QuestDiary qd = human.getQuestDiary();
		qd.addDoingQuest(doing);
		
		//接受的任务如果为主线任务，则更新当前主线任务区域
		if (getQuestTypeEnum() == QuestType.MAIN) {
			human.getQuestDiary().setCurrentRegion(getQuestRegion());
		}
		
		//记录日志，格式为：任务Id={0}|任务名称={1}
		String detailReason = TaskLogReason.REASON_TASK_ACCEPT.getReasonText();
		detailReason = MessageFormat.format(detailReason, getId(), getQuestTitle());
		qd.sendTaskLog(human, getId(), LogReasons.TaskLogReason.REASON_TASK_ACCEPT, detailReason);
		
		//发送成长任务的更新消息
		QuestInfo info = getDestInfos(human);
		if (canFinish(human, false)) {
			info.setStatus((short)QuestStatus.CANFINISH.getIndex());
		} else {
			info.setStatus((short)QuestStatus.ACCEPTED.getIndex());
		}
		qd.sendQuestUpdateMsg(info);
		
		//发送剧情触发消息
		StoryShowEvent event = new StoryShowEvent(human, StoryEventType.ACCEPT_QUEST, getId());
		Globals.getEventService().fireEvent(event);
		
		//LOG
		Globals.getLogService().sendQuestLog(human, QuestLogReason.ACCEPT, null, QuestLogReason.ACCEPT.getReason(), template.getId(), Globals.getTimeService().now());
		
		//返回成功
		return true;
	}
	
	/**
	 * 接受任务处理，子类扩展处理
	 * @param human
	 * @return
	 */
	protected abstract boolean acceptImpl(Human human);
	
	/**
	 * 判断任务是否可以完成，仅检查等级和任务完成条件是否满足
	 * @param human
	 * @param showError
	 * @return
	 */
	public final boolean canFinish(Human human, boolean showError) {
		//角色等级未达到最低等级要求时不可完成
		if (human.getLevel() < getFinishMinLevel()) {
			if (showError) {
				human.getPlayer().sendErrorPromptMessage(QuestLangConstants_40000.QUEST_LEVEL_NOT_REACH, getFinishMinLevel());
			}
			return false;
		}
		
		//任务必须在进行中才能完成
		if (!human.getQuestDiary().isDoingQuest(getId())) {
			if (showError) {
				human.getPlayer().sendErrorPromptMessage(QuestLangConstants_40000.QUEST_NOT_IN_PROCESS, getQuestTitle());
			}
			return false;
		}
		
		//逐个检查任务完成条件
		for (IQuestCondition c : getFinishConditionList()) {
			if (!c.isMeet(human, getId(), showError)) {
				return false;
			}
		}
		
		//检查扩展条件
		return canFinishImpl(human, showError);
	}
	
	/**
	 * 判断任务是否可以完成，子类扩展条件
	 * @param human
	 * @param showError
	 * @return
	 */
	protected abstract boolean canFinishImpl(Human human, boolean showError);
	
	/**
	 * 任务完成处理
	 * @param human
	 * @return
	 */
	public final boolean finish(Human human) {
		//首先判断任务必须可以完成
		if (!canFinish(human, true)) {
			return false;
		}
		
		//判断物品奖励是否可以领取
		if (!human.getInventory().checkSpace(getItemBonusList(), true)) {
			return false;
		}
		
		//逐个检查特殊任务奖励是否可以领取
		for (IQuestBonus bonus : getSpecialBonusList()) {
			if (!bonus.canGiveBonus(human, true)) {
				return false;
			}
		}
		
		//调用扩展处理
		if (!finishImpl(human)) {
			return false;
		}
		
		//完成任务后的各个完成条件的处理
		for (IQuestCondition c : getFinishConditionList()) {
			c.onFinish(human);
		}
		
		//从已接任务列表中删除任务，并刪除数据库中数据
		QuestDiary qd = human.getQuestDiary();
		DoingQuest doingQuest = qd.removeDoingQuest(getId());
		doingQuest.onDelete();
		
		//发放物品奖励
		human.getInventory().addAllItems(getItemBonusList(), ItemGenLogReason.QUEST_BONUS, Integer.toString(getId()), true);
		
		//发放金钱奖励
		if (getMoneyBonusCount() > 0) {
			human.giveMoney(getMoneyBonusCount(), getCurrency(), true, CurrencyLogReason.XITONG, Integer.toString(getId()));
		}
		
		//发放经验奖励
		if (getExpBonusCount() > 0) {
			human.addExp(getExpBonusCount());
		}
		
		//发放特殊奖励
		for (IQuestBonus bonus : getSpecialBonusList()) {
			bonus.giveBonus(human);
		}
		
		//调用扩展处理
		handleFinishedQuest(human, doingQuest.getStartTime());
		
		//任务Id={0}|任务名称={1}
		String detailReason = TaskLogReason.REASON_TASK_FINISH.getReasonText();
		detailReason = MessageFormat.format(detailReason, getId(), getQuestTitle());
		
		//记录日志
		qd.sendTaskLog(human, getId(), TaskLogReason.REASON_TASK_FINISH, detailReason);
		
		//发送任务完成消息
		qd.sendQuestFinishMsg(getId());
		
		//重新发送任务列表给客户端
		qd.sendQuestListMsg();
		
		//发送剧情触发消息
		StoryShowEvent event = new StoryShowEvent(human, StoryEventType.FINISH_QUEST, getId());
		Globals.getEventService().fireEvent(event);
		
		//LOG
		Globals.getLogService().sendQuestLog(human, QuestLogReason.COMPLETE, null, QuestLogReason.COMPLETE.getReason(), template.getId(), Globals.getTimeService().now());
				
		
		//返回成功
		return true;
	}
	
	/**
	 * 完成任务处理，子类扩展处理
	 * @param human
	 * @return
	 */
	protected abstract boolean finishImpl(Human human);
	
	/**
	 * 完成任务后处理保存信息
	 * @param human
	 */
	protected abstract void handleFinishedQuest(Human human, Timestamp startTime);
	
	/**
	 * 放弃任务处理
	 * @param human
	 * @return
	 */
	public final boolean giveUp(Human human) {
		//任务必须在进行中才能放弃
		if (!human.getQuestDiary().isDoingQuest(getId())) {
			human.sendErrorMessage(QuestLangConstants_40000.QUEST_NOT_IN_PROCESS, getQuestTitle());
			return false;
		}
		
		//调用扩展处理
		if (!giveUpImpl(human)) {
			return false;
		}
		
		//从已接任务列表中删除任务，并刪除数据库中数据
		QuestDiary qd = human.getQuestDiary();
		DoingQuest doing = qd.removeDoingQuest(getId());
		doing.onDelete();
		
		//发送成长任务的更新消息
		QuestInfo info = getDestInfos(human);
		info.setStatus((short)QuestStatus.CANACCEPT.getIndex());
		qd.sendQuestUpdateMsg(info);
		
		//LOG
		Globals.getLogService().sendQuestLog(human, QuestLogReason.CANCEL, null, QuestLogReason.CANCEL.getReason(), template.getId(), Globals.getTimeService().now());
				
		
		//返回成功
		return true;
	}
	
	/**
	 * 放弃任务处理，子类扩展处理
	 * @param human
	 * @return
	 */
	protected abstract boolean giveUpImpl(Human human);

	/**
	 * 根据任务完成条件初始化任务状态
	 * @param human
	 * @return
	 */
	public Map<QuestConditionType, Map<String, Integer>> initFinishCondition(Human human) {
		Map<QuestConditionType, Map<String, Integer>> params = new HashMap<QuestConditionType, Map<String, Integer>>();
		for (IQuestCondition c : getFinishConditionList()) {
			QuestConditionType type = c.getType();
			Map<String, Integer> map = params.get(type);
			if (map == null) {
				map = new HashMap<String, Integer>();
				params.put(type, map);
			}
			map.put(c.getParam(), c.initParam(human));
		}
		return params;
	}
	
	/**
	 * 获取任务当前状态
	 * @param human
	 * @return
	 */
	public QuestInfo getDestInfos(Human human) {
		QuestInfo info = new QuestInfo();
		info.setQuestId(getId());
		List<QuestDestInfo> list = new ArrayList<QuestDestInfo>();
		DoingQuest quest = human.getQuestDiary().getDoingQuest(getId());
		if (quest != null) {
			for (IQuestCondition cond : getFinishConditionList()) {
				QuestDestInfo qdi = cond.getDestInfo(human, getId());
				if (qdi != null) {
					list.add(qdi);
				}
			}
		}
		info.setDestInfo(list.toArray(new QuestDestInfo[]{}));
		return info;
	}
	
	public int getId() {
		return template.getQuestId();
	}
	
	public List<IQuestCondition> getAcceptConditionList() {
		return template.getAcceptConditionList();
	}
	
	public List<IQuestCondition> getFinishConditionList() {
		return template.getFinishConditionList();
	}
	
	public String getQuestTitle() {
		return template.getQuestTitle();
	}
	
	public QuestType getQuestTypeEnum() {
		return template.getQuestTypeEnum();
	}
	
	public int getAcceptMinLevel() {
		return template.getAcceptMinLevel();
	}
	
	public int getFinishMinLevel() {
		return template.getFinishMinLevel();
	}
	
	public int getQuestRegion() {
		return template.getQuestRegion();
	}
	
	public Currency getCurrency() {
		return template.getCurrency();
	}
	
	public List<ItemParam> getItemBonusList() {
		return template.getItemBonusList();
	}
	
	public List<IQuestBonus> getSpecialBonusList() {
		return template.getSpecialBonusList();
	}
	
	public int getMoneyBonusCount() {
		return template.getMoneyBonusCount();
	}
	
	public int getExpBonusCount() {
		return template.getExpBonusCount();
	}
	
	public String getPreQuestIds() {
		return template.getPreQuestIds();
	}
	
	public QuestTemplate getTemplate() {
		return template;
	}
}
