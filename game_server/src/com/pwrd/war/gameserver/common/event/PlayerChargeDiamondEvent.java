package com.pwrd.war.gameserver.common.event;

import com.pwrd.war.core.event.IEvent;
import com.pwrd.war.gameserver.human.Human;

public class PlayerChargeDiamondEvent implements IEvent<Human>{
	
	/** 玩家角色 */
	private final Human human;
	
	/** 充值钻石数额 */
	private int chargeDiamond;
	
	public PlayerChargeDiamondEvent(Human human, int chargeDiamond) {
		this.human = human;
		this.chargeDiamond = chargeDiamond;
	}

	@Override
	public Human getInfo() {
		return human;
	}

	public int getChargeDiamond() {
		return chargeDiamond;
	}

	public void setChargeDiamond(int chargeDiamond) {
		this.chargeDiamond = chargeDiamond;
	}

}
