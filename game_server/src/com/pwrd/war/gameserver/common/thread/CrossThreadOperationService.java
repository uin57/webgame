package com.pwrd.war.gameserver.common.thread;

import com.pwrd.war.core.async.AsyncService;
import com.pwrd.war.gameserver.player.OnlinePlayerService;
import com.pwrd.war.gameserver.player.Player;

/**
 * 跨线程操作的服务
 * @author yue.yan
 *
 */
public class CrossThreadOperationService {

	/** 异步服务 */
	private AsyncService asyncService;
	/** 在线玩家服务 */
	private OnlinePlayerService onlinePlayerService;
	
	public CrossThreadOperationService(AsyncService asyncService, OnlinePlayerService onlinePlayerService) {
		this.asyncService = asyncService;
		this.onlinePlayerService = onlinePlayerService;
	}
	
	/**
	 * 处理对其他玩家数据的操作
	 * @param operation
	 */
	public void executeOtherRoleOperation(IOtherRoleOperation operation) {
		//检查玩家是否在线
		Player player = onlinePlayerService.getPlayer(operation.getRoleUUID());
		if(player != null) {
			//在线的处理：生成内部消息，扔到玩家的消息队列中
			SysCommonOnlineRoleOperationMsg sysMsg = new SysCommonOnlineRoleOperationMsg(operation);
			player.putMessage(sysMsg);
		} else {
			//离线的处理：生成IoOperation，用异步服务执行
			CommonOfflineRoleIoOperation offlineIoOperation = new CommonOfflineRoleIoOperation(operation);
			asyncService.createOperationAndExecuteAtOnce(offlineIoOperation);
		}
	}
	
	//public void 
}
