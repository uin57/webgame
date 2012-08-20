package com.pwrd.war.core.server;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pwrd.war.common.constants.CommonErrorLogInfo;
import com.pwrd.war.core.msg.BaseMinaMessage;
import com.pwrd.war.core.msg.IMessage;
import com.pwrd.war.core.util.ErrorsUtil;
import com.pwrd.war.core.util.ExecutorUtil;

/**
 * 基于队列的消息处理器,使用{@link ExecutorService}处理队列中消息
 * 
 * 
 * @see {@link NoQueueMessageProcessor}
 * 
 */
public class QueueMessageExecutorProcessor implements IMessageProcessor {
	private static final Logger queue_logger = LoggerFactory
			.getLogger("messageQueue");
	private static final Logger logger = LoggerFactory.getLogger("");

	/** 消息队列 * */
	private final BlockingQueue<IMessage> queue;

	/** 消息处理线程停止时剩余的还未处理的消息 **/
	private volatile List<IMessage> leftQueue;

	/** 消息处理器 * */
	private final IMessageHandler<IMessage> handler;

	/** 消息处理线程池 */
	private volatile ExecutorService executorService;

	/** 线程池的线程个数 */
	private int excecutorCoreSize;

	/** 是否停止 */
	private volatile boolean stop = false;

	/** 处理的消息总数 */
	private long statisticsMessageCount = 0;

	private final boolean processLeft;

	@SuppressWarnings("unchecked")
	public QueueMessageExecutorProcessor(IMessageHandler messageHandler) {
		this(messageHandler, false, 1);
	}

	@SuppressWarnings("unchecked")
	public QueueMessageExecutorProcessor(IMessageHandler messageHandler,
			boolean processLeft, int executorCoreSize) {
		queue = new LinkedBlockingQueue<IMessage>();
		handler = messageHandler;
		this.processLeft = processLeft;
		this.excecutorCoreSize = executorCoreSize;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mop.lzr.core.server.IMessageProcessor#put(com.mop.lzr.core.msg.
	 * IMessage)
	 */
	public void put(IMessage msg) {
		try {
			queue.put(msg);
			if (queue_logger.isDebugEnabled()) {
				queue_logger.debug("put queue size:" + queue.size());
			}
		} catch (InterruptedException e) {
			if (logger.isErrorEnabled()) {
				logger.error(CommonErrorLogInfo.THRAD_ERR_INTERRUPTED, e);
			}
		}
	}

	/**
	 * 处理具体的消息，每个消息有自己的参数和来源,如果在处理消息的过程中发生异常,则马上将此消息的发送者断掉
	 * 
	 * @param msg
	 */
	@SuppressWarnings("unchecked")
	public void process(IMessage msg) {
		if (msg == null) {
			if (logger.isWarnEnabled()) {
				logger.warn("[#CORE.QueueMessageExecutorProcessor.process] ["
						+ CommonErrorLogInfo.MSG_PRO_ERR_NULL_MSG + "]");
			}
			return;
		}
		long begin = 0;
		if (logger.isInfoEnabled()) {
			begin = System.nanoTime();
		}
		this.statisticsMessageCount++;
		try {
			this.handler.execute(msg);
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {
				logger.error(ErrorsUtil.error("Error",
						"#.QueueMessageExecutorProcessor.process", "param"), e);
			}
			try {
				// TODO 此处的逻辑应该由一个接口代为实现
				if (msg instanceof BaseMinaMessage) {
					// 如果在处理消息的过程中出现了错误,将其断掉
					final BaseMinaMessage imsg = (BaseMinaMessage) msg;
					if (imsg.getSession() != null
							&& imsg.getSession().closeOnException()) {
						if (logger.isErrorEnabled()) {
							logger.error(ErrorsUtil.error(
									CommonErrorLogInfo.MSG_PRO_ERR_EXP,
									"Disconnect sender", msg.getTypeName()
											+ "@" + imsg.getSession()), e);
						}
						imsg.getSession().close();
					}
				}
			} catch (Exception e1) {
				if (logger.isErrorEnabled()) {
					logger.error(ErrorsUtil.error(
							CommonErrorLogInfo.MSG_PRO_ERR_DIS_SENDER_FAIL,
							"#CORE.QueueMessageProcessor.process", null), e1);
				}
			}
		} finally {
			if (logger.isInfoEnabled()) {
				// 特例，统计时间跨度
				long time = (System.nanoTime() - begin) / (1000 * 1000);
				if (time > 1) {
					int type = msg.getType();
					logger.info("#CORE.MSG.PROCESS.STATICS Message Name:"
							+ msg.getTypeName() + " Type:" + type + " Time:"
							+ time + "ms" + " Total:"
							+ this.statisticsMessageCount);
				}
			}
		}
	}

	public IMessageHandler<IMessage> getHandler() {
		return handler;
	}

	/**
	 * 取得未处理消息队列的长度
	 * 
	 * @return
	 */
	public int getQueueLength() {
		return queue.size();
	}

	/**
	 * 开始消息处理
	 */
	public synchronized void start() {
		if (this.executorService != null) {
			throw new IllegalStateException(
					"The executorSerive has not been stopped.");
		}
		stop = false;
		this.executorService = Executors
				.newFixedThreadPool(this.excecutorCoreSize, new NamedThreadFactory(getClass()));
		for (int i = 0; i < this.excecutorCoreSize; i++) {
			this.executorService.execute(new Worker());
		}
		logger.info("Message processor executorService started ["
				+ this.executorService + " with " + this.excecutorCoreSize
				+ " threads ]");
	}

	/**
	 * 停止消息处理
	 */
	public synchronized void stop() {
		logger.info("Message processor executor " + this + " stopping ...");
		this.stop = true;
		if (this.executorService != null) {
			ExecutorUtil.shutdownAndAwaitTermination(this.executorService, 50,
					TimeUnit.MILLISECONDS);
			this.executorService = null;
		}
		logger.info("Message processor executor " + this + " stopped");
		if (this.processLeft) {
			// 将未处理的消息放入到leftQueue中,以备后续处理
			this.leftQueue = new LinkedList<IMessage>();
			while (true) {
				IMessage _msg = this.queue.poll();
				if (_msg != null) {
					this.leftQueue.add(_msg);
				} else {
					break;
				}
			}
		}
		this.queue.clear();
	}

	/**
	 * 达到5000上限时认为满了
	 */
	public boolean isFull() {
		return this.queue.size() > 5000;
	}

	/**
	 * 取得消息处理器停止后的遗留的消息
	 * 
	 * @return the leftQueue
	 */
	public List<IMessage> getLeftQueue() {
		return leftQueue;
	}

	/**
	 * 重置
	 */
	public void resetLeftQueue() {
		this.leftQueue = null;
	}

	private final class Worker implements Runnable {
		@Override
		public void run() {
			while (!stop) {
				try {
					process(queue.take());
					if (queue_logger.isDebugEnabled()) {
						queue_logger.debug("run queue size:" + queue.size());
					}
				} catch (InterruptedException e) {
					if (logger.isWarnEnabled()) {
						logger
								.warn("[#CORE.QueueMessageExecutorProcessor.run] [Stop process]");
					}
					Thread.currentThread().interrupt();
					break;
				} catch (Exception e) {
					logger.error(CommonErrorLogInfo.MSG_PRO_ERR_EXP, e);
				}
			}
		}
	}

}
