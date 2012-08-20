package com.pwrd.war.robot.strategy.impl;

import com.pwrd.war.common.model.quest.QuestInfo;
import com.pwrd.war.core.msg.IMessage;
import com.pwrd.war.gameserver.quest.msg.CGGiveUpQuest;
import com.pwrd.war.gameserver.quest.msg.GCQuestList;
import com.pwrd.war.gameserver.quest.msg.GCQuestUpdate;
import com.pwrd.war.robot.Robot;
import com.pwrd.war.robot.strategy.OnceExecuteStrategy;

public class QuestGiveUpStrategy extends OnceExecuteStrategy {


	public QuestGiveUpStrategy(Robot robot, int delay) {
		super(robot, delay);
	}

	@Override
	public void doAction() {
		int questId = 111000001;
		System.out.println(">>> give up quest id=" + questId);
		CGGiveUpQuest msg = new CGGiveUpQuest(questId);
		getRobot().sendMessage(msg);
	}

	@Override
	public void onResponse(IMessage message) {
		if(message instanceof GCQuestList){
			GCQuestList list = (GCQuestList) message;
			System.out.println(">>> GCQuestList size=" + list.getQuestInfos().length);
		} else if (message instanceof GCQuestUpdate) {
			GCQuestUpdate update = (GCQuestUpdate) message;
			QuestInfo info = update.getQuestInfo();
			System.out.println(">>> GCQuestUpdate id=" + info.getQuestId() + ", status=" + info.getStatus());
		}
	}

}
