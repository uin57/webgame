package com.pwrd.war.gameserver.interactive.handler;

/**
 * 
 * 物品使用处理器工厂类
 * 
 * @author Yvon
 * @since 2010-3-26
 */
public class InteractiveHandlerFactory {

	private static final InteractiveMessageHandler handler = new InteractiveMessageHandler();

	public static InteractiveMessageHandler getHandler() {
		return handler;
	}
	
	

}
