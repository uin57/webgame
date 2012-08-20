package com.pwrd.war.gameserver.qiecuo.handler;

/**
 * 管理各模块通用消息处理器实例
 * 
 */
public class QiecuoHandlerFactory {
	private static QieCuoMessageHandler handler = new QieCuoMessageHandler();

	public static QieCuoMessageHandler getHandler() {
		return handler;
	}
}
