package com.pwrd.war.core.msg.sys;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.core.msg.SessionMessage;
import com.pwrd.war.core.msg.SysInternalMessage;
import com.pwrd.war.core.session.ISession;

/**
 * 会话打开的消息
 * @param <T>
 * 
 */
public class SessionOpenedMessage<T extends ISession> extends
		SysInternalMessage implements SessionMessage<T> {

	protected T session;

	public SessionOpenedMessage(T session) {
		super.setType(MessageType.SYS_SESSION_OPEN);
		super.setTypeName("SYS_SESSION_OPEN");
		this.session = session;
	}

	@Override
	public T getSession() {
		return this.session;
	}

	public void setSession(T session) {
		this.session = session;
	}

	@Override
	public void execute() {
	}

	@Override
	public String toString() {
		return "SessionOpenedMessage";
	}
}
