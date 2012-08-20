package com.pwrd.war.gameserver.human.enums;

/**
 * 冷却队列子类型
 * @author xf
 */
public enum SubCoolType {

	NONE(0), 
	SUB_ONE(1), 
	SUB_TWO(2), 
	;

	private int id;

	SubCoolType(int id) {
		this.id = id;
	}

	public static SubCoolType getCoolTypeById(int id) {
		for (SubCoolType coolType : SubCoolType.values()) {
			if (coolType.getId() == id) {
				return coolType;
			}
		}
		return null;
	}

	public int getId() {
		return id;
	}

}
