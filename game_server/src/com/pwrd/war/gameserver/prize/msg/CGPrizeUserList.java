package com.pwrd.war.gameserver.prize.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.CGMessage;
import com.pwrd.war.gameserver.prize.handler.PrizeHandlerFactory;

/**
 * 查询GM补偿的奖励列表
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGPrizeUserList extends CGMessage{
	
	
	public CGPrizeUserList (){
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
		return MessageType.CG_PRIZE_USER_LIST;
	}
	
	@Override
	public String getTypeName() {
		return "CG_PRIZE_USER_LIST";
	}

	@Override
	public void execute() {
		PrizeHandlerFactory.getHandler().handlePrizeUserList(this.getSession().getPlayer(), this);
	}
}