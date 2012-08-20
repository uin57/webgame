package com.pwrd.war.gameserver.quest;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.slf4j.Logger;

import com.pwrd.war.common.LogReasons.TaskLogReason;
import com.pwrd.war.common.constants.Loggers;
import com.pwrd.war.common.model.quest.QuestInfo;
import com.pwrd.war.core.annotation.SyncIoOper;
import com.pwrd.war.core.orm.DataAccessException;
import com.pwrd.war.core.util.Assert;
import com.pwrd.war.core.util.ErrorsUtil;
import com.pwrd.war.core.util.LogUtils;
import com.pwrd.war.db.model.DoingQuestEntity;
import com.pwrd.war.db.model.FinishedQuestEntity;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.common.db.GameDaoService;
import com.pwrd.war.gameserver.common.db.RoleDataHolder;
import com.pwrd.war.gameserver.common.i18n.constants.QuestLangConstants_40000;
import com.pwrd.war.gameserver.common.log.GameErrorLogInfo;
import com.pwrd.war.gameserver.common.log.LogService;
import com.pwrd.war.gameserver.human.Human;
import com.pwrd.war.gameserver.human.JsonPropDataHolder;
import com.pwrd.war.gameserver.quest.DoingQuest.QuestStatus;
import com.pwrd.war.gameserver.quest.IQuest.QuestType;
import com.pwrd.war.gameserver.quest.condition.IQuestCondition.QuestConditionType;
import com.pwrd.war.gameserver.quest.msg.GCFinishQuest;
import com.pwrd.war.gameserver.quest.msg.GCQuestList;
import com.pwrd.war.gameserver.quest.msg.GCQuestUpdate;

/**
 * 任务日志,负责维护角色的已接任务及其进度，已完成任务等
 * 
 */
public class QuestDiary implements RoleDataHolder,JsonPropDataHolder {

	private final Logger logger = Loggers.questLogger;

	/** 任务日志所属玩家 */
	private Human owner;
	
	/** 正在做的任务映射表 */
	private Map<Integer, DoingQuest> doingQuestMap;
	/** 做过的任务映射表 */
	private Map<Integer, FinishedQuest> finishedQuestMap;
	/** 当前主线任务所在区域 */
	private int questRegion;

	/** 游戏数据管理器 */
	private GameDaoService gameDaoService;
	/** 日志管理器 */
	private LogService logService;

	/** 任务列表排序 */
	public final static Comparator<QuestInfo> questsorter = new QuestInfoSorter();
	
	public QuestDiary(Human owner, QuestMessageBuilder messageBuilder, GameDaoService gameDaoService, LogService logService) {
		Assert.notNull(owner);
		this.owner = owner;
		
		this.doingQuestMap = new HashMap<Integer, DoingQuest>();
		this.finishedQuestMap = new HashMap<Integer, FinishedQuest>();
		
		this.gameDaoService = gameDaoService;
		this.logService = logService;
		
		this.questRegion = 0;
	}
	
	/**
	 * 加载玩家的任务
	 */
	@SyncIoOper
	public void load() {
		final String _charId = this.getOwner().getUUID();
		try {
			//加载正在进行中的任务对象
			List<DoingQuestEntity> _doingQuests = this.gameDaoService.getDoingQuestDao().loadByCharId(_charId);
			if (_doingQuests != null) {
				for (DoingQuestEntity _doingQuest : _doingQuests) {
					int _questId = _doingQuest.getQuestId();
					AbstractQuest _questTemplate = Globals.getQuestService().getQuestById(_questId);
					if (_questTemplate != null) {
						DoingQuest quest = new DoingQuest(this.getOwner(), _questTemplate);
						quest.fromEntity(_doingQuest);
						quest.getLifeCycle().activate();
						this.doingQuestMap.put(_questId, quest);
					} else if (logger.isWarnEnabled()) {
						logger.warn(ErrorsUtil.error(GameErrorLogInfo.TASK_NO_EXIST, "", "Task Id=" + _questId));
					}
				}
			}
			
			//加载已经完成的任务
			List<FinishedQuestEntity> _finishedQuests = this.gameDaoService.getFinishedQuestDao().loadByCharId(_charId);
			if (_finishedQuests != null) {
				for (FinishedQuestEntity _finishedQuest : _finishedQuests) {
					int _questId = _finishedQuest.getQuestId();
					AbstractQuest _questTemplate = Globals.getQuestService().getQuestById(_questId);
					if (_questTemplate != null) {
						FinishedQuest quest = new FinishedQuest();
						quest.fromEntity(_finishedQuest);
						quest.setOwner(this.getOwner());
						quest.getLifeCycle().activate();
						this.finishedQuestMap.put(_questId, quest);
					} else if (logger.isWarnEnabled()) {
						logger.warn(ErrorsUtil.error(GameErrorLogInfo.TASK_NO_EXIST, "", "Task Id=" + _questId));
					}
				}
			}
			
		} catch (DataAccessException e) {
			if (logger.isErrorEnabled()) {
				logger.error(ErrorsUtil.error(GameErrorLogInfo.DB_OPERATE_FAIL, "#GS.QuestDiary.load", null), e);
			}
		}
	}
	
	@Override
	public void checkAfterRoleLoad() {
		//更新当前主线任务区域
		updateQuestRegion();
	}

	@Override
	public void checkBeforeRoleEnter() {
	}
	
	/**
	 * 设置当前进行中主线任务所在的区域
	 * @param region
	 */
	public void setCurrentRegion(int region) {
		this.questRegion = region;
	}
	
	/**
	 * 获取当前进行中主线任务所在的区域
	 * @return 无区域返回0
	 */
	public int getCurrentRegion() {
		return this.questRegion;
	}
	
	/**
	 * 获取人物
	 * 
	 * @return
	 */
	public Human getOwner() {
		return owner;
	}
	
	/**
	 * 发送任务列表消息
	 */
	public void sendQuestListMsg() {
		List<QuestInfo> list = new ArrayList<QuestInfo>();
		
		//遍历全部任务模板，判断任务是否在进行中或可以接受
		//TODO 可以优化
		for (AbstractQuest quest : Globals.getQuestService().getQuests()) {
			if (quest.canAccept(owner, false)) {
				QuestInfo info = quest.getDestInfos(owner);
				info.setStatus((short)QuestStatus.CANACCEPT.getIndex());
				list.add(info);
			} else if (doingQuestMap.containsKey(quest.getId())) {
				QuestInfo info = quest.getDestInfos(owner);
				if (quest.canFinish(owner, false)) {
					info.setStatus((short)QuestStatus.CANFINISH.getIndex());
				} else {
					info.setStatus((short)QuestStatus.ACCEPTED.getIndex());
				}
				list.add(info);
			}
		}
		
		//排序任务并发送消息
		Collections.sort(list, questsorter);
		GCQuestList gcCommonQuestList = new GCQuestList();
		gcCommonQuestList.setQuestInfos(list.toArray(new QuestInfo[0]));
		owner.sendMessage(gcCommonQuestList);
	}
	
	/**
	 * 接受任务
	 * @param questId
	 */
	public void acceptQuest(int questId) {
		AbstractQuest quest = Globals.getQuestService().getQuestById(questId);
		if (quest != null) {
			quest.accept(owner);
		} else {
			owner.getPlayer().sendErrorPromptMessage(QuestLangConstants_40000.QUEST_NOT_FOUND);
		}
	}
	
	/**
	 * 完成任务
	 * @param questId
	 */
	public void finishQuest(int questId) {
		AbstractQuest quest = Globals.getQuestService().getQuestById(questId);
		if (quest != null) {
			quest.finish(owner);
		} else {
			owner.getPlayer().sendErrorPromptMessage(QuestLangConstants_40000.QUEST_NOT_FOUND);
		}
	}
	
	/**
	 * 发送任务完成消息
	 * @param questId
	 */
	public void sendQuestFinishMsg(int questId) {
		GCFinishQuest gcFinishQuest = new GCFinishQuest(questId);
		owner.sendMessage(gcFinishQuest);
	}
	
	/**
	 * 放弃任务
	 * @param questId
	 */
	public void giveUpQuest(int questId) {
		AbstractQuest quest = Globals.getQuestService().getQuestById(questId);
		if (quest != null) {
			quest.giveUp(owner);
		} else {
			owner.getPlayer().sendErrorPromptMessage(QuestLangConstants_40000.QUEST_NOT_FOUND);
		}
	}
	
	/**
	 * 发送任务更新消息
	 * @param info
	 */
	public void sendQuestUpdateMsg(QuestInfo info) {
		GCQuestUpdate gcQuestUpdate = new GCQuestUpdate();
		gcQuestUpdate.setQuestInfo(info);
		owner.sendMessage(gcQuestUpdate);
	}
	
	/**
	 * 更新当前主线任务区域
	 */
	public void updateQuestRegion() {
		questRegion = 0;
		Iterator<Map.Entry<Integer, DoingQuest>> itor = doingQuestMap.entrySet().iterator();
		while (itor.hasNext()) {
			Map.Entry<Integer, DoingQuest> entry = itor.next();
			DoingQuest dq = entry.getValue();
			AbstractQuest template = dq.getTemplate();
			if (template.getQuestTypeEnum() == QuestType.MAIN) {
				questRegion = template.getQuestRegion();
			}
		}
	}
	
	/**
	 * 更新正在进行中任务状态
	 * @param type
	 * @param param
	 * @param count
	 */
	public void increaseDoingQuestCount(QuestConditionType type, String param, int count) {
		Iterator<Map.Entry<Integer, DoingQuest>> itor = doingQuestMap.entrySet().iterator();
		while (itor.hasNext()) {
			Map.Entry<Integer, DoingQuest> entry = itor.next();
			DoingQuest dq = entry.getValue();
			if (dq.increaseParamsCount(type, param, count)) {
				//任务状态发生变化，发送任务更新消息
				AbstractQuest template = dq.getTemplate();
				QuestInfo info = template.getDestInfos(owner);
				if (template.canFinish(owner, false)) {
					info.setStatus((short)QuestStatus.CANFINISH.getIndex());
				} else {
					info.setStatus((short)QuestStatus.ACCEPTED.getIndex());
				}
				owner.getQuestDiary().sendQuestUpdateMsg(info);
			}
		}
	}

	/**
	 * 得到指定任务中指定条件的当前完成情况
	 * @param questId
	 * @param type
	 * @param param
	 * @return
	 */
	public int getFinishedCount(int questId, QuestConditionType type, String param) {
		DoingQuest dQuest = doingQuestMap.get(questId);
		if (dQuest != null) {
			return dQuest.getCount(type, param);
		}
		return 0;
	}
	
	/**
	 * 移除正在进行中的任务状态
	 * @param questId
	 * @return
	 */
	public DoingQuest removeDoingQuest(int questId) {
		return doingQuestMap.remove(questId);
	}

	/**
	 * 获取正在进行中的任务状态，不移除
	 * @param questId
	 * @return
	 */
	public DoingQuest getDoingQuest(int questId) {
		return doingQuestMap.get(questId);
	}
	
	/**
	 * 增加正在进行中的任务
	 * @param quest
	 * @return
	 */
	public void addDoingQuest(DoingQuest quest) {
		if (!doingQuestMap.containsKey(quest.getQuestId())) {
			doingQuestMap.put(quest.getQuestId(), quest);
		}
	}

	/**
	 * 是否正在做一个任务
	 * @param questId
	 * @return
	 */
	public boolean isDoingQuest(int questId) {
		return doingQuestMap.containsKey(questId);
	}
	
	/**
	 * 判断一个任务是否在进行中并且可以完成了
	 * @param questId
	 * @return
	 */
	public boolean isQuestCanFinish(int questId) {
		AbstractQuest quest = Globals.getQuestService().getQuestById(questId);
		return (quest != null && quest.canFinish(owner, false));
	}

	/**
	 * 是否完成过某任务
	 * @param questId
	 * @return
	 */
	public boolean hasFinishedQuest(int questId) {
		return finishedQuestMap.containsKey(questId);
	}

	/**
	 * 获取已完成的任务
	 * @param questId
	 * @return
	 */
	public FinishedQuest getFinishedQuest(int questId) {
		return finishedQuestMap.get(questId);
	}

	/**
	 * 增加已完成任务
	 * @param quest
	 * @return
	 */
	public void addFinishedQuest(FinishedQuest quest) {
		finishedQuestMap.put(quest.getQuestId(), quest);
	}

	@Override
	public String toJsonProp() {
		JSONObject json = new JSONObject();		
		return json.toString();	
	}
	
	@Override
	public void loadJsonProp(String jsonStr) {
	}
	
	/**
	 * 发送任务日志
	 * @param human 玩家角色
	 * @param questID 任务ID
	 * @param stepID 步骤ID
	 * @param reason 日志产生原因
	 * @param detailReason 附加信息
	 */
	public void sendTaskLog(Human human, int questID, TaskLogReason reason, String detailReason) {
		try {
			logService.sendTaskLog(human, reason, detailReason, questID);
		} catch (Exception e) {
			Loggers.questLogger.error(LogUtils.buildLogInfoStr(human.getUUID(), "记录任务日志时出错"), e);
		}
	}
	
	/**
	 * 通过 GM 账号增加指定日常任务, <font color='#990000'>注意: 只能在 GiveQuestCmd 类中调用!</font>
	 * 
	 * @param questId 任务 Id
	 * 
	 */
	public void gmAddDailyQuest(int questId) {
	}
	
	private static class QuestInfoSorter implements Comparator<QuestInfo>, Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public int compare(QuestInfo o1, QuestInfo o2) {
			int questId1 = o1.getQuestId();
			int questId2 = o2.getQuestId();
			return questId1 - questId2;
		}		
	}
}
