package com.pwrd.war.gameserver.interactive.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.GCMessage;

/**
 * 查看信息
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCInfomationMessage extends GCMessage{
	
	/** 玩家的UUID */
	private String playUUID;
	/** 角色UUID */
	private String roleSn;

	public GCInfomationMessage (){
	}
	
	public GCInfomationMessage (
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
		return MessageType.GC_Infomation_Message;
	}
	
	@Override
	public String getTypeName() {
		return "GC_Infomation_Message";
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
}