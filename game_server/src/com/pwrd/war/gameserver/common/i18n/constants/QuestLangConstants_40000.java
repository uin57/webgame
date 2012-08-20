package com.pwrd.war.gameserver.common.i18n.constants;

import com.pwrd.war.core.annotation.SysI18nString;

public class QuestLangConstants_40000 {
	/** 任务相关常量 20001 ~ 21000 */
	public static Integer QUEST_BASE = 40000 ;
	@SysI18nString(content = "任务不存在")
	public static final Integer QUEST_NOT_FOUND = ++QUEST_BASE;
	@SysI18nString(content = "任务[{0}]不在进行中", comment = "{0}任务名称")
	public static final Integer QUEST_NOT_IN_PROCESS = ++QUEST_BASE;
	@SysI18nString(content = "任务[{0}]正在进行中", comment = "{0}任务名称")
	public static final Integer QUEST_IN_PROCESS = ++QUEST_BASE;
	@SysI18nString(content = "任务[{0}]已经完成", comment = "{0}任务名称")
	public static final Integer QUEST_IS_FINISHED = ++QUEST_BASE;
	@SysI18nString(content = "您还没有达到任务要求的最小等级")
	public static final Integer QUEST_LEVEL_NOT_REACH = ++QUEST_BASE;
	@SysI18nString(content = "每日任务完成数超过了最大上限")
	public static final Integer DAILY_QUEST_ACCEPT_ERR_TODAY_FULL = ++QUEST_BASE;
	@SysI18nString(content = "您还没有杀死足够数量的怪物")
	public static final Integer QUEST_NO_ENOUGH_MONSTERS_KILLED = ++QUEST_BASE;
	@SysI18nString(content = "完成任务所需要的道具不足")
	public static final Integer QUEST_NO_ENOUGH_ITEM = ++QUEST_BASE;
	@SysI18nString(content = "您还没有完成该任务的前置任务")
	public static final Integer QUEST_PREQUEST_NOT_FINISHED = ++QUEST_BASE;
	@SysI18nString(content = "任务{0}没有完成")
	public static final Integer QUEST_NOT_FINISHED = ++QUEST_BASE;
}
