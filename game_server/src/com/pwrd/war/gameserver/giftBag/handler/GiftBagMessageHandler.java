/**
 * 
 */
package com.pwrd.war.gameserver.giftBag.handler;

import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.giftBag.msg.CGGetGiftBagPrize;
import com.pwrd.war.gameserver.giftBag.msg.CGGiftBagInfoList;
import com.pwrd.war.gameserver.player.Player;

/**
 * 礼包消息处理器
 * @author dengdan
 *
 */
public class GiftBagMessageHandler {

	/**
	 * 领取礼包奖励
	 */
	public void handleGetGiftBagPrize(Player player,CGGetGiftBagPrize msg){
		Globals.getGiftBagService().giveGiftPrize(player, msg.getId(), msg.getGiftId());
	}
	
	/**
	 * 请求礼包信息列表
	 * @param player
	 * @param msg
	 */
	public void handleGiftBagInfoList(Player player,CGGiftBagInfoList msg){
		Globals.getGiftBagService().sendGiftList(player);
	}
}
