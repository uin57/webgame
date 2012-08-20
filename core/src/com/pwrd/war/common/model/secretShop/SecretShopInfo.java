package com.pwrd.war.common.model.secretShop;

/**
 * 商品信息
 * @author mxy
 *
 */
public class SecretShopInfo {
	
	private String itemSn;		//物品的itemSn
	
	private int price;
	
	private int currencyType;	//货币类型
	
	private int number;			//数量
	
	private int position;		//位置
	
	private boolean buyFlag;	//是否已经购买

	public String getItemSn() {
		return itemSn;
	}

	public void setItemSn(String itemSn) {
		this.itemSn = itemSn;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getCurrencyType() {
		return currencyType;
	}

	public void setCurrencyType(int currencyType) {
		this.currencyType = currencyType;
	}

	public boolean getBuyFlag() {
		return buyFlag;
	}

	public void setBuyFlag(boolean buyFlag) {
		this.buyFlag = buyFlag;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}
		
}
