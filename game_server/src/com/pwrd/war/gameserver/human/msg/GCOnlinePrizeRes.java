package com.pwrd.war.gameserver.human.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.GCMessage;

/**
 * 领取奖励
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCOnlinePrizeRes extends GCMessage{
	
	/** 是否成功 */
	private boolean res;

	public GCOnlinePrizeRes (){
	}
	
	public GCOnlinePrizeRes (
			boolean res ){
			this.res = res;
	}

	@Override
	protected boolean readImpl() {
		res = readBoolean();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeBoolean(res);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_ONLINE_PRIZE_RES;
	}
	
	@Override
	public String getTypeName() {
		return "GC_ONLINE_PRIZE_RES";
	}

	public boolean getRes(){
		return res;
	}
		
	public void setRes(boolean res){
		this.res = res;
	}
}