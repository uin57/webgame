package com.pwrd.war.gameserver.prize.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.CGMessage;
import com.pwrd.war.gameserver.prize.handler.PrizeHandlerFactory;

/**
 * 领取玩家奖励
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGPrizeUserGet extends CGMessage{
	
	/** 奖励编号 */
	private int prizeId;
	
	public CGPrizeUserGet (){
	}
	
	public CGPrizeUserGet (
			int prizeId ){
			this.prizeId = prizeId;
	}
	
	@Override
	protected boolean readImpl() {
		prizeId = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(prizeId);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_PRIZE_USER_GET;
	}
	
	@Override
	public String getTypeName() {
		return "CG_PRIZE_USER_GET";
	}

	public int getPrizeId(){
		return prizeId;
	}
		
	public void setPrizeId(int prizeId){
		this.prizeId = prizeId;
	}

	@Override
	public void execute() {
		PrizeHandlerFactory.getHandler().handlePrizeUserGet(this.getSession().getPlayer(), this);
	}
}