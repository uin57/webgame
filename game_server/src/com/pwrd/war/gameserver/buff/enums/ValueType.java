package com.pwrd.war.gameserver.buff.enums;

public enum ValueType {
	/** 百分比 */
	ratio(1),
	/** 数值 */
	numerical(2);

	private int id;

	ValueType(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

}
