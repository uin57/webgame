package com.pwrd.war.common.model.pet;

/**
 * 武将列表中拥有的武将信息
 * @author yue.yan
 *
 */
public class PetListInfo {

	/** 武将id */
	private int id;
	/** 名称 */
	private String name;
	/** 头像 */
	private int photo;
	/** 简介 */
	private String desc;
	/** 招募状态 */
	private int hireStatus;
	/** 武将分组 */
	private int group;
	
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getPhoto() {
		return photo;
	}
	
	public void setPhoto(int photo) {
		this.photo = photo;
	}
	
	public String getDesc() {
		return desc;
	}
	
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	public int getHireStatus() {
		return hireStatus;
	}
	
	public void setHireStatus(int hireStatus) {
		this.hireStatus = hireStatus;
	}
	
	public int getGroup() {
		return group;
	}
	
	public void setGroup(int group) {
		this.group = group;
	}
}
