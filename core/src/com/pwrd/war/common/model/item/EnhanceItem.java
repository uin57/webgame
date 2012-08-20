package com.pwrd.war.common.model.item;

import java.util.Arrays;

public class EnhanceItem {
	
	protected String uuid;
	
	protected String name;
	
	protected short rarity;
	
	protected short level;
	
	protected short enhanceLevel;
	
	protected short afterEnhanceLevel;
	
	private int levelUpCost;
	
	private int failedBackMoney;
	
	private boolean canLevelUp;
	
	/** 装备强化描述 */
	private String levelUpDesc;
	
	private String levelDownDesc;
	
	private boolean canLevelDown;
	
	private boolean isLevelLimit;
	
	private boolean isCdLimit;
	
	protected AttrDescBase[] baseAttrs;
	
	protected AttrDescBase[] afterBaseAttrs;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public short getRarity() {
		return rarity;
	}

	public void setRarity(short rarity) {
		this.rarity = rarity;
	}

	public short getLevel() {
		return level;
	}

	public void setLevel(short level) {
		this.level = level;
	}

	public short getEnhanceLevel() {
		return enhanceLevel;
	}

	public void setEnhanceLevel(short enhanceLevel) {
		this.enhanceLevel = enhanceLevel;
	}
	
	public short getAfterEnhanceLevel() {
		return afterEnhanceLevel;
	}

	public void setAfterEnhanceLevel(short afterEnhanceLevel) {
		this.afterEnhanceLevel = afterEnhanceLevel;
	}

	public AttrDescBase[] getBaseAttrs() {
		return baseAttrs;
	}

	public void setBaseAttrs(AttrDescBase[] baseAttrs) {
		this.baseAttrs = baseAttrs;
	}
	
	public AttrDescBase[] getAfterBaseAttrs() {
		return afterBaseAttrs;
	}

	public void setAfterBaseAttrs(AttrDescBase[] afterBaseAttrs) {
		this.afterBaseAttrs = afterBaseAttrs;
	}

	public int getLevelUpCost() {
		return levelUpCost;
	}

	public void setLevelUpCost(int levelUpCost) {
		this.levelUpCost = levelUpCost;
	}

	public int getFailedBackMoney() {
		return failedBackMoney;
	}

	public void setFailedBackMoney(int failedBackMoney) {
		this.failedBackMoney = failedBackMoney;
	}

	public boolean getCanLevelUp() {
		return canLevelUp;
	}

	public void setCanLevelUp(boolean canLevelUp) {
		this.canLevelUp = canLevelUp;
	}
	
	public String getLevelUpDesc() {
		return levelUpDesc;
	}

	public void setLevelUpDesc(String levelUpDesc) {
		this.levelUpDesc = levelUpDesc;
	}
	
	public String getLevelDownDesc() {
		return levelDownDesc;
	}


	public void setLevelDownDesc(String levelDownDesc) {
		this.levelDownDesc = levelDownDesc;
	}

	public boolean getCanLevelDown() {
		return canLevelDown;
	}

	public void setCanLevelDown(boolean canLevelDown) {
		this.canLevelDown = canLevelDown;
	}
	
	public boolean isLevelLimit() {
		return isLevelLimit;
	}

	public void setLevelLimit(boolean isLevelLimit) {
		this.isLevelLimit = isLevelLimit;
	}

	public boolean isCdLimit() {
		return isCdLimit;
	}

	public void setCdLimit(boolean isCdLimit) {
		this.isCdLimit = isCdLimit;
	}

	@Override
	public String toString() {
		return "EnhanceItem [ "
				+ "uuid=" + uuid 
				+ ", name=" + name 
				+ ", rarity=" + rarity
				+ ", level=" + level 
				+ ", enhanceLevel=" + enhanceLevel 
				+ ", afterEnhanceLevel=" + afterEnhanceLevel 
				+ ", levelUpCost=" + levelUpCost 
				+ ", failedBackMoney=" + failedBackMoney 
				+ ", canLevelUp=" + canLevelUp 
				+ ", levelUpDesc=" + levelUpDesc
				+ ", canLevelDown=" + canLevelDown 
				+ ", baseAttrs=" + Arrays.toString(baseAttrs) 
				+ ", afterBaseAttrs=" + Arrays.toString(afterBaseAttrs) 
				+ "]";
	}


}
