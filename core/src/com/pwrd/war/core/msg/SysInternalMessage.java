package com.pwrd.war.core.msg;

/**
 * 系统内部产生的消息,用于根据用户的状态产生模拟消息,这种类型的消息都不
 * 应该输出到客户端，并且不能从客户端读取产生
 * 
  *
 */
public abstract class SysInternalMessage extends BaseMessage implements IMessage {
	
	public SysInternalMessage() {
		super();
	}

	/**
	 * 消息的产生不能从客户端触发
	 */
	@Override
	protected final boolean readImpl() {
		throw new UnsupportedOperationException();
	}

	/**
	 * 消息不能输出到客户端
	 */
	@Override
	protected final boolean writeImpl() {
		throw new UnsupportedOperationException();
	}

}