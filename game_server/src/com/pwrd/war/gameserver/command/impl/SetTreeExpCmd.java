package com.pwrd.war.gameserver.command.impl;

import java.util.Arrays;

import com.pwrd.war.core.command.IAdminCommand;
import com.pwrd.war.core.session.ISession;
import com.pwrd.war.gameserver.command.CommandConstants;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.human.Human;
import com.pwrd.war.gameserver.player.Player;
import com.pwrd.war.gameserver.startup.GameClientSession;
import com.pwrd.war.gameserver.tree.HumanTreeInfo;
import com.pwrd.war.gameserver.tree.TreeInfoManager;
import com.pwrd.war.gameserver.tree.msg.GCWaterInfo;

public class SetTreeExpCmd  implements IAdminCommand<ISession>{
	@Override
	public void execute(ISession playerSession, String[] commands) {
		int exp = 0;
		if (!(playerSession instanceof GameClientSession)) {
			return;
		}
		Player player = ((GameClientSession) playerSession).getPlayer();
		System.out.println(Arrays.toString(commands));
		try {
			if(commands.length < 1)
			{
				exp = 0;
			}else{
				exp = Integer.parseInt(commands[0]);
			}
			
			Human human = player.getHuman();
			TreeInfoManager tim = human.getTreeInfoManager();
			HumanTreeInfo info = tim.getTreeInfos().get(human.getUUID());
					
			if(info == null){
				//如果没有数据 首先加入数据
				info = tim.creatHumanTreeInfo();
			}
			
			info.setCurTreeExp(exp);
			info.setTreeLevel(1);
			info.setModified();
			
			Globals.getTreeService().treeLevelUp(info);
			
			GCWaterInfo msg = new GCWaterInfo();
	
			
			msg.setCurTreeExp(info.getCurTreeExp());
			msg.setFriendWaterTimes(info.getFriendWaterTimes());
			msg.setFruitLevel(info.getFruitLevel());
			msg.setTreeLevel(info.getTreeLevel());
			msg.setMaxTreeExp(info.getMaxTreeExp());
			msg.setWaterTime(info.getWaterTimes());
			msg.setWaterTimes(info.getWaterTimes());
			
			player.sendMessage(msg);
			
		} catch (Exception e) {
			player.sendErrorMessage("错误的命令！");
			e.printStackTrace();
		}
	}

	@Override
	public String getCommandName() {
		return CommandConstants.GM_CMD_SET_TREE_EXP;
	}
}
