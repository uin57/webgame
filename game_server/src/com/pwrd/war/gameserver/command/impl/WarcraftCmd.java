/**
 * 
 */
package com.pwrd.war.gameserver.command.impl;

import java.util.Arrays;

import com.pwrd.war.core.command.IAdminCommand;
import com.pwrd.war.core.session.ISession;
import com.pwrd.war.gameserver.command.CommandConstants;
import com.pwrd.war.gameserver.player.Player;
import com.pwrd.war.gameserver.startup.GameClientSession;
import com.pwrd.war.gameserver.warcraft.model.WarcraftInfo;

/**
 * 
 * 增加兵法cmd
 * @author dengdan
 *
 */
public class WarcraftCmd implements IAdminCommand<ISession> {

	@Override
	public void execute(ISession playerSession, String[] commands) {
		if (!(playerSession instanceof GameClientSession)) {
			return;
		}
		Player player = ((GameClientSession) playerSession).getPlayer();
		System.out.println(Arrays.toString(commands));
		/**
		 * 格式:!warcraft addWarcraft arg1(兵法id) 
		 * 		!warcraft addCoin arg1 (兵法碎片数量)
		 * 
		 */
		try {
			if(commands.length != 2){
				return;
			}
			String cmd = commands[0];
			if("addWarcraft".equals(cmd)){
				String warcraftId = commands[1];
				player.getHuman().getWarcraftInventory().addWarcraftToTemp(warcraftId,0,0);
			}
			if("addCoin".equals(cmd)){
				int count = Integer.parseInt(commands[1]);
				WarcraftInfo warcraftInfo = player.getHuman().getWarcraftInventory().getWarcraftInfo();
				if(warcraftInfo == null){
					return;
				}
				warcraftInfo.setWarcraftCoin(warcraftInfo.getWarcraftCoin() + count);
				warcraftInfo.setModified();
				player.getHuman().getWarcraftInventory().snapWarcraftInfo();
			}
		} catch (Exception e) {
			player.sendErrorMessage("错误的命令！");
			e.printStackTrace();
		}
	}

	@Override
	public String getCommandName() {
		return CommandConstants.GM_CMD_WARCRAFT;
	}

}
