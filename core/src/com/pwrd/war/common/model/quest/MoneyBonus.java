package com.pwrd.war.common.model.quest;

/**
 * 金钱奖励
 * 
 * 
 * 
 */
public class MoneyBonus {
	/** 钱数 */
	private int money;
	/** 货币类型 */
	private int type;

	public MoneyBonus(int money, int type) {
		this.money = money;
		this.type = type;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
}
