package com.pwrd.war.gameserver.quest.condition;

import com.pwrd.war.common.model.quest.QuestDestInfo;
import com.pwrd.war.gameserver.common.i18n.constants.QuestLangConstants_40000;
import com.pwrd.war.gameserver.human.Human;
import com.pwrd.war.gameserver.quest.QuestDiary;

/**
 * 杀怪任务目标
 * @author zy
 *
 */
public class KillCondition implements IQuestCondition {
	private String monsterSn;
	private int count;
	
	public KillCondition(String sn, int count) {
		this.monsterSn = sn;
		this.count = count;
	}

	@Override
	public boolean isMeet(Human human, int questId, boolean showError) {
		//判断任务条件保存的杀怪数量是否满足
		QuestDiary diary = human.getQuestDiary();
		int amount = diary.getFinishedCount(questId, QuestConditionType.KILL, monsterSn);
		boolean result = (amount >= count);
		if (showError && !result) {
			human.getPlayer().sendErrorPromptMessage(QuestLangConstants_40000.QUEST_NO_ENOUGH_MONSTERS_KILLED);
		}
		return result;
	}

	@Override
	public void onAccept(Human human) {
	}

	@Override
	public void onFinish(Human human) {
	}

	@Override
	public int initParam(Human human) {
		return 0;
	}

	@Override
	public QuestConditionType getType() {
		return QuestConditionType.KILL;
	}

	@Override
	public String getParam() {
		return monsterSn;
	}

	@Override
	public QuestDestInfo getDestInfo(Human human, int questId) {
		int amount = human.getQuestDiary().getFinishedCount(questId, QuestConditionType.KILL, monsterSn);
		return new QuestDestInfo((short)QuestConditionType.KILL.getIndex(), monsterSn, (short)count, (short)amount);
	}

}
