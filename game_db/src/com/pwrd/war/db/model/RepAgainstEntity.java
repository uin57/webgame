package com.pwrd.war.db.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.pwrd.war.core.orm.BaseEntity;

@Entity
@Table(name = "t_rep_against")
public class RepAgainstEntity implements BaseEntity<String> {
	
	/** */
	private static final long serialVersionUID = 1L;
	private String id;//就是角色的id
	
	private boolean isInAgainst;	//是否在副本扫荡
	private int againstCycles;		//总轮数
	private int againstLeftTimes;	//剩余次数,
	private long againstStartTime;	//开始时间,
	private long againstLastTime;	//上次扫荡时间,
	private int againstCurCycle;	//当前轮,
	private int againstCurTimes;	//当前次数
	private String againstRepId;	//扫荡的副本
	@Id
	@Override
	public String getId() { 
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	public boolean isInAgainst() {
		return isInAgainst;
	}

	public void setInAgainst(boolean isInAgainst) {
		this.isInAgainst = isInAgainst;
	}

	public int getAgainstCycles() {
		return againstCycles;
	}

	public void setAgainstCycles(int againstCycles) {
		this.againstCycles = againstCycles;
	}

	public int getAgainstLeftTimes() {
		return againstLeftTimes;
	}

	public void setAgainstLeftTimes(int againstLeftTimes) {
		this.againstLeftTimes = againstLeftTimes;
	}

	public long getAgainstStartTime() {
		return againstStartTime;
	}

	public void setAgainstStartTime(long againstStartTime) {
		this.againstStartTime = againstStartTime;
	}

	public long getAgainstLastTime() {
		return againstLastTime;
	}

	public void setAgainstLastTime(long againstLastTime) {
		this.againstLastTime = againstLastTime;
	}

	public int getAgainstCurCycle() {
		return againstCurCycle;
	}

	public void setAgainstCurCycle(int againstCurCycle) {
		this.againstCurCycle = againstCurCycle;
	}

	public int getAgainstCurTimes() {
		return againstCurTimes;
	}

	public void setAgainstCurTimes(int againstCurTimes) {
		this.againstCurTimes = againstCurTimes;
	}

	public String getAgainstRepId() {
		return againstRepId;
	}

	public void setAgainstRepId(String againstRepId) {
		this.againstRepId = againstRepId;
	}
	
	 

}
