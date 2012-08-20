package com.pwrd.war.gameserver.prize.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.CGMessage;
import com.pwrd.war.gameserver.prize.handler.PrizeHandlerFactory;

/**
 * 领取奖励
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGPrizePlatformGet extends CGMessage{
	
	/** 平台奖励唯一编号 */
	private int uniqueId;
	/** 奖励编号 */
	private int prizeId;
	
	public CGPrizePlatformGet (){
	}
	
	public CGPrizePlatformGet (
			int uniqueId,
			int prizeId ){
			this.uniqueId = uniqueId;
			this.prizeId = prizeId;
	}
	
	@Override
	protected boolean readImpl() {
		uniqueId = readInteger();
		prizeId = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(uniqueId);
		writeInteger(prizeId);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_PRIZE_PLATFORM_GET;
	}
	
	@Override
	public String getTypeName() {
		return "CG_PRIZE_PLATFORM_GET";
	}

	public int getUniqueId(){
		return uniqueId;
	}
		
	public void setUniqueId(int uniqueId){
		this.uniqueId = uniqueId;
	}

	public int getPrizeId(){
		return prizeId;
	}
		
	public void setPrizeId(int prizeId){
		this.prizeId = prizeId;
	}

	@Override
	public void execute() {
		PrizeHandlerFactory.getHandler().handlePrizePlatformGet(this.getSession().getPlayer(), this);
	}
}