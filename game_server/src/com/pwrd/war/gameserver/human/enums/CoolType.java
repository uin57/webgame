package com.pwrd.war.gameserver.human.enums;

public enum CoolType {

	NONE(0),
	/** 强化装备 **/
	ENHANCE_EQUIP(1),
	/** 提升成长度 **/
	GROW_UP_ROLE(2),
	/**转职冷却*/
	TRANSFORM_VOCATION(3),
	
	/** 研究 **/
	DEVELOP(4),
	
	/** 副本扫荡花费计算 **/
	AGAINST_REP(10),
	
	/** 夺宝护送 **/
	ROBBERY(5),
	/** 抢劫夺宝护送 **/
	ROBBERY_ROB(6),
	
	/** 竞技场挑战冷却 */
	ARENA_FIGHT(7),
	
	/** 主将争夺 */
	HERO_WAR(8), 
	;

	private int id;

	CoolType(int id) {
		this.id = id;
	}

	public static CoolType getCoolTypeById(int id) {
		for (CoolType coolType : CoolType.values()) {
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
