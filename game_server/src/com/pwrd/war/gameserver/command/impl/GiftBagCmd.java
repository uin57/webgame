/**
 * 
 */
package com.pwrd.war.gameserver.command.impl;

import java.util.Arrays;

import com.pwrd.war.core.command.IAdminCommand;
import com.pwrd.war.core.session.ISession;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.player.Player;
import com.pwrd.war.gameserver.startup.GameClientSession;

/**
 * 礼包cmd
 * @author dendgan
 *
 */
public class GiftBagCmd implements IAdminCommand<ISession> {

	@Override
	public void execute(ISession playerSession, String[] commands) {
		if (!(playerSession instanceof GameClientSession)) {
			return;
		}
		Player player = ((GameClientSession) playerSession).getPlayer();
		System.out.println(Arrays.toString(commands));
		/**
		 * 格式:!giftbag add arg1 
		 * arg1:礼包id
		 */
		try {
			String giftId = commands[0];
			Globals.getGiftBagService().addGiftBag(player.getHuman().getCharId(), giftId);
		} catch (Exception e) {
			player.sendErrorMessage("错误的命令！");
			e.printStackTrace();
		}
	}

	@Override
	public String getCommandName() {
		return "addgiftbag";
	}
}
