package com.pwrd.war.gameserver.common.db;

import com.pwrd.war.core.annotation.AsyncThreadOper;
import com.pwrd.war.core.annotation.MainThreadOper;

/**
 * 持有玩家角色数据的对象实现此接口
 * 
 * <pre>
 * 用于在玩家角色数据刚刚加载完、玩家进入游戏之前等事件点
 * 对玩家的数据进行合法性检查、预处理等操作
 * </pre>
 * 
 */
public interface RoleDataHolder {

	/**
	 * 当对象数据加载完毕后还未在主线程中时的预处理,即在异步IO线程中执行的预处理操作
	 */
	@AsyncThreadOper
	public abstract void checkAfterRoleLoad();

	/**
	 * 当对象数据进入游戏时的检查,即在主线程中执行的校验操作
	 */
	@MainThreadOper
	public abstract void checkBeforeRoleEnter();

}