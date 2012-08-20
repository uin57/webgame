package com.pwrd.war.db.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.pwrd.war.core.orm.BaseEntity;

/**
 * 服务器相关配置等信息存储在这里
 * @author xf
 */
@Entity
@Table(name = "t_server_info")
public class ServerInfoEntity implements BaseEntity<String> {
	private static final long serialVersionUID = 1L;
	
	private String id;
	
	private String key;
	
	private String value;
	
	private Timestamp updateTime;
	@Id
	@Column
	@GeneratedValue(generator="uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid.hex")
	public String getId() { 
		return id;
	}

	@Override
	public void setId(String id) { 
		this.id = id;
	}
	@Column(length=10240)
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Column(name="key_")
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	@Column(columnDefinition="timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
	public Timestamp getUpdateTime() {
		return updateTime;
	}
 
	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

}
