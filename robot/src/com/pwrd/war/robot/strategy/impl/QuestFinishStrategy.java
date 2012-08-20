package com.pwrd.war.robot.strategy.impl;

import com.pwrd.war.core.msg.IMessage;
import com.pwrd.war.gameserver.quest.msg.CGFinishQuest;
import com.pwrd.war.robot.Robot;
import com.pwrd.war.robot.strategy.OnceExecuteStrategy;

public class QuestFinishStrategy extends OnceExecuteStrategy {
	private int questId;

	public QuestFinishStrategy(Robot robot, int delay) {
		super(robot, delay);
	}
	
	public QuestFinishStrategy(Robot robot, int delay, int questId) {
		super(robot, delay);
		this.questId = questId;
	}

	@Override
	public void doAction() {
		System.out.println(">>> finish quest id=" + questId);
		CGFinishQuest msg = new CGFinishQuest(questId);
		getRobot().sendMessage(msg);
	}

	@Override
	public void onResponse(IMessage message) {
	}

}
