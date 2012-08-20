package com.pwrd.war.gameserver.form;


public class PetOrder {

	private String petSn;
	
	private String skillSn;
	
	//武将时，这个属性没用
	private int skillLevel;
	public PetOrder(){
		
	}

	public PetOrder(String roleSn, String skillSn, int skillLevel) {
		this.petSn =roleSn;
		this.skillSn=skillSn;
		this.skillLevel=skillLevel;
	}

	public String getPetSn() {
		return petSn;
	}

	public void setPetSn(String petSn) {
		this.petSn = petSn;
	}

	public String getSkillSn() {
		return skillSn;
	}

	public void setSkillSn(String skillSn) {
		this.skillSn = skillSn;
	}

	public int getSkillLevel() {
		return skillLevel;
	}

	public void setSkillLevel(int skillLevel) {
		this.skillLevel = skillLevel;
	}


}