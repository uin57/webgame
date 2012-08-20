package com.pwrd.war.gameserver.quest;

import com.pwrd.war.gameserver.human.Human;
import com.pwrd.war.gameserver.quest.template.QuestTemplate;

public abstract class AbstractDailyQuest implements IQuest<QuestTemplate>{
	
	@Override
	public boolean canAccept(Human human, boolean showTip) {
		return false;
	}
	
	@Override
	public boolean accept(Human human) {
		return false;
	}
	
	@Override
	public boolean giveUp(Human human) {
		return false;
	}

	@Override
	public boolean canFinish(Human human, boolean showTip) {
		return false;
	}

	@Override
	public boolean finish(Human human) {
		return false;
	}

}
