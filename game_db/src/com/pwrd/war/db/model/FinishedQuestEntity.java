package com.pwrd.war.db.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.pwrd.war.core.orm.BaseEntity;

/**
 * 已完成的任务信息
 * 
 */
@Entity
@Table(name = "t_finished_quest")
public class FinishedQuestEntity implements BaseEntity<String>, CharSubEntity {

	/** */
	private static final long serialVersionUID = -5440513168906155133L;
	/** 主键 */
	private String id;
	/** 所属角色ID */
	private String charId;
	/** 任务ID */
	private int questId;
	/** 任务开始时间 */
	private Timestamp startTime;
	/** 任务结束时间 */
	private Timestamp endTime;
	/** 任务每天完成次数 */
	private int dailyTimes;

	@Id
	@Override
	public String getId() {
		return id;
	}

	@Column
	public int getQuestId() {
		return questId;
	}

	@Column
	public Timestamp getStartTime() {
		return startTime;
	}

	@Column
	public Timestamp getEndTime() {
		return endTime;
	}

	@Column
	public int getDailyTimes() {
		return dailyTimes;
	}

	@Column
	public String getCharId() {
		return charId;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setQuestId(int questId) {
		this.questId = questId;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	public void setCharId(String charId) {
		this.charId = charId;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}

	public void setDailyTimes(int dailyTimes) {
		this.dailyTimes = dailyTimes;
	}
}
