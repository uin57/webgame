package com.pwrd.war.gameserver.skill.handler;

import com.pwrd.war.common.ModuleMessageHandler;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.player.Player;
import com.pwrd.war.gameserver.skill.msg.CGSkillCooldown;
import com.pwrd.war.gameserver.skill.msg.CGSkillOrder;
import com.pwrd.war.gameserver.skill.msg.CGSkillUpgrade;

/**
 * 
 * 物品消息处理器
 * 
 * 
 */
public class SkillMessageHandler implements ModuleMessageHandler {

	public SkillMessageHandler() {
	}


	public void handleSkillCooldown(Player player, CGSkillCooldown message) {
//		Globals.getSkillService().
	}

	public void handleSkillOrder(Player player, CGSkillOrder message) {
		Globals.getSkillService().modifySkillOrder(player, message);
	}
	
	public void handleSkillUpgrade(Player player, CGSkillUpgrade message) {
		Globals.getSkillService().updateSkill(player, message);
	}


	




	


}
