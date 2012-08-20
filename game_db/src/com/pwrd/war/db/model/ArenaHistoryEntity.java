package com.pwrd.war.db.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.pwrd.war.core.orm.BaseEntity;


/**
 * 竞技场战斗历史数据
 * 
 */
@Entity
@Table(name = "t_arena_history")
public class ArenaHistoryEntity implements BaseEntity<String>, CharSubEntity {
	private static final long serialVersionUID = 5693946658894689026L;
	
	/** 主键 */
	private String id;
	
	/** 用户id */
	private String charId;
	
	/** 对方id */
	private String targetId;
	
	/** 对方名称 */
	private String targetName;
	
	/** 是否胜利 */
	private boolean win;
	
	/** 是否自己主动挑战 */
	private boolean active;
	
	/** 当前排名 */
	private int rank;
	
	/** 排名上升值 */
	private int rankUp;
	
	/** 战斗时间 */
	private long time;
	
	/** 战报地址 */
	private String reportId;

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
	public String getTargetId() {
		return targetId;
	}

	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}

	@Column
	public String getTargetName() {
		return targetName;
	}

	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}

	@Column
	public boolean isWin() {
		return win;
	}

	public void setWin(boolean win) {
		this.win = win;
	}

	@Column
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	@Column
	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	@Column
	public int getRankUp() {
		return rankUp;
	}

	public void setRankUp(int rankUp) {
		this.rankUp = rankUp;
	}

	@Column
	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	@Column
	public String getReportId() {
		return reportId;
	}

	public void setReportId(String reportId) {
		this.reportId = reportId;
	}
	
	
}
