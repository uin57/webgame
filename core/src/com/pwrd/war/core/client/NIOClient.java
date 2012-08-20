package com.pwrd.war.core.client;

import java.util.concurrent.ExecutorService;

import com.pwrd.war.core.msg.IMessage;
import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.core.msg.recognizer.BaseMessageRecognizer;
import com.pwrd.war.core.msg.recognizer.IMessageRecognizer;
import com.pwrd.war.core.server.AbstractIoHandler;
import com.pwrd.war.core.server.IMessageProcessor;
import com.pwrd.war.core.session.MinaSession;

/**
 * 普通的NIO客户端,主要完成以下功能:
 * <ul>
 * <li>建立与服务器之间的连接</li>
 * <li>接收从服务器返回的消息,交由{@link #messageProcessor}处理</li>
 * </ul>
 * 
 * 
 */
public class NIOClient {

	/** Server地址 */
	private final String ip;

	/** Server端口 */
	private final int port;

	/** Server的连接 */
	private final NIOClientConnection connection;

	/** 消息处理器 */
	private final IMessageProcessor messageProcessor;

	/** 消息识别器 */
	private final IMessageRecognizer messageRecognizer;
	
	/** Io处理器 */
	private final AbstractIoHandler<? extends MinaSession> messageIoHandler;

	/** 连接回调接口 */
	private final ConnectionCallback connectionCallback;

	/**
	 * 
	 * @param ip
	 *            Server的地址
	 * @param port
	 *            Server监听的端口
	 * @param executor
	 *            执行重连接操作的服务
	 * @param messageProcessor
	 *            消息处理器,将收到的消息交由该处理器进行处理
	 * @param worldMessageRecognizers
	 *            消息识别器列表,用于注册多个消息识别器
	 */
	public NIOClient(String destName, String ip, int port,
			ExecutorService executor, 
			final IMessageProcessor messageProcessor,
			final BaseMessageRecognizer messageRecognizer,
			final AbstractIoHandler<? extends MinaSession> messageIoHandler,
			final ConnectionCallback connectionCallback
			) {
		if (ip == null || port <= 0 || executor == null) {
			throw new IllegalArgumentException(
					"The ip,port and threadPool must not be null or empty");
		}
		this.ip = ip;
		this.port = port;
		this.messageProcessor = new MessageProcessorWrapper(messageProcessor);
		this.messageRecognizer = messageRecognizer;
		this.connectionCallback = connectionCallback;
		this.messageIoHandler = messageIoHandler;
		this.messageIoHandler.setMessageProcessor(this.messageProcessor);
		connection = new NIOClientConnection(destName, this.ip, this.port,
				this.messageRecognizer , this.messageIoHandler, executor);
	}

	/**
	 * 检查是否已经连接上
	 * 
	 * @return
	 */
	public boolean isConnected() {
		return this.connection.isConnect();
	}


	/**
	 * 建立连接
	 * 
	 * @return
	 */
	public boolean open() {
		return this.connection.open();
	}

	/**
	 * 关闭连接
	 */
	public void close() {
		this.connection.close();
	}

	/**
	 * 发送消息
	 * 
	 * @param message
	 * @return
	 */
	public boolean sendMessage(IMessage message) {
		return this.connection.sendMessage(message);
	}
	
	
	/**
	 * 得到messageProcessor
	 * @return
	 */
	public IMessageProcessor getMessageProcessor() {
		return messageProcessor;
	}

	public NIOClientConnection getConnection() {
		return connection;
	}

	@Override
	public String toString() {
		return this.connection.toString();
	}

	/**
	 * 
	 * 包装器,便于调用@link NIOClientConnection
	 */
	private final class MessageProcessorWrapper implements IMessageProcessor {
		private final IMessageProcessor messageProcessor;

		private MessageProcessorWrapper(IMessageProcessor messageProcessor) {
			this.messageProcessor = messageProcessor;
		}

		@Override
		public void put(IMessage msg) {
			if (msg.getType() == MessageType.SYS_SESSION_OPEN) {
				if (connectionCallback != null) {
					connectionCallback.onOpen(NIOClient.this, msg);
				}
			} else if (msg.getType() == MessageType.SYS_SESSION_CLOSE) {
				if (connectionCallback != null) {
					connectionCallback.onClose(NIOClient.this, msg);
				}
			} else {
				messageProcessor.put(msg);
			}
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
	}

	/**
	 * 客户端连接状态回调接口
	 * 
	  *
	 * 
	 */
	public static interface ConnectionCallback {
		/**
		 * 连接打开
		 * 
		 * @param msg
		 */
		public void onOpen(NIOClient client, IMessage msg);

		/**
		 * 连接关闭
		 * 
		 * @param msg
		 */
		public void onClose(NIOClient client, IMessage msg);
	}
}
