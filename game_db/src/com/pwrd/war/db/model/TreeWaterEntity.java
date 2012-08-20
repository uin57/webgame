/**
 * 
 */
package com.pwrd.war.db.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.pwrd.war.core.orm.BaseEntity;

/**
 * @author mxy
 * 
 * 摇钱树 浇水记录
 *
 */
@Entity
@Table(name = "t_tree_water_info")
public class TreeWaterEntity implements BaseEntity<String>{

	/** */
	private static final long serialVersionUID = -4596864745529331047L;
	
	/** 主键 */	
	private String id;
	
	/** 浇水角色ID */
	private String charId;
	
	/** 浇水角色名称 */
	private String charName;
	
	/** 被浇水角色ID */
	private String toCharId;
	
	/** 被浇水角色名称 */
	private String toCharName;	
	
	/** 浇水的时间 */
	private long waterTime;
	
	@Id
	@Column
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column
	public String getCharId() {
		return charId;
	}

	public void setCharId(String charId) {
		this.charId = charId;
	}

	public String getToCharId() {
		return toCharId;
	}

	public void setToCharId(String toCharId) {
		this.toCharId = toCharId;
	}

	public long getWaterTime() {
		return waterTime;
	}

	public void setWaterTime(long waterTime) {
		this.waterTime = waterTime;
	}

	public String getCharName() {
		return charName;
	}

	public void setCharName(String charName) {
		this.charName = charName;
	}

	public String getToCharName() {
		return toCharName;
	}

	public void setToCharName(String toCharName) {
		this.toCharName = toCharName;
	}
}
