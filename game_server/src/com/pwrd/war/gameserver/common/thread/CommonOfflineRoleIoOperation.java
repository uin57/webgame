package com.pwrd.war.gameserver.common.thread;

import com.pwrd.war.gameserver.common.db.operation.BindUUIDIoOperation;

/**
 * 统一定制的对其他离线玩家数据进行操作的IoOperation
 * @author yue.yan
 *
 */
public class CommonOfflineRoleIoOperation implements BindUUIDIoOperation{

	private IOtherRoleOperation operationImpl;
	
	public CommonOfflineRoleIoOperation(IOtherRoleOperation operationImpl) {
		this.operationImpl = operationImpl;
	}

	@Override
	public int doStart() {
		return STAGE_START_DONE;
	}
	
	@Override
	public int doIo() {
		//执行离线操作
		operationImpl.offlineAction();
		return STAGE_IO_DONE;
	}

	@Override
	public int doStop() {
		return STAGE_STOP_DONE;
	}

	@Override
	public String getBindUUID() {
		return operationImpl.getRoleUUID();
	}
	
}
