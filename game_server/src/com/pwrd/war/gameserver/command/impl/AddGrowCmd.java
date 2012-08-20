/**
 * 
 */
package com.pwrd.war.gameserver.command.impl;

import java.util.Arrays;

import com.pwrd.war.core.command.IAdminCommand;
import com.pwrd.war.core.session.ISession;
import com.pwrd.war.gameserver.player.Player;
import com.pwrd.war.gameserver.startup.GameClientSession;

/**
 * 成长cmd命令
 * 
 * @author dengdan
 *
 */
public class AddGrowCmd implements IAdminCommand<ISession> {

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
			int num = Integer.valueOf(commands[0]);
			if(num > 0){
				int maxGrow = player.getHuman().getMaxGrow();
				int grow = player.getHuman().getGrow();
				if(num + grow >= maxGrow){
					player.getHuman().setGrow(maxGrow);
					player.getHuman().calcProps();
				}else{
					player.getHuman().setGrow(num + grow);
					player.getHuman().calcProps();
				}
				player.getHuman().snapChangedProperty(true);
			} 
		} catch (Exception e) {
			player.sendErrorMessage("错误的命令！");
			e.printStackTrace();
		}
	}

	@Override
	public String getCommandName() {
		return "addgrow";
	}
}
