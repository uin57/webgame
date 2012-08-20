package com.pwrd.war.gameserver.prize.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.GCMessage;

/**
 * 玩家奖励列表
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCPrizeUserList extends GCMessage{
	
	/** 奖励列表 */
	private com.pwrd.war.gameserver.prize.UserPrizeInfo[] prizes;

	public GCPrizeUserList (){
	}
	
	public GCPrizeUserList (
			com.pwrd.war.gameserver.prize.UserPrizeInfo[] prizes ){
			this.prizes = prizes;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		prizes = new com.pwrd.war.gameserver.prize.UserPrizeInfo[count];
		for(int i=0; i<count; i++){
			com.pwrd.war.gameserver.prize.UserPrizeInfo obj = new com.pwrd.war.gameserver.prize.UserPrizeInfo();
			obj.setPrizeId(readInteger());
			obj.setPrizeName(readString());
			prizes[i] = obj;
		}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeShort(prizes.length);
		for(int i=0; i<prizes.length; i++){
			writeInteger(prizes[i].getPrizeId());
			writeString(prizes[i].getPrizeName());
		}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_PRIZE_USER_LIST;
	}
	
	@Override
	public String getTypeName() {
		return "GC_PRIZE_USER_LIST";
	}

	public com.pwrd.war.gameserver.prize.UserPrizeInfo[] getPrizes(){
		return prizes;
	}

	public void setPrizes(com.pwrd.war.gameserver.prize.UserPrizeInfo[] prizes){
		this.prizes = prizes;
	}	
}