package com.pwrd.war.core.client;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pwrd.war.core.handler.ClientToServerIoHandler;
import com.pwrd.war.core.msg.IMessage;
import com.pwrd.war.core.msg.recognizer.IMessageRecognizer;
import com.pwrd.war.core.msg.special.SequenceMessage;
import com.pwrd.war.core.msg.sys.SessionClosedMessage;
import com.pwrd.war.core.server.IMessageProcessor;

/**
 * 顺序的NIO请求客户端
 * 
 * 
 */
public class NIOSequenceClient {
	private final static Logger logger = LoggerFactory
			.getLogger("NIOSequenceClient");
	private final static long responseTimeout = 15000;
	private NIOClientConnection servreConnection;
	private final AtomicInteger tokenSequence = new AtomicInteger(0);
	private final ScheduledExecutorService executor;
	private final Map<Integer, ResponseCallbackWrapper> sequcenceMap = new HashMap<Integer, ResponseCallbackWrapper>();

	/**
	 * 
	 * @param host
	 *            服务器地址
	 * @param port
	 *            服务器的端口
	 * @param messageRecognizer
	 *            接收服务器发来的消息识别器
	 * @param messageProcessor
	 *            消息处理器
	 * @param reconnectExecutorService
	 *            重新连接的执行线程池服务
	 */
	public NIOSequenceClient(String host, int port,
			IMessageRecognizer messageRecognizer,
			final IMessageProcessor messageProcessor,
			ScheduledExecutorService reconnectExecutorService) {
		this.executor = reconnectExecutorService;
		ClientToServerIoHandler _ioHandler = new ClientToServerIoHandler();

		IMessageProcessor _processorWrapper = new IMessageProcessor() {
			@SuppressWarnings("unchecked")
			@Override
			public void put(final IMessage msg) {
				if (logger.isDebugEnabled()) {
					logger.debug("【Receive】" + msg);
				}
				if (msg instanceof SequenceMessage) {
					// 如果是SequenceMsg,首先查找记录的回调接口对象,如果找到则将其放入messageProcessor中继续处理,否则忽略处理
					SequenceMessage _sequnceMsg = (SequenceMessage) msg;
					ResponseCallbackWrapper _callback = getSequence(_sequnceMsg
							.getSequenceId());
					if (_callback != null) {
						_callback.cancelTriggerTimeout();
						_sequnceMsg.setCallback(_callback);
					} else {
						if (logger.isDebugEnabled()) {
							logger.debug("Unknown seqence:" + msg
									+ ", will execute msg directly");
						}
						msg.execute();// added by zhangwh 2010/8/2
						return;
					}
				} else if (msg instanceof SessionClosedMessage) {
					// 到服务器的连接被关闭了,强制取消所有的请求,并通知调用者
					logger
							.info("The server connection closed,cancel all sequence request");
					cancelAllSequence();
					return;
				}
				messageProcessor.put(msg);
			}

			@Override
			public void start() {
				messageProcessor.start();
			}

			@Override
			public void stop() {
				messageProcessor.stop();
			}

			@Override
			public boolean isFull() {
				return messageProcessor.isFull();
			}
		};
		_ioHandler.setMessageProcessor(_processorWrapper);
		servreConnection = new NIOClientConnection(host, host, port,
				messageRecognizer, _ioHandler, executor);
		servreConnection.open();
	}

	/**
	 * 发送带序号的消息
	 * 
	 * @param req
	 *            带序号的消息
	 * @param responsCallback
	 *            该消息响应的回调接口
	 */
	public void sendMessage(SequenceMessage req,
			NIOSequenceClient.ResponseCallback responsCallback) {
		req.setSequenceId(this.tokenSequence.addAndGet(1));
		boolean _ok = false;
		final Integer _sequenceId = req.getSequenceId();
		ResponseCallbackWrapper _callback = new ResponseCallbackWrapper(
				_sequenceId, responsCallback);
		try {
			putSequence(_sequenceId, _callback);
			if (servreConnection.sendMessage(req)) {
				_callback.triggerTimeout();
				_ok = true;
			}
		} finally {
			if (!_ok) {
				_callback = getSequence(req.getSequenceId());
				_callback.onError();
			}
		}
	}

	/**
	 * 直接发送消息
	 * 
	 * @param message
	 */
	public void sendMessage(IMessage message) {
		this.servreConnection.sendMessage(message);
	}

	private final synchronized void putSequence(final Integer sequenceId,
			final ResponseCallbackWrapper callback) {
		this.sequcenceMap.put(sequenceId, callback);
	}

	private final synchronized ResponseCallbackWrapper getSequence(
			Integer sequenceId) {
		return this.sequcenceMap.remove(sequenceId);
	}

	private final synchronized void cancelAllSequence() {
		for (ResponseCallbackWrapper _wrapper : this.sequcenceMap.values()) {
			_wrapper.onTimeout();
		}
		this.sequcenceMap.clear();
	}

	@SuppressWarnings("unchecked")
	private final class ResponseCallbackWrapper implements
			NIOSequenceClient.ResponseCallback {
		private final NIOSequenceClient.ResponseCallback callback;
		private final Integer sequenceId;
		private volatile ScheduledFuture future;
		private volatile boolean canceld = false;

		public ResponseCallbackWrapper(Integer sequenceId,
				NIOSequenceClient.ResponseCallback callback) {
			this.sequenceId = sequenceId;
			this.callback = callback;
		}

		@SuppressWarnings("unused")
		public void setFuture(ScheduledFuture future) {
			this.future = future;
		}

		@SuppressWarnings("unused")
		public Integer getSequenceId() {
			return sequenceId;
		}

		@Override
		public void onTimeout() {
			if (logger.isInfoEnabled()) {
				logger.info("Timeout sequence id[" + this.sequenceId + "]");
			}
			this.cancelTriggerTimeout();
			callback.onTimeout();
		}

		@Override
		public void onResponse(SequenceMessage msg) {
			if (logger.isInfoEnabled()) {
				logger.info("Response sequence id[" + this.sequenceId + "]");
			}

			this.cancelTriggerTimeout();
			callback.onResponse(msg);
		}

		@Override
		public void onError() {
			if (logger.isInfoEnabled()) {
				logger.info("Error sequence id[" + this.sequenceId + "]");
			}

			this.cancelTriggerTimeout();
			callback.onError();

		}

		public final void triggerTimeout() {
			if (future != null) {
				throw new IllegalStateException("The trigger has been inited.");
			}
			ScheduledFuture _future = executor.schedule(new Runnable() {
				@Override
				public void run() {
					ResponseCallbackWrapper _callback = getSequence(sequenceId);
					if (_callback != null && !canceld) {
						if (logger.isInfoEnabled()) {
							logger.info("Trigger timeout sequence id["
									+ sequenceId + "]");
						}
						_callback.onTimeout();
					}
				}
			}, responseTimeout, TimeUnit.MILLISECONDS);
			this.future = _future;
		}

		private final void cancelTriggerTimeout() {
			if (this.canceld) {
				return;
			}
			if (logger.isDebugEnabled()) {
				logger.debug("Cancel trigger sequence id[" + this.sequenceId
						+ "]");
			}
			this.canceld = true;
			ScheduledFuture _future = this.future;
			this.future = null;
			if (_future == null) {
				// 还没有触发器,不做处理,返回
				return;
			}
			// 停止触发
			_future.cancel(true);
		}
	}

	/**
	 * 
	  *
	 * 
	 */
	public static interface ResponseCallback {
		/**
		 * 消息成功返回时调用
		 * 
		 * @param msg
		 */
		public void onResponse(SequenceMessage msg);

		/**
		 * 调用过程超时
		 */
		public void onTimeout();

		/**
		 * 调用过程中出错
		 */
		public void onError();
	}
}
