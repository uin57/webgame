package com.pwrd.war.gameserver.item.handler;

/**
 * 
 * 物品使用处理器工厂类
 * 
 * @author Yvon
 * @since 2010-3-26
 */
public class ItemHandlerFactory {

	private static final ItemMessageHandler handler = new ItemMessageHandler();

	public static ItemMessageHandler getHandler() {
		return handler;
	}

}
