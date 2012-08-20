package com.pwrd.war.core.server;

import java.util.HashMap;
import java.util.Map;

import com.pwrd.war.core.msg.IMessage;

/**
 * 消息转发器
 * 
 */
public class MessageDispatcher<T extends IMessageProcessor> implements IMessageProcessor {
	/**
	 * 各子系统的消息分类{@link IMessageProcessor}的分类型配置,用于根据消息的类型分配到对应的
	 * {@link IMessageProcessor}中处理,用于处理一些相对独立的子系统的消息,比如好友系统,公会系统的消息
	 */
	private final Map<Class<?>, IMessageProcessor> subSystemProcessorMap = new HashMap<Class<?>, IMessageProcessor>();

	/** 主消息处理器,用于处理一些状态全局共享的数据 */
	private final T mainProcessor;

	/** 停止处理标识 */
	private volatile boolean stop = false;
	private volatile boolean isStarted = false;

	/**
	 * 
	 * @param mainProcessor
	 */
	public MessageDispatcher(T mainProcessor) {
		this.mainProcessor = mainProcessor;
	}

	/**
	 * 
	 * @param messageType
	 * @param processor
	 */
	public void registerMessageProcessor(Class<?> messageType,IMessageProcessor processor) {
		IMessageProcessor _processor = getSubSystemMessageProcessor(messageType);
		if (_processor != null) {
			throw new IllegalArgumentException("The message type class["
					+ messageType + "] has already been registed with ["
					+ _processor + "]");
		}
		this.subSystemProcessorMap.put(messageType, processor);
	}

	/**
	 * 将待处理消息放入队列与消息类型相匹配的{@link IMessageProcessor}的处理队列中
	 */
	public void put(IMessage msg) {
		if (!stop) {
			final Class<?> _msgClass = msg.getClass();
			IMessageProcessor _processor = getSubSystemMessageProcessor(_msgClass);
			if (_processor != null) {
				_processor.put(msg);
			} else {
				// 没有注册,由全局共享的处理器处理
				this.mainProcessor.put(msg);
			}
		}
	}

	/**
	 * 开始处理
	 */
	public void start() {
		if (isStarted) {
			return;
		}
		isStarted = true;
		stop = false;
		this.mainProcessor.start();
		for (Map.Entry<Class<?>, IMessageProcessor> _entry : this.subSystemProcessorMap
				.entrySet()) {
			_entry.getValue().start();
		}
	}

	/**
	 * 停止处理
	 */
	public void stop() {
		stop = true;
		this.mainProcessor.stop();
		for (Map.Entry<Class<?>, IMessageProcessor> _entry : this.subSystemProcessorMap
				.entrySet()) {
			_entry.getValue().stop();
		}
	}
	
	public T getMainProcessor() {
		return mainProcessor;
	}

	/**
	 * 根据消息的类型取得对应的子系统的{@link IMessageProcessor}
	 * 
	 * @param msgClass
	 * @return
	 */
	private IMessageProcessor getSubSystemMessageProcessor(final Class<?> msgClass) {
		for (Map.Entry<Class<?>, IMessageProcessor> _entry : this.subSystemProcessorMap
				.entrySet()) {
			Class<?> _type = _entry.getKey();
			if (_type.isAssignableFrom(msgClass)) {
				return _entry.getValue();
			}
		}
		return null;
	}

	@Override
	public boolean isFull() {
		return false;
	}

}
