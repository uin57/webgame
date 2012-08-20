package com.pwrd.war.gameserver.common.event;

import com.pwrd.war.core.event.IEvent;
import com.pwrd.war.gameserver.human.Human;

public class DayTaskEvent implements IEvent<Human> {
	private Human human;
	private String taskId;
	
	public DayTaskEvent(Human human, String taskId) {
		this.human = human;
		this.taskId = taskId;
	}

	public String getTaskId() {
		return taskId;
	}

	@Override
	public Human getInfo() {
		return human;
	}

}
