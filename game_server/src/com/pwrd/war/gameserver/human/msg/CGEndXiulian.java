package com.pwrd.war.gameserver.human.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.CGMessage;
import com.pwrd.war.gameserver.human.handler.HumanHandlerFactory;

/**
 * 结束挂机修炼
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGEndXiulian extends CGMessage{
	
	
	public CGEndXiulian (){
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
		return MessageType.CG_END_XIULIAN;
	}
	
	@Override
	public String getTypeName() {
		return "CG_END_XIULIAN";
	}

	@Override
	public void execute() {
		HumanHandlerFactory.getHandler().handleEndXiulian(this.getSession().getPlayer(), this);
	}
}