package com.pwrd.war.gameserver.common.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.GCMessage;

/**
 * 服务器准备好之后,告知客户端可以进行登录操作
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCHandshake extends GCMessage{
	

	public GCHandshake (){
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
		return MessageType.GC_HANDSHAKE;
	}
	
	@Override
	public String getTypeName() {
		return "GC_HANDSHAKE";
	}
}