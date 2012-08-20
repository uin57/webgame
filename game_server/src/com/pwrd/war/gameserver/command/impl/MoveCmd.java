package com.pwrd.war.gameserver.command.impl;

import java.util.Arrays;

import com.pwrd.war.core.command.IAdminCommand;
import com.pwrd.war.core.session.ISession;
import com.pwrd.war.gameserver.command.CommandConstants;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.player.Player;
import com.pwrd.war.gameserver.startup.GameClientSession;

public class MoveCmd implements IAdminCommand<ISession>{
	@Override
	public void execute(ISession playerSession, String[] commands) {
		if (!(playerSession instanceof GameClientSession)) {
			return;
		}
		Player player = ((GameClientSession) playerSession).getPlayer();
		System.out.println(Arrays.toString(commands));
		try {
			if(commands.length < 3)
			{
				return;
			}
			String sceneId = commands[0];
			if(sceneId.equals("0"))sceneId = player.getSceneId();
			int x = Integer.valueOf(commands[1]);
			int y = Integer.valueOf(commands[2]);
			
			Globals.getSceneService().onRelive(player, sceneId, x, y);
			
		} catch (Exception e) {
			player.sendErrorMessage("错误的命令！");
			e.printStackTrace();
		}
	}

	@Override
	public String getCommandName() {
		return CommandConstants.GM_CMD_MOVE;
	}

}
