package com.pwrd.war.gameserver.tree.handler;

public class TreeHandlerFactory {
	private static final TreeMessageHandler handler = new TreeMessageHandler();
	
	public static TreeMessageHandler getHandler() {
		return handler;
	}
}
