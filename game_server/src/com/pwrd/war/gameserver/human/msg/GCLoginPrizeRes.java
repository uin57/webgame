package com.pwrd.war.gameserver.human.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.GCMessage;

/**
 * 领取登陆奖励
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCLoginPrizeRes extends GCMessage{
	
	/** 是否成功 */
	private boolean res;

	public GCLoginPrizeRes (){
	}
	
	public GCLoginPrizeRes (
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
		return MessageType.GC_LOGIN_PRIZE_RES;
	}
	
	@Override
	public String getTypeName() {
		return "GC_LOGIN_PRIZE_RES";
	}

	public boolean getRes(){
		return res;
	}
		
	public void setRes(boolean res){
		this.res = res;
	}
}