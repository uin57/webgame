package com.pwrd.war.gameserver.prize.handler;

import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.player.Player;
import com.pwrd.war.gameserver.player.async.PlayerGetPlatformPrize;
import com.pwrd.war.gameserver.player.async.PlayerGetUserPrize;
import com.pwrd.war.gameserver.player.async.PlayerQueryPlatformPrizes;
import com.pwrd.war.gameserver.player.async.PlayerQueryUserPrizes;
import com.pwrd.war.gameserver.prize.msg.CGPrizePlatformGet;
import com.pwrd.war.gameserver.prize.msg.CGPrizePlatformList;
import com.pwrd.war.gameserver.prize.msg.CGPrizeUserGet;
import com.pwrd.war.gameserver.prize.msg.CGPrizeUserList;

/**
 * 奖励处理器
 * 
 * 
 */
public class PrizeMessageHandler {
	
	/**
	 * 查询平台奖励信息
	 * @param player
	 * @param cgPrizePlatformList
	 */
	public void handlePrizePlatformList(Player player,
			CGPrizePlatformList cgPrizePlatformList) {
		// 执行异步查询
		PlayerQueryPlatformPrizes operation = new PlayerQueryPlatformPrizes(
				player);
		Globals.getAsyncService().createOperationAndExecuteAtOnce(operation,
				player.getRoleUUID());
	}

	/**
	 * 领取平台补偿奖励
	 * 
	 * @param player
	 * @param cgPrizePlatformGet
	 */
	public void handlePrizePlatformGet(Player player,
			CGPrizePlatformGet cgPrizePlatformGet) {

		PlayerGetPlatformPrize operation = new PlayerGetPlatformPrize(player,
				cgPrizePlatformGet.getUniqueId(),
				cgPrizePlatformGet.getPrizeId());
		Globals.getAsyncService().createOperationAndExecuteAtOnce(operation,
				player.getRoleUUID());

	}

	/**
	 * 查询GM补偿列表
	 * @param player
	 * @param cgPrizeUserList
	 */
	public void handlePrizeUserList(Player player,CGPrizeUserList cgPrizeUserList) {
		// 执行异步查询
		PlayerQueryUserPrizes operation = new PlayerQueryUserPrizes(player);
		Globals.getAsyncService().createOperationAndExecuteAtOnce(operation, player.getRoleUUID());
	}

	/**
	 * 领取GM平台的补偿奖励
	 * @param player
	 * @param cgPrizeUserGet
	 */
	public void handlePrizeUserGet(Player player, CGPrizeUserGet cgPrizeUserGet) {
		
		PlayerGetUserPrize operation = new PlayerGetUserPrize(player, cgPrizeUserGet.getPrizeId());		
		Globals.getAsyncService().createOperationAndExecuteAtOnce(operation, player.getRoleUUID());				
	}



}
