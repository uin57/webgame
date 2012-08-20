package com.pwrd.war.core.msg;

/**
 * 
 * 洪水攻击测试消息
 * 
 */
public class SysFloodBytesAttack extends BaseMessage implements IMessage {
	
	private int len = 0;
	
	public SysFloodBytesAttack()
	{		
	}
	
	public SysFloodBytesAttack(int len) {
		this.len = len;
	}

	@Override
	protected final boolean readImpl() {
		byte[] bytes = new byte[Math.max(0, this.len - IMessage.HEADER_SIZE)];
		readBytes(bytes);
		return true;
	}

	/**
	 * 消息不能输出到客户端
	 */
	@Override
	protected final boolean writeImpl() {
		writeByteArray(new byte[this.len]);
		return true;
	}

	@Override
	public void execute() {
	}

	@Override
	public short getType() {
		return MessageType.SYS_TEST_MSG_LENGTH;
	}
}
