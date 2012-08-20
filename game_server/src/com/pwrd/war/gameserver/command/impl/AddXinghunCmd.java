package com.pwrd.war.gameserver.command.impl;

import java.util.Arrays;

import com.pwrd.war.common.LogReasons.ItemGenLogReason;
import com.pwrd.war.core.command.IAdminCommand;
import com.pwrd.war.core.session.ISession;
import com.pwrd.war.gameserver.command.CommandConstants;
import com.pwrd.war.gameserver.human.wealth.Bindable.BindStatus;
import com.pwrd.war.gameserver.player.Player;
import com.pwrd.war.gameserver.startup.GameClientSession;

public class AddXinghunCmd implements IAdminCommand<ISession>{
	@Override
	public void execute(ISession playerSession, String[] commands) {
		if (!(playerSession instanceof GameClientSession)) {
			return;
		}
		Player player = ((GameClientSession) playerSession).getPlayer();
		System.out.println(Arrays.toString(commands));
		try {
			if(commands.length < 2)
			{
				return;
			}
			String  xinghunSn= commands[0];
			int num = Integer.valueOf(commands[1]);
			
			for(int i = 0; i < num; i++){
				player.getHuman().getInventory().addItem(xinghunSn, 1, BindStatus.BIND_YET, ItemGenLogReason.ITEM_GIFT_GET, "GM", true);
			}
		} catch (Exception e) {
			player.sendErrorMessage("错误的命令！");
			e.printStackTrace();
		}
	}

	@Override
	public String getCommandName() {
		return CommandConstants.GM_CMD_XINGHUN;
	}

}
