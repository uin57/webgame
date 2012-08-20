package com.pwrd.war.robot.strategy.impl;

import com.pwrd.war.core.msg.IMessage;
import com.pwrd.war.gameserver.quest.msg.CGQuestList;
import com.pwrd.war.robot.Robot;
import com.pwrd.war.robot.strategy.OnceExecuteStrategy;

public class QuestStrategy extends OnceExecuteStrategy {


	public QuestStrategy(Robot robot, int delay) {
		super(robot, delay);
	}

	@Override
	public void doAction() {
		CGQuestList msg = new CGQuestList();
		getRobot().sendMessage(msg);
	}

	@Override
	public void onResponse(IMessage message) {
	}

}
