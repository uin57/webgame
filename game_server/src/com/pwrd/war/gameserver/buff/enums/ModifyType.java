package com.pwrd.war.gameserver.buff.enums;

public enum ModifyType {

	delete(-1), change(0), add(1);

	ModifyType(int id) {
		this.id = id;
	}

	private int id;

	public int getId() {
		return id;
	}

}
