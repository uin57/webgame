package com.pwrd.war.gameserver.common.i18n.constants;

import com.pwrd.war.core.annotation.SysI18nString;

public class RepLangConstants_70000 {
	public static Integer REP_BASE = 70000;
	
	@SysI18nString(content = "进入副本失败")
	public static final Integer REP_ENTER_ERR = ++REP_BASE;
	@SysI18nString(content = "副本ID错误")
	public static final Integer REP_ID_ERR = ++REP_BASE;
	@SysI18nString(content = "前一个英雄副本未通过")
	public static final Integer REP_HERO_FRONT_ERR = ++REP_BASE;
	@SysI18nString(content = "前一个副本未通过")
	public static final Integer REP_FRONT_ERR = ++REP_BASE;
	@SysI18nString(content = "已经达到当天进入最大次数")
	public static final Integer REP_ENTER_TIMES_ENOUGH = ++REP_BASE;
	@SysI18nString(content = "体力不足")
	public static final Integer REP_VIT_NOT_ENOUGH = ++REP_BASE;
	@SysI18nString(content = "等级不足")
	public static final Integer REP_LEVEL_NOT_ENOUGH = ++REP_BASE;
	@SysI18nString(content = "扫荡体力不足")
	public static final Integer REP_AGAINST_VIT_NOT_ENOUGH = ++REP_BASE;
	@SysI18nString(content = "当前状态无法扫荡")
	public static final Integer REP_STATE_CANNOT_AGAINST = ++REP_BASE;
	@SysI18nString(content = "扫荡挂机中")
	public static final Integer REP_STATE_AGAINST = ++REP_BASE;
	@SysI18nString(content = "无需加速扫荡")
	public static final Integer REP_NEED_AGAINST_ERR = ++REP_BASE;
	@SysI18nString(content = "背包必须有一个位置才可以扫荡")
	public static final Integer REP_AGAINST_BAG_FULL = ++REP_BASE;
	@SysI18nString(content = "请先停止修炼")
	public static final Integer STOP_XIULIAN = ++REP_BASE;
	
}
