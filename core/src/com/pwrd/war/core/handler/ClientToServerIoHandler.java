package com.pwrd.war.core.handler;

import org.apache.mina.common.IoSession;

import com.pwrd.war.core.server.AbstractIoHandler;
import com.pwrd.war.core.session.ExternalDummyServerSession;

/**
 * 用于客户端连接到Server的网络消息接收器
 * 
 * @see {@link ExternalDummyServerSession}
 * 
 */
public class ClientToServerIoHandler extends
		AbstractIoHandler<ExternalDummyServerSession> {

	@Override
	protected ExternalDummyServerSession createSession(IoSession session) {
		return new ExternalDummyServerSession(session);
	}

	@Override
	public void sessionClosed(IoSession session) {
		super.sessionClosed(session);
	}
}
