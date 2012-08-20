package com.pwrd.war.gameserver.vocation.handler;


public class VocationHandlerFactory {
	
	private static final VocationMessageHandler handler = new VocationMessageHandler();

	public static VocationMessageHandler getHandler() {
		return handler;
	}

}
