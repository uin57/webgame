package com.pwrd.war.gameserver.tree.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.CGMessage;
import com.pwrd.war.gameserver.tree.handler.TreeHandlerFactory;

/**
 * 一键浇水
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGWaterFriendBatch extends CGMessage{
	
	
	public CGWaterFriendBatch (){
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
		return MessageType.CG_WATER_FRIEND_BATCH;
	}
	
	@Override
	public String getTypeName() {
		return "CG_WATER_FRIEND_BATCH";
	}

	@Override
	public void execute() {
		TreeHandlerFactory.getHandler().handleWaterFriendBatch(this.getSession().getPlayer(), this);
	}
}