package com.pwrd.war.core.session;

import org.apache.mina.common.IoSession;

/**
 * 一个无意义的外部Server连接,不用于连接保持之类的功能
 * 
 */
public class ExternalDummyServerSession extends MinaSession {

	public ExternalDummyServerSession(IoSession s) {
		super(s);
	}
}
