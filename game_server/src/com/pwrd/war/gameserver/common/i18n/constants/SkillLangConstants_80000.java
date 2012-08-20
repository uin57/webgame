package com.pwrd.war.gameserver.common.i18n.constants;

import com.pwrd.war.core.annotation.SysI18nString;

public class SkillLangConstants_80000 {
	
	public static Integer  BASE = 80000;
	@SysI18nString(content = "升级技能时，等级不够")
	public static final Integer  NO_ENOUGH_LEVEL_IN_UPGRADE_SKILL = ++BASE;
	@SysI18nString(content = "升级技能时，阅历不够")
	public static final Integer  NO_ENOUGH_SEE_IN_UPGRADE_SKILL = ++BASE;
	@SysI18nString(content = "升级技能时，碎银不够")
	public static final Integer  NO_ENOUGH_MONEY_IN_UPGRADE_SKILL = ++BASE;
	@SysI18nString(content = "技能升级时，没这个技能")
	public static final Integer  NO_SUCH_SKILL_IN_UPGRADE_SKILL = ++BASE;
	@SysI18nString(content = "职业技能排序时，技能重复")
	public static final Integer  SKILL_INVALID_ORDER = ++BASE;
	@SysI18nString(content = "阵型排序时，非法武将")
	public static final Integer  INVALID_PET = ++BASE;
	@SysI18nString(content = "玩家正在战斗不能进行新的战斗")
	public static final Integer  PLAYER_IN_BATTLE = ++BASE;
	@SysI18nString(content = "玩家等级过高不能进行战斗")
	public static final Integer  PLAYER_LEVEL_HIGHT_IN_BATTLE = ++BASE;
	@SysI18nString(content = "玩家等级过低不能进行战斗")
	public static final Integer  PLAYER_LEVEL_LOWER_IN_BATTLE = ++BASE;
	@SysI18nString(content = "怪物正在战斗，不能进行战斗")
	public static final Integer  MONSTER_IN_BATTLE = ++BASE;
	@SysI18nString(content = "阵型排序时，没有主角")
	public static final Integer  NO_PLAYER_IN_FORM = ++BASE;
	@SysI18nString(content = "无法获取阵型配置")
	public static final Integer  TEMPLATE_NOT_FOUND = ++BASE;
	@SysI18nString(content = "阵型排序时，重复的武将")
	public static final Integer  DUPLICATE_PET = ++BASE;
	@SysI18nString(content = "阵型排序时，超出阵型最大人数限制")
	public static final Integer  OUT_OF_MAX_POSITIONS = ++BASE;

}
