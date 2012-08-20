package com.pwrd.war.core.session;

import com.pwrd.war.core.msg.IMessage;

/**
 * 
 * 
 */
public class InternalSession implements ISession {
	@Override
	public void close() {
	}

	@Override
	public boolean isConnected() {
		return false;
	}

	@Override
	public void write(IMessage msg) {
	}

	@Override
	public boolean closeOnException() {
		return false;
	}
}