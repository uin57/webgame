package com.pwrd.war.gameserver.player.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.GCMessage;

/**
 * 通知用户登录成功
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCLoginSuccess extends GCMessage{
	

	public GCLoginSuccess (){
	}
	

	@Override
	protected boolean readImpl() {
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_LOGIN_SUCCESS;
	}
	
	@Override
	public String getTypeName() {
		return "GC_LOGIN_SUCCESS";
	}
}