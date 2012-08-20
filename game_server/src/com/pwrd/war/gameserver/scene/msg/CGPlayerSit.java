package com.pwrd.war.gameserver.scene.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.CGMessage;
import com.pwrd.war.gameserver.scene.handler.SceneHandlerFactory;

/**
 * 在场景内有坐下
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGPlayerSit extends CGMessage{
	
	
	public CGPlayerSit (){
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
		return MessageType.CG_PLAYER_SIT;
	}
	
	@Override
	public String getTypeName() {
		return "CG_PLAYER_SIT";
	}

	@Override
	public void execute() {
		SceneHandlerFactory.getHandler().handlePlayerSit(this.getSession().getPlayer(), this);
	}
}