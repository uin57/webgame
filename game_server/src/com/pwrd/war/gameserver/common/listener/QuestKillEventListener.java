package com.pwrd.war.gameserver.common.listener;

import java.util.List;

import com.pwrd.war.core.event.IEventListener;
import com.pwrd.war.gameserver.common.event.QuestKillEvent;
import com.pwrd.war.gameserver.common.event.QuestKillEvent.KillInfo;
import com.pwrd.war.gameserver.human.Human;
import com.pwrd.war.gameserver.quest.QuestDiary;
import com.pwrd.war.gameserver.quest.condition.IQuestCondition.QuestConditionType;

public class QuestKillEventListener implements IEventListener<QuestKillEvent> {

	@Override
	public void fireEvent(QuestKillEvent event) {
		Human human = event.getInfo();
		List<KillInfo> infos = event.getList();
		QuestDiary quest = human.getQuestDiary();
		for (KillInfo info : infos) {
			quest.increaseDoingQuestCount(QuestConditionType.KILL, info.getMonsterSn(), info.getCount());
		}
	}

}
