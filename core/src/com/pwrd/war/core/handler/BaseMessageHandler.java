package com.pwrd.war.core.handler;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pwrd.war.core.msg.IMessage;
import com.pwrd.war.core.server.IMessageHandler;

/**
 * 消息处理器注册的统一接口
 * 
 * 
 */
public class BaseMessageHandler implements IMessageHandler<IMessage> {
	private static Logger logger = LoggerFactory
			.getLogger(BaseMessageHandler.class);
	private final Map<Short, IMessageHandler<IMessage>> handlers = new HashMap<Short, IMessageHandler<IMessage>>();

	public void execute(IMessage message) {
		getHandler(message.getType()).execute(message);
	}

	/**
	 * 注册IMessageHandler
	 * 
	 * @param handler
	 * @throws IllegalStateException
	 *             当重复注册同一消息的类型的处理器时会抛出此异常
	 * @throws IllegalArgumentException
	 *             handler为空时
	 * 
	 */
	public void registerHandler(IMessageHandler<IMessage> handler) {
		if (handler == null) {
			throw new IllegalArgumentException("No handler");
		}
		final short[] types = handler.getTypes();
		if (types == null) {
			if (logger.isWarnEnabled()) {
				logger.warn("No types with handler [" + handler + "]");
			}
			return;
		}
		for (int i = 0; i < types.length; i++) {
			if (logger.isDebugEnabled()) {
				logger.debug("Add handler for type [" + types[i]
						+ "],handler class " + handler.getClass());
			}
			IMessageHandler<IMessage> _handler = handlers.get(types[i]);
//			if (_handler != null) {
//				throw new IllegalStateException(
//						"Duplicate Message Handler for type [" + types[i]
//								+ " with handler "  + "]");
//			}
			handlers.put(types[i], handler);
		}
	}

	public IMessageHandler<IMessage> getHandler(short messageType) {
		IMessageHandler<IMessage> handler = handlers.get(messageType);
		if (handler == null) {
			return dumyHandler;
		}
		return handler;
	}

	@Override
	public short[] getTypes() {
		return new short[0];
	}

	private final DumyHandler dumyHandler = new DumyHandler();

}
