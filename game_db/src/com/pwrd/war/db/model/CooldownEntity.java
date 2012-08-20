package com.pwrd.war.db.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.pwrd.war.core.orm.BaseEntity;
@Entity
@Table(name = "t_cooldown")
public class CooldownEntity implements BaseEntity<String>{
	private static final long serialVersionUID = -1754310232492930959L;
	
	/**主键ID*/
	private String id;
	 
	/** 角色UUID **/
	private String humanUUID;
	
	private int index;
	
	private int coolType;
	
	private long startTime;
	
	private long endTime;

	@Id
	@Column
	@GeneratedValue(generator="uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid.hex")
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id=id;
	}

	@Column
	public int getCoolType() {
		return coolType;
	}

	public void setCoolType(int coolType) {
		this.coolType = coolType;
	}
	@Column
	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}
	@Column
	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}
	 
	public String getHumanUUID() {
		return humanUUID;
	}

	public void setHumanUUID(String humanUUID) {
		this.humanUUID = humanUUID;
	}
	@Column(name="_index")
	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

}
