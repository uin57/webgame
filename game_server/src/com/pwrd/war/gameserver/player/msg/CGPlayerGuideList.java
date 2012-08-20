package com.pwrd.war.gameserver.player.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.CGMessage;
import com.pwrd.war.gameserver.player.handler.PlayerHandlerFactory;

/**
 * 新手引导查询
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGPlayerGuideList extends CGMessage{
	
	
	public CGPlayerGuideList (){
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
		return MessageType.CG_PLAYER_GUIDE_LIST;
	}
	
	@Override
	public String getTypeName() {
		return "CG_PLAYER_GUIDE_LIST";
	}

	@Override
	public void execute() {
		PlayerHandlerFactory.getHandler().handlePlayerGuideList(this.getSession().getPlayer(), this);
	}
}