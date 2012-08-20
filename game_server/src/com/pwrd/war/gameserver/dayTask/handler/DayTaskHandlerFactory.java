package com.pwrd.war.gameserver.dayTask.handler;


public class DayTaskHandlerFactory {
	private static final DayTaskMessageHandler handler = new DayTaskMessageHandler();

	public static DayTaskMessageHandler getHandler() {
		return handler;
	}
}
