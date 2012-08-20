package com.pwrd.war.core.msg;

/**
 * 用于测试消息长度
 * 
 * 
 */
public class SysTestMsgLength extends BaseMessage implements IMessage {
	private int length;

	public SysTestMsgLength(int length) {
		this.length = length;
	}

	public SysTestMsgLength() {
	}

	/**
	 * 消息的产生不能从客户端触发
	 */
	@Override
	protected final boolean readImpl() {
		byte[] bytes = new byte[getLength() - IMessage.HEADER_SIZE];
		readBytes(bytes);
		System.out
				.println("[#SysTestMsgLength] Read bytes num:" + bytes.length);
		return true;
	}

	/**
	 * 消息不能输出到客户端
	 */
	@Override
	protected final boolean writeImpl() {
		writeByteArray(new byte[length]);
		System.out.println("[#SysTestMsgLength] Write bytes num:"
				+ (length - 4));
		return true;
	}

	@Override
	public short getType() {
		return MessageType.SYS_TEST_MSG_LENGTH;
	}

	@Override
	public void execute() {
	}

}
