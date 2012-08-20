package com.pwrd.war.gameserver.human.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.GCMessage;

/**
 * 登陆奖励
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCLoginPrize extends GCMessage{
	
	/** 已领取的天 */
	private String gotDays;
	/** 未领取的天 */
	private String canGetDays;

	public GCLoginPrize (){
	}
	
	public GCLoginPrize (
			String gotDays,
			String canGetDays ){
			this.gotDays = gotDays;
			this.canGetDays = canGetDays;
	}

	@Override
	protected boolean readImpl() {
		gotDays = readString();
		canGetDays = readString();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeString(gotDays);
		writeString(canGetDays);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_LOGIN_PRIZE;
	}
	
	@Override
	public String getTypeName() {
		return "GC_LOGIN_PRIZE";
	}

	public String getGotDays(){
		return gotDays;
	}
		
	public void setGotDays(String gotDays){
		this.gotDays = gotDays;
	}

	public String getCanGetDays(){
		return canGetDays;
	}
		
	public void setCanGetDays(String canGetDays){
		this.canGetDays = canGetDays;
	}
}