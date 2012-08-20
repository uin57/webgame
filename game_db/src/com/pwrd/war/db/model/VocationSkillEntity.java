package com.pwrd.war.db.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.pwrd.war.core.orm.BaseEntity;

@Entity
@Table(name = "t_vacation_skill")
public class VocationSkillEntity implements BaseEntity<String> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1035009365957647467L;

	private String id;
	
	private String humanSn;
	
	private int vocationType;
	
	private String skillSn1;
	
	private int skillLevel1;
	
	private String skillSn2;
	
	private int skillLevel2;
	
	private String skillSn3;
	
	private int skillLevel3;
	
	private String skillSn4;
	
	private int skillLevel4;

	private String skillGroup1;

	private String skillGroup2;

	private String skillGroup3;

	private String skillGroup4;

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
	public String getSkillGroup1() {
		return skillGroup1;
	}
	@Column
	public String getSkillGroup2() {
		return skillGroup2;
	}
	@Column
	public String getSkillGroup3() {
		return skillGroup3;
	}
	@Column
	public String getSkillGroup4() {
		return skillGroup4;
	}

	public void setSkillGroup1(String skillGroup1) {
		this.skillGroup1 = skillGroup1;
	}

	public void setSkillGroup2(String skillGroup2) {
		this.skillGroup2 = skillGroup2;
	}

	public void setSkillGroup3(String skillGroup3) {
		this.skillGroup3 = skillGroup3;
	}

	public void setSkillGroup4(String skillGroup4) {
		this.skillGroup4 = skillGroup4;
	}
	@Column
	public String getSkillSn1() {
		return skillSn1;
	}
	@Column
	public int getSkillLevel1() {
		return skillLevel1;
	}
	@Column
	public String getSkillSn2() {
		return skillSn2;
	}
	@Column
	public int getSkillLevel2() {
		return skillLevel2;
	}
	@Column
	public String getSkillSn3() {
		return skillSn3;
	}
	@Column
	public int getSkillLevel3() {
		return skillLevel3;
	}
	@Column
	public String getSkillSn4() {
		return skillSn4;
	}
	@Column
	public int getSkillLevel4() {
		return skillLevel4;
	}

	public void setSkillSn1(String skillSn1) {
		this.skillSn1 = skillSn1;
	}

	public void setSkillLevel1(int skillLevel1) {
		this.skillLevel1 = skillLevel1;
	}

	public void setSkillSn2(String skillSn2) {
		this.skillSn2 = skillSn2;
	}

	public void setSkillLevel2(int skillLevel2) {
		this.skillLevel2 = skillLevel2;
	}

	public void setSkillSn3(String skillSn3) {
		this.skillSn3 = skillSn3;
	}

	public void setSkillLevel3(int skillLevel3) {
		this.skillLevel3 = skillLevel3;
	}

	public void setSkillSn4(String skillSn4) {
		this.skillSn4 = skillSn4;
	}

	public void setSkillLevel4(int skillLevel4) {
		this.skillLevel4 = skillLevel4;
	}
	@Column
	public String getHumanSn() {
		return humanSn;
	}

	public void setHumanSn(String humanSn) {
		this.humanSn = humanSn;
	}
	@Column
	public int getVocationType() {
		return vocationType;
	}

	public void setVocationType(int vocationType) {
		this.vocationType = vocationType;
	}
	
}
