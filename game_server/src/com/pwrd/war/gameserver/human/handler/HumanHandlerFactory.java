package com.pwrd.war.gameserver.human.handler;


public class HumanHandlerFactory {
	private static final HumanMessageHandler handler = new HumanMessageHandler();

	public static HumanMessageHandler getHandler() {
		return handler;
	}
}
