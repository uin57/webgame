package com.pwrd.war.gameserver.scene.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.GCMessage;

/**
 * 在场景内有坐下
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCPlayerSit extends GCMessage{
	
	/** 角色uuid */
	private String roleUUID;

	public GCPlayerSit (){
	}
	
	public GCPlayerSit (
			String roleUUID ){
			this.roleUUID = roleUUID;
	}

	@Override
	protected boolean readImpl() {
		roleUUID = readString();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeString(roleUUID);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_PLAYER_SIT;
	}
	
	@Override
	public String getTypeName() {
		return "GC_PLAYER_SIT";
	}

	public String getRoleUUID(){
		return roleUUID;
	}
		
	public void setRoleUUID(String roleUUID){
		this.roleUUID = roleUUID;
	}
}