package com.pwrd.war.gameserver.command.impl;

import java.util.Arrays;

import com.pwrd.war.common.constants.Loggers;
import com.pwrd.war.core.command.IAdminCommand;
import com.pwrd.war.core.session.ISession;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.player.Player;
import com.pwrd.war.gameserver.startup.GameClientSession;
import com.pwrd.war.gameserver.team.TeamInfo;

public class TeamCmd implements IAdminCommand<ISession>{
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
			String subCmd = commands[0];
			if("create".equals(subCmd)){
				TeamInfo t = Globals.getTeamService().createTeam(player, player.getHuman().getName()+"的队伍", "");
				Loggers.msgLogger.info("队伍创建成功：uuid="+t.getUuid());
			}else if("join".equals(subCmd)){
				Globals.getTeamService().joinTeam(player, commands[1], "");
				Loggers.msgLogger.info("进入队伍成功");
			}else if("leave".equals(subCmd)){
				Globals.getTeamService().leaveTeam(player);
				Loggers.msgLogger.info("离开队伍成功");
			}
			 
		} catch (Exception e) {
			player.sendErrorMessage("错误的命令！");
			e.printStackTrace();
		}
	}

	@Override
	public String getCommandName() {
		return "team";
	}

}
