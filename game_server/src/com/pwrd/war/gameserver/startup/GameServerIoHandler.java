package com.pwrd.war.gameserver.startup;

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.mina.common.IoSession;
import org.slf4j.Logger;

import com.pwrd.war.common.constants.Loggers;
import com.pwrd.war.core.handler.BaseFlashIoHandler;
import com.pwrd.war.core.msg.IMessage;
import com.pwrd.war.core.session.OnlineSessionService;
import com.pwrd.war.core.util.IpUtils;
import com.pwrd.war.gameserver.player.msg.GameClientSessionClosedMsg;
import com.pwrd.war.gameserver.player.msg.GameClientSessionOpenedMsg;

/**
 * Game Server的网络消息接收器
 * 
  *
 * 
 */
public class GameServerIoHandler extends BaseFlashIoHandler<MinaGameClientSession> {
	
	private static final Logger log = Loggers.gameLogger;
	
	private final GameExecutorService executor;
	
	private volatile AtomicBoolean isChecking = new AtomicBoolean(false);
	
	private volatile ConcurrentHashMap<IoSession, IoSession> suspendReadSessions = new ConcurrentHashMap<IoSession, IoSession>();
	
	protected OnlineSessionService<MinaGameClientSession> sessionService;

	/**
	 * 
	 * @param flashSocketPolicy Flash客户端建立socket连接时发送的policy请求的响应
	 * @param executor
	 */
	public GameServerIoHandler(String flashSocketPolicy, GameExecutorService executor, OnlineSessionService<MinaGameClientSession> sessionService) {
		super(flashSocketPolicy);
		this.sessionService = sessionService;
		this.executor = executor;
		this.executor.scheduleTask(createCheckSuspendTask(), 2000);
	}

	@Override
	public void messageReceived(IoSession session, Object obj) {
		super.messageReceived(session, obj);
		if (GameServerRuntime.isReadTrafficControl()) {
			if (msgProcessor.isFull()) {
				// 队列已经满了,停止session的读取
				session.suspendRead();
				suspendReadSessions.putIfAbsent(session, session);
				if (log.isWarnEnabled()) {
					log.warn("[#GS.GameServerIoHandler.messageReceived] [suspen read from session "	+ session + "]");
				}
			}
		}
	}

	@Override
	public void sessionOpened(IoSession session) {
		MinaGameClientSession minaSession = this.createSession(session);
		if (sessionService.addSession(minaSession)) {
			if (log.isDebugEnabled()) {
				log.debug("Session opened");
			}
			curSessionCount.incrementAndGet();
			session.setAttachment(minaSession);
			IMessage msg = new GameClientSessionOpenedMsg(minaSession);
			msgProcessor.put(msg);
		}	
		else {
			log.warn("Another AgentServer already connected");
			minaSession.close();
		}
		
	}

	@Override
	public void sessionClosed(IoSession session) {
		if (log.isDebugEnabled()) {
			log.debug("Session closed");
		}
		curSessionCount.decrementAndGet();
		MinaGameClientSession ms = (MinaGameClientSession) session.getAttachment();
		sessionService.removeSession(ms);
		if (ms == null) {
			return;
		}	
		session.setAttachment(null);
		IMessage msg = new GameClientSessionClosedMsg(ms);
		if (log.isInfoEnabled() && ms != null) {
			log.info(String.format("#CLOSE.SESSION.PASSIVE:%s;", IpUtils.getIp(session)));
		}
		msgProcessor.put(msg);
	}

	/**
	 * 检查被挂起的session,如果session还处于连接状态,则将其恢复读取
	 */
	private void checkSuspenReadSessions() {
		if (!this.isChecking.compareAndSet(false, true)) {
			return;
		}
		try {
			Iterator<IoSession> _sessionsSet = this.suspendReadSessions.keySet().iterator();
			while (_sessionsSet.hasNext()) {
				IoSession _session = _sessionsSet.next();
				_sessionsSet.remove();
				if (_session.isConnected()) {
					_session.resumeRead();
					if (log.isWarnEnabled()) {
						log.warn("[#GS.GameServerIoHandler.messageReceived] [resume read from session "	+ _session + "]");
					}
				}
			}
		} finally {
			this.isChecking.set(false);
		}
	}

	/**
	 * 创建一个用于检查读取挂起的任务
	 * 
	 * @return
	 */
	private Runnable createCheckSuspendTask() {
		return new Runnable() {
			@Override
			public void run() {
				if (suspendReadSessions.isEmpty()) {
					return;
				}
				if (msgProcessor.isFull()) {
					return;
				} else {
					checkSuspenReadSessions();
				}
			}
		};
	}

	@Override
	protected MinaGameClientSession createSession(IoSession session) {
		return new MinaGameClientSession(session);
	}

}
