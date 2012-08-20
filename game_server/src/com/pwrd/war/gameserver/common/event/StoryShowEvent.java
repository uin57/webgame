package com.pwrd.war.gameserver.common.event;

import com.pwrd.war.core.event.IEvent;
import com.pwrd.war.gameserver.human.Human;
import com.pwrd.war.gameserver.story.StoryEventType;

public class StoryShowEvent implements IEvent<Human> {
	private Human human;
	private StoryEventType type;
	private String param;
	
	public StoryShowEvent(Human human, StoryEventType type, String param) {
		this.human = human;
		this.type = type;
		this.param = param;
	}
	
	public StoryShowEvent(Human human, StoryEventType type, int param) {
		this.human = human;
		this.type = type;
		this.param = Integer.toString(param);
	}

	public StoryEventType getType() {
		return type;
	}

	public String getParam() {
		return param;
	}

	@Override
	public Human getInfo() {
		return human;
	}

}
