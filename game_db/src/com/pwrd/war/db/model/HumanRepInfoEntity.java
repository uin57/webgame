package com.pwrd.war.db.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.pwrd.war.core.orm.BaseEntity;

/**
 * 数据库实体类：副本信息信息，保存一个人物已经通过的副本信息
 * 
 */
@Entity
@Table(name = "t_human_rep_info")
public class HumanRepInfoEntity implements BaseEntity<String>{
	
	private static final long serialVersionUID = -6200274964247701164L;

	/** 主键 UUID */
	private String id;
	
	/** 所属角色 */
	private String charId;
	
	/** 副本id */
	private String repId;		
	
	/** 过关分数 */
	private int score;		
	
	/** 当天进入次数 */
	private int num;

	@Id
	@Column(length = 36)
	@GeneratedValue(generator="uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid.hex")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column
	public String getCharId() {
		return charId;
	}

	public void setCharId(String charId) {
		this.charId = charId;
	}

	@Column
	public String getRepId() {
		return repId;
	}

	public void setRepId(String repId) {
		this.repId = repId;
	}

	@Column
	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	@Column
	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

}
