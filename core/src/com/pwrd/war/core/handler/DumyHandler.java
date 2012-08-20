package com.pwrd.war.core.handler;

import com.pwrd.war.core.msg.IMessage;
import com.pwrd.war.core.server.IMessageHandler;

/**
 * 捕获所有未被识别消息,记录到日志中
 * 
 * 
 */
public class DumyHandler implements IMessageHandler<IMessage> {
 
	@Override
	public void execute(IMessage message) {
		message.execute();
	}
	
	@Override
	public short[] getTypes() {
		return null;
	}
}
