package com.pwrd.war.gameserver.tree.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.CGMessage;
import com.pwrd.war.gameserver.tree.handler.TreeHandlerFactory;

/**
 * 给自己浇水
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGWaterMyself extends CGMessage{
	
	
	public CGWaterMyself (){
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
		return MessageType.CG_WATER_MYSELF;
	}
	
	@Override
	public String getTypeName() {
		return "CG_WATER_MYSELF";
	}

	@Override
	public void execute() {
		TreeHandlerFactory.getHandler().handleWaterMyself(this.getSession().getPlayer(), this);
	}
}