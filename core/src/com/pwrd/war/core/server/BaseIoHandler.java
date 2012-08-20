package com.pwrd.war.core.server;

import org.apache.mina.common.IoHandlerAdapter;

public class BaseIoHandler extends IoHandlerAdapter {
	protected IMessageProcessor msgProcessor;

	/**
	 * 设置MessageProcessor
	 * 
	 * @param msgProcessor
	 */
	public void setMessageProcessor(IMessageProcessor msgProcessor) {
		this.msgProcessor = msgProcessor;
	}
}
