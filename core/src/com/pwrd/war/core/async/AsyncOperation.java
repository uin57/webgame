package com.pwrd.war.core.async;

import static com.pwrd.war.core.async.IIoOperation.STAGE_INITAILZED;
import static com.pwrd.war.core.async.IIoOperation.STAGE_IO_DONE;
import static com.pwrd.war.core.async.IIoOperation.STAGE_START_DONE;

import java.util.concurrent.ExecutorService;

import com.pwrd.war.core.server.IMessageProcessor;

/**
 * 异步操作
 * 
 * 
 */
public class AsyncOperation {
	/** 当前的状态 */
	private volatile int stage;
	/** 线程池 */
	private final ExecutorService asyncExecutor;
	/** 主线程处理器 */
	private final IMessageProcessor messageProcessor;
	/** 与IO相关的操作 */
	private final IIoOperation operation;
	private final String uuid;


	public AsyncOperation(IIoOperation operation, ExecutorService asyncExecutor, IMessageProcessor messageProcessor) {
		stage = STAGE_INITAILZED;
		this.operation = operation;
		this.asyncExecutor = asyncExecutor;
		this.messageProcessor = messageProcessor;
//		this.uuid = -1;
		this.uuid = "";
	}
	
	public AsyncOperation(IIoOperation operation,
			ExecutorService asyncExecutor, IMessageProcessor messageProcessor,
			String uuid) {
		stage = STAGE_INITAILZED;
		this.operation = operation;
		this.asyncExecutor = asyncExecutor;
		this.messageProcessor = messageProcessor;
		this.uuid = uuid;
	}

	/**
	 * 根据当前所处的状态来执行相应的操作
	 * 
	 * 框架根据返回值来决定调用的方法
	 * 实现者的状态如果更加复杂，可以根据内部状态来进一步决定doStartStep/doIoStep/doStopStep的执行内容
	 * 
	 * 这里无需指定参数，因为参数在其它步骤都已经获得了
	 */
	public void execute() {
		switch (stage) {
		case STAGE_INITAILZED: {
			stage = operation.doStart();			
			if (stage == STAGE_START_DONE) {
				// 启动线程执行IOStep
				this.asyncExecutor.execute(new Runnable() {
					@Override
					public void run() {
						execute();
					}
				});
			}else if(stage == STAGE_IO_DONE){
				stage= this.operation.doStop();	
			}
			break;
		}
		case STAGE_START_DONE: {
			stage = operation.doIo();			
			if (stage == STAGE_IO_DONE) {
				
//				if (uuid == -1) {
				if (uuid.equals("")) {
					// 给主线程发送消息
					ScheduleAsyncFinishMessage _msg = new ScheduleAsyncFinishMessage(
							System.currentTimeMillis(), this);
					messageProcessor.put(_msg);
				} else {
					// 目的为了让消息在玩家所在场景线程来处理
					SchedulePlayerAsyncFinishMessage _msg = new SchedulePlayerAsyncFinishMessage(
							System.currentTimeMillis(), this, uuid);
					messageProcessor.put(_msg);
				}
				
				
			} else {
				// 可能已经完成，就是一个单纯的异步调用，不需要通知主线程
			}
			break;
		}
		case STAGE_IO_DONE: {
			stage= this.operation.doStop();			
			break;
		}
		}
	}
	
	@Override
	public String toString(){
		String _operationName = this.operation!=null?this.operation.getClass().getName():"null";
		return this.getClass()+" operation["+_operationName+"]";
	}
}
