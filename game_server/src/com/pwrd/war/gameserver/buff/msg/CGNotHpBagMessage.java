package com.pwrd.war.gameserver.buff.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.CGMessage;
import com.pwrd.war.gameserver.buff.handler.BuffHandlerFactory;

/**
 * 没气血包时,提示购买气血包的消息
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGNotHpBagMessage extends CGMessage{
	
	
	public CGNotHpBagMessage (){
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
		return MessageType.CG_Not_Hp_Bag_Message;
	}
	
	@Override
	public String getTypeName() {
		return "CG_Not_Hp_Bag_Message";
	}

	@Override
	public void execute() {
		BuffHandlerFactory.getHandler().handleNotHpBagMessage(this.getSession().getPlayer(), this);
	}
}