package com.pwrd.war.core.log;

import org.apache.mina.common.IoHandlerAdapter;
import org.apache.mina.common.IoSession;

/**
 * 实现LogIoHandler
 * 
 * 
 */
public class LogClientIoHandler extends IoHandlerAdapter {
	private UdpLoggerClient logger;

	public LogClientIoHandler(UdpLoggerClient logger) {
		this.logger = logger;
	}

	@Override
	public void sessionOpened(IoSession session) throws Exception {
	}

	@Override
	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		//cause.printStackTrace();
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		logger.closed();
	}
}
