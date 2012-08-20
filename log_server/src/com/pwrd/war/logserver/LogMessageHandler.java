package com.pwrd.war.logserver;

import java.util.HashMap;
import java.util.Map;

import org.apache.mina.common.IoHandlerAdapter;
import org.apache.mina.common.IoSession;
import org.slf4j.Logger;

import com.pwrd.war.core.util.ErrorsUtil;
import com.pwrd.war.logserver.model.OnlineTimeLog;

/**
 * 日志消息处理器
 * 
 *
 */
public class LogMessageHandler extends IoHandlerAdapter {
	private static Logger logger = org.slf4j.LoggerFactory.getLogger(LogMessageHandler.class);
	/** 各类型日志消息的分类型处理配置，如需要更新的日志消息分配到对应的{@link IoHandlerAdapter}处理 */
	@SuppressWarnings("unchecked")
    private final Map<Class, IoHandlerAdapter> subLogMsgHandler = new HashMap<Class, IoHandlerAdapter>();
    /** 主消息处理器,用于处理只插入的日志 */
	private final IoHandlerAdapter mainHandler = new SampleMessageHandler();
	
	public LogMessageHandler() {
		this.registerMessageProcessor(OnlineTimeLog.class, new OnlineTimeMessageHandler());
	}
	
	/**
	 * 
	 * @param messageType
	 * @param processor
	 */
	@SuppressWarnings("unchecked")
	private void registerMessageProcessor(Class messageType, IoHandlerAdapter handlerAdapter) {
		IoHandlerAdapter _adapter = getSubLogMsgHandlerAdapter(messageType);
		if (_adapter != null) {
			throw new IllegalArgumentException("The log message type class[" + messageType
					+ "] has already been registed with [" + _adapter + "]");
		}
		this.subLogMsgHandler.put(messageType, handlerAdapter);
	}
	
	@Override
	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		if (logger.isErrorEnabled()) {
			logger.error(ErrorsUtil.error("LOGSERVER_EXCEPTION", "#GS.SampleMessageHandler.exceptionCaught", cause
					.toString()), cause);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public void messageReceived(IoSession session, Object obj) throws Exception {
		if (obj instanceof BaseLogMessage) {
			final Class _msgClass = obj.getClass();
			IoHandlerAdapter _adapter = getSubLogMsgHandlerAdapter(_msgClass);
			if (_adapter != null) {
				_adapter.messageReceived(session, obj);
			} else {
				//没有注册,由全局共享的处理器处理
				this.mainHandler.messageReceived(session, obj);
			}
		}
	}

	/**
	 * 根据日志消息的类型取得对应的处理器的{@link IoHandlerAdapter}
	 * 
	 * @param msgClass
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private IoHandlerAdapter getSubLogMsgHandlerAdapter(final Class msgClass) {
		for (Map.Entry<Class, IoHandlerAdapter> _entry : this.subLogMsgHandler.entrySet()) {
			Class _type = _entry.getKey();
			if (_type.isAssignableFrom(msgClass)) {
				return _entry.getValue();
			}
		}
		return null;
	}
}

