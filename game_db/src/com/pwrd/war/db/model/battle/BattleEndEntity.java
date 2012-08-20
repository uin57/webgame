package com.pwrd.war.db.model.battle;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.pwrd.war.core.orm.BaseEntity;

@Entity
@Table(name = "t_battle_end")
public class BattleEndEntity implements BaseEntity<String>{
	
	private static final long serialVersionUID = -2400354415024599492L;
	/** 主键ID */
	private String id;
	
	private String battleSn;
	
	private String data;
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
	public String getBattleSn() {
		return battleSn;
	}

	public void setBattleSn(String battleSn) {
		this.battleSn = battleSn;
	}

	@Column
	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}



}
