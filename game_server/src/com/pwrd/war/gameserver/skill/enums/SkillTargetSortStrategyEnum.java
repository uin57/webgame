package com.pwrd.war.gameserver.skill.enums;

public enum SkillTargetSortStrategyEnum {
	RANDOM(0),		//目标随机排列顺序
	HP_ASC(1),		//血量从少到多
	HP_DESC(2),		//血量从多到少
	VOCATION1(3),	//优先职业1，其他职业随机
	VOCATION2(4),	//优先职业2，其他职业随机
	VOCATION3(5),	//优先职业3，其他职业随机
	VOCATION4(6),	//优先职业4，其他职业随机
	VOCATION12(7),	//优先近战职业，其他职业随机
	VOCATION34(8),	//优先远程职业，其他职业随机
	;
	
	private int id;

	SkillTargetSortStrategyEnum(int id) {
		this.id = id;
	}

	public static SkillTargetSortStrategyEnum getStrategyTypeById(int id) {
		for (SkillTargetSortStrategyEnum type : SkillTargetSortStrategyEnum.values()) {
			if (type.getId() == id) {
				return type;
			}
		}
		return null;
	}

	public int getId() {
		return id;
	}
}
