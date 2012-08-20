package com.pwrd.war.gameserver.command.impl;

import com.pwrd.war.core.command.IAdminCommand;
import com.pwrd.war.core.session.ISession;
import com.pwrd.war.gameserver.command.CommandConstants;
import com.pwrd.war.gameserver.human.Human;
import com.pwrd.war.gameserver.player.Player;
import com.pwrd.war.gameserver.startup.GameClientSession;

public class ClearMoneyCmd implements IAdminCommand<ISession>{
	
	@Override
	public void execute(ISession sess, String[] commands) {
		if (!(sess instanceof GameClientSession)) {
			return;
		}
		
		Player player = ((GameClientSession)sess).getPlayer();
		
		if (player == null) {
			return;
		}

		// 获取当前玩家角色
		Human currHuman = player.getHuman();

		if (currHuman == null) {
			return;
		}
		
//		currHuman.setGold(0);
		
		currHuman.snapChangedProperty(true);		
	}
	
	@Override
	public String getCommandName() {
		return CommandConstants.GM_CMD_CLEAR_MONEY;
	}

}
