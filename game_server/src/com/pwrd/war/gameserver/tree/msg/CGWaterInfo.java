package com.pwrd.war.gameserver.tree.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.CGMessage;
import com.pwrd.war.gameserver.tree.handler.TreeHandlerFactory;

/**
 * 浇水和果实信息
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGWaterInfo extends CGMessage{
	
	
	public CGWaterInfo (){
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
		return MessageType.CG_WATER_INFO;
	}
	
	@Override
	public String getTypeName() {
		return "CG_WATER_INFO";
	}

	@Override
	public void execute() {
		TreeHandlerFactory.getHandler().handleWaterInfo(this.getSession().getPlayer(), this);
	}
}