package com.pwrd.war.core.session;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.pwrd.war.common.constants.Loggers;
import com.pwrd.war.core.msg.IMessage;

/**
 * 维护服务器上所有的会话
 * 
 * 
 */
public class OnlineSessionService<E extends MinaSession> {
	private List<E> sessions = new CopyOnWriteArrayList<E>();

	public boolean addSession(E session) {
		sessions.add(session);
		return true;
	}

	public void removeSession(E session) {
		sessions.remove(session);
	}

	/**
	 * @param msg
	 */
	public void broadcast(IMessage msg) {
		for (ISession session : sessions) {
			if (Loggers.msgLogger.isDebugEnabled()) {
				Loggers.msgLogger.debug("【Broadcast】" + msg);
			}
			session.write(msg);
		}
	}
}
