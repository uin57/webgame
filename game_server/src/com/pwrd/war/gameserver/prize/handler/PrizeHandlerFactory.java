package com.pwrd.war.gameserver.prize.handler;

public class PrizeHandlerFactory {
	
	private static final PrizeMessageHandler handler = new PrizeMessageHandler();

	public static PrizeMessageHandler getHandler() {
		return handler;
	}

}
