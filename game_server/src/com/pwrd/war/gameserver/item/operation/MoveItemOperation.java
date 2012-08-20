package com.pwrd.war.gameserver.item.operation;

import com.pwrd.war.gameserver.human.Human;
import com.pwrd.war.gameserver.item.Item;
import com.pwrd.war.gameserver.pet.Pet;

/**
 * 移动道具的处理策略接口，实现此接口的对象应该可以被公用的无状态，可以被所有请求者公用。
 * 
 * 
 */
public interface MoveItemOperation {

	/**
	 * 执行移动操作
	 * 
	 * @param user
	 *            操作者
	 * @param from
	 *            源道具
	 * @param to
	 *            目标道具
	 * @return 如果移动成功返回true，否则返回false
	 */
	boolean move(Human user, Item from, Item to);
	
	
	
	boolean move(Human user,Pet wearer, Item from , Item to);
	
}
