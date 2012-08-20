package com.pwrd.war.gameserver.story.handler;

public class StoryHandlerFactory {
	private static final StoryMessageHandler handler = new StoryMessageHandler();
	
	public static StoryMessageHandler getHandler() {
		return handler;
	}
}
