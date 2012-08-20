package com.pwrd.war.gameserver.player.handler;

import com.pwrd.war.gameserver.common.Globals;

/**
 *  玩家消息处理器提供类
 *
 */
public class PlayerHandlerFactory {
	private static final PlayerMessageHandler handler = new PlayerMessageHandler(Globals.getOnlinePlayerService(),Globals.getLangService());

	public static PlayerMessageHandler getHandler() {
		return handler;
	}
}
