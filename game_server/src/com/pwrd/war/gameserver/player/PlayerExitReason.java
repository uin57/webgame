package com.pwrd.war.gameserver.player;

/**
 * 玩家退出服务器原因定义
 * 
 */
public enum PlayerExitReason {
	/** 玩家点击退出或直接断开连接 */
	LOGOUT,
	/** GS停机 */
	SERVER_SHUTDOWN,
	/** 服务器处理出错 */
	SERVER_ERROR,
	/** 服务器人数已满 */
	SERVER_IS_FULL,
	/** GM踢掉 */
	GM_KICK,
	/** 自己把自己踢掉 */
	MULTI_LOGIN,
	/** 防沉迷阻止登录或者踢出 */
	WALLOW_KICK,
	/** GS踢人*/
	SERVER_KICK,
	
	
	;
	
	
}
