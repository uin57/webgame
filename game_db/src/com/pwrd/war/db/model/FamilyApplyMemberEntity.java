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
 * 家族申请成员实体类
 *
 */
@Entity
@Table(name = "t_family_apply_member_info")
public class FamilyApplyMemberEntity implements BaseEntity<String> {

	private static final long serialVersionUID = 918767396948781313L;

	/** 主键 */
	private String id;
	/** 家族id */
	private String familyId;
	/** 角色id */
	private String charId;
	/** 角色名称 */
	private String charName;
	/** 角色等级 */
	private int level;
	/** 角色性别 */
	private int sex;
	/** 申请时间 */
	private long applyTime;
	
	@Id
	@Column
	public String getId() {
		return id;
	}

	@Column
	public String getFamilyId() {
		return familyId;
	}

	public void setFamilyId(String familyId) {
		this.familyId = familyId;
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
	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	@Column
	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column
	public long getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(long applyTime) {
		this.applyTime = applyTime;
	}

}
