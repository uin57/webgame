package com.pwrd.war.gameserver.prize;

/**
 * 平台奖励数据结构
 * 
 * 
 */
public class PlatformPrizeHolder extends BasePrizeHolder {
	/** 平台奖励唯一编号 */
	private int uniqueId;
	/** 奖励编号 */
	private int prizeId;
	/** 奖励名称 */
	private String prizeName;
	
	public int getUniqueId() {
		return uniqueId;
	}

	public int getPrizeId() {
		return prizeId;
	}

	public String getPrizeName() {
		return prizeName;
	}

	public void setUniqueId(int uniqueId) {
		this.uniqueId = uniqueId;
	}

	public void setPrizeId(int prizeId) {
		this.prizeId = prizeId;
	}

	public void setPrizeName(String prizeName) {
		this.prizeName = prizeName;
	}

	@Override
	public String toString() {
		return "PlatformPrizeHolder [prizeId=" + prizeId + ", prizeName="
				+ prizeName + ", uniqueId=" + uniqueId + "]";
	}

}
