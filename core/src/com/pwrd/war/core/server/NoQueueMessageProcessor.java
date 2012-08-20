package com.pwrd.war.core.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pwrd.war.common.constants.CommonErrorLogInfo;
import com.pwrd.war.core.msg.IMessage;
import com.pwrd.war.core.msg.SessionMessage;
import com.pwrd.war.core.util.ErrorsUtil;

/**
 * 不带队列的消息处理器,即当接收到消息后不放入队列中直接处理
 * 
 * @see {@link QueueMessageProcessor}
 * 
 */
public class NoQueueMessageProcessor implements IMessageProcessor {

	/** 日志 */
	private static final Logger logger = LoggerFactory.getLogger(NoQueueMessageProcessor.class);

	/** 消息处理器 */
	private final IMessageHandler<IMessage> handler;

	/** 停止处理标识 */
	private volatile boolean stop = false;

	public NoQueueMessageProcessor(IMessageHandler<IMessage> messageHandler) {
		handler = messageHandler;
	}

	/**
	 * 将待处理消息放入队列 ： 这里暂时直接处理
	 */
	public void put(IMessage msg) {
		if (!stop) {
			process(msg);
		}
	}

	/**
	 * 处理具体的消息，每个消息有自己的参数和来源,如果在处理消息的过程中发生异常,则马上将此消息的发送者断掉
	 * 
	 * @param msg
	 */
	public void process(IMessage msg) {
		if (msg == null) {
			if (logger.isWarnEnabled()) {
				logger.warn("[#CORE.NoQueueMessageProcessor.process] [" + CommonErrorLogInfo.MSG_PRO_ERR_NULL_MSG + "]");
			}
			return;
		}
		long begin = 0;
		if (logger.isInfoEnabled()) {
			begin = System.currentTimeMillis();
		}
		try {
			this.handler.execute(msg);
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {
				logger.error(ErrorsUtil.error("Error", "#.NoQueueMessageProcessor.process", "param"), e);
			}			
			try {
				// TODO 此处的逻辑应该由一个接口代为实现
				if (msg instanceof SessionMessage<?>) {
					// 如果在处理消息的过程中出现了错误,将其断掉
					final SessionMessage<?> imsg = (SessionMessage<?>) msg;
					if (imsg.getSession() != null) {
						if (logger.isErrorEnabled()) {
							logger.error(ErrorsUtil.error(CommonErrorLogInfo.MSG_PRO_ERR_EXP, "Disconnect sender", msg
									.getTypeName()
									+ "@" + imsg.getSession()), e);
						}
						imsg.getSession().close();
					}
				}
			} catch (Exception e1) {
				if (logger.isErrorEnabled()) {
					logger.error(ErrorsUtil.error(CommonErrorLogInfo.MSG_PRO_ERR_DIS_SENDER_FAIL,
							"#CORE.NoQueueMessageProcessor.process", null), e1);
				}
			}
		} finally {
			if (logger.isInfoEnabled()) {
				// 特例，统计时间跨度
				long time = System.currentTimeMillis() - begin;
				if (time > 1) {
					int type = msg.getType();
					logger.debug("#CORE.MSG.PROCESS.STATICS Message Name:" + msg.getTypeName() + " Type:" + type
							+ " Time:" + time + "ms");
				}
			}
		}
	}

	/**
	 * 开始处理
	 */
	public synchronized void start() {
		stop = false;
	}

	/**
	 * 停止处理
	 */
	public synchronized void stop() {
		stop = true;
	}

	@Override
	public boolean isFull() {
		return false;
	}

}
