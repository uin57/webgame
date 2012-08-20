package com.pwrd.war.gameserver.common.handler;

/**
 * 管理各模块通用消息处理器实例
 * 
 */
public class CommonHandlerFactory {
	private static CommonMessageHandler handler = new CommonMessageHandler();

	public static CommonMessageHandler getHandler() {
		return handler;
	}
}
