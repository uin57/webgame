package com.pwrd.war.gameserver.command.impl;

import java.util.Arrays;

import com.pwrd.war.core.command.IAdminCommand;
import com.pwrd.war.core.session.ISession;
import com.pwrd.war.db.model.UserInfo;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.common.enums.Camp;
import com.pwrd.war.gameserver.common.enums.Sex;
import com.pwrd.war.gameserver.common.enums.VocationType;
import com.pwrd.war.gameserver.human.enums.ResearchType;
import com.pwrd.war.gameserver.player.Player;
import com.pwrd.war.gameserver.robbery.HumanRobberyInfo;
import com.pwrd.war.gameserver.startup.GameClientSession;

public class EditPlayerCmd  implements IAdminCommand<ISession>{
	@Override
	public void execute(ISession playerSession, String[] commands) {
		if (!(playerSession instanceof GameClientSession)) {
			return;
		}
		Player player = ((GameClientSession) playerSession).getPlayer();
		System.out.println(Arrays.toString(commands));
		try {
			int len = commands.length;
			Player target = null;
			String newv = "";
			if(len >= 2){
				target = player;
				newv = commands[1];
			}else{
//				target = Globals.getOnlinePlayerService().getPlayer(commands[1]);
//				newv = commands[2];
			}
			String cmd = commands[0]; 
			if("camp".equals(cmd)){ 
				target.getHuman().setCamp(Camp.getByCode(Integer.valueOf(newv)));
				target.getHuman().snapChangedProperty(true);
			}else if("sex".equals(cmd)){
				target.getHuman().setSex(Sex.getByCode(Integer.valueOf(newv)));
				target.getHuman().snapChangedProperty(true);
			}else if("voc".equals(cmd)){
				target.getHuman().setVocationType(VocationType.getByCode(Integer.valueOf(newv)));
				target.getHuman().snapChangedProperty(true);
			}else if("shengwang".equals(cmd)){
				target.getHuman().addPopularity(Integer.valueOf(newv));
				target.getHuman().snapChangedProperty(true);
			}else if("yueli".equals(cmd)){
				target.getHuman().getPropertyManager().setSee(Integer.valueOf(newv));
				target.getHuman().snapChangedProperty(true);
			}else if("vit".equals(cmd)){
				target.getHuman().getPropertyManager().setVitality(Integer.valueOf(newv));
				target.getHuman().snapChangedProperty(true);
			}else if("pri".equals(cmd)){
				//修改账号权限
				target.setPermission(Integer.valueOf(newv));
				UserInfo info = Globals.getDaoService().getUserInfoDao().get(target.getPassportId());
				info.setRole(Integer.valueOf(newv));
				Globals.getDaoService().getUserInfoDao().saveOrUpdate(info);
			}else if("robbery".endsWith(cmd)){
				int  v1 = Integer.valueOf(commands[1]);
				int  v2 = Integer.valueOf(commands[2]);
				int  v3 = Integer.valueOf(commands[3]);
				HumanRobberyInfo info = player.getHuman().getRobberyInfo();
				info.setRobberyTodayTimes((short) v1);
				info.setRobberyTodayRobTimes((short) v2);
				info.setRobberyProtectTimes((short) v3);
				player.getHuman().setModified();
				player.getHuman().snapChangedProperty(true);
			}else if("yanjiu".equals(cmd)){
				ResearchType type = ResearchType.getById(Integer.valueOf(commands[1]));
				player.getHuman().getBaseIntProperties().setInt(type.getPropIndex(), Integer.valueOf(commands[2]));
				player.getHuman().setModified();
				player.getHuman().snapChangedProperty(true); 
			}
			player.sendSystemMessage("修改属性成功");
		} catch (Exception e) {
			player.sendErrorMessage("错误的命令！");
			e.printStackTrace();
		}
	}

	@Override
	public String getCommandName() {
		return "edit";
	}
}
