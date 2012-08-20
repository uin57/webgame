package com.pwrd.war.gameserver.skill.enums;

/**
 * 技能类型
 * @author zy
 *
 */
public enum SkillTypeEnum {
	NONE(0),	//错误类型
    SPECIAL(1),		//非锁屏武将技
    VOCATION_NORMAL(2),	//普通职业攻击技能
    LOCKED_SPECIAL(3),	//锁屏武将技
    ;

	private int id;
	
	SkillTypeEnum(int id ) {
		this.id = id;
	}

	public static SkillTypeEnum getTypeById(int id) {
		for (SkillTypeEnum amountType : SkillTypeEnum.values()) {
			if (amountType.getId() == id) {
				return amountType;
			}
		}
		return NONE;
	}

	public int getId() {
		return id;
	}

}
