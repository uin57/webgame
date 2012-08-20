package com.pwrd.war.gameserver.prize.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.GCMessage;

/**
 * 领取平台奖励成功
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCPrizePlatformGetSuccess extends GCMessage{
	
	/** 平台奖励唯一编号 */
	private int uniqueId;

	public GCPrizePlatformGetSuccess (){
	}
	
	public GCPrizePlatformGetSuccess (
			int uniqueId ){
			this.uniqueId = uniqueId;
	}

	@Override
	protected boolean readImpl() {
		uniqueId = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(uniqueId);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_PRIZE_PLATFORM_GET_SUCCESS;
	}
	
	@Override
	public String getTypeName() {
		return "GC_PRIZE_PLATFORM_GET_SUCCESS";
	}

	public int getUniqueId(){
		return uniqueId;
	}
		
	public void setUniqueId(int uniqueId){
		this.uniqueId = uniqueId;
	}
}