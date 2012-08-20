package com.pwrd.war.gameserver.quest;

import java.util.Collection;

import com.pwrd.war.gameserver.human.Human;

/**
 * 已经做过的任务
 * 
 * 
 */
public interface IFinishedQuestManager {

	/**
	 * 是否包含一个任务
	 * 
	 * @param questId
	 * @return
	 */
	public boolean contains(int questId);

	/**
	 * 向DB中添加已完成任务列表中任务
	 * 
	 * @param finishedQuest
	 */
	public void addFinishedQuest(FinishedQuest finishedQuest);


	/**
	 * 取得DB中玩家的历史任务
	 * 
	 * @return
	 */
	public Collection<FinishedQuest> getFinishedQuest();

	/**
	 * 获取一个已完成任务
	 * 
	 * @param questId
	 * @return
	 */
	public FinishedQuest getFinishedQuest(int questId);

	/**
	 * 根据任务ID，将其设置成已完成任务
	 * 
	 * @param quest
	 * @param owner
	 */
	public void addFinishedQuest(DoingQuest quest, Human owner);

	/**
	 * 判断该已完成的任务是否可以接着做
	 * 
	 * @param questId
	 * @return
	 */
	public boolean canDo(int questId);
}
