package com.pwrd.war.common.model.pet;



/**
 * 武将招募信息
 *
 */
public class JingjiuInfo { 
	
	/** id 1美酒 2佳酿 3陈年老窖*/
	private int id; 

	/** 名称 */
	private String name; 
	
	/** 所需vip等级 */
	private int vipLevel; 
	
	/** 所需铜钱 */
	private int coin;
	
	/** 所需元宝 */
	private int gold;
	
	/** 获得声望 */
	private int popularity;
	
	/** 敬酒次数 */
	private int times;
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getVipLevel() {
		return vipLevel;
	}

	public void setVipLevel(int vipLevel) {
		this.vipLevel = vipLevel;
	}

	public int getCoin() {
		return coin;
	}

	public void setCoin(int coin) {
		this.coin = coin;
	}

	public int getGold() {
		return gold;
	}

	public void setGold(int gold) {
		this.gold = gold;
	}

	public int getPopularity() {
		return popularity;
	}

	public void setPopularity(int popularity) {
		this.popularity = popularity;
	}

	public int getTimes() {
		return times;
	}

	public void setTimes(int times) {
		this.times = times;
	} 
}
