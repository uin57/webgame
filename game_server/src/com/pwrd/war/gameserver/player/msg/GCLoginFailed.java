package com.pwrd.war.gameserver.player.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.GCMessage;

/**
 * 通知用户登录失败
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCLoginFailed extends GCMessage{
	
	/** 失败信息 */
	private String failMsg;

	public GCLoginFailed (){
	}
	
	public GCLoginFailed (
			String failMsg ){
			this.failMsg = failMsg;
	}

	@Override
	protected boolean readImpl() {
		failMsg = readString();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeString(failMsg);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_LOGIN_FAILED;
	}
	
	@Override
	public String getTypeName() {
		return "GC_LOGIN_FAILED";
	}

	public String getFailMsg(){
		return failMsg;
	}
		
	public void setFailMsg(String failMsg){
		this.failMsg = failMsg;
	}
}