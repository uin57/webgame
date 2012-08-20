package com.pwrd.war.common.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * 由于玩家被动断线时的错误原因定义
 * 
 * 
 */
@Deprecated
public enum DisconnectReason {
	/** 正常断下线开 */
	NORMAL(0),
	/** 处理消息时异常 */
	HANDLE_MSG_EXCEPTION(1001),
	/** 心跳时异常 */
	HEARTBEAT_EXCEPTION(1002),
	/** 账号被人从另一个客户端登录 */
	LOGIN_ON_ANOTHER_CLIENT(1003),
	/** 角色加载完成时出现异常 */
	FINISH_LOAD_HUMAN_EXCEPTION(1004),
	/** 进入游戏失败 */
	ENTER_SERVER_FAIL(1005),
	/** 进入场景失败 */
	ENTER_SCENE_FAIL(1006),
	/** GM踢人  */
	GM_KICK(1007),
	/** 防沉迷阻止登录或者踢出 */
	WALLOW_KICK(1008),
	
	/** cookie登录验证失败 */
	COOKIE_VALID_FAIL(1009),
	
	;

	private DisconnectReason(int code) {
		this.code = (short) code;
	}

	public final short code;

	private static Map<Short, DisconnectReason> valueMap;

	static {
		valueMap = new HashMap<Short, DisconnectReason>();
		for (DisconnectReason value : DisconnectReason.values()) {
			valueMap.put(value.code, value);
		}
	}

	public static DisconnectReason valueOf(short code) {
		return valueMap.get(code);
	}
}
