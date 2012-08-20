package com.pwrd.war.gameserver.quest.handler;

import com.pwrd.war.gameserver.common.Globals;

/**
 *  玩家消息处理器提供类
 *
 */
public class QuestHandlerFactory {
	private static final QuestMessageHandler handler = new QuestMessageHandler(Globals.getOnlinePlayerService());

	public static QuestMessageHandler getHandler() {
		return handler;
	}
}
