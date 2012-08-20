package com.pwrd.war.db.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.pwrd.war.core.orm.BaseEntity;

/**
 * 已完成的剧情信息
 * 
 */
@Entity
@Table(name = "t_finished_story")
public class FinishedStoryEntity implements BaseEntity<String>, CharSubEntity {

	/** */
	private static final long serialVersionUID = -5440513168906155133L;
	/** 主键 */
	private String id;
	/** 所属角色ID */
	private String charId;
	/** 剧情ID */
	private String storyId;

	@Id
	@Override
	public String getId() {
		return id;
	}

	@Column
	public String getCharId() {
		return charId;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setCharId(String charId) {
		this.charId = charId;
	}
	
	@Column
	public String getStoryId() {
		return storyId;
	}

	public void setStoryId(String storyId) {
		this.storyId = storyId;
	}
	
}
