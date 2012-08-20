package com.pwrd.war.db.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.pwrd.war.core.orm.BaseEntity;

/**
 * 一般场景实体
 * 
 */
@Entity
@Table(name = "t_scene_info")
public class SceneEntity implements BaseEntity<String> {

	/**  **/
	private static final long serialVersionUID = -529968269391748752L;
	 
	/** 主键 uuid */
	private String id;
	/** 模版 Id */
	private int templateId;
	
	/** 场景ID **/
	private int sceneId;
	
	@Id
	@Column 
	@GeneratedValue(generator="uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid.hex")
	public String getId() {
		return id;
	}
 
	public void setId(String id) {
		this.id = id;
	}

	@Column
	public Integer getTemplateId() {
		return this.templateId;
	}

	public void setTemplateId(int value) {
		this.templateId = value;
	}

	public int getSceneId() {
		return sceneId;
	}

	public void setSceneId(int sceneId) {
		this.sceneId = sceneId;
	}

}
