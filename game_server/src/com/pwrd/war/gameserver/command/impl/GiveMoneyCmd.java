package com.pwrd.war.gameserver.command.impl;

import com.pwrd.war.common.LogReasons.CurrencyLogReason;
import com.pwrd.war.common.LogReasons.MoneyLogReason;
import com.pwrd.war.core.command.IAdminCommand;
import com.pwrd.war.core.session.ISession;
import com.pwrd.war.gameserver.command.CommandConstants;
import com.pwrd.war.gameserver.currency.Currency;
import com.pwrd.war.gameserver.currency.CurrencyCostType;
import com.pwrd.war.gameserver.player.Player;
import com.pwrd.war.gameserver.startup.GameClientSession;

/**
 * 给金钱的debug命令
 * 
 * 
 */

public class GiveMoneyCmd implements IAdminCommand<ISession> {

	@Override
	public void execute(ISession playerSession, String[] commands) {
		if (!(playerSession instanceof GameClientSession)) {
			return;
		}
		Player player = ((GameClientSession) playerSession).getPlayer();
		try {
			Currency currency = Currency.valueOf(Integer.parseInt(commands[0]));
			int amount = Integer.parseInt(commands[1]);
			player.getHuman().giveMoney(amount, currency, true,
					CurrencyLogReason.GM, CurrencyLogReason.GM.getReasonText());
		} catch (Exception e) {
			e.printStackTrace();
			player.sendErrorMessage(e.getMessage());
		}

	}

	@Override
	public String getCommandName() {
		return CommandConstants.GM_CMD_GIVE_MONEY;
	}

}
