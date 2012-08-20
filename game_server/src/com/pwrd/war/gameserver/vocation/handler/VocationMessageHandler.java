package com.pwrd.war.gameserver.vocation.handler;

import com.pwrd.war.common.ModuleMessageHandler;
import com.pwrd.war.gameserver.player.Player;
import com.pwrd.war.gameserver.vocation.SkillGroup;
import com.pwrd.war.gameserver.vocation.VocationSkill;
import com.pwrd.war.gameserver.vocation.msg.CGDefaultSkillGroup;
import com.pwrd.war.gameserver.vocation.msg.GCDefaultSkillGroup;

/**
 * 
 * 物品消息处理器
 * 
 * 
 */
public class VocationMessageHandler implements ModuleMessageHandler {

	public VocationMessageHandler() {
	}

	
	public void handleDefaultSkillGroup(Player player, CGDefaultSkillGroup message) {
		for(VocationSkill vocationSkill:player.getHuman().getVocationSkills()){
			if(vocationSkill.getVocationType()==message.getVocationType()){
				for(SkillGroup skillGroup:vocationSkill.getSkillGroups()){
					if(skillGroup.getNumber()==message.getDefaultSkillGroupOrder()){
						skillGroup.setChoose(true);
						skillGroup.setModified();
						player.sendMessage(new GCDefaultSkillGroup(vocationSkill.getVocationType(), skillGroup.getNumber()));
					}
					else{
						skillGroup.setChoose(false);
					}
				}
				break;
			}
		}
		
	}

	



	


}
