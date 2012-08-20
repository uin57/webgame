package com.pwrd.war.gameserver.common.log;

import com.pwrd.war.common.constants.CommonErrorLogInfo;

/**
 * 日志中常用的错误输出
 * 
 */
public class GameErrorLogInfo extends CommonErrorLogInfo {

	/* 玩家游戏相关的常量 */
	/** 玩家角色不匹配 */
	public static final String PLAYER_CHARACTER_NO_MATCH = "PLA.ERR.CHARACTER.NOMATCH";
	/** 玩家的状态不匹配 */
	public static final String PLAYER_STATAE_NO_MATCH = "PLA.ERR.STATE.NOMATCH";
	/** 玩家不在战斗中 */
	public static final String PLAYER_NO_BATTLE = "PLA.ERR.NOT.BATTLE";
	/** 玩家移动的位置无效 */
	public static final String PLAYER_MOVE_INVALID_LOC = "PLA.ERR.MOVE.INVALID.LOC";
	/** 没有玩家对象,出现此状态,一般说明逻辑有误 */
	public static final String PLAYER_NULL = "PLA.ERR.NULL";
	/** 玩家没有与passport绑定 */
	public static final String PLAYER_NO_PASSPORT = "PLA.ERR.PASSPORT";
	/** 不是同一个玩家对象 */
	public static final String PLAYER_NO_SAME = "PLA.ERR.NO.SAME";
	/** 玩家没有与角色对象绑定 */
	public static final String PLAYER_NO_CHARACTER = "PLA.ERR.NO.CHARACTER";
	/** 宠物经验错误 */
	public static final String PET_EXP_ERR = "PET.EXP.ERR";

	/** 未知的消息类型 */
	public static final String MSG_UNKNOWN = "MSG.ERR.UNKNOWN";

	/* 场景相关的常量定义 */
	/** 地图加载失败 */
	public static final String SCENE_LOAD_FAIL = "SCENE.ERR.LOAD.FAIL";
	/** 玩家进入场景时的位置错误 **/
	public static final String SCENE_PLAYER_POS_ERR = "SCENE.ERR.PLAYER.POS";
	/** 没有场景对象,出现此状态,一般说明逻辑有误 */
	public static final String SCENE_NULL = "SCENE.ERR.NULL";

	/* 物品相关的常量定义 */
	/** 物品没有加载器 */
	public static final String ITEM_NO_LOADER = "ITEM.ERR.NOLOADER";
	/** 物品加载失败 */
	public static final String ITEM_LOAD_FAIL = "ITEM.ERR.LOAD.FAIL";
	/** 物品不存在 */
	public static final String ITEM_NO_EXIST = "ITEM.ERR.NOEXIST";
	/** 物品的数量无效 */
	public static final String ITEM_INVALID_COUNT = "ITEM.ERR.INVALID.COUNT";
	/** 物品ID重复 */
	public static final String ITEM_ID_DUP_FAIL = "ITEM.ERR.DUP.ID";
	
	
	/** 军团相关的错误日志 */
	public static final String GUILD_ERROR = "GUILD.ERR";


	/** 模板对象不存在 */
	public static final String OBJECT_NO_EXIST = "OBJECT.ERR.NOEXIST";

	/** 角色属性模板没有加载器 */
	public static final String CHARACTER_TMPL_NO_LOADER = "CHARACTER.TMPL.ERR.NOLOADER";
	/** 角色骨骼模板没有加载器 */
	public static final String CHARACTER_MODEL_TMPL_NO_LOADER = "CHARACTER.MODEL.TMPL.ERR.NOLOADER";
	/** 角色脸部模板没有加载器 */
	public static final String CHARACTER_FACE_TMPL_NO_LOADER = "CHARACTER.FACE.TMPL.ERR.NOLOADER";
	/** 角色属性转换模板没有加载器 */
	public static final String CHARACTER_JOBA2JOBB_NO_LOADER = "CHARACTER.JOBA2JOBB.ERR.NOLOADER";
	/** 职业持有武器状态的动作时间模板没有加载器 */
	public static final String CHARACTER_ACTION_TIME_NO_LOADER = "CHARACTER.ACTIONTIME.ERR.NOLOADER";
	/** 角色模板加载失败 */
	public static final String CHARACTER_TMPL_LOAD_FAIL = "CHARACTER.ERR.LOAD.TMPL.FAIL";
	/** 角色创建失败 */
	public static final String CHARACTER_INFO_CREATE_FAIL = "CHARACTER.ERR.CREATE.INFO.FAIL";
	/** 从数据库加载角色信息出错 */
	public static final String CHARACTER_INFO_LOAD_FAIL = "CHARACTER.ERR.LOAD.INFO.FAIL";
	/** 从数据库加载角色列表出错 */
	public static final String CHARACTER_LIST_LOAD_FAIL = "CHARACTER.ERR.LOAD.LIST.FAIL";
	/** 保存角色信息到数据库出错 */
	public static final String CHARACTER_SAVE_FAIL = "CHARACTER.ERR.SAVE.FAIL";
	/** 更新角色信息到数据库出错 */
	public static final String CHARACTER_UPDATE_FAIL = "CHARACTER.ERR.UPDATE.FAIL";
	/** 宠物属性模板没有加载器 */
	public static final String PET_TMPL_NO_LOADER = "PET.TMPL.ERR.NOLOADER";
	/** 宠物属性转换模板没有加载器 */
	public static final String PET_JOBA2JOBB_NO_LOADER = "PET.JOBA2JOBB.ERR.NOLOADER";
	/** 宠物骨骼模板没有加载器 */
	public static final String PET_BONE_TMPL_NO_LOADER = "PET.BONE.ERR.NOLOADER";
	/** 宠物模板加载失败 */
	public static final String PET_TMPL_LOAD_FAIL = "PET.ERR.LOAD.TMPL.FAIL";
	/** 怪物属性模板没有加载器 */
	public static final String MONSTER_TMPL_NO_LOADER = "MONSTER.TMPL.ERR.NOLOADER";
	/** 怪物属性转换模板没有加载器 */
	public static final String MONSTER_LEVELA2LEVELB_NO_LOADER = "MONSTER.LEVELA2LEVELB.ERR.NOLOADER";
	/** 怪物模板加载失败 */
	public static final String MONSTER_TMPL_LOAD_FAIL = "MONSTER.ERR.LOAD.TMPL.FAIL";
	/** 怪物模板不存在 */
	public static final String MONSTER_TMPL_NOT_EXIST = "MONSTER.ERR.TMPL.NOTEXIST";
	/** 怪物随机生成规则加载失败 */
	public static final String MONSTER_RAND_RULE_LOAD_FAIL = "MONSTER.ERR.LOAD.RAND.RULE.FAIL";
	/** 怪物掉落物品包加载失败 */
	public static final String MONSTER_DROP_BAG_LOAD_FAIL = "MONSTER.ERR.LOAD.DROP.BAG.FAIL";
	/** 玩家的数据有误 */
	public static final String CHARACTER_DATA_ERR = "CHARACTER.ERR.DATA";
	/** 加载宠物数据时出现错误 */
	public static final String PETS_LOAD_ERR = "PETS.ERR.LOAD";
	/** 加载经验分配系数数据时出现错误 */
	public static final String EXPERIENCE_LOAD_ERR = "EXPERIENCE.ERR.LOAD";
	/** 经验分配系数模板没有加载器 */
	public static final String EXPERIENCE_TMPL_NO_LOADER = "EXPERIENCE.TMPL.ERR.NOLOADER";
	/** 宠物模板不存在 */
	public static final String PET_TMPL_NOT_ENOUGH = "PET.ERR.TMPL.NOTENOUGH";
	/** 技能效果重复 */
	public static final String SKILLEFFECTOR_OVERLAP = "SKILLEFFECTOR.ERR.OVERLAP";
	/** 技能效果不存在 */
	public static final String SKILLEFFECTOR_NO_EXIST = "SKILLEFFECTOR.ERR.NOEXIST";
	/** Buff模板加载失败 */
	public static final String BUFFTMPL_LOAD_FAIL = "BUFFTMPL.ERR.LOAD.FAIL";
	/** 心法修正的key违法 */
	public static final String XINFA_AMEND_KEY_ILLEGAL = "XINFA.ERR.AMEND.KEY.ILLEGAL";
	/** 记录角色快照时失败 */
	public static final String CHARACTER_SNAP_TOLOG_FAIL = "CHARACTER.ERR.SNAP.TOLOG.FAIL";
	/** 记录物品快照时失败 */
	public static final String ITEM_SNAP_TOLOG_FAIL = "ITEM.ERR.SNAP.TOLOG.FAIL";
	/** 记录宠物快照时失败 */
	public static final String PET_SNAP_TOLOG_FAIL = "PET.ERR.SNAP.TOLOG.FAIL";
	/** 记录物品日志时失败 */
	public static final String ITEM_LOG_FAIL = "ERR.ITEM.LOG.FAIL";
	/** 记录交易日志时失败 */
	public static final String TRADE_LOG_FAIL = "ERR.ITEM.LOG.FAIL";
	/** 记录玩家基本日志时失败 */
	public static final String BASIC_PLAYER_LOG_FAIL = "ERR.PLAYER.BASIC.LOG.FAIL";
	/** 角色权限加载失败 */
	public static final String ROLE_LOAD_FAIL = "ROLE.ERR.LOAD.FAIL";

	/** 无修正属性类型 */
	public static final String NO_AMEND_TYPE = "ERR.NO.AMEND.TYPE";
	
	/** 修正的key违法 */
	public static final String AMEND_KEY_ILLEGAL = "AMEND.ERR.KEY.ILLEGAL";
	

	/* 商品相关的错误定义 */
	/** 商品加载错误 */
	public static final String SHOP_ITEM_LOAD_FAIL = "SHOPITEM.ERR.LOAD.FAIL";
	/** 商品模板没有加载器 */
	public static final String SHOP_ITEM_NO_LOADER = "SHOPITEM.ERR.NOLOADER";

	/* NPC相关的错误定义 */

	/** NPC加载错误 */
	public static final String NPC_LOAD_FAIL = "NPC.ERR.LOAD.FAIL";
	/** NPC不存在 */
	public static final String NPC_NO_EXIST = "NPC.ERR.NOEXIST";

	/* 技能相关的错误定义 */

	/** 技能不存在 */
	public static final String SKILL_NO_EXIST = "SKILL.ERR.NOEXIST";
	/** 无效的技能持续时间 */
	public static final String SKILL_INVALID_EFFECTTIME = "SKILL.INVALID.EFFECTTIME";
	/** 心法加载失败 */
	public static final String SKILL_XINFA_LOAD_FAIL = "SKILL.ERR.XINFA.LOAD.FAIL";
	/** 心法不存在 */
	public static final String SKILL_XINFA_NO_EXIST = "SKILL.ERR.XINFA.NOEXIST";
	/** 技能ID不合法 */
	public static final String SKILL_ID_ILLEGAL = "SKILL.ERR.ID.ILLEGAL";
	/** 技能加载失败 */
	public static final String SKILL_LOAD_FAIL = "SKILL.ERR.LOAD.FAIL";
	/** 技能没有加载器 */
	public static final String SKILL_NO_LOADER = "SKILL.ERR.NOLOADER";
	/** 技能重新加载失败 */
	public static final String SKILL_RELOAD_FAIL = "SKILL.ERR.RELOAD.FAIL";
	/** 技能父技能不存在 */
	public static final String SKILL_PARENT_NOEXIST = "SKILL.ERR.PARENT.NOEXIST";

	/* 状态相关的错误定义 */

	/** 无效的状态转移 */
	public static final String STATE_INVALID_TRANS = "STATE.CANT.TRANS";
	/** 不能进入加点状态错误 */
	public static final String CHANGE_POINT_NOENTER_STATE = "CHANGE.POINT.ERR.NOENTER.STATE";

	/* 事件监听器错误定义 */
	/** 事件监听器引用了自身,可以会导致死循环 */
	public static final String EVENT_LISTENER_SELF_REF = "EVENT.ERR.LISTENER.SELF.REF";

	/* 属性相关的错误定义 */
	/** 只读属性,不应该被修改 */
	public static final String PROP_READONLY = "PROP.ERR.READONLY";
	/** 不是相同的属性类型 */
	public static final String PROP_NOT_SAME_TYPE = "PROP.ERR.NOT.SAME.TYPE";
	/** 不存在的类型 */
	public static final String PROP_NO_EXIST_TYPE = "PROP.ERR.NO.EXIT.TYPE";

	/** 加点出错 */
	public static final String CHANGE_POINT_ERROR = "CHANGE.POINT.ERR.PROCESS";

	/** 数据解析错误 */
	public static final String PARSE_ERR = "PARSE.ERR";

	/** 错误采取的动作:抛出异常 */
	public static final String ACTION_EXP = "Exp";

	/* 任务相关的错误定义 */
	/** 对话中有重复的ACTION配置 */
	public static final String TASK_TALK_DUP_ACTION = "TASK.ERR.TALK.DUP.ACTION";
	/** 对话中的Action选项不是有效值 */
	public static final String TASK_TALK_INVALID_ACTION = "TASK.ERR.TALK.INVALID.ACTION";
	/** 无效的任务条件 */
	public static final String TASK_TALK_INVALID_CONDITION = "TASK.ERR.TALK.INVALID.CONDITION";
	/** 任务不存在 */
	public static final String TASK_NO_EXIST = "TASK.ERR.NO.EXIST";
	/** 保存任务时发生异常 */
	public static final String TASK_SAVE_ERR = "TASK.ERR.SAVE";
	/** 加载任务时发生异常 */
	public static final String TASK_LOAD_ERR = "TASK.ERR.LOAD";
	/** 重复的任务 */
	public static final String TASK_DUP = "TASK.ERR.DUP";
	/** 加载到了已经完成的任务,如果任务已经完成,不应该再加载进来 */
	public static final String TASK_DOING_FINISH_LOAD = "TASK.ERR.DONING.FINISH.LOAD";
	/** 无效的任务奖励 */
	public static final String TASK_INVALID_BONUS = "TASK.ERR.INVALID.BONUS";
	/** 任务配置的地图ID不存在 */
	public static final String TASK_INVALID_MAP = "TASK.ERR.INVALID.MAP";

	/* 货币相关的错误定义 */
	/** 无效的货币类型 */
	public static final String CURRENTCY_INVALID_TYPE = "CURR.ERR.INVALID.TYPE";
	/** 货币操作流程错误 */
	public static final String CURRENTCY_ILLEGAL_OPERATION = "CURR.ERR.ILLEGAL.OPERATION";
	/** 无效的货币值 */
	public static final String CURRENTCY_INVALID_VALUE = "CURR.ERR.INVALID.VALUE";

	/* GM命令相关错误定义 */
	/** GM命令处理出错 */
	public static final String GM_COMMAND_ERROR = "GM.COMMAND.ERROR";

	/* 战斗相关 */
	/** 战斗相关错误 */
	public static final String BATTLE_ERROR = "BATTLE.ERR";
	/** 未处于战斗状态 */
	public static final String BATTLE_NO_INSTATE = "BATTLE.ERR.NOINSTATE";
	/** 战斗结束时错误 */
	public static final String BATTLE_FINISHED = "BATTLE.ERR.FINISHED";
	/** 战斗退出时错误 */
	public static final String BATTLE_EXITED = "BATTLE.ERR.EXITED";
	/** 战斗角色奖励错误 */
	public static final String BATTLE_CAL_CHARBONUS = "BATTLE.ERR.CAL.CHARBONUS";
	/** 战斗宠物奖励错误 */
	public static final String BATTLE_CAL_PETBONUS = "BATTLE.ERR.CAL.PETBONUS";
	/** 战斗物品掉落错误 */
	public static final String BATTLE_ITEM_DROP = "BATTLE.ERR.ITEM.DROP";
	/** 不能进入战斗状态错误 */
	public static final String BATTLE_NOENTER_STATE = "BATTLE.ERR.NOENTER.STATE";
	/** 未知的战斗动作 */
	public static final String BATTLE_UNKNOWN_ACTION = "BATTLE.ERR.UNKNOWN.ACTION";
	/** 死的战斗 */
	public static final String BATTLE_DEAD = "BATTLE.ERR.DEAD";
	/** buff不存在 */
	public static final String BUFF_NOEXIST = "BUFF.ERR.NOEXIST";
	/** buff的持续类型不存在 */
	public static final String BUFF_KEEPTYPE_NOEXIST = "BUFF.ERR.KEEPTYPE.NOEXIST";
	/** 技能结果不存在 */
	public static final String BATTLE_SKILLRESULT_NOEXIST = "BATTLE.ERR.SKILLRESULT.NOEXIST";
	/** 经验奖励错误 */
	public static final String BATTLE_EXP_BONUS = "BATTLE.ERR.EXP.BONUS";
	/** 参战的B方的角色数量大于位置数量 */
	public static final String BATTLE_BPOS_COUNT_ERR = "BATTLE.ERR.BPOS.COUNT";
	/** 参战的B方的位置不合法 */
	public static final String BATTLE_BPOS_ILLEGAL = "BATTLE.ERR.BPOS.ILLEGAL";
	/** 参战的B方的位置重复 */
	public static final String BATTLE_BPOS_REPEAT = "BATTLE.ERR.BPOS.REPEAT";

	/* 逻辑错误相关 */
	/** 无效的逻辑状态 */
	public static final String INVALID_STATE = "STATE.ERR.INVALID";

	/* 宠物相关 */
	/** 宠物在DB中的技能不存在 */
	public static final String PET_DBSKILL_NOEXIST = "PET.ERR.DBSKILL.NOEXIST";

	/* 装备生产相关 */
	/** 生产的装备不存在 */
	public static final String FORGE_EQUIPITEM_NOT_EXIST = "FORGE.ERR.EQUIPITEM.NOTEXIST";
	/** 非法的选项 */
	public static final String FORGE_ILLEGAL_OPTION = "FORGE.ERR.ILLEGAL.OPTION";

	/** 非法使用GM命令 */
	public static final String ILLEGAL_ROLE_USER_USE_GMCMD = "ERR.ILLEGAL.ROLE.USER.USE.GMCMD";

	/** Token 异常 */
	public static final String ILLEGAL_TOKEN = "ERR.ILLEGAL.TOKEN";
	
	/* 调用接口相关错误定义 */
	/* local接口相关 */	
	/** 充金币接口异常 */
	public static final String CHARGE_DIAMOND_INVOKE_FAIL = "CHARGE.GOLD.ERR.INVOKEFAIL";
	/** 查询平台账户接口异常 */
	public static final String QUERY_ACCOUNT_INVOKE_FAIL = "QUERY.ACCOUNT.ERR.INVOKEFAIL";
	/** 查询奖励接口异常 */
	public static final String QUERY_PRIZE_INVOKE_FAIL = "QUERY.PRIZE.ERR.INVOKEFAIL";
	/** 领取奖励接口异常 */
	public static final String GET_PRIZE_INVOKE_FAIL = "GET.PRIZE.ERR.INVOKEFAIL";
	/** 奖励数据配置异常 */
	public static final String GET_PRIZE_CONFIG_ERROR = "GET.PRIZE.ERR.CONFIGERROR";
	/** 访问奖励数据库异常 */
	public static final String GET_PRIZE_DATAACCESS_ERROR = "GET.PRIZE.ERR.DATAACCESS";
	/** 奖励数据库不存在 */
	public static final String GET_PRIZE_NOT_EXIST_ERROR = "GET.PRIZE.ERR.NOTEXIST";
	/** 领取平台奖励不存在 */
	public static final String GET_PRIZE_PLAT_NOPRIZE_ERROR = "GET.PRIZE.ERR.PLATNOPRIZE";
	/** 领取奖励其它错误 */
	public static final String GET_PRIZE_OTHER_ERROR = "GET.PRIZE.ERR.OTHERERROR";
	/** 选中的充值套餐不存在 */
	public static final String CHARGE_COMBO_DATA_ERR = "CHARGE.COMBO.DATA.ERR";
	/** 成为人人粉丝接口异常 */
	public static final String REPORT_FANS_ERR = "REPORT.FANS.ERR";
	
	
	/** 贵重物品消耗接口异常 */
	public static final String TRANS_RECORD_INVOKE_FAIL = "TRANS.RECORD.ERR.INVOKEFAIL";
	
	public final static String INVOKE_REPORT_LOCAL_STATUS_ERR = "LOCAL.REPORT.STATUS.ERR";
	
	public final static String INVOKE_REPORT_LOCAL_ONLINE_ERR = "LOCAL.REPORT.STATUS.ERR";
	
	public final static String INVOKE_REPORT_LOCAL_LOGOUT_ERR = "LOCAL.REPORT.LOGOUT.ERR";
	
	/** 剧情相关 */
	public static final String STORY_NOT_EXIST = "STORY.NOT.EXIST.ERROR";

}
