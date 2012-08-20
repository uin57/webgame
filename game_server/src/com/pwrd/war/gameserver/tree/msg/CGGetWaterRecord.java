package com.pwrd.war.gameserver.tree.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.CGMessage;
import com.pwrd.war.gameserver.tree.handler.TreeHandlerFactory;

/**
 * 获取浇水记录
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGGetWaterRecord extends CGMessage{
	
	
	public CGGetWaterRecord (){
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
		return MessageType.CG_GET_WATER_RECORD;
	}
	
	@Override
	public String getTypeName() {
		return "CG_GET_WATER_RECORD";
	}

	@Override
	public void execute() {
		TreeHandlerFactory.getHandler().handleGetWaterRecord(this.getSession().getPlayer(), this);
	}
}