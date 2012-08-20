package com.pwrd.war.gameserver.human.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.CGMessage;
import com.pwrd.war.gameserver.human.handler.HumanHandlerFactory;

/**
 * 客户端请求进入主城
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGEnterHome extends CGMessage{
	
	
	public CGEnterHome (){
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
		return MessageType.CG_ENTER_HOME;
	}
	
	@Override
	public String getTypeName() {
		return "CG_ENTER_HOME";
	}

	@Override
	public void execute() {
		HumanHandlerFactory.getHandler().handleEnterHome(this.getSession().getPlayer(), this);
	}
}