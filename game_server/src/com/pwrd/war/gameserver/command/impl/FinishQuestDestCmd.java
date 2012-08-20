package com.pwrd.war.gameserver.command.impl;

import com.pwrd.war.core.command.IAdminCommand;
import com.pwrd.war.core.session.ISession;

/**
 * 完成指定任务的所有目标
 * 
 */
public class FinishQuestDestCmd implements IAdminCommand<ISession> {

	@Override
	public void execute(ISession playerSession, String[] commands) {
	}

	@Override
	public String getCommandName() {
		return "finishquestdest";
	}

}
