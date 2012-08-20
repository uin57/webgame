package com.pwrd.war.db.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.pwrd.war.core.orm.BaseEntity;

/**
 * 数据库实体类:服务器版本实体
 * 
 */
@Entity
@Table(name = "t_db_version")
public class DbVersion implements BaseEntity<Integer>{
	private static final long serialVersionUID = 1L;
	/** 主键 */
	private Integer id;
	/** 服务器版本号 */
	private String version;
	/** 服务器版本号更新时间 */
	private Timestamp updateTime;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(nullable = false, insertable = false, updatable = false)
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	@Column(nullable = false, insertable = false, updatable = false)
	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}
}
