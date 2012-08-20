package com.pwrd.war.common.model.tree;

/**
 * 商品信息
 * @author mxy
 *
 */
public class WaterInfo {
	
	private String humanUuid;		//好友UUID
		
	private String humanName;		//好友名称
	
	private boolean isWater;		//是否已经浇水
	
	private long lastWaterTime;	 	//上次浇水时间
	
	private boolean isFriend;	 	//上次浇水时间

	public String getHumanUuid() {
		return humanUuid;
	}

	public void setHumanUuid(String humanUuid) {
		this.humanUuid = humanUuid;
	}

	public String getHumanName() {
		return humanName;
	}

	public void setHumanName(String humanName) {
		this.humanName = humanName;
	}

	public boolean getIsWater() {
		return isWater;
	}

	public void setIsWater(boolean isWater) {
		this.isWater = isWater;
	}

	public long getLastWaterTime() {
		return lastWaterTime;
	}

	public void setLastWaterTime(long lastWaterTime) {
		this.lastWaterTime = lastWaterTime;
	}

	public boolean getIsFriend() {
		return isFriend;
	}

	public void setIsFriend(boolean isFriend) {
		this.isFriend = isFriend;
	}
	
}
