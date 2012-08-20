package com.pwrd.war.db.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.pwrd.war.core.orm.BaseEntity;

@Entity
@Table(name = "t_skill_group")
public class SkillGroupEntity implements BaseEntity<String> {

	private String id;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1587193777038172815L;
	/** 是否是默认的 */
	private int number;
	/** 是否被选中 */
	private boolean choose;
	
	private String name;

	private String skillSn1;

	private int skillRank1;

	private String skillSn2;

	private int skillRank2;

	private String skillSn3;

	private int skillRank3;

	private String skillSn4;

	private int skillRank4;
	
	

	@Id
	@Column(length = 36)
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid.hex")
	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;

	}

	@Column
	public String getSkillSn1() {
		return skillSn1;
	}
	@Column
	public int getSkillRank1() {
		return skillRank1;
	}

	public String getSkillSn2() {
		return skillSn2;
	}
	@Column
	public int getSkillRank2() {
		return skillRank2;
	}
	@Column
	public String getSkillSn3() {
		return skillSn3;
	}
	@Column
	public int getSkillRank3() {
		return skillRank3;
	}
	@Column
	public String getSkillSn4() {
		return skillSn4;
	}
	@Column
	public int getSkillRank4() {
		return skillRank4;
	}

	


	public void setSkillSn1(String skillSn1) {
		this.skillSn1 = skillSn1;
	}

	public void setSkillRank1(int skillRank1) {
		this.skillRank1 = skillRank1;
	}

	public void setSkillSn2(String skillSn2) {
		this.skillSn2 = skillSn2;
	}

	public void setSkillRank2(int skillRank2) {
		this.skillRank2 = skillRank2;
	}

	public void setSkillSn3(String skillSn3) {
		this.skillSn3 = skillSn3;
	}

	public void setSkillRank3(int skillRank3) {
		this.skillRank3 = skillRank3;
	}

	public void setSkillSn4(String skillSn4) {
		this.skillSn4 = skillSn4;
	}

	public void setSkillRank4(int skillRank4) {
		this.skillRank4 = skillRank4;
	}

	public int getNumber() {
		return number;
	}

	public boolean isChoose() {
		return choose;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public void setChoose(boolean choose) {
		this.choose = choose;
	}
	@Column
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	

}
