package com.pwrd.war.gameserver.common.msg;

import com.pwrd.war.core.msg.BaseMinaMessage;
import com.pwrd.war.gameserver.startup.MinaGameClientSession;

/**
 * 客户端发送给服务器的消息的
 * 
 */
public abstract class CGMessage extends
	BaseMinaMessage<MinaGameClientSession> {
	
	@Override
	protected boolean writeImpl() {
		return false;
	}
}
