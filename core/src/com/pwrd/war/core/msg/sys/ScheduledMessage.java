package com.pwrd.war.core.msg.sys;

import java.util.concurrent.ScheduledFuture;

import org.apache.mina.common.ByteBuffer;

import com.pwrd.war.core.msg.IMessage;
import com.pwrd.war.core.msg.MessageParseException;
import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.core.util.StringUtils;

/**
 * 系统内部的调度消息
 * 
 * 
 */
public abstract class ScheduledMessage implements IMessage {
	public static final int MESSAGE_STATE_INITIALIZED = 0; // 消息初始化
	public static final int MESSAGE_STATE_WAITING = 1; // 消息处于等待状态，可以被取消
	public static final int MESSAGE_STATE_INQUEUE = 2; // 执行中，已经投递到消息队列中了
	public static final int MESSAGE_STATE_CANCELED = 3; // 被取消
	public static final int MESSAGE_STATE_EXECUTING = 4; // 执行中，从队列中取出了
	public static final int MESSAGE_STATE_DONE = 5; // 执行完毕

	private ScheduledFuture<?> future;
	private int state;
	private final long createTimestamp;
	private long trigerTimestamp;

	@Override
	public int getInitBufferLength() {
		return 0;
	}

	@Override
	public int getLength() {
		return 0;
	}

	public ScheduledMessage(long createTime) {
		setState(MESSAGE_STATE_INITIALIZED);
		this.createTimestamp = createTime;
	}

	public long getTrigerTimestamp() {
		return trigerTimestamp;
	}

	public void setTrigerTimestamp(long finishTimestamp) {
		this.trigerTimestamp = finishTimestamp;
	}

	public synchronized int getState() {
		return state;
	}

	public synchronized boolean isCanceled() {
		return state == MESSAGE_STATE_CANCELED;
	}

	public synchronized void setState(int state) {
		this.state = state;
	}

	public ScheduledFuture<?> getFuture() {
		return future;
	}

	public void setFuture(ScheduledFuture<?> future) {
		this.future = future;
	}

	public synchronized void cancel() {
		if (state == MESSAGE_STATE_WAITING) {
			future.cancel(false);
			this.setState(MESSAGE_STATE_CANCELED);
		} else if (state == MESSAGE_STATE_INQUEUE) {
			this.setState(MESSAGE_STATE_CANCELED);
		}
	}

	public long getCreateTimestamp() {
		return createTimestamp;
	}

	@Override
	public boolean read() throws MessageParseException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean write() throws MessageParseException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setBuffer(ByteBuffer buf) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String toString() {
		return StringUtils.obj2String(this, null);
	}

	@Override
	public short getType() {
		return MessageType.SYS_SCHEDULE;
	}

	@Override
	public String getTypeName() {
		return "SYS_SCHEDULE";
	}
}
