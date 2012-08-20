package com.pwrd.war.gameserver.interactive.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.CGMessage;
import com.pwrd.war.gameserver.interactive.handler.InteractiveHandlerFactory;

/**
 * 查看信息
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGInfomationMessage extends CGMessage{
	
	/** 玩家的UUID */
	private String playUUID;
	/** 角色UUID */
	private String roleSn;
	
	public CGInfomationMessage (){
	}
	
	public CGInfomationMessage (
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
		return MessageType.CG_Infomation_Message;
	}
	
	@Override
	public String getTypeName() {
		return "CG_Infomation_Message";
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
		InteractiveHandlerFactory.getHandler().handleInfomationMessage(this.getSession().getPlayer(), this);
	}
}