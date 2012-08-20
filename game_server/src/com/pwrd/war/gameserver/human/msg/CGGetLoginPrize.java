package com.pwrd.war.gameserver.human.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.CGMessage;
import com.pwrd.war.gameserver.human.handler.HumanHandlerFactory;

/**
 * 取奖励信息
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGGetLoginPrize extends CGMessage{
	
	
	public CGGetLoginPrize (){
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
		return MessageType.CG_GET_LOGIN_PRIZE;
	}
	
	@Override
	public String getTypeName() {
		return "CG_GET_LOGIN_PRIZE";
	}

	@Override
	public void execute() {
		HumanHandlerFactory.getHandler().handleGetLoginPrize(this.getSession().getPlayer(), this);
	}
}