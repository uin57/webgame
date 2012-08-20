package com.pwrd.war.gameserver.command.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.pwrd.war.common.LogReasons.ItemGenLogReason;
import com.pwrd.war.core.command.IAdminCommand;
import com.pwrd.war.core.session.ISession;
import com.pwrd.war.gameserver.command.CommandConstants;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.human.wealth.Bindable.BindStatus;
import com.pwrd.war.gameserver.human.wealth.Bindable.Oper;
import com.pwrd.war.gameserver.item.ItemParam;
import com.pwrd.war.gameserver.item.template.ItemTemplate;
import com.pwrd.war.gameserver.player.Player;
import com.pwrd.war.gameserver.startup.GameClientSession;

public class RemovePetCmd implements IAdminCommand<ISession>{
	@Override
	public void execute(ISession playerSession, String[] commands) {
		if (!(playerSession instanceof GameClientSession)) {
			return;
		}
		Player player = ((GameClientSession) playerSession).getPlayer();
		System.out.println(Arrays.toString(commands));
		try {
			if(commands.length < 1)
			{
				return;
			}
			
			String name = commands[0];
			Globals.getPetService().removePet(player, name);
			System.out.println("删除武将" + name);
		} catch (Exception e) {
			player.sendErrorMessage("错误的命令！");
			e.printStackTrace();
		}
	}

	@Override
	public String getCommandName() {
		return CommandConstants.GM_CMD_REMOVE_PET;
	}

}
