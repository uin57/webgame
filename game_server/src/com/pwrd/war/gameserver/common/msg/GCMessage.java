package com.pwrd.war.gameserver.common.msg;

import com.pwrd.war.core.msg.BaseMinaMessage;
import com.pwrd.war.gameserver.startup.MinaGameClientSession;

/**
 * 服务器端发送给客户端的消息基类
 * 
 */
public abstract class GCMessage extends
	BaseMinaMessage<MinaGameClientSession> {
	
	@Override
	protected boolean readImpl() {
		return false;
	}

	@Override
	public void execute() {
		throw new UnsupportedOperationException();
	}

}
