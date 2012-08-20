package com.pwrd.war.db.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.pwrd.war.core.orm.BaseEntity;

/**
 * 正在做的任务信息
 * 
 */
@Entity
@Table(name = "t_doing_task")
public class DoingQuestEntity implements BaseEntity<String>, CharSubEntity {

	/** */
	private static final long serialVersionUID = -6243749353854620815L;
	/** 主键 */
	private String id;
	/** 所属角色ID */
	private String charId;
	/** 任务编号 */
	private int questId;
	/** 任务开始时间 */
	private Timestamp startTime;
	/** 任务相关的属性 */
	private String props;

	@Id
	@Column(length = 36)
	@Override
	public String getId() {
		return id;
	}

	@Column
	public String getCharId() {
		return charId;
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
	public String getProps() {
		return props;
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

	public void setProps(String props) {
		this.props = props;
	}
}
