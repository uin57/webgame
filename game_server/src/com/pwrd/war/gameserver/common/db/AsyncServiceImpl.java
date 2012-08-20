package com.pwrd.war.gameserver.common.db;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pwrd.war.common.constants.CommonErrorLogInfo;
import com.pwrd.war.core.async.AsyncOperation;
import com.pwrd.war.core.async.AsyncService;
import com.pwrd.war.core.async.IIoOperation;
import com.pwrd.war.core.async.SyncOperation;
import com.pwrd.war.core.server.IMessageProcessor;
import com.pwrd.war.core.server.NamedThreadFactory;
import com.pwrd.war.core.util.ErrorsUtil;
import com.pwrd.war.core.util.ExecutorUtil;
import com.pwrd.war.gameserver.common.db.operation.BindUUIDIoOperation;
import com.pwrd.war.gameserver.startup.GameServerRuntime;

/**
 * 异步操作管理器
 * 
  *
 * 
 */
public class AsyncServiceImpl implements AsyncService {
	private static final Logger logger = LoggerFactory.getLogger("async");

	/** 位于主线程的消息处理器,用于当异步操作完成后,在主线程中执行收尾操作 */
	private final IMessageProcessor messageProcessor;
	/** 与玩家角色绑定的线程池,即根据玩家角色的id号{@link PlayerCharacter#getTemplateId()},固定的操作总在同一个线程池中运行 */
	private final ExecutorService[] charBindExecutors;
	/** 不与玩家角色绑定的线程池 */
	private final ExecutorService charUnBindExecutor;

	public AsyncServiceImpl(final int charBindExecutorSize, final int charUnBindExecutorSize,
			IMessageProcessor messageProcessor) {
		charBindExecutors = new ExecutorService[charBindExecutorSize];
		for (int i = 0; i < charBindExecutorSize; i++) {
			charBindExecutors[i] = Executors.newSingleThreadExecutor(new NamedThreadFactory(getClass()));
		}
		this.messageProcessor = messageProcessor;
		this.charUnBindExecutor = Executors.newFixedThreadPool(charUnBindExecutorSize,new NamedThreadFactory(getClass()));
	}
	
	public void stop() {
		try {
			for (ExecutorService _executor : this.charBindExecutors) {
				ExecutorUtil.shutdownAndAwaitTermination(_executor);
			}
			ExecutorUtil.shutdownAndAwaitTermination(this.charUnBindExecutor);
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {
				logger.error(ErrorsUtil.error(CommonErrorLogInfo.DB_OPERATE_FAIL, "#GS.AsyncServiceImpl.stop", null), e);
			}
		}
	}


	@Override
	public SyncOperation createSyncOperationAndExecuteAtOnce(IIoOperation operation) {
		SyncOperation _operation = new SyncOperation(operation);
		_operation.execute();
		return _operation;
	}

	@Override
	public AsyncOperation createOperationAndExecuteAtOnce(IIoOperation operation) {
		if (GameServerRuntime.isShutdowning()) {
			if (logger.isErrorEnabled()) {
				logger.error(ErrorsUtil.error(CommonErrorLogInfo.INVALID_STATE,
						"#GS.AsyncServiceImpl.createOperationAndExecuteAtOnce", "Shutdowning the server"),
						new Exception());
			}
		}
		AsyncOperation _operation = this.createOperation(operation);
		_operation.execute();
		return _operation;
	}
	
	@Override
	public AsyncOperation createOperationAndExecuteAtOnce(IIoOperation operation, String uuid) {
		if (GameServerRuntime.isShutdowning()) {
			if (logger.isErrorEnabled()) {
				logger.error(ErrorsUtil.error(CommonErrorLogInfo.INVALID_STATE,
						"#GS.AsyncServiceImpl.createOperationAndExecuteAtOnce",
						"Shutdowning the server"), new Exception());
			}
		}
		AsyncOperation _operation = this.createOperation(operation,uuid);
		_operation.execute();
		return _operation;
	}
	
	private AsyncOperation createOperation(IIoOperation operation) {
		if (operation instanceof BindUUIDIoOperation) {
//			long _charId = ((BindUUIDIoOperation) operation).getBindUUID();
//			int _executorIndex = (int) (_charId % this.charBindExecutors.length);
			String _charId = ((BindUUIDIoOperation) operation).getBindUUID();
			int _executorIndex = (int) (_charId.hashCode() % this.charBindExecutors.length);
			_executorIndex = _executorIndex < 0 ? 0 : _executorIndex;
			ExecutorService _asyncExecutor = this.charBindExecutors[_executorIndex];
			if (logger.isDebugEnabled()) {
				logger.debug("[#GS.AsyncServiceImpl.createOperation] [char:" + _charId + " bind to executor :"
						+ _executorIndex + "]");
			}
			return new AsyncOperation(operation, _asyncExecutor, messageProcessor);
		} else {
			return new AsyncOperation(operation, charUnBindExecutor, messageProcessor);
		}
	}
	
	private AsyncOperation createOperation(IIoOperation operation,String uuid) {
		if (operation instanceof BindUUIDIoOperation) {
			String _charId = ((BindUUIDIoOperation) operation).getBindUUID();
//			int _executorIndex = (int) (_charId % this.charBindExecutors.length);
//			_executorIndex = _executorIndex < 0 ? 0 : _executorIndex;
			int _executorIndex = (int) (_charId.hashCode() % this.charBindExecutors.length);
			_executorIndex = _executorIndex < 0 ? 0 : _executorIndex;
			ExecutorService _asyncExecutor = this.charBindExecutors[_executorIndex];
			if (logger.isDebugEnabled()) {
				logger.debug("[#GS.AsyncServiceImpl.createOperation] [char:" + _charId + " bind to executor :"
						+ _executorIndex + "]");
			}
			return new AsyncOperation(operation, _asyncExecutor, messageProcessor,uuid);
		} else {
			return new AsyncOperation(operation, charUnBindExecutor, messageProcessor,uuid);
		}
	}
	
}
