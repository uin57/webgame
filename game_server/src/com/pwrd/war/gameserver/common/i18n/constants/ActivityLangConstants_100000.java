package com.pwrd.war.gameserver.common.i18n.constants;

import com.pwrd.war.core.annotation.SysI18nString;

public class ActivityLangConstants_100000 {
	public  static Integer  BASE = 100000;
	
	@SysI18nString(content = "活动尚未开始")
	public static final Integer ACTIVITY_NOT_START=++BASE;
	
	public  static Integer BOSS_ACTIVITY = BASE + 1000;
	
	@SysI18nString(content = "南蛮入侵活动准备")
	public static final Integer BOSS_ACTIVITY_READY =++BOSS_ACTIVITY;	
	@SysI18nString(content = "南蛮入侵活动开始")
	public static final Integer BOSS_ACTIVITY_START =++BOSS_ACTIVITY;
	@SysI18nString(content = "本次南蛮入侵活动结束")
	public static final Integer BOSS_ACTIVITY_STOP =++BOSS_ACTIVITY;
	@SysI18nString(content = "第{0}波 ： {1}来袭我都，各位勇士做好准备")
	public static final Integer BOSS_ACTIVITY_MONSTER_START =++BOSS_ACTIVITY;
	@SysI18nString(content = "正在复活中...")
	public static final Integer BOSS_ACTIVITY_ALIVE =++BOSS_ACTIVITY;
	@SysI18nString(content = "正在战斗中...")
	public static final Integer BOSS_ACTIVITY_BATTLE =++BOSS_ACTIVITY;
	@SysI18nString(content = "怪物已经死亡...")
	public static final Integer BOSS_ACTIVITY_MONSTER_DEAD =++BOSS_ACTIVITY;
	@SysI18nString(content = "{0}一展神勇，成功击杀{1}，为{2}国赢得{3}分")
	public static final Integer BOSS_ACTIVITY_LAST_DEAD =++BOSS_ACTIVITY;
	@SysI18nString(content = "{0}伤害首先达到{1}，开启酒坛犒赏大家")
	public static final Integer BOSS_ACTIVITY_FIRST_BUFF =++BOSS_ACTIVITY;
	@SysI18nString(content = "休息10秒后下一波开始")
	public static final Integer BOSS_ACTIVITY_GROUP_DEAD =++BOSS_ACTIVITY;
	@SysI18nString(content = "全部怪物击杀完毕，退场")
	public static final Integer BOSS_ACTIVITY_MONSTER_ALL_DEAD =++BOSS_ACTIVITY;
	@SysI18nString(content = "鼓舞增强攻击力{0}%")
	public static final Integer BOSS_ACTIVITY_BUFF_DESC =++BOSS_ACTIVITY;
	@SysI18nString(content = "阅历不足")
	public static final Integer BOSS_ACTIVITY_LEAK_SEE =++BOSS_ACTIVITY;
	@SysI18nString(content = "鼓舞成功")
	public static final Integer BOSS_ACTIVITY_GUWU_OK =++BOSS_ACTIVITY;
	@SysI18nString(content = "鼓舞失败")
	public static final Integer BOSS_ACTIVITY_GUWU_FAIL =++BOSS_ACTIVITY;
	@SysI18nString(content = "鼓舞满")
	public static final Integer BOSS_ACTIVITY_GUWU_FULL =++BOSS_ACTIVITY;
	@SysI18nString(content = "获得了{0}的犒赏三军，攻击力提高{1}%")
	public static final Integer BOSS_ACTIVITY_GET_NPC_BUFF_OK =++BOSS_ACTIVITY;
}
