package com.pwrd.war.common.model.mall;

/**
 * 回购物品信息
 * @author mxy
 *
 */
public class RedeemInfo {
	
	private int flag; 			//1增加 0列表 -1删除
	
	private String uuid;		//物品的uuid
	
	private String itemSn;		//物品的itemSn
	
	private int price;
	
	private int currencyType;	//货币类型
	
	private int number;			//数量
	
	private long sellTime;    //卖出时间

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

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

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}
	
	public long getSellTime() {
		return sellTime;
	}

	public void setSellTime(long sellTime) {
		this.sellTime = sellTime;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}	
}
