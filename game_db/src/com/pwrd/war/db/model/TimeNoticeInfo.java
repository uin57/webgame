package com.pwrd.war.db.model;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.pwrd.war.core.orm.BaseEntity;

@Entity
@Table(name = "t_time_notice")
public class TimeNoticeInfo implements BaseEntity<Integer> {
	
	/** */
	private static final long serialVersionUID = 1L;
	 
	private int id;
	private int intervalTime;
	private String serverIds;
	private String operator;
	private String content;
	private Timestamp startTime;
	private Timestamp endTime;
	private short type;
 

	@Override
	public void setId(Integer id) {
		this.id = id;
	}
	@Id   
	@GeneratedValue(generator = "paymentableGenerator")  
	@GenericGenerator(name = "paymentableGenerator", strategy = "native") 
	public Integer getId() {
		return id;
	}


	public int getIntervalTime() {
		return intervalTime;
	}


	public void setIntervalTime(int intervalTime) {
		this.intervalTime = intervalTime;
	}


	public String getServerIds() {
		return serverIds;
	}


	public void setServerIds(String serverIds) {
		this.serverIds = serverIds;
	}


	public String getOperator() {
		return operator;
	}


	public void setOperator(String operator) {
		this.operator = operator;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public Timestamp getStartTime() {
		return startTime;
	}


	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}


	public Timestamp getEndTime() {
		return endTime;
	}


	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}


	public short getType() {
		return type;
	}


	public void setType(short type) {
		this.type = type;
	}


	public void setId(int id) {
		this.id = id;
	}

}
