package com.pwrd.war.gameserver.story.handler;

import com.pwrd.war.gameserver.player.Player;
import com.pwrd.war.gameserver.story.msg.CGStoryList;

public class StoryMessageHandler {
	public void handleStoryList(Player player, CGStoryList cgStoryList) {
		player.getHuman().getStoryManager().sendStoriesList();
	}
}
