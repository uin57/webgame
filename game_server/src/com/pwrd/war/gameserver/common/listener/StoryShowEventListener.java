package com.pwrd.war.gameserver.common.listener;

import com.pwrd.war.core.event.IEventListener;
import com.pwrd.war.gameserver.common.event.StoryShowEvent;
import com.pwrd.war.gameserver.human.Human;

public class StoryShowEventListener implements IEventListener<StoryShowEvent> {

	@Override
	public void fireEvent(StoryShowEvent event) {
		Human human = event.getInfo();
		human.getStoryManager().triggerStory(event.getType(), event.getParam());
	}

}
