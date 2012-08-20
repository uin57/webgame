package com.pwrd.war.gameserver.command.impl;

import com.pwrd.war.common.constants.NoticeTypes;
import com.pwrd.war.core.command.IAdminCommand;
import com.pwrd.war.core.session.ISession;
import com.pwrd.war.gameserver.command.CommandConstants;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.player.Player;
import com.pwrd.war.gameserver.startup.GameClientSession;

/**
 * 发送公告
 * 
 * 
 */
public class NoticeCmd implements IAdminCommand<ISession> {

	@Override
	public void execute(ISession playerSession, String[] commands) {

		if (!(playerSession instanceof GameClientSession)) {
			return;
		}
		Player player = ((GameClientSession) playerSession).getPlayer();
		if (commands.length != 1) {
			player.sendErrorMessage("error command!");
			return;
		}

		Globals.getNoticeService().sendNotice(NoticeTypes.system, commands[0]);
	}

	@Override
	public String getCommandName() {
		return CommandConstants.GM_CMD_NOTICE;
	}

}
