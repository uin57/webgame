package com.pwrd.war.gameserver.quest;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.pwrd.war.core.time.TimeService;
import com.pwrd.war.core.util.KeyUtil;
import com.pwrd.war.core.util.TimeUtils;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.human.Human;
import com.pwrd.war.gameserver.quest.template.QuestTemplate;

/**
 * 维护玩家已完成任务列表
 * 
 * 
 */
public class FinishedQuestsManager implements IFinishedQuestManager {

	/** DB存储的已完成任务 */
	private final Map<Integer, FinishedQuest> inDbFinishedQuest;

	private TimeService timeService;

	public FinishedQuestsManager(TimeService timeService) {
		inDbFinishedQuest = new HashMap<Integer, FinishedQuest>();
		this.timeService = timeService;
	}

	@Override
	public boolean contains(int questId) {
		return inDbFinishedQuest.containsKey(questId);
	}

	@Override
	public void addFinishedQuest(FinishedQuest finishedQuest) {
		this.inDbFinishedQuest.put(finishedQuest.getQuestId(), finishedQuest);
	}

	@Override
	public void addFinishedQuest(DoingQuest quest, Human owner) {
		FinishedQuest finishedQuest = getFinishedQuest(quest.getQuestId());
		if (finishedQuest == null) {
			finishedQuest = new FinishedQuest();
			finishedQuest.setDbId(KeyUtil.UUIDKey());
			finishedQuest.setQuestId(quest.getQuestId());
			finishedQuest.setOwner(owner);
			finishedQuest.getLifeCycle().activate();
			finishedQuest.setModified();
			inDbFinishedQuest.put(finishedQuest.getQuestId(), finishedQuest);
		}

		finishedQuest.setStartTime(quest.getStartTime());

		// 需处理玩家一直在线时的情况，如果接任务与交任务不在同一天，则需要清零
		if (!TimeUtils.isSameDay(quest.getStartTime().getTime(), timeService
				.now())) {
			finishedQuest.setDailyTimes(0);
		}

		finishedQuest.setDailyTimes(finishedQuest.getDailyTimes() + 1);

		finishedQuest.setEndTime(new Timestamp(timeService.now()));
	}

	@Override
	public Collection<FinishedQuest> getFinishedQuest() {
		return this.inDbFinishedQuest.values();
	}

	@Override
	public FinishedQuest getFinishedQuest(int questId) {
		return inDbFinishedQuest.get(questId);
	}

	@Override
	public boolean canDo(int questId) {
		FinishedQuest quest = getFinishedQuest(questId);
		// 如果没有在已完成任务列表，则可做
		if (quest == null) {
			return true;
		}

		// 考虑每日任务
//		if (Globals.getTemplateService().isTemplateExist(questId,
//				QuestTemplate.class)) {
//			QuestTemplate questTmp = Globals.getTemplateService().get(questId,
//					QuestTemplate.class);
//			
//			if (!questTmp.isRepeat()) {
//				return false;
//			}
//
//			// 每日任务可做次数已够
//			if (questTmp.getDailyTimes() > 0
//					&& questTmp.getDailyTimes() <= quest.getDailyTimes()) {
//				return false;
//			}
//		}

//		// 前置任务需要全部完成
//		Set<Integer> preIds = Globals.getQuestService().getPreQuest(questId);
//		if (preIds != null) {
//			for (Integer id : preIds) {
//				if (getFinishedQuest(id) == null) {
//					return false;
//				}
//			}
//		}
		return true;
	}

}
