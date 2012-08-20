package com.pwrd.war.gameserver.command.impl;

import java.util.Arrays;

import com.pwrd.war.core.command.IAdminCommand;
import com.pwrd.war.core.session.ISession;
import com.pwrd.war.core.util.TimeUtils;
import com.pwrd.war.gameserver.command.CommandConstants;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.human.Human;
import com.pwrd.war.gameserver.player.Player;
import com.pwrd.war.gameserver.startup.GameClientSession;
import com.pwrd.war.gameserver.tree.HumanTreeInfo;
import com.pwrd.war.gameserver.tree.TreeInfoManager;

public class TreeShakeCmd  implements IAdminCommand<ISession>{
	@Override
	public void execute(ISession playerSession, String[] commands) {
		int times = 0;
		if (!(playerSession instanceof GameClientSession)) {
			return;
		}
		Player player = ((GameClientSession) playerSession).getPlayer();
		System.out.println(Arrays.toString(commands));
		try {
			if(commands.length < 1)
			{
				times = 0;
			}else{
				times = Integer.parseInt(commands[0]);
			}
			
			Human human = player.getHuman();
			TreeInfoManager tim = human.getTreeInfoManager();
			HumanTreeInfo info = tim.getTreeInfos().get(human.getUUID());
					
			if(info == null){
				//如果没有数据 首先加入数据
				info = tim.creatHumanTreeInfo();
			}
			
			info.setShakeTimes(times);
			info.setModified();
			
			Globals.getTreeService().getTreeInfo(player);
		} catch (Exception e) {
			player.sendErrorMessage("错误的命令！");
			e.printStackTrace();
		}
	}

	@Override
	public String getCommandName() {
		return CommandConstants.GM_CMD_TREE_SHAKE;
	}
}
