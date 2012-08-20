package com.pwrd.war.gameserver.charge;


import org.slf4j.Logger;

import com.pwrd.war.common.constants.Loggers;
import com.pwrd.war.common.constants.SharedConstants;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.common.i18n.constants.CommonLangConstants_10000;
import com.pwrd.war.gameserver.human.Human;
import com.pwrd.war.gameserver.player.Player;
import com.pwrd.war.gameserver.player.async.PlayerQueryAccount;


public class ChargeLogicalProcessor {
	
	public static final Logger CHARGE_LOGGER = Loggers.chargeLogger;
	
	
	public ChargeLogicalProcessor() {
	}
	
	/**
	 * 查询MM数量 : 相当于打开充值面板，按照充值类型不同返回不同数据
	 * 
	 * @param player
	 */
	public void queryPlayerAccount(Player player) {
		if(!Globals.getServerConfig().getFuncSwitches().isChargeEnabled()){
			player.sendSystemMessage(CommonLangConstants_10000.FUNC_INVALID);
			return;
		}
		
		Human human = player.getHuman();
		if (human == null) {
			return;
		}
		
		if (!Globals.getServerConfig().isTurnOnLocalInterface()) {
			player.sendSystemMessage(CommonLangConstants_10000.LOCAL_TURN_OFF);
			return;
		}
						
		PlayerQueryAccount _queryOper = new PlayerQueryAccount(player.getRoleUUID());
		Globals.getAsyncService().createOperationAndExecuteAtOnce(_queryOper,player.getRoleUUID());		
	}
	
	/**
	 * 自由兑换模式：MM兑换金币公式
	 * 
	 * @param mmCount
	 * @return
	 */
	private int getGoldConverted(int mmCount) {
		int rate = SharedConstants.CHARGE_MM_2_GOLD_RATE;
		return mmCount * rate;
	}
	
	/**
	 * 自由兑换模式：玩家充入金币，使用MM兑换金币
	 * 
	 * @param player
	 * @param mmCost
	 *            要兑换多少MM
	 */
	public void chargeGold(Player player, int mmCost) {
		
		if(!Globals.getServerConfig().getFuncSwitches().isChargeEnabled()){
//			player.sendErrorMessage(CommonLangConstants_10000.GAME_CHARGE_SWITCH_CLOSED);
			return;
		}
		
		Human _human = player.getHuman();
		if (_human == null) {
			return;
		}
		
		if (mmCost > SharedConstants.MAX_EXCHANGE_MM) {
			//兑换的MM太多了
//			player.sendSystemMessage(CommonLangConstants_10000.GAME_CHARGE_DIAMOND_MM_TOO_MANY);
			return;
		}
		//TODO
//		if (mmCost > 0) {
//			int _diamondConv = getGoldConverted(mmCost);//可以兑换成多少金币
//			int _diamondBefore = _human.getGold();
//			int _diamondWill = _diamondBefore + _diamondConv;
//			if (_diamondWill <= 0 || _diamondWill > SharedConstants.MAX_DIAMOND_CARRY_AMOUNT) {
//				//钻石已经太多了，再充下去会溢出的
//				player.sendSystemMessage(LangConstants.GAME_BEFORE_CHARGE_DIAMOND_OVER_FLOW);
//				return;
//			}
//			PlayerChargeDiamond _chargOper = new PlayerChargeDiamond(player.getRoleUUID(), mmCost, _diamondConv);
//			Globals.getAsyncService().createOperationAndExecuteAtOnce(_chargOper,player.getRoleUUID());
//		} else {
//			//MM数量是负数
//			player.sendSystemMessage(LangConstants.GAME_CHARGE_DIAMOND_MM_ILLEGAL);
//		}
	}

}
