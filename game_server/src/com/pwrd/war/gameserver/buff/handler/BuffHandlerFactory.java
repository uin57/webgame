package com.pwrd.war.gameserver.buff.handler;


public class BuffHandlerFactory {
	
	private static final BufferMessageHandler handler = new BufferMessageHandler();
	
	public static BufferMessageHandler getHandler() {
		return handler;
	}

}
