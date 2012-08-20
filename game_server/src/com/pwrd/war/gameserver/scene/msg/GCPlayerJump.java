package com.pwrd.war.gameserver.scene.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.GCMessage;

/**
 * 在场景内有跳动
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCPlayerJump extends GCMessage{
	
	/** 角色uuid */
	private String roleUUID;
	/** 目标x位置 */
	private int tox;
	/** 目标y位置 */
	private int toy;

	public GCPlayerJump (){
	}
	
	public GCPlayerJump (
			String roleUUID,
			int tox,
			int toy ){
			this.roleUUID = roleUUID;
			this.tox = tox;
			this.toy = toy;
	}

	@Override
	protected boolean readImpl() {
		roleUUID = readString();
		tox = readInteger();
		toy = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeString(roleUUID);
		writeInteger(tox);
		writeInteger(toy);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_PLAYER_JUMP;
	}
	
	@Override
	public String getTypeName() {
		return "GC_PLAYER_JUMP";
	}

	public String getRoleUUID(){
		return roleUUID;
	}
		
	public void setRoleUUID(String roleUUID){
		this.roleUUID = roleUUID;
	}

	public int getTox(){
		return tox;
	}
		
	public void setTox(int tox){
		this.tox = tox;
	}

	public int getToy(){
		return toy;
	}
		
	public void setToy(int toy){
		this.toy = toy;
	}
}