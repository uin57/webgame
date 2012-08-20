package com.pwrd.war.gameserver.skill.enums;

/**
 * 被动技能触发点类型
 * @author zy
 *
 */
public enum SkillTriggerTypeEnum {
	NONE(0),	//错误类型
    MOVE(1),	//移动
    NO_ATTACKED(2),	//受到攻击
    CHANGE_LINE(3),	//换线
    FIGHT_START(5),	//战斗开始
    ;

	private int id;
	
	SkillTriggerTypeEnum(int id ) {
		this.id = id;
	}

	public static SkillTriggerTypeEnum getTypeById(int id) {
		for (SkillTriggerTypeEnum amountType : SkillTriggerTypeEnum.values()) {
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
