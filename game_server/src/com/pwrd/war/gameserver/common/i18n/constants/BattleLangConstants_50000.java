package com.pwrd.war.gameserver.common.i18n.constants;

import com.pwrd.war.core.annotation.SysI18nString;

public class BattleLangConstants_50000 {
	
	public static Integer  BASE = 50000;
	
	@SysI18nString(content = "玩家状态不对")
	public static final Integer PLAYER_STATUES_ERROE=++BASE;
	
	@SysI18nString(content = "玩家等级过高不能参加战斗")
	public static final Integer PLAYER_LEVEL_LOWER=++BASE;
	
	@SysI18nString(content = "玩家等级过低不能参加战斗")
	public static final Integer PLAYER_LEVEL_HIGHT=++BASE;
	
	@SysI18nString(content = "怪物正在战斗不能再进行战斗")
	public static final Integer MONSTER_IN_BATTLE =++BASE;
	
	@SysI18nString(content = "玩家不在线")
	public static final Integer PLAYER_NO_ONLINE=++BASE;

	@SysI18nString(content = "目标怪物不存在")
	public static final Integer MONSTER_NOT_FOUND=++BASE;

	@SysI18nString(content = "今日挑战次数已经用完")
	public static final Integer ARENA_NO_TIME_LEFT = ++BASE;
	
	@SysI18nString(content = "挑战目标不存在")
	public static final Integer ARENA_NO_TARGET = ++BASE;
	
	@SysI18nString(content = "挑战目标正在战斗中")
	public static final Integer ARENA_TARGET_IN_FIGHTING = ++BASE;
	
	@SysI18nString(content = "今日挑战次数已达上限，无法增加")
	public static final Integer ARENA_NO_RMB_TIME_LEFT = ++BASE;
	
	@SysI18nString(content = "吐槽内容不能为空")
	public static final Integer ARENA_NO_EMPTY_MSG = ++BASE;
	
	@SysI18nString(content = "主动挑战冷却中")
	public static final Integer ARENA_IN_COOLDOWN = ++BASE;
	
	@SysI18nString(content = "战斗冷却中")
	public static final Integer PLAYER_IN_COOLDOWN = ++BASE;
	
	@SysI18nString(content = "{0}在战场上大杀特杀，获取了{1}连胜", comment = "{0}在战场上大杀特杀，获取了{1}连胜")
	public static final Integer ARENA_WIN_NOTICE = ++BASE;
	
	@SysI18nString(content = "{0}在竞技场成为了第一名", comment = "{0}在竞技场成为了第一名")
	public static final Integer ARENA_WIN_TOP = ++BASE;
}
