package com.pwrd.war.gameserver.human.condition;

import com.pwrd.war.gameserver.human.Human;


/**
 * 
 * 用于检查一个Human是否满足一定的条件
 * 
 * 
 */
public interface IHumanCondition {

	/**
	 * 检查一个Human是否满足一定的条件
	 * 
	 * @param human
	 * @return
	 */
	boolean evaluate(Human human);
	
	/**
	 * @param human
	 * 
	 * @return 返回错误提示
	 */
	String getTip(Human human);
	
	/**
	 * 检查数据完整性
	 *
	 * @warning 需要在系统的所有数据被生成后调用
	 * @return
	 */
	void check();
}
