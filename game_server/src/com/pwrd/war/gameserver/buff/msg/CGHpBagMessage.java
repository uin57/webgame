package com.pwrd.war.gameserver.buff.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.CGMessage;
import com.pwrd.war.gameserver.buff.handler.BuffHandlerFactory;

/**
 * 有气血包时,提示使用气血包的消息
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGHpBagMessage extends CGMessage{
	
	
	public CGHpBagMessage (){
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
		return MessageType.CG_Hp_Bag_Message;
	}
	
	@Override
	public String getTypeName() {
		return "CG_Hp_Bag_Message";
	}

	@Override
	public void execute() {
		BuffHandlerFactory.getHandler().handleHpBagMessage(this.getSession().getPlayer(), this);
	}
}