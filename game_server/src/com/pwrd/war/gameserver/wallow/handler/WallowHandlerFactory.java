package com.pwrd.war.gameserver.wallow.handler;

public class WallowHandlerFactory {

	private static WallowMessageHandler handler = new WallowMessageHandler();

	public static WallowMessageHandler getHandler() {
		return handler;
	}
	
}
