package com.pwrd.war.gameserver.player;

/**
 * 退出玩家某一状态之后的回调接口
 * 
 */
public interface PlayerStateExitCallback {
	/**
	 * 
	 */
	void onExitCurState();
}
