package com.pwrd.war.core.msg.special;

import com.pwrd.war.core.client.NIOSequenceClient;
import com.pwrd.war.core.msg.BaseMessage;


/**
 * 序列的消息
 * 
 * 
 */
public abstract class SequenceMessage extends BaseMessage {
	/** 该消息的序列号 */
	protected int sequenceId;
	
	/** 响应消息回调接口 */
	protected NIOSequenceClient.ResponseCallback  callback;


	@Override
	protected final boolean readImpl() {
		this.sequenceId = readInt();
		return this.readContent();
	}

	@Override
	protected final boolean writeImpl() {
		this.writeInt(this.sequenceId);
		return this.writeContent();
	}

	/**
	 * 具体的消息读取
	 * 
	 * @return
	 */
	protected abstract boolean readContent();

	/**
	 * 具体的消息写入
	 * 
	 * @return
	 */
	protected abstract boolean writeContent();

	public int getSequenceId() {
		return sequenceId;
	}

	public void setSequenceId(int sequenceId) {
		this.sequenceId = sequenceId;
	}
	
	public NIOSequenceClient.ResponseCallback getCallback() {
		return callback;
	}

	public void setCallback(NIOSequenceClient.ResponseCallback callback) {
		this.callback = callback;
	}
}
