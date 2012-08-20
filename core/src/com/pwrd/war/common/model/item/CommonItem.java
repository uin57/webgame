package com.pwrd.war.common.model.item;

import net.sf.json.JSONObject;


/**
 * 物品信息
 * 
 */
public class CommonItem {
	
	protected String uuid;
	
	protected String itemSn;//itemSn
	  
	protected int bagId;//所在背包
	
	protected int index;//背包格子序号 

	protected int bind;//是否绑定
	
	protected int num;//数量
	
	protected String wearerUuid;//穿戴者uuid
	
	protected int createTime;
	
	/** 唯一类型 **/
	private int identity;
	/** feature字符串 **/
	protected String feature;
	
	/** 战斗属性 **/
	protected String battleProps;
	
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

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	 
	 

	public void setBind(int bind) {
		this.bind = bind;
	}
 

	public int getBind() {
		return bind;
	}

	@Override
	public String toString() { 
		return JSONObject.fromObject(this).toString();
	}

 

	public String getWearerUuid() {
		return wearerUuid;
	}

	public void setWearerUuid(String wearerUuid) {
		this.wearerUuid = wearerUuid;
	}

	public int getCreateTime() {
		return createTime;
	}

	public void setCreateTime(int createTime) {
		this.createTime = createTime;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public int getBagId() {
		return bagId;
	}

	public void setBagId(int bagId) {
		this.bagId = bagId;
	}

	public int getIdentity() {
		return identity;
	}

	public void setIdentity(int identity) {
		this.identity = identity;
	}

	public String getFeature() {
		return feature;
	}

	public void setFeature(String feature) {
		this.feature = feature;
	}

	public String getBattleProps() {
		return battleProps;
	}

	public void setBattleProps(String battleProps) {
		this.battleProps = battleProps;
	}
	
//	protected AttrDescBase[] baseAttrs;
//	protected AttrDescAddon[] addonAttrs;
//	protected AttrDescSpec[] specAttrs;


 
}
