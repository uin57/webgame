package com.pwrd.war.gameserver.human.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.CGMessage;
import com.pwrd.war.gameserver.human.handler.HumanHandlerFactory;

/**
 * 提升修炼境界
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGXiulianUpLevel extends CGMessage{
	
	
	public CGXiulianUpLevel (){
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
		return MessageType.CG_XIULIAN_UP_LEVEL;
	}
	
	@Override
	public String getTypeName() {
		return "CG_XIULIAN_UP_LEVEL";
	}

	@Override
	public void execute() {
		HumanHandlerFactory.getHandler().handleXiulianUpLevel(this.getSession().getPlayer(), this);
	}
}