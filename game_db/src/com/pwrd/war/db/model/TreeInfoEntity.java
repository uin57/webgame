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
 * 摇钱树
 *
 */
@Entity
@Table(name = "t_tree_info")
public class TreeInfoEntity implements BaseEntity<String>{

	/** */
	private static final long serialVersionUID = -4596864745529331047L;
	
	/** 主键 */	
	private String id;
	
	/** 所属角色ID */
	private String charId;
	
	/** 摇钱次数 */
	private int shakeTimes;	
	
	/** 上次摇钱的时间 */
	private long lastShakeTime;
	
	/** 给自己的浇水次数 */
	private int waterTimes;
	
	/** 给自己的上次浇水时间 */
	private long lastWaterTime;
	
	/** 给好友的浇水次数 */
	private int friendWaterTimes;
	
	/** 给好友的上次浇水时间 */
	private long lastFriendWaterTime;
	
	/** 摇钱树当前经验 */
	private int curTreeExp;
	
	/** 摇钱树等级 */
	private int treeLevel;
	
	/** 摇钱树升级经验 */
	private int maxTreeExp;
	
	/** 已经领取的果子等级 */
	private int fruitLevel;
	
	

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
	public int getShakeTimes() {
		return shakeTimes;
	}

	public void setShakeTimes(int shakeTimes) {
		this.shakeTimes = shakeTimes;
	}

	@Column
	public long getLastShakeTime() {
		return lastShakeTime;
	}

	public void setLastShakeTime(long lastShakeTime) {
		this.lastShakeTime = lastShakeTime;
	}

	@Column
	public int getWaterTimes() {
		return waterTimes;
	}

	public void setWaterTimes(int waterTimes) {
		this.waterTimes = waterTimes;
	}

	@Column
	public long getLastWaterTime() {
		return lastWaterTime;
	}

	public void setLastWaterTime(long lastWaterTime) {
		this.lastWaterTime = lastWaterTime;
	}

	@Column
	public int getFriendWaterTimes() {
		return friendWaterTimes;
	}

	public void setFriendWaterTimes(int friendWaterTimes) {
		this.friendWaterTimes = friendWaterTimes;
	}

	@Column
	public int getCurTreeExp() {
		return curTreeExp;
	}

	public void setCurTreeExp(int curTreeExp) {
		this.curTreeExp = curTreeExp;
	}
	
	@Column
	public int getTreeLevel() {
		return treeLevel;
	}

	public void setTreeLevel(int treeLevel) {
		this.treeLevel = treeLevel;
	}

	@Column
	public int getFruitLevel() {
		return fruitLevel;
	}

	public void setFruitLevel(int fruitLevel) {
		this.fruitLevel = fruitLevel;
	}

	public long getLastFriendWaterTime() {
		return lastFriendWaterTime;
	}

	public void setLastFriendWaterTime(long lastFriendWaterTime) {
		this.lastFriendWaterTime = lastFriendWaterTime;
	}

	public int getMaxTreeExp() {
		return maxTreeExp;
	}

	public void setMaxTreeExp(int maxTreeExp) {
		this.maxTreeExp = maxTreeExp;
	}
}
