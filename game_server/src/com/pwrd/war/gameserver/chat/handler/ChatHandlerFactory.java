package com.pwrd.war.gameserver.chat.handler;


/**
 * 聊天消息处理器提供类
 * 
 */
public class ChatHandlerFactory {

	private static final ChatMessageHandler handler = new ChatMessageHandler();

	public static ChatMessageHandler getHandler() {
		return handler;
	}
}
