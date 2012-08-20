package com.pwrd.war.gameserver.common.thread;

/**
 * 处理一个玩家的消息时，对其他玩家数据的操作
 * 因为此玩家可能与本玩家不在一个场景线程，为了保证线程安全，必须将所有处理作为内部消息抛入此玩家的消息线程中
 * 另外此有可能处于在线或离线状态，两种状态的处理方式也是不一样的
 * @author yue.yan
 *
 */
public interface IOtherRoleOperation {

	/**
	 * 获得操作的玩家的UUID
	 * @return
	 */
	public String getRoleUUID();
	
	/**
	 * 如果此玩家在线，对其进行的操作
	 */
	public void onlineAction();
	
	/**
	 * 如果此玩家不在线，对其进行的操作
	 */
	public void offlineAction();
}
