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
 * 家族实体类
 *
 */
@Entity
@Table(name = "t_family_info")
public class FamilyEntity implements BaseEntity<String>{

	/** */
	private static final long serialVersionUID = -4596864745529331047L;
	/** 家族id,主键 */
	private String id;
	/** 家族名称 */
	private String name;
	/** 族长名称 */
	private String leaderName;
	/** 家族等级 */
	private int level;
	/** 家族经验 */
	private int exp;
	/** 家族成员数量 */
	private int memberNum;
	/** 家族成员数量上限 */
	private int maxMemberNum;
	/** 家族介绍 */
	private String desc;
	/** 家族qq群号 */
	private String qq;
	/** 家族公告 */
	private String announcement;
	/** 家族资金 */
	private int money;
	/** 是否自动加入0是1不是 */
	private int isAutoAdd;

	@Id
	@Column
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column
	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	@Column
	public int getExp() {
		return exp;
	}

	public void setExp(int exp) {
		this.exp = exp;
	}

	@Column
	public int getMemberNum() {
		return memberNum;
	}

	public void setMemberNum(int memberNum) {
		this.memberNum = memberNum;
	}

	@Column
	public int getMaxMemberNum() {
		return maxMemberNum;
	}

	public void setMaxMemberNum(int maxMemberNum) {
		this.maxMemberNum = maxMemberNum;
	}

	@Column(name="_desc")
	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	@Column
	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	@Column
	public String getAnnouncement() {
		return announcement;
	}

	public void setAnnouncement(String announcement) {
		this.announcement = announcement;
	}

	@Column
	public int getIsAutoAdd() {
		return isAutoAdd;
	}

	public void setIsAutoAdd(int isAutoAdd) {
		this.isAutoAdd = isAutoAdd;
	}

	@Column
	public String getLeaderName() {
		return leaderName;
	}

	public void setLeaderName(String leaderName) {
		this.leaderName = leaderName;
	}

	@Column
	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}
	
	
}
