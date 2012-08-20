package com.pwrd.war.gameserver.quest;

import java.util.ArrayList;
import java.util.List;

import com.pwrd.war.db.model.DoingQuestEntity;
import com.pwrd.war.db.model.FinishedQuestEntity;

/**
 * 加载玩家任务数据时保存无效的数据，包括已完成任务和正执行任务
 * 
 * 
 */
public class InvalidQuestInfo {
	
	/** 无效的正执行任务信息 */
	private List<DoingQuestEntity> invalidDoingQuests = null;
	/** Db中记录的无效已完成任务信息 */
	private List<FinishedQuestEntity> invalidDbFinishedQuests = null;


	/**
	 * 添加无效的正执行任务信息
	 * 
	 * @param invalidDoingQuest
	 */
	public void addInvalidDoingQuest(DoingQuestEntity invalidDoingQuest) {
		if (invalidDoingQuests == null) {
			invalidDoingQuests = new ArrayList<DoingQuestEntity>();
		}
		invalidDoingQuests.add(invalidDoingQuest);
	}

	/**
	 * 添加Db中记录的无效已完成任务信息
	 * 
	 * @param invalidFinishQuest
	 */
	public void addInvalidDbFinishedQuest(FinishedQuestEntity invalidFinishedQuest) {
		if (invalidDbFinishedQuests == null) {
			invalidDbFinishedQuests = new ArrayList<FinishedQuestEntity>();
		}
		invalidDbFinishedQuests.add(invalidFinishedQuest);
	}

	/**
	 * 判断是否需要删除
	 * 
	 * @return
	 */
	public boolean isNeedToDel() {
		if (invalidDoingQuests == null && invalidDbFinishedQuests == null) {
			return false;
		}
		return true;
	}

	/**
	 * 清除无效任务数据
	 */
	public void clearInvalidQuestInfo() {
		this.invalidDbFinishedQuests = null;
		this.invalidDoingQuests = null;
	}

	public List<DoingQuestEntity> getInvalidDoingQuests() {
		return invalidDoingQuests;
	}


	public List<FinishedQuestEntity> getInvalidDbFinishedQuests() {
		return invalidDbFinishedQuests;
	}
	
	/**
	 * 无效任务类型
	 * 
	 * 
	 */
	public static enum InvalidQuestType {
		/** 无效正执行任务 */
		INVALID_DOING_QUEST(0),
		/** DB无效已完成任务 */
		INVALID_DB_FINISH_QUEST(1);

		private final int index;

		private InvalidQuestType(int index) {
			this.index = index;
		}

		public int getIndex() {
			return this.index;
		}
	}

}
