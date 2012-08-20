package com.pwrd.war.gameserver.quest.bonus;

import com.pwrd.war.gameserver.human.Human;

/**
 * 
 * 任务奖励
 *
 */
public interface IQuestBonus {
	
	/**
	 * 发奖励
	 * @param human
	 * @return
	 */
	boolean giveBonus(Human human);
	
	/**
	 * 是否能够给奖励
	 * @param human
	 * @return
	 */
	boolean canGiveBonus(Human human, boolean showError);

}
