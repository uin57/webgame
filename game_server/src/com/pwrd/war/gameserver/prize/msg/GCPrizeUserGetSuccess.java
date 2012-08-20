package com.pwrd.war.gameserver.prize.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.GCMessage;

/**
 * 领取奖励成功
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCPrizeUserGetSuccess extends GCMessage{
	
	/** 奖励类型，1-系统补偿 */
	private short prizeType;
	/** 奖励ID */
	private int prizeId;

	public GCPrizeUserGetSuccess (){
	}
	
	public GCPrizeUserGetSuccess (
			short prizeType,
			int prizeId ){
			this.prizeType = prizeType;
			this.prizeId = prizeId;
	}

	@Override
	protected boolean readImpl() {
		prizeType = readShort();
		prizeId = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeShort(prizeType);
		writeInteger(prizeId);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_PRIZE_USER_GET_SUCCESS;
	}
	
	@Override
	public String getTypeName() {
		return "GC_PRIZE_USER_GET_SUCCESS";
	}

	public short getPrizeType(){
		return prizeType;
	}
		
	public void setPrizeType(short prizeType){
		this.prizeType = prizeType;
	}

	public int getPrizeId(){
		return prizeId;
	}
		
	public void setPrizeId(int prizeId){
		this.prizeId = prizeId;
	}
}