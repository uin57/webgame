package com.pwrd.war.gameserver.command.impl;

import java.util.Arrays;

import com.pwrd.war.core.command.IAdminCommand;
import com.pwrd.war.core.session.ISession;
import com.pwrd.war.gameserver.item.handler.ItemHandlerFactory;
import com.pwrd.war.gameserver.item.msg.CGUseItem;
import com.pwrd.war.gameserver.player.Player;
import com.pwrd.war.gameserver.startup.GameClientSession;

public class UseItemCmd implements IAdminCommand<ISession>{
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
			short bagId = Short.valueOf(commands[0]);
			short index = Short.valueOf(commands[1]);
			CGUseItem msg = new CGUseItem();
			msg.setBagId(bagId);
			msg.setIndex(index);
			msg.setTargetUuid(player.getRoleUUID());
			msg.setWearId("");
			msg.setParams("");
			ItemHandlerFactory.getHandler().handleUseItem(player, msg);
			 
		} catch (Exception e) {
			player.sendErrorMessage("错误的命令！");
			e.printStackTrace();
		}
	}

	@Override
	public String getCommandName() {
		return "useitem";
	}

}
