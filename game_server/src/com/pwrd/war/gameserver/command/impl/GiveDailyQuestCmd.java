package com.pwrd.war.gameserver.command.impl;

import com.pwrd.war.core.command.IAdminCommand;
import com.pwrd.war.core.session.ISession;
import com.pwrd.war.gameserver.command.CommandConstants;
import com.pwrd.war.gameserver.human.Human;
import com.pwrd.war.gameserver.player.Player;
import com.pwrd.war.gameserver.quest.QuestDiary;
import com.pwrd.war.gameserver.startup.GameClientSession;

/**
 * 领取任务
 * 
 * @author haijiang.jin
 *
 */
public class GiveDailyQuestCmd implements IAdminCommand<ISession> {
	@Override
	public void execute(ISession sess, String[] cmds) {
	}

	@Override
	public String getCommandName() {
		return CommandConstants.GM_CMD_GIVE_DAILY_QUEST;
	}
}
