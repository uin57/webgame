package com.pwrd.war.db.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.pwrd.war.core.orm.BaseEntity;

@Entity
@Table(name = "t_secret_shop_info")
public class SecretShopInfoEntity implements BaseEntity<String>{
	private static final long serialVersionUID = 1L;

	/** 主键 */	
	private String id;
	
	/** 所属角色ID */
	private String charId;
	
	/** 物品的itemSn */
	private String itemSn;	
	
	/** 物品的价格 */
	private int price;
	
	/** 货币类型 */
	private int currencyType;
	
	/** 数量 */
	private int number;	
	
	/** 位置 */
	private int position;
	
	/** 是否已经购买 */
	private boolean buyFlag;
	
	/** 是否为历史信息*/
	private boolean histroyFlag;
	

	@Id
	@Column(length = 36)
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid.hex")
	@Override
	public String getId() {
		return id;
	}
	
	@Column
	public String getCharId() {
		return charId;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}
	
	public void setCharId(String charId) {
		this.charId = charId;
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

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public boolean isBuyFlag() {
		return buyFlag;
	}

	public void setBuyFlag(boolean buyFlag) {
		this.buyFlag = buyFlag;
	}

	public boolean isHistroyFlag() {
		return histroyFlag;
	}

	public void setHistroyFlag(boolean histroyFlag) {
		this.histroyFlag = histroyFlag;
	}
}
