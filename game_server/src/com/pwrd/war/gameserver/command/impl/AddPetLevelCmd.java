package com.pwrd.war.gameserver.command.impl;

import java.util.Arrays;

import com.pwrd.war.core.command.IAdminCommand;
import com.pwrd.war.core.session.ISession;
import com.pwrd.war.gameserver.command.CommandConstants;
import com.pwrd.war.gameserver.pet.Pet;
import com.pwrd.war.gameserver.player.Player;
import com.pwrd.war.gameserver.startup.GameClientSession;

public class AddPetLevelCmd implements IAdminCommand<ISession>{
	@Override
	public void execute(ISession playerSession, String[] commands) {
		int level = 0;
//		String petId = "";
		if (!(playerSession instanceof GameClientSession)) {
			return;
		}
		Player player = ((GameClientSession) playerSession).getPlayer();
		System.out.println(Arrays.toString(commands));
		try {
			if(commands.length < 1){
				return;
			}else if(commands.length == 1)
			{
				level = 1;
			}else{
				level = Integer.parseInt(commands[1]);
			}
//			petId = commands[0];
			for(int i=1; i<=level;i++){
//				Pet pet = player.getHuman().getPetManager().getPetByUuid(petId);
//				if(pet != null){
//					pet.levelUp();
//				}
				for(Pet pet : player.getHuman().getPetManager().getPets()){
					pet.levelUp(false);
				}
			}
			//TODO 将player level升level级
			System.out.println("升了"+level+"级啦!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		} catch (Exception e) {
			player.sendErrorMessage("错误的命令！");
			e.printStackTrace();
		}
	}

	@Override
	public String getCommandName() {
		return CommandConstants.GM_CMD_ADD_PET_LEVEL;
	}

}
