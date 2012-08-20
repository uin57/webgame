package com.pwrd.war.gameserver.prize.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.GCMessage;

/**
 * 返回平台玩家奖励列表
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCPrizePlatformList extends GCMessage{
	
	/** 奖励列表 */
	private com.pwrd.war.gameserver.prize.PlatformPrizeHolder[] prizes;

	public GCPrizePlatformList (){
	}
	
	public GCPrizePlatformList (
			com.pwrd.war.gameserver.prize.PlatformPrizeHolder[] prizes ){
			this.prizes = prizes;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		prizes = new com.pwrd.war.gameserver.prize.PlatformPrizeHolder[count];
		for(int i=0; i<count; i++){
			com.pwrd.war.gameserver.prize.PlatformPrizeHolder obj = new com.pwrd.war.gameserver.prize.PlatformPrizeHolder();
			obj.setUniqueId(readInteger());
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
			writeInteger(prizes[i].getUniqueId());
			writeInteger(prizes[i].getPrizeId());
			writeString(prizes[i].getPrizeName());
		}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_PRIZE_PLATFORM_LIST;
	}
	
	@Override
	public String getTypeName() {
		return "GC_PRIZE_PLATFORM_LIST";
	}

	public com.pwrd.war.gameserver.prize.PlatformPrizeHolder[] getPrizes(){
		return prizes;
	}

	public void setPrizes(com.pwrd.war.gameserver.prize.PlatformPrizeHolder[] prizes){
		this.prizes = prizes;
	}	
}