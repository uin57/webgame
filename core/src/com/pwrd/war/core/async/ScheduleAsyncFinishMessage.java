package com.pwrd.war.core.async;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.core.msg.sys.ScheduledMessage;

/**
 * 调度执行的异步消息通知,主要用于当异步消息处理完毕后，通知由主线程进行收尾处理
 * 
 */
public class ScheduleAsyncFinishMessage extends ScheduledMessage {

	private final AsyncOperation operation;

	public ScheduleAsyncFinishMessage(long createTime, AsyncOperation operation) {
		super(createTime);
		this.operation = operation;
	}

	public AsyncOperation getOperation() {
		return operation;
	}

	@Override
	public short getType() {
		return MessageType.SCHD_ASYNC_FINISH;
	}

	@Override
	public String getTypeName() {
		String operationName = this.operation != null ? this.operation
				.toString() : "null";
		return "SCHD_ASYNC_FINISH [" + operationName + "]";
	}

	@Override
	public void execute() {
		operation.execute();
	}
}
