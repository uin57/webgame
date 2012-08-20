package com.pwrd.war.gameserver.scene.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.CGMessage;
import com.pwrd.war.gameserver.scene.handler.SceneHandlerFactory;

/**
 * 在场景内站立起来
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGPlayerStand extends CGMessage{
	
	
	public CGPlayerStand (){
	}
	
	
	@Override
	protected boolean readImpl() {
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_PLAYER_STAND;
	}
	
	@Override
	public String getTypeName() {
		return "CG_PLAYER_STAND";
	}

	@Override
	public void execute() {
		SceneHandlerFactory.getHandler().handlePlayerStand(this.getSession().getPlayer(), this);
	}
}