package com.pwrd.war.gameserver.form;


public class FormPosition {
	private String petSn;
	private int position;
	
	public FormPosition() {
	}
	
	public FormPosition(int position, String petSn) {
		this.petSn = petSn;
		this.position = position;
	}
	
	public String getPetSn() {
		return petSn;
	}

	public int getPosition() {
		return position;
	}

	public void setPetSn(String petSn) {
		this.petSn = petSn;
	}

	public void setPosition(int position) {
		this.position = position;
	}


}
