/**
 * 
 */
package com.pwrd.war.gameserver.giftBag.model.vo;

/**
 * 
 * 礼包信息数据类
 * @author dengdan
 *
 */
public class GiftBagInfo {

	/** 礼包实例id */
	private String id;
	/** 礼包模版id */
	private String giftId;
	/** 礼包名称 */
	private String name;
	/** 礼包图标 */
	private String img;
	/** 礼包描述 */
	private String desc;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getGiftId() {
		return giftId;
	}
	public void setGiftId(String giftId) {
		this.giftId = giftId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
}
