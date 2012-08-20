package com.pwrd.war.gameserver.scene;

import com.pwrd.war.gameserver.player.Player;

/**
 * 玩家离开场景后的回调接口
 * 
 */
public interface PlayerLeaveSceneCallback {
	/**
	 * 在玩家离开场景后调用
	 * 
	 * @param player
	 */
	public void afterLeaveScene(final Player player);
}