package com.pwrd.war.gameserver.prize;

import java.text.MessageFormat;
import java.util.Arrays;

import com.pwrd.war.common.LogReasons.CurrencyLogReason;
import com.pwrd.war.common.LogReasons.ItemGenLogReason;
import com.pwrd.war.common.LogReasons.MoneyLogReason;
import com.pwrd.war.common.model.quest.MoneyBonus;
import com.pwrd.war.gameserver.currency.Currency;
import com.pwrd.war.gameserver.currency.CurrencyCostType;
import com.pwrd.war.gameserver.human.Human;
import com.pwrd.war.gameserver.item.ItemParam;
import com.pwrd.war.gameserver.player.Player;

/**
 * 奖励基类
 * 
 * 
 */
public abstract class BasePrizeHolder {
	
	/** 奖励物品列表 */
	private ItemParam[] itemList;
	
	/** 货币奖励列表 */
	private MoneyBonus[] coinList;
	
	abstract public int getUniqueId();

	public ItemParam[] getItemList() {
		return itemList;
	}


	public MoneyBonus[] getCoinList() {
		return coinList;
	}

	public void setItemList(ItemParam[] itemList) {
		this.itemList = itemList;
	}

	public void setCoinList(MoneyBonus[] coinList) {
		this.coinList = coinList;
	}

	/**
	 * 检查奖励是否可以领取
	 * 
	 * @param player
	 * @return
	 */
	public boolean checkPlayerCanGet(Player player) {
		Human human = player.getHuman();
		// 判断背包空间
		if (itemList != null) {
			if (!human.getInventory().checkSpace(Arrays.asList(itemList), true)) {
				return false;
			}
		}

		// 此处没有对金钱进行判断，系统默认为任何时候都可以给

		return true;
	}

	/**
	 * 奖励玩家
	 * 
	 * @param player
	 */
	public void doPrizePlayer(Player player) {
		Human human = player.getHuman();
		// 给玩家物品
		if (itemList != null) {
			human.getInventory().addAllItems(
					Arrays.asList(itemList),
					ItemGenLogReason.PLATFORM_PRIZE, 
					MessageFormat.format(ItemGenLogReason.PLATFORM_PRIZE.getReasonText(),getUniqueId()),
					true);
		}

		// 给玩家金钱
		if (coinList != null) {
			for (MoneyBonus mb : coinList) {
				Currency currency = Currency.valueOf(mb.getType());
				human.giveMoney(mb.getMoney(), currency, true,
						CurrencyLogReason.XITONG, 
						CurrencyLogReason.XITONG.getReasonText());
			}
		}
	}

}
