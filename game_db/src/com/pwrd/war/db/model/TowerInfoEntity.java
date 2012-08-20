package com.pwrd.war.db.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.pwrd.war.core.orm.BaseEntity;

/**
 * 数据库实体类：保存一个人物已经通过的 塔数
 * 
 */
@Entity
@Table(name = "t_tower_info")
public class TowerInfoEntity implements BaseEntity<String>{
	
	private static final long serialVersionUID = -6200274964247701164L;

	/** 主键 UUID */
	private String id;
	
	/** 所属角色 */
	private String charId;
	
	/** 星座id */
	private int constellationId;
	
	/** 今天挑战的序号 */
	private int curNum;
	
	/** 历史最高序号 */
	private int maxNum;		
	
	/** 当天刷新时间*/
	private long lastRefreshTime;
	
	/** 该星座的刷新次数*/
	private int refreshNum;
	
	/** 神秘星标志*/
	private boolean secretFlag;

	@Id
	@Column(length = 36)
//	@GeneratedValue(generator="uuid")
//	@GenericGenerator(name = "uuid", strategy = "uuid.hex")
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

	public int getCurNum() {
		return curNum;
	}

	public void setCurNum(int curNum) {
		this.curNum = curNum;
	}

	public int getMaxNum() {
		return maxNum;
	}

	public void setMaxNum(int maxNum) {
		this.maxNum = maxNum;
	}

	public long getLastRefreshTime() {
		return lastRefreshTime;
	}

	public void setLastRefreshTime(long lastRefreshTime) {
		this.lastRefreshTime = lastRefreshTime;
	}

	public int getRefreshNum() {
		return refreshNum;
	}

	public void setRefreshNum(int refreshNum) {
		this.refreshNum = refreshNum;
	}

	public int getConstellationId() {
		return constellationId;
	}

	public void setConstellationId(int constellationId) {
		this.constellationId = constellationId;
	}

	public boolean isSecretFlag() {
		return secretFlag;
	}

	public void setSecretFlag(boolean secretFlag) {
		this.secretFlag = secretFlag;
	}
}
