package com.pwrd.war.gameserver.command.impl;

import java.util.Arrays;

import com.pwrd.war.common.LogReasons.CurrencyLogReason;
import com.pwrd.war.common.LogReasons.MoneyLogReason;
import com.pwrd.war.core.command.IAdminCommand;
import com.pwrd.war.core.session.ISession;
import com.pwrd.war.gameserver.currency.Currency;
import com.pwrd.war.gameserver.currency.CurrencyCostType;
import com.pwrd.war.gameserver.player.Player;
import com.pwrd.war.gameserver.startup.GameClientSession;

public class AddMoneyCmd implements IAdminCommand<ISession>{
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
			int currencyId = Integer.valueOf(commands[0]);
			int num = Integer.valueOf(commands[1]);
			if(num > 0){
				player.getHuman().giveMoney(num, Currency.valueOf(currencyId),true, CurrencyLogReason.GM, CurrencyLogReason.GM.getReasonText());
			} else {
				player.getHuman().costMoney(-num, Currency.valueOf(currencyId),
						null, true, 0, CurrencyLogReason.GM,
						CurrencyLogReason.GM.getReasonText(), 0);
			}
			 
		} catch (Exception e) {
			player.sendErrorMessage("错误的命令！");
			e.printStackTrace();
		}
	}

	@Override
	public String getCommandName() {
		return "addmoney";
	}

}
