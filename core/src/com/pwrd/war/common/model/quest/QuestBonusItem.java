package com.pwrd.war.common.model.quest;

public class QuestBonusItem {
	
	protected String name;
	
	protected short count;	

	protected short rarity;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public short getCount() {
		return count;
	}

	public void setCount(short count) {
		this.count = count;
	}

	public short getRarity() {
		return rarity;
	}

	public void setRarity(short rarity) {
		this.rarity = rarity;
	}

}
