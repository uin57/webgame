package com.pwrd.war.gameserver.interactive.domain;

public class PetInfomation {
	private String uuid;
	private String petSn;
	private String name;
	private int sex;
    private int vocation ;
	private String skeltonId;
	private int level;
	private boolean inBattle;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getPetSn() {
		return petSn;
	}

	public void setPetSn(String petSn) {
		this.petSn = petSn;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public boolean getInBattle() {
		return inBattle;
	}

	public void setInBattle(boolean inBattle) {
		this.inBattle = inBattle;
	}

	public String getSkeltonId() {
		return skeltonId;
	}

	public void setSkeltonId(String skeltonId) {
		this.skeltonId = skeltonId;
	}

	public int getVocation() {
		return vocation;
	}

	public void setVocation(int vocation) {
		this.vocation = vocation;
	}

}
