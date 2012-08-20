package com.pwrd.war.robot.strategy.impl;

import com.pwrd.war.core.msg.IMessage;
import com.pwrd.war.gameserver.quest.msg.CGAcceptQuest;
import com.pwrd.war.robot.Robot;
import com.pwrd.war.robot.strategy.OnceExecuteStrategy;

public class QuestAcceptStrategy extends OnceExecuteStrategy {
	private int questId;

	public QuestAcceptStrategy(Robot robot, int delay) {
		super(robot, delay);
	}
	
	public QuestAcceptStrategy(Robot robot, int delay, int questId) {
		super(robot, delay);
		this.questId = questId;
	}

	@Override
	public void doAction() {
		System.out.println(">>> accept quest id=" + questId);
		CGAcceptQuest msg = new CGAcceptQuest(questId);
		getRobot().sendMessage(msg);
	}

	@Override
	public void onResponse(IMessage message) {
	}

}
