package com.pwrd.war.gameserver.command.impl;

import com.pwrd.war.core.command.IAdminCommand;
import com.pwrd.war.core.session.ISession;

/**
 * 完成日常任务的所有目标
 * 
 */
public class FinishDailyQuestDestCmd implements IAdminCommand<ISession> {

	@Override
	public void execute(ISession playerSession, String[] commands) {
	}

	@Override
	public String getCommandName() {
		return "finishdailyquest";
	}

}
