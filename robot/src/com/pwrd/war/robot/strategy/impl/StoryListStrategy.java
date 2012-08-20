package com.pwrd.war.robot.strategy.impl;

import com.pwrd.war.core.msg.IMessage;
import com.pwrd.war.gameserver.story.msg.CGStoryList;
import com.pwrd.war.robot.Robot;
import com.pwrd.war.robot.strategy.OnceExecuteStrategy;

public class StoryListStrategy extends OnceExecuteStrategy {


	public StoryListStrategy(Robot robot, int delay) {
		super(robot, delay);
	}

	@Override
	public void doAction() {
		CGStoryList msg = new CGStoryList();
		getRobot().sendMessage(msg);
	}

	@Override
	public void onResponse(IMessage message) {
	}

}
