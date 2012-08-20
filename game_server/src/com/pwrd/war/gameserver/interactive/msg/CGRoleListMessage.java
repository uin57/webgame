package com.pwrd.war.gameserver.interactive.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.CGMessage;
import com.pwrd.war.gameserver.interactive.handler.InteractiveHandlerFactory;

/**
 * 查看装备基本信息
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGRoleListMessage extends CGMessage{
	
	/** 玩家的UUID */
	private String playUUID;
	
	public CGRoleListMessage (){
	}
	
	public CGRoleListMessage (
			String playUUID ){
			this.playUUID = playUUID;
	}
	
	@Override
	protected boolean readImpl() {
		playUUID = readString();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeString(playUUID);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_Role_List_Message;
	}
	
	@Override
	public String getTypeName() {
		return "CG_Role_List_Message";
	}

	public String getPlayUUID(){
		return playUUID;
	}
		
	public void setPlayUUID(String playUUID){
		this.playUUID = playUUID;
	}

	@Override
	public void execute() {
		InteractiveHandlerFactory.getHandler().handleRoleListMessage(this.getSession().getPlayer(), this);
	}
}