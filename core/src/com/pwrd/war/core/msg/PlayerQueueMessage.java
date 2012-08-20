package com.pwrd.war.core.msg;


/**
 * 系统内产生,需要扔到玩家的消息队列中的消息,会在玩家所属线程 中进行处理
 * 
 * <pre>
 * 	没有实现此接口的消息会在GameServer的主线程中处理
 * </pre>
 * 
 * @see GameMessageProcessor
 */
public interface PlayerQueueMessage {
	
	/**
	 * 获得玩家角色的UUID
	 * 
	 * @return
	 */
	String getRoleUUID();
	
}
