package com.pwrd.war.gameserver.fight.domain;

import com.pwrd.war.gameserver.item.template.ItemTemplate;

/**
 * 丢失物品
 * @author zhutao
 *
 */
public class FightLostItem {
	/** 宝贝sn */
	private String sn;
	/** 宝贝数量 */
	private int num;
	/** 宝贝完美值 */
	private int perfect;
	
	private ItemTemplate itemTemplate;

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public int getPerfect() {
		return perfect;
	}

	public void setPerfect(int perfect) {
		this.perfect = perfect;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public ItemTemplate getItemTemplate() {
		return itemTemplate;
	}

	public void setItemTemplate(ItemTemplate itemTemplate) {
		this.itemTemplate = itemTemplate;
	}

}
