package com.pwrd.war.gameserver.interactive.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.CGMessage;
import com.pwrd.war.gameserver.interactive.handler.InteractiveHandlerFactory;

/**
 * 查看别人的信息消息
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGLookUpMessage extends CGMessage{
	
	/** 玩家的UUID */
	private String playUUID;
	
	public CGLookUpMessage (){
	}
	
	public CGLookUpMessage (
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
		return MessageType.CG_Look_Up_Message;
	}
	
	@Override
	public String getTypeName() {
		return "CG_Look_Up_Message";
	}

	public String getPlayUUID(){
		return playUUID;
	}
		
	public void setPlayUUID(String playUUID){
		this.playUUID = playUUID;
	}

	@Override
	public void execute() {
		InteractiveHandlerFactory.getHandler().handleLookUpMessage(this.getSession().getPlayer(), this);
	}
}