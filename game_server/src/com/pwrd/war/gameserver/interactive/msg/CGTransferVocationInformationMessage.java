package com.pwrd.war.gameserver.interactive.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.CGMessage;
import com.pwrd.war.gameserver.interactive.handler.InteractiveHandlerFactory;

/**
 * 查看转职信息
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGTransferVocationInformationMessage extends CGMessage{
	
	/** 玩家的UUID */
	private String playUUID;
	/** 角色UUID */
	private String roleSn;
	
	public CGTransferVocationInformationMessage (){
	}
	
	public CGTransferVocationInformationMessage (
			String playUUID,
			String roleSn ){
			this.playUUID = playUUID;
			this.roleSn = roleSn;
	}
	
	@Override
	protected boolean readImpl() {
		playUUID = readString();
		roleSn = readString();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeString(playUUID);
		writeString(roleSn);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_Transfer_Vocation_Information_Message;
	}
	
	@Override
	public String getTypeName() {
		return "CG_Transfer_Vocation_Information_Message";
	}

	public String getPlayUUID(){
		return playUUID;
	}
		
	public void setPlayUUID(String playUUID){
		this.playUUID = playUUID;
	}

	public String getRoleSn(){
		return roleSn;
	}
		
	public void setRoleSn(String roleSn){
		this.roleSn = roleSn;
	}

	@Override
	public void execute() {
		InteractiveHandlerFactory.getHandler().handleTransferVocationInformationMessage(this.getSession().getPlayer(), this);
	}
}