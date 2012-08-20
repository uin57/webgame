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
 * @author dengdan
 * 
 * 家族日志实体类
 *
 */
@Entity
@Table(name = "t_family_log")
public class FamilyLogEntity implements BaseEntity<String> {
	
	private static final long serialVersionUID = -3360392010644386648L;

	/** 主键id */
	private String id;
	/** 角色id */
	private String charId;
	/** 角色名称 */
	private String charName;
	/** 家族id */
	private String familyId;
	/** 角色贡献 */
	private int popularity;
	/** 家族增加的经验 */
	private int familyExp;
	/** 贡献时间 */
	private long time;

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

	@Column
	public String getCharName() {
		return charName;
	}

	public void setCharName(String charName) {
		this.charName = charName;
	}

	@Column
	public int getPopularity() {
		return popularity;
	}

	public void setPopularity(int popularity) {
		this.popularity = popularity;
	}

	@Column
	public int getFamilyExp() {
		return familyExp;
	}

	public void setFamilyExp(int familyExp) {
		this.familyExp = familyExp;
	}

	@Column
	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	@Column
	public String getFamilyId() {
		return familyId;
	}

	public void setFamilyId(String familyId) {
		this.familyId = familyId;
	}
	
	
	
}
