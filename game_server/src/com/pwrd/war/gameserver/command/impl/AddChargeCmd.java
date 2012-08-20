package com.pwrd.war.gameserver.command.impl;

import java.util.Arrays;

import com.pwrd.war.core.command.IAdminCommand;
import com.pwrd.war.core.session.ISession;
import com.pwrd.war.gameserver.command.CommandConstants;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.player.Player;
import com.pwrd.war.gameserver.startup.GameClientSession;

public class AddChargeCmd  implements IAdminCommand<ISession>{
	@Override
	public void execute(ISession playerSession, String[] commands) {
		int charge = 0;
		if (!(playerSession instanceof GameClientSession)) {
			return;
		}
		Player player = ((GameClientSession) playerSession).getPlayer();
		System.out.println(Arrays.toString(commands));
		try {
			if(commands.length < 1)
			{
				charge = 0;
			}else{
				charge = Integer.parseInt(commands[0]);
			}
			Globals.getVipService().charge(player.getHuman(), charge);
			//TODO 将player level升level级
			System.out.println(" 充值"+charge+"元宝级啦!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		} catch (Exception e) {
			player.sendErrorMessage("错误的命令！");
			e.printStackTrace();
		}
	}

	@Override
	public String getCommandName() {
		return CommandConstants.GM_CMD_ADD_CHARGE;
	}
}
