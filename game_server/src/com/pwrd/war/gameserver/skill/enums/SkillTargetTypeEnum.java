package com.pwrd.war.gameserver.skill.enums;

public enum SkillTargetTypeEnum {
	NONE(-1),		//错误类型
	
	ENEMY(0),		//仅敌方人物
	ENEMY_ITEMS(1),	//仅敌方障碍物
	ENEMY_ALL(2),	//敌方全体
	
	TEAM(3),		//仅我方人物
	TEAM_ITEMS(4),	//仅我方障碍物
	TEAM_ALL(5),	//我方全体
	
	MYSELF(6),		//仅自己
	;
	
	private int id;

	SkillTargetTypeEnum(int id) {
		this.id = id;
	}

	public static SkillTargetTypeEnum getTargetTypeById(int id) {
		for (SkillTargetTypeEnum type : SkillTargetTypeEnum.values()) {
			if (type.id == id) {
				return type;
			}
		}
		return NONE;
	}

}
