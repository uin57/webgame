package com.pwrd.war.db.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.pwrd.war.core.orm.BaseEntity;


/**
 * 竞技场数据
 * 
 */
@Entity
@Table(name = "t_arena")
public class ArenaEntity implements BaseEntity<String>, CharSubEntity {
	
	private static final long serialVersionUID = 6124506937214069540L;
	
	/** 主键 */
	private String id;
	
	/** 所属角色ID */
	private String charId;
	
	/** 所属角色名  */
	private String charName;
	
	/** 所属角色等级  */
	private int charLevel;
	
	/** 所属角色排名 */
	private int charRank;
	
	/** 所属角色连胜 */
	private int charWin;
	
	/** 每天挑战剩余次数 */
	private int timeLeft;
	
	/** 每天花费提升上限次数 */
	private int rmbTime;
	
	/** 上次更新时间戳 */
	private long lastTimestamp;
	
	/** 角色吐槽 */
	private String charMsg;
	
	/** 角色职业 */
	private int vocation;
	
	/** 角色性别，true为男 */
	private boolean gender;
	
	/** 角色战力 */
	private int power;
	
	/** 角色国家 */
	private int charCamp;

	@Id
	@Column(length = 36)
	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	@Override
	@Column
	public String getCharId() {
		return charId;
	}

	@Column
	public String getCharName() {
		return charName;
	}

	public void setCharName(String charName) {
		this.charName = charName;
	}

	@Column
	public int getCharLevel() {
		return charLevel;
	}

	public void setCharLevel(int charLevel) {
		this.charLevel = charLevel;
	}

	@Column
	public int getCharRank() {
		return charRank;
	}

	public void setCharRank(int charRank) {
		this.charRank = charRank;
	}

	@Column
	public int getCharWin() {
		return charWin;
	}

	public void setCharWin(int charWin) {
		this.charWin = charWin;
	}

	@Column
	public int getTimeLeft() {
		return timeLeft;
	}

	public void setTimeLeft(int timeLeft) {
		this.timeLeft = timeLeft;
	}

	@Column
	public long getLastTimestamp() {
		return lastTimestamp;
	}

	public void setLastTimestamp(long lastTimestamp) {
		this.lastTimestamp = lastTimestamp;
	}

	@Column(length = 1024)
	public String getCharMsg() {
		return charMsg;
	}

	public void setCharMsg(String charMsg) {
		this.charMsg = charMsg;
	}

	@Column
	public int getVocation() {
		return vocation;
	}

	public void setVocation(int vocation) {
		this.vocation = vocation;
	}

	@Column
	public boolean isGender() {
		return gender;
	}

	public void setGender(boolean gender) {
		this.gender = gender;
	}

	@Column
	public int getPower() {
		return power;
	}

	public void setPower(int power) {
		this.power = power;
	}

	public void setCharId(String charId) {
		this.charId = charId;
	}

	@Column
	public int getRmbTime() {
		return rmbTime;
	}

	public void setRmbTime(int rmbTime) {
		this.rmbTime = rmbTime;
	}

	@Column
	public int getCharCamp() {
		return charCamp;
	}

	public void setCharCamp(int charCamp) {
		this.charCamp = charCamp;
	}
	
	
}
