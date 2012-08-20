package com.pwrd.war.gameserver.interactive.handler;

import com.pwrd.war.common.ModuleMessageHandler;
import com.pwrd.war.gameserver.interactive.InteractiveService;
import com.pwrd.war.gameserver.interactive.msg.CGEquipmentInfomationMessage;
import com.pwrd.war.gameserver.interactive.msg.CGInfomationMessage;
import com.pwrd.war.gameserver.interactive.msg.CGLookUpMessage;
import com.pwrd.war.gameserver.interactive.msg.CGRoleListMessage;
import com.pwrd.war.gameserver.interactive.msg.CGTransferVocationInformationMessage;
import com.pwrd.war.gameserver.player.Player;

/**
 * 
 * 物品消息处理器
 * 
 * 
 */
public class InteractiveMessageHandler implements ModuleMessageHandler {
	
	private static InteractiveService server=new InteractiveService();

	public InteractiveMessageHandler() {
	}

	public void  handleRoleListMessage(Player player,CGRoleListMessage message){
		server.roleList(player, message);
	}
	
	public void handleEquipmentInfomationMessage(Player player,CGEquipmentInfomationMessage message){
		server.equipmentInfomation(player, message);
	}
	
	public void  handleInfomationMessage(Player player,CGInfomationMessage message){
		server.infomation(player, message);
	}
	
	public void  handleLookUpMessage(Player player,CGLookUpMessage message){
		server.lookUp(player, message);
	}
	
	public void  handleTransferVocationInformationMessage(Player player,CGTransferVocationInformationMessage message){
		server.transferVocationInformation(player, message);
	}
	
	
	
}
