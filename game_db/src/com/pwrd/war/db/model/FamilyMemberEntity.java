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
 * 家族成员信息实体类
 *
 */
@Entity
@Table(name = "t_family_member_info")
public class FamilyMemberEntity implements BaseEntity<String>{
	
	private static final long serialVersionUID = 700521978607790523L;
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
	/** 角色家族职位 */
	private int familyRole;
	/** 角色每日家族贡献 */
	private int dayContribute;
	/** 角色累计家族贡献 */
	private int totalContribute;
	/** 角色最近登录时间 */
	private long lastLoginTime;
	/** 将角色最近贡献时间 */
	private long lastContributeTime; 
	
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
	@Column
	public int getFamilyRole() {
		return familyRole;
	}
	public void setFamilyRole(int familyRole) {
		this.familyRole = familyRole;
	}
	@Column
	public int getDayContribute() {
		return dayContribute;
	}
	public void setDayContribute(int dayContribute) {
		this.dayContribute = dayContribute;
	}
	@Column
	public int getTotalContribute() {
		return totalContribute;
	}
	public void setTotalContribute(int totalContribute) {
		this.totalContribute = totalContribute;
	}
	@Column
	public long getLastLoginTime() {
		return lastLoginTime;
	}
	public void setLastLoginTime(long lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	@Column
	public long getLastContributeTime() {
		return lastContributeTime;
	}
	public void setLastContributeTime(long lastContributeTime) {
		this.lastContributeTime = lastContributeTime;
	}
	@Column
	public String getFamilyId() {
		return familyId;
	}
	public void setFamilyId(String familyId) {
		this.familyId = familyId;
	}
}
