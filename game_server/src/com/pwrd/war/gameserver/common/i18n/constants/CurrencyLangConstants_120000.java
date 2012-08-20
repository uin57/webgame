package com.pwrd.war.gameserver.common.i18n.constants;

import com.pwrd.war.core.annotation.SysI18nString;

public class CurrencyLangConstants_120000 {
	public  static Integer  BASE = 120000;
	
	/** 任务相关*/
	public  static Integer QUEST_BASE = BASE + 500;	
	@SysI18nString(content = "任务模版Id={0,number,#}|任务名称={1}|任务类型={2}")
	public static final Integer QUEST_BONUS =++QUEST_BASE;	
	
	/** 商店相关*/
	public  static Integer SHOP_BASE = BASE + 500;	
	@SysI18nString(content = "购买道具模版Id={0,number,#}|道具名称={1}")
	public static final Integer BUY_FROM_SHOP =++SHOP_BASE;	
	@SysI18nString(content = "卖出道具模版Id={0,number,#}|道具名称={1}")
	public static final Integer SELL_TO_SHOP =++SHOP_BASE;
	
	/** 竞技场相关*/
	public  static Integer JJC_BASE = BASE + 500;	
	@SysI18nString(content = "竞技场增加挑战次数上限扣除元宝")
	public static final Integer ARENA_ADD_TIME =++JJC_BASE;	
	@SysI18nString(content = "竞技场战斗奖励金钱")
	public static final Integer ARENA_BATTLE_PRIZE =++JJC_BASE;
	@SysI18nString(content = "竞技场成就奖励金钱")
	public static final Integer ARENA_ACHIEVEMENT_PRIZE =++JJC_BASE;
	
	/**各模块相关*/
	
	
	/** 其他相关*/
	public  static Integer QITA_BASE = BASE + 500;	
}
