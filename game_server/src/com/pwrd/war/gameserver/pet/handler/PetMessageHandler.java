package com.pwrd.war.gameserver.pet.handler;

import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.pet.msg.CGPetFire;
import com.pwrd.war.gameserver.pet.msg.CGPetHire;
import com.pwrd.war.gameserver.pet.msg.CGPetHireList;
import com.pwrd.war.gameserver.pet.msg.CGPetJingjiu;
import com.pwrd.war.gameserver.player.Player;


public class PetMessageHandler {
	
	public void handlePetHire(Player player, CGPetHire msg){
		String npcId = msg.getPub();
		int pub = Globals.getPetService().getPubFromNpc(npcId);
		Globals.getPetService().hirePet(player, msg.getPetSn(), pub);
	}
	
	public void handlePetHireList(Player player, CGPetHireList msg){
		String npcId = msg.getPub();
		int pub = Globals.getPetService().getPubFromNpc(npcId);
		Globals.getPetService().getHirePetList(player, pub);
		Globals.getPetService().sendJingjiuMsg(player.getHuman());
	}
	
	public void handlePetFire(Player player, CGPetFire msg){
//		Globals.getPetService().removePet(player, msg.getPetUUID());
	}

	public void handlePetJingjiu(Player player, CGPetJingjiu cgPetJingjiu) {
		// TODO Auto-generated method stub
		int id = cgPetJingjiu.getId();
		Globals.getPetService().jingjiu(player, id);
	}
}
