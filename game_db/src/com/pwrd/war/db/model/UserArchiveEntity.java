package com.pwrd.war.db.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.pwrd.war.core.orm.BaseEntity;

@Entity
@Table(name="t_user_archive")
public class UserArchiveEntity implements BaseEntity<String> {
 
	private static final long serialVersionUID = -3989477428933479883L;
	private String id;
	private String humanUUID;
	
	public String getHumanUUID() {
		return humanUUID;
	}

	public void setHumanUUID(String humanUUID) {
		this.humanUUID = humanUUID;
	}

	/** 类型 **/
	private String type; 
	/** 当前得到的数目 **/
	private int number;
	/** 获得称号 **/
	private String title;
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	
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

}
