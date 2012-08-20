package com.pwrd.war.gameserver.common.thread;

import com.pwrd.war.core.msg.SysInternalMessage;

/**
 * 统一定制的执行对其他在线玩家数据操作的内部消息
 * @author yue.yan
 *
 */
public class SysCommonOnlineRoleOperationMsg extends SysInternalMessage  {

	private IOtherRoleOperation operationImpl;
	
	public SysCommonOnlineRoleOperationMsg(IOtherRoleOperation operationImpl) {
		this.operationImpl = operationImpl;
	}
	
	@Override
	public void execute() {
		//执行针对在线角色的行为
		operationImpl.onlineAction();
	}

}
