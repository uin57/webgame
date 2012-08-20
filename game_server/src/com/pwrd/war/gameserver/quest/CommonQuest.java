package com.pwrd.war.gameserver.quest;

import java.sql.Timestamp;

import com.pwrd.war.core.util.KeyUtil;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.common.i18n.constants.QuestLangConstants_40000;
import com.pwrd.war.gameserver.human.Human;
import com.pwrd.war.gameserver.quest.IQuest.QuestType;
import com.pwrd.war.gameserver.quest.template.QuestTemplate;

/**
 * 普通任务（包括主线任务和支线任务）
 * 
 * 
 */
public class CommonQuest extends AbstractQuest {
	public CommonQuest(QuestTemplate template) {
		super(template);
	}
	
	/**
	 * 判断普通任务是否可以接受扩展条件，包括判断任务是否已经完成、是否进行中等
	 */
	@Override
	protected boolean canAcceptImpl(Human human, boolean showError) {
		//任务已经完成，不能再次接受
		if (human.getQuestDiary().hasFinishedQuest(getId())) {
			if (showError) {
				human.getPlayer().sendErrorPromptMessage(QuestLangConstants_40000.QUEST_IS_FINISHED, getQuestTitle());
			}
			return false;
		}
		
		return true;
	}

	/**
	 * 普通任务接受扩展处理
	 */
	@Override
	protected boolean acceptImpl(Human human) {
		return true;
	}

	/**
	 * 判断普通任务是否可以完成扩展条件，包括判断任务是否进行中等
	 */
	@Override
	protected boolean canFinishImpl(Human human, boolean showError) {
		return true;
	}

	/**
	 * 普通任务完成扩展处理
	 */
	@Override
	protected boolean finishImpl(Human human) {
		return true;
	}

	/**
	 * 判断普通任务是否可以放弃扩展条件，包括判断任务是否进行中等
	 */
	@Override
	protected boolean giveUpImpl(Human human) {
		return true;
	}

	/**
	 * 任务完成信息扩展处理，普通任务不需要进行处理
	 */
	@Override
	protected void handleFinishedQuest(Human human, Timestamp startTime) {
		//将任务添加到已完成任务列表中，如果已经存在则更新信息
		QuestDiary qd = human.getQuestDiary();
		FinishedQuest finishedQuest = qd.getFinishedQuest(getId());
		if (finishedQuest == null) {
			finishedQuest = new FinishedQuest();
			finishedQuest.setDbId(KeyUtil.UUIDKey());
			finishedQuest.setQuestId(getId());
			finishedQuest.setOwner(human);
			finishedQuest.getLifeCycle().activate();
			finishedQuest.setModified();
			qd.addFinishedQuest(finishedQuest);
		}
	
		//更新任务开始结束时间
		finishedQuest.setStartTime(startTime);
		finishedQuest.setEndTime(new Timestamp(Globals.getTimeService().now()));
		
		//完成的任务如果为主线任务，则更新当前主线任务区域
		if (getQuestTypeEnum() == QuestType.MAIN) {
			qd.updateQuestRegion();
		}
	}

}
