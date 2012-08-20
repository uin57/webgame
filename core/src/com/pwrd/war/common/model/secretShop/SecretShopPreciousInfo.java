package com.pwrd.war.common.model.secretShop;

/**
 * 商品信息
 * @author mxy
 *
 */
public class SecretShopPreciousInfo {
	
	private String itemSn;		//物品的itemSn
	
	private int price;
	
	private int currencyType;	//货币类型
	
	private int seq;//顺序
	
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

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}	
}
