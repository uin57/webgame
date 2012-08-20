package com.pwrd.war.gameserver.command.impl;

import java.util.Arrays;

import com.pwrd.war.core.command.IAdminCommand;
import com.pwrd.war.core.session.ISession;
import com.pwrd.war.gameserver.command.CommandConstants;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.player.Player;
import com.pwrd.war.gameserver.startup.GameClientSession;

public class ActivityCmd  implements IAdminCommand<ISession>{
	@Override
	public void execute(ISession playerSession, String[] commands) {
		int charge = 0;
		if (!(playerSession instanceof GameClientSession)) {
			return;
		}
		Player player = ((GameClientSession) playerSession).getPlayer();
		System.out.println(Arrays.toString(commands));
		try {
			String cmd = commands[0];
			String actId = commands[1];
			if("start".equals(cmd)){
				Globals.getActivityService().start(actId);
			}
			if("stop".equals(cmd)){
				Globals.getActivityService().stop(actId);
			}
			if("ready".equals(cmd)){
				Globals.getActivityService().ready(actId);
			}
		} catch (Exception e) {
			player.sendErrorMessage("错误的命令！");
			e.printStackTrace();
		}
	}

	@Override
	public String getCommandName() {
		return "act";
	}
}
