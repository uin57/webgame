package com.pwrd.war.gameserver.human.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.CGMessage;
import com.pwrd.war.gameserver.human.handler.HumanHandlerFactory;

/**
 * 开启新的强化队列
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGOpenEquipenhanceCdqueue extends CGMessage{
	
	
	public CGOpenEquipenhanceCdqueue (){
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
		return MessageType.CG_OPEN_EQUIPENHANCE_CDQUEUE;
	}
	
	@Override
	public String getTypeName() {
		return "CG_OPEN_EQUIPENHANCE_CDQUEUE";
	}

	@Override
	public void execute() {
		HumanHandlerFactory.getHandler().handleOpenEquipenhanceCdqueue(this.getSession().getPlayer(), this);
	}
}