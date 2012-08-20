package com.pwrd.war.common.model.secretShop;

/**
 * 人物购买商品信息
 * @author gameuser
 *
 */
public class SecretShopBuyInfo {
	String humanUuid;
	
	String humanName;
	
	String itemSn;
	
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
	
	public String getItemSn() {
		return itemSn;
	}
	
	public void setItemSn(String itemSn) {
		this.itemSn = itemSn;
	}
	
}
