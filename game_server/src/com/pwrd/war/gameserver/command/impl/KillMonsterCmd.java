package com.pwrd.war.gameserver.command.impl;

import java.util.Arrays;

import org.apache.commons.lang.StringUtils;

import com.pwrd.war.core.command.IAdminCommand;
import com.pwrd.war.core.session.ISession;
import com.pwrd.war.gameserver.command.CommandConstants;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.fight.field.FightFieldFactory;
import com.pwrd.war.gameserver.monster.VisibleMonster;
import com.pwrd.war.gameserver.monster.template.VisibleMonsterTemplate;
import com.pwrd.war.gameserver.player.Player;
import com.pwrd.war.gameserver.startup.GameClientSession;

public class KillMonsterCmd  implements IAdminCommand<ISession>{
	@Override
	public void execute(ISession playerSession, String[] commands) {
		String monsterId = "";
		if (!(playerSession instanceof GameClientSession)) {
			return;
		}
		Player player = ((GameClientSession) playerSession).getPlayer();
		System.out.println(Arrays.toString(commands));
		try {
			if(commands.length < 1)
			{
				monsterId = "";
			}else{
				monsterId = commands[0];
			}
			
//			Human human = player.getHuman();
//			TreeInfoManager tim = human.getTreeInfoManager();
//			HumanTreeInfo info = tim.getTreeInfos().get(human.getUUID());
//					
//			if(info == null){
//				//如果没有数据 首先加入数据
//				info = tim.creatHumanTreeInfo();
//			}
//			
//			info.setShakeTimes(times);
//			info.setModified();
//			
//			Globals.getTreeService().getTreeInfo(player);
			
			if(StringUtils.isNotBlank(monsterId)){
				VisibleMonsterTemplate template = Globals.getMonsterService().getVisibleMonsterTemplatesBySn(monsterId);
				VisibleMonster monster = new VisibleMonster(player.getHuman().getScene(), template, Globals.getMonsterService(), null);
				monster.init();
				monster.heartBeat();
				FightFieldFactory.doMonsterFight(player, monster);
			}
			
			
		} catch (Exception e) {
			player.sendErrorMessage("错误的命令！");
			e.printStackTrace();
		}
	}

	@Override
	public String getCommandName() {
		return CommandConstants.KILL_MONSTER;
	}
}
