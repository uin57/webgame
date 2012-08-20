package com.pwrd.war.db.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.pwrd.war.core.orm.BaseEntity;

@Entity
@Table(name = "t_prob_info")
public class ProbEntity implements BaseEntity<String> {
	/**  **/
	private static final long serialVersionUID = -8580896558291840613L;

	private String id;
	
	private String playerUUID;
	
	private String eventId;//事件
	
	private int[] times;//每个概率产生的次数
	
	private int[] probs;//每个概率的权重
	
	
	@Id
	@Column(length = 36) 
	@Override
	public String getId() { 
		return id;
	}

	@Override
	public void setId(String id) { 
		this.id = id;
	}

	public String getPlayerUUID() {
		return playerUUID;
	}

	public void setPlayerUUID(String playerUUID) {
		this.playerUUID = playerUUID;
	}

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public int[] getTimes() {
		return times;
	}

	public void setTimes(int[] times) {
		this.times = times;
	}

	public int[] getProbs() {
		return probs;
	}

	public void setProbs(int[] probs) {
		this.probs = probs;
	}

}
