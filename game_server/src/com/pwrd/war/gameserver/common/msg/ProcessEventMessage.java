package com.pwrd.war.gameserver.common.msg;

import com.pwrd.war.gameserver.player.Player;

/**
 * 进程类消息的特殊接口
 * 
 * <pre>
 * 	在主消息处理器中进行特殊处理,分在线处理和离线处理两种处理路径
 * </pre>
 *
 */
public interface ProcessEventMessage {
	
	/**
	 * 获得玩家角色的UUID
	 * 
	 * @return
	 */
	String getRoleUUID();

	/**
	 * 如果玩家在线,设置Player
	 * @param player
	 */
	public void setPlayer(Player player);

}
