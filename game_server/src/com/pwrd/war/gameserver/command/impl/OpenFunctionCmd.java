package com.pwrd.war.gameserver.command.impl;

import java.util.Arrays;

import com.pwrd.war.core.command.IAdminCommand;
import com.pwrd.war.core.session.ISession;
import com.pwrd.war.gameserver.human.OpenFunction;
import com.pwrd.war.gameserver.player.Player;
import com.pwrd.war.gameserver.startup.GameClientSession;

public class OpenFunctionCmd  implements IAdminCommand<ISession>{
	@Override
	public void execute(ISession playerSession, String[] commands) {
		int charge = 0;
		if (!(playerSession instanceof GameClientSession)) {
			return;
		}
		Player player = ((GameClientSession) playerSession).getPlayer();
		System.out.println(Arrays.toString(commands));
		try {
			String funcid = commands[0]; 
			if(-1 == Integer.valueOf(funcid)){
				for(int i = 0; i <= 32; i++){
					String s = OpenFunction.open(player.getHuman().getPropertyManager().getOpenFunc(),
							i);
					player.getHuman().getPropertyManager().setOpenFunc(s);					
				}
				player.getHuman().snapChangedProperty(true);
			}else{
				String s = OpenFunction.open(player.getHuman().getPropertyManager().getOpenFunc(),
						Integer.valueOf(funcid));
				player.getHuman().getPropertyManager().setOpenFunc(s);
				player.getHuman().snapChangedProperty(true);
			}
			player.sendSystemMessage("开启功能成功");
		} catch (Exception e) {
			player.sendErrorMessage("错误的命令！");
			e.printStackTrace();
		}
	}

	@Override
	public String getCommandName() {
		return "openfunc";
	}
}
