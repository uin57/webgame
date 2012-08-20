package com.pwrd.war.gameserver.common.listener;

import com.pwrd.war.gameserver.human.Human;

public interface MarketListener {
	
	/**
	 * 完成买卖粮食
	 * @param human
	 */
	void onMarketSuccess(Human human);

}
