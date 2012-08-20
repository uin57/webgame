package com.pwrd.war.gameserver.human.domain;

import com.pwrd.war.db.model.UserArchiveEntity;

/**
 * 成就
 * @author xf
 */
public class Archive { 
	
	private String uuid;
	private String humanUUID;
	

	/** 类型 **/
	private ArchiveType type; 
	/** 当前得到的数目 **/
	private int number;
	/** 获得称号 **/
	private String title;
	
	
	public String getType() {
		return type.toString();
	}
	public ArchiveType getEnumType() {
		return type;
	}
	public void setType(ArchiveType type) {
		this.type = type;
	}
	public void setType(String type) {
		this.type = ArchiveType.valueOf(type);
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	public String getHumanUUID() {
		return humanUUID;
	}

	public void setHumanUUID(String humanUUID) {
		this.humanUUID = humanUUID;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public static void main(String[] args){
		ArchiveType a = ArchiveType.valueOf("PET_HIRE_PURPLE_NUM");
		System.out.println(a.toString());
	}
	
	public UserArchiveEntity toEntity(){
		UserArchiveEntity entity = new UserArchiveEntity();
		entity.setId(uuid);
		entity.setHumanUUID(humanUUID);
		entity.setNumber(number);
		entity.setTitle(title);
		entity.setType(this.type.toString());
		return entity;
	}
	
	/**
	 * @author xf
	 */
	public static enum ArchiveType{
		/** 招募武将的数目 **/
		PET_HIRE_NUM,
		/** 招募紫色武将的数目 **/
		PET_HIRE_PURPLE_NUM,
		/** 武将收集值 **/
		PET_HIRE_STAR_VALUE,
		;	
		
	}
}


