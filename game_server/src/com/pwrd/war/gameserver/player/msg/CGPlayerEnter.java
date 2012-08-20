package com.pwrd.war.gameserver.player.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.CGMessage;
import com.pwrd.war.gameserver.player.handler.PlayerHandlerFactory;

/**
 * 选择角色进入游戏场景
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGPlayerEnter extends CGMessage{
	
	/** 角色的uuid */
	private String roleUUID;
	
	public CGPlayerEnter (){
	}
	
	public CGPlayerEnter (
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
		return MessageType.CG_PLAYER_ENTER;
	}
	
	@Override
	public String getTypeName() {
		return "CG_PLAYER_ENTER";
	}

	public String getRoleUUID(){
		return roleUUID;
	}
		
	public void setRoleUUID(String roleUUID){
		this.roleUUID = roleUUID;
	}

	@Override
	public void execute() {
		PlayerHandlerFactory.getHandler().handlePlayerEnter(this.getSession().getPlayer(), this);
	}
}