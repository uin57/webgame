package com.pwrd.war.db.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.pwrd.war.core.orm.BaseEntity;


/**
 * 竞技场成就数据
 * 
 */
@Entity
@Table(name = "t_arena_achivement")
public class ArenaAchievementEntity implements BaseEntity<String>, CharSubEntity {
	private static final long serialVersionUID = -4598429668200934100L;

	/** 主键 */
	private String id;
	
	/** 用户id */
	private String charId;
	
	/** 成就id */
	private int achievementId;
	
	/** 是否完成 */
	private boolean finished;
	
	/** 铜钱奖励 */
	private int money;
	
	/** 声望奖励 */
	private int shengwang;
	
	/** 需要连胜次数 */
	private int winneed;
	
	/** 累积连胜次数 */
	private int wincurrent;
	
	/** 需要累积连胜次数 */
	private int totalwinneed;
	
	/** 目前连胜次数 */
	private int totalwincurrent;
	
	/** 需要排名 */
	private int rankneed;
	
	/** 当前排名 */
	private int rankcurrent;

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

	public void setCharId(String charId) {
		this.charId = charId;
	}

	@Column
	public int getAchievementId() {
		return achievementId;
	}

	public void setAchievementId(int achievementId) {
		this.achievementId = achievementId;
	}

	@Column
	public boolean isFinished() {
		return finished;
	}

	public void setFinished(boolean finished) {
		this.finished = finished;
	}

	@Column
	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	@Column
	public int getShengwang() {
		return shengwang;
	}

	public void setShengwang(int shengwang) {
		this.shengwang = shengwang;
	}

	@Column
	public int getWinneed() {
		return winneed;
	}

	public void setWinneed(int winneed) {
		this.winneed = winneed;
	}

	@Column
	public int getWincurrent() {
		return wincurrent;
	}

	public void setWincurrent(int wincurrent) {
		this.wincurrent = wincurrent;
	}

	@Column
	public int getTotalwinneed() {
		return totalwinneed;
	}

	public void setTotalwinneed(int totalwinneed) {
		this.totalwinneed = totalwinneed;
	}

	@Column
	public int getTotalwincurrent() {
		return totalwincurrent;
	}

	public void setTotalwincurrent(int totalwincurrent) {
		this.totalwincurrent = totalwincurrent;
	}

	@Column
	public int getRankneed() {
		return rankneed;
	}

	public void setRankneed(int rankneed) {
		this.rankneed = rankneed;
	}

	@Column
	public int getRankcurrent() {
		return rankcurrent;
	}

	public void setRankcurrent(int rankcurrent) {
		this.rankcurrent = rankcurrent;
	}

	
}
