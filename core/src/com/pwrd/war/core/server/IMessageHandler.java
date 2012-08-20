package com.pwrd.war.core.server;

import com.pwrd.war.core.msg.IMessage;

/**
 * 消息逻辑处理器
 * 
 */
public interface IMessageHandler<T extends IMessage> {
	/**
	 * 处理消息
	 * 
	 * @param message
	 *            需要被处理的消息
	 */
	public void execute(T message);
	
	/**
	 * 取得此处理器可以处理的消息类型
	 * 
	 * @return
	 */
	public short[] getTypes();
}
