package com.pwrd.war.gameserver.human.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.GCMessage;

/**
 * 发给其他玩家当前玩家离开场景消息
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCSceneDelRole extends GCMessage{
	
	/** 角色UUID */
	private String UUID;

	public GCSceneDelRole (){
	}
	
	public GCSceneDelRole (
			String UUID ){
			this.UUID = UUID;
	}

	@Override
	protected boolean readImpl() {
		UUID = readString();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeString(UUID);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SCENE_DEL_ROLE;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SCENE_DEL_ROLE";
	}

	public String getUUID(){
		return UUID;
	}
		
	public void setUUID(String UUID){
		this.UUID = UUID;
	}
}