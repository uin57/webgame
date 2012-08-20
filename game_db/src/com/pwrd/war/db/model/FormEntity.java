package com.pwrd.war.db.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.pwrd.war.core.orm.BaseEntity;

@Entity
@Table(name = "t_form")
public class FormEntity implements BaseEntity<String>{
	
	private String id;		//数据库中条目id
	private String humanSn;	//玩家sn
	private int formSn;	//阵型模板sn
	private String positionSn;	//阵型中角色摆放情况，角色id使用逗号分隔，空位置为空字符串
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 966682805439194145L;

	@Id
	@Column(length = 36)
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid.hex")
	@Override
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column
	public int getFormSn() {
		return formSn;
	}

	public void setFormSn(int formSn) {
		this.formSn = formSn;
	}
	
	@Column
	public String getHumanSn() {
		return humanSn;
	}

	public void setHumanSn(String humanSn) {
		this.humanSn = humanSn;
	}

	@Column(length = 512)
	public String getPositionSn() {
		return positionSn;
	}

	public void setPositionSn(String positionSn) {
		this.positionSn = positionSn;
	}


}
