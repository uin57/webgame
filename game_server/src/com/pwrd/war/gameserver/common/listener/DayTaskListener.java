package com.pwrd.war.gameserver.common.listener;

import com.pwrd.war.core.event.IEventListener;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.common.event.DayTaskEvent;
import com.pwrd.war.gameserver.human.Human;

public class DayTaskListener implements IEventListener<DayTaskEvent> {

	@Override
	public void fireEvent(DayTaskEvent event) {
		Human human = event.getInfo();
		String taskId = event.getTaskId();
		Globals.getDayTaskService().udateDayTask(human, taskId);
	}

}
