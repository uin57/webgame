package com.pwrd.war.core.msg;

/**
 * 定义消息类型,规则如下:
 * 
 * <pre>
 * 1.所有消息类型均为short类型，消息类型保证惟一
 * 2.系统内部消息以SYS_开头
 * 3.客户端发往GameServer的以CG_开头 
 * 4.GameServer发往客户端的以GC_开头 
 * 5.保留消息类型0-100,给系统内部一些特殊消息使用
 * 6.每个子系统或模块的消息类型定义应放在一起
 * </pre>
 * 
 */
public abstract class MessageType {
	/** Flash socket 发送的policy request请求协议 "<policy" 中第3,4两个字节ol的16进制表示,28524 */
	public static final short FLASH_POLICY = 0x6f6c;
	public static final short MSG_UNKNOWN = 0;

	/* === 系统内部消息类型定义开始,范围0~100 === */
	public static final short SYS_SESSION_OPEN = 1;
	public static final short SYS_SESSION_CLOSE = 2;
	public static final short SYS_SCHEDULE = 3;
	public static final short SCHD_ASYNC_FINISH = 10;
	public static final short SCHD_PLAYER_ASYNC_FINISH = 11;
	public static final short SYS_TEST_MSG_LENGTH = 14;
	public static final short SYS_TEST_FLOOD_BYTE_ATTACK = 15;
	/** 错误消息 */
	public static final short GC_Error_Message = 16;

	/* === 系统内部消息类型定义结束 === */

	// /////////////
	// 服务器内部状态
	// ////////////
	public static short STAUS_BEGIN = 400;

	private static short BASE_NUMBER = 500;
	/** 每个大系统分配的消息个数 */
	public static final short NUMBER_PER_SYS = 350;
	/** 每个子系统分配的消息个数 */
	public static final short NUMBER_PER_SUB_SYS = 50;

	// /////////////
	// 各模块通用的消息
	// ////////////
	public static short COMMON_BEGIN = BASE_NUMBER;
	public static final short CG_ADMIN_COMMAND = ++COMMON_BEGIN;
	public static final short GC_SYSTEM_MESSAGE = ++COMMON_BEGIN;
	public static final short GC_WAITING_START = ++COMMON_BEGIN;
	public static final short GC_WAITING_OVER = ++COMMON_BEGIN;
	public static final short GC_COMMON_ROLE_EFFECT = ++COMMON_BEGIN;
	public static final short GC_COMMON_CHARGE = ++COMMON_BEGIN;
	public static final short GC_PING = ++COMMON_BEGIN;
	public static final short CG_PING = ++COMMON_BEGIN;
	public static final short GC_SYSTEM_NOTICE = ++COMMON_BEGIN;
	public static final short CG_HANDSHAKE = ++COMMON_BEGIN;
	public static final short GC_HANDSHAKE = ++COMMON_BEGIN;
	public static final short GC_SHOW_OPTION_DLG = ++COMMON_BEGIN;
	public static final short CG_SELECT_OPTION = ++COMMON_BEGIN;
	public static final short GC_COMMON_ASK_AND_ANSWER_URL = ++COMMON_BEGIN;
	/** 提示消息 */
	public static final short GC_SYSTEM_PROMPT = ++COMMON_BEGIN;
	
	// /////////////
	// 玩家登录退出模块
	// ////////////
	public static short PLAYER_BEGIN = (BASE_NUMBER += NUMBER_PER_SYS);
	public static final short CG_PLAYER_LOGIN = ++PLAYER_BEGIN;
	public static final short CG_PLAYER_COOKIE_LOGIN = ++PLAYER_BEGIN;
	public static final short GC_LOGIN_SUCCESS = ++PLAYER_BEGIN;
	public static final short GC_LOGIN_FAILED = ++PLAYER_BEGIN;
	public static final short CG_ROLE_TEMPLATE = ++PLAYER_BEGIN;
	public static final short GC_ROLE_TEMPLATE = ++PLAYER_BEGIN;
	public static final short GC_ROLE_INFO = ++PLAYER_BEGIN;
	public static final short CG_CREATE_ROLE = ++PLAYER_BEGIN;
	public static final short CG_PLAYER_ENTER = ++PLAYER_BEGIN;

	public static final short GC_SCENE_INFO = ++PLAYER_BEGIN;
	public static final short CG_ENTER_SCENE = ++PLAYER_BEGIN;
	public static final short GC_ENTER_SCENE = ++PLAYER_BEGIN;
	public static final short GC_FAILED_MSG = ++PLAYER_BEGIN;
	public static final short GC_NOTIFY_EXCEPTION = ++PLAYER_BEGIN;

	public static final short CG_PLAYER_CHARGE_DIAMOND = ++PLAYER_BEGIN;
	public static final short GC_PLAYER_CHARGE_DIAMOND = ++PLAYER_BEGIN;
	public static final short CG_PLAYER_QUERY_ACCOUNT = ++PLAYER_BEGIN;
	public static final short GC_PLAYER_QUERY_ACCOUNT = ++PLAYER_BEGIN;

	public static final short CG_PLAYER_MOVE = ++PLAYER_BEGIN;
	public static final short GC_PLAYER_MOVE = ++PLAYER_BEGIN;
	public static final short CG_PLAYER_GUIDE = ++PLAYER_BEGIN;
	public static final short CG_PLAYER_GUIDE_LIST = ++PLAYER_BEGIN;
	public static final short GC_PLAYER_GUIDE_LIST = ++PLAYER_BEGIN;

	// /////////////
	// 玩家角色基本属性和操作模块
	// ////////////
	public static short HUMAN_BEGIN = (BASE_NUMBER += NUMBER_PER_SYS);
	public static final short GC_HUMAN_DETAIL_INFO = ++HUMAN_BEGIN;
	public static final short CG_ENTER_HOME = ++HUMAN_BEGIN;
	public static final short CG_QUERY_HUMAN_INFO = ++HUMAN_BEGIN;
	public static final short GC_HUMAN_ASSETS_UPDATE = ++HUMAN_BEGIN;
	public static final short GC_HUMAN_TOKENS_UPDATE = ++HUMAN_BEGIN;
	public static final short GC_HUMAN_CD_QUEUE_UPDATE = ++HUMAN_BEGIN;
	public static final short GC_PROPERTY_CHANGED_NUMBER = ++HUMAN_BEGIN;
	public static final short GC_PROPERTY_CHANGED_STRING = ++HUMAN_BEGIN;
	public static final short GC_PROPERTY_CHANGED_DOUBLE = ++HUMAN_BEGIN;
	public static final short CG_SHOW_CHARGE_PANEL = ++HUMAN_BEGIN;
	public static final short GC_SHOW_CHARGE_PANEL = ++HUMAN_BEGIN;
	public static final short CG_VIP_INFO = ++HUMAN_BEGIN;
	public static final short GC_VIP_INFO = ++HUMAN_BEGIN;
	public static final short GC_VIP_CONFIG = ++HUMAN_BEGIN;
	public static final short CG_SHOW_SLAVE_DLG = ++HUMAN_BEGIN;
	public static final short GC_SHOW_SLAVE_DLG = ++HUMAN_BEGIN;
	public static final short CG_SHOW_MASTER = ++HUMAN_BEGIN;
	public static final short GC_SHOW_MASTER = ++HUMAN_BEGIN;
	public static final short CG_SHOW_SLAVE = ++HUMAN_BEGIN;
	public static final short GC_SHOW_SLAVE = ++HUMAN_BEGIN;
	public static final short CG_SLAVE_TORMENT = ++HUMAN_BEGIN;
	public static final short GC_SLAVE_TORMENT = ++HUMAN_BEGIN;
	public static final short CG_GIVE_UP_SLAVE = ++HUMAN_BEGIN;
	public static final short GC_GET_OPENED_FUNC_LIST = ++HUMAN_BEGIN;
	public static final short GC_WALLOW_OPEN_PANEL = ++HUMAN_BEGIN;
	public static final short CG_WALLOW_ADD_INFO = ++HUMAN_BEGIN;
	public static final short GC_WALLOW_ADD_INFO_RESULT = ++HUMAN_BEGIN;
	public static final short CG_SET_CONSUME_CONFIRM = ++HUMAN_BEGIN;
	public static final short GC_SCENE_UPDATE_ROLE = ++HUMAN_BEGIN;
	public static final short CG_ARCHIVE_LIST = ++HUMAN_BEGIN;
	public static final short GC_ARCHIVE_LIST = ++HUMAN_BEGIN;
	public static final short CG_OPEN_EQUIPENHANCE_CDQUEUE = ++HUMAN_BEGIN;
	public static final short CG_RELEASE_CDQUEUE = ++HUMAN_BEGIN;
	public static final short GC_QUEUE_INFO = ++HUMAN_BEGIN;
	public static final short CG_JOIN_CHANNEL = ++HUMAN_BEGIN;
	public static final short CG_UNJOIN_CHANNEL = ++HUMAN_BEGIN;
	public static final short CG_LEVELUP_GROW = ++HUMAN_BEGIN;
	public static final short GC_GROW_PROPS = ++HUMAN_BEGIN;
	public static final short CG_GROW_PROPS = ++HUMAN_BEGIN;
	public static final short GC_WALLOW_INFO = ++HUMAN_BEGIN;
	public static final short GC_GOLD_GROW_CRI = ++HUMAN_BEGIN;
	public static final short GC_JingJie_Props = ++HUMAN_BEGIN;
	public static final short CG_JingJie_Props = ++HUMAN_BEGIN;

	// 对其他玩家的查询
	public static final short CG_QUERY_OTHER_ROLE_INFO = ++HUMAN_BEGIN;
	public static final short GC_OTHER_ROLE_INFO = ++HUMAN_BEGIN;

	// 基本玩家行为操作
	public static final short CG_UPGRADE_BUILDING = ++HUMAN_BEGIN;
	public static final short GC_UPGRADE_BUILDING = ++HUMAN_BEGIN;
	
	public static final short CG_START_XIULIAN = ++HUMAN_BEGIN;
	public static final short GC_START_XIULIAN = ++HUMAN_BEGIN;
	public static final short CG_END_XIULIAN = ++HUMAN_BEGIN;
	public static final short GC_END_XIULIAN = ++HUMAN_BEGIN;
	public static final short CG_RESEARCH_LIST = ++HUMAN_BEGIN;
	public static final short GC_RESEARCH_LIST = ++HUMAN_BEGIN;
	public static final short CG_RESEARCH_START = ++HUMAN_BEGIN;
	public static final short GC_XIULIAN_BE_COLLECT_SYMBOL = ++HUMAN_BEGIN;
	public static final short GC_XIULIAN_COLLECT_SYMBOL = ++HUMAN_BEGIN;
	public static final short CG_XIULIAN_COLLECT_SYMBOL = ++HUMAN_BEGIN;
	public static final short CG_XIULIAN_UP_LEVEL = ++HUMAN_BEGIN;
	
	public static final short CG_MODIFY_PERSONSIGN = ++HUMAN_BEGIN;
	public static final short CG_ONLINE_PRIZE = ++HUMAN_BEGIN;
	public static final short GC_ONLINE_PRIZE = ++HUMAN_BEGIN;
	public static final short GC_ONLINE_PRIZE_RES = ++HUMAN_BEGIN;
	public static final short CG_LOGIN_PRIZE = ++HUMAN_BEGIN;
	public static final short GC_LOGIN_PRIZE = ++HUMAN_BEGIN;
	public static final short GC_LOGIN_PRIZE_RES = ++HUMAN_BEGIN;
	public static final short GC_LOGIN_PRIZE_SHOW = ++HUMAN_BEGIN;
	public static final short CG_GET_LOGIN_PRIZE = ++HUMAN_BEGIN;
	
	
	// 武将
	public static short PET_BEGIN = (BASE_NUMBER += NUMBER_PER_SYS);
	public static final short GC_PET_INFO = ++PET_BEGIN;
	public static final short GC_PET_LIST = ++PET_BEGIN;
	public static final short GC_PET_ADD = ++PET_BEGIN;
	public static final short GC_PET_DEL = ++PET_BEGIN;
	public static final short CG_HIRE_PET = ++PET_BEGIN;
	public static final short CG_FIRE_PET = ++PET_BEGIN;
	public static final short CG_SHOW_PET_MANAGE_PANEL = ++PET_BEGIN;
	public static final short GC_SHOW_PET_MANAGE_PANEL = ++PET_BEGIN;
	public static final short CG_SHOW_PET_INFO_ON_MANAGE_PANEL = ++PET_BEGIN;
	public static final short GC_SHOW_PET_INFO_ON_MANAGE_PANEL = ++PET_BEGIN;
	public static final short CG_PET_HIRE_LIST = ++PET_BEGIN;
	public static final short GC_PET_HIRE_LIST = ++PET_BEGIN;
	public static final short CG_PET_HIRE = ++PET_BEGIN;
	public static final short CG_PET_FIRE = ++PET_BEGIN;
	public static final short CG_PET_JINGJIU = ++PET_BEGIN;
	public static final short GC_PET_JINGJIU = ++PET_BEGIN;
	public static final short GC_PET_JINGJIU_LIST = ++PET_BEGIN;
	
	

	// /////////////
	// 聊天功能
	// ////////////
	public static short CHAT_BEGIN = (BASE_NUMBER += NUMBER_PER_SYS);
	public static final short GC_CHAT_CHANNEL_LIST = ++CHAT_BEGIN;
	public static final short CG_CHAT_CHANNEL_CHANGE = ++CHAT_BEGIN;
	public static final short CG_CHAT_MSG = ++CHAT_BEGIN;
	public static final short GC_CHAT_MSG = ++CHAT_BEGIN;

	// /////////////
	// 场景功能
	// ////////////
	public static short SCENE_BEGIN = (BASE_NUMBER += NUMBER_PER_SYS);
	public static final short CG_SHOW_SCENE_LIST = ++SCENE_BEGIN;
	public static final short GC_SHOW_SCENE_LIST = ++SCENE_BEGIN;
	public static final short CG_CLICK_SCENE = ++SCENE_BEGIN;
	public static final short GC_SHOW_CITY_SCENE_DLG = ++SCENE_BEGIN;
	public static final short CG_CLICK_SCENE_FUNC = ++SCENE_BEGIN;
	public static final short CG_SWITCH_SCENE = ++SCENE_BEGIN;
	public static final short GC_SWITCH_SCENE = ++SCENE_BEGIN;
	public static final short GC_SEASON_INFO = ++SCENE_BEGIN;
	public static final short GC_SHOW_FARM_SCENE_DLG = ++SCENE_BEGIN;

	public static final short GC_PLAYER_POS = ++SCENE_BEGIN;
	public static final short CG_PLAYER_POS = ++SCENE_BEGIN;
	public static final short GC_SCENE_DEL_ROLE = ++SCENE_BEGIN;
	public static final short GC_SCENE_ADD_ROLE = ++SCENE_BEGIN;
	public static final short GC_SCENE_ROLE_LIST = ++SCENE_BEGIN;

	public static final short GC_MONSTER_LIST = ++SCENE_BEGIN;
	public static final short GC_ADD_MONSTER = ++SCENE_BEGIN;
	public static final short GC_DEL_MONSTER = ++SCENE_BEGIN;
	public static final short GC_MONSTER_MOVE = ++SCENE_BEGIN;
	public static final short GC_MONSTER_STOP_MOVE = ++SCENE_BEGIN;
	public static final short CG_SCENE_LINE = ++SCENE_BEGIN;
	public static final short GC_SCENE_LINE = ++SCENE_BEGIN;
	public static final short CG_SWITCH_SCENE_LINE = ++SCENE_BEGIN;
	public static final short GC_SWITCH_SCENE_LINE = ++SCENE_BEGIN;
	public static final short GC_TEAM_SWITCH_SCENE_LINE = ++SCENE_BEGIN;
	public static final short CG_PLAYER_JUMP = ++SCENE_BEGIN;
	public static final short GC_PLAYER_JUMP = ++SCENE_BEGIN;
	public static final short CG_PLAYER_SIT = ++SCENE_BEGIN;
	public static final short GC_PLAYER_SIT = ++SCENE_BEGIN;
	public static final short GC_PLAYER_CHANGE_POS = ++SCENE_BEGIN;
	public static final short CG_STEP_MONSTER = ++SCENE_BEGIN;
	public static final short CG_PLAYER_STAND = ++SCENE_BEGIN;
	public static final short GC_PLAYER_STAND = ++SCENE_BEGIN;
	public static final short GC_MONSTER_HP_DEC = ++SCENE_BEGIN;

	// //////////////
	// 物品 //
	// //////////////
	public static short ITEM_BEGIN = (BASE_NUMBER += NUMBER_PER_SYS);
	public static final short GC_ITEM_UPDATE = ++ITEM_BEGIN;
	public static final short CG_DROP_ITEM = ++ITEM_BEGIN;
	public static final short CG_MOVE_ITEM = ++ITEM_BEGIN;
	public static final short CG_REQ_BAGINFO = ++ITEM_BEGIN;
	public static final short CG_SPLIT_ITEM = ++ITEM_BEGIN;
	public static final short CG_TIDY_BAG = ++ITEM_BEGIN;
	public static final short GC_RESET_CAPACITY = ++ITEM_BEGIN;
	public static final short GC_SWAP_ITEM = ++ITEM_BEGIN;
	public static final short GC_BAG_UPDATE = ++ITEM_BEGIN;
	public static final short GC_MOVE_ITEM = ++ITEM_BEGIN;
	public static final short GC_REMOVE_ITEM = ++ITEM_BEGIN;
	public static final short CG_USE_ITEM = ++ITEM_BEGIN;
	public static final short CG_REQ_ITEM_INFO = ++ITEM_BEGIN;
	public static final short GC_RES_ITEM_INFO = ++ITEM_BEGIN;
	public static final short GC_ITEM_START_CD = ++ITEM_BEGIN;
	public static final short GC_SWITCH_EQUIP = ++ITEM_BEGIN;
	public static final short CG_SHOW_EQUIP_ENHANCE_PANEL = ++ITEM_BEGIN;
	public static final short GC_SHOW_EQUIP_ENHANCE_PANEL = ++ITEM_BEGIN;
	public static final short CG_EQUIP_ENHANCE_INFO = ++ITEM_BEGIN;
	public static final short GC_EQUIP_ENHANCE_INFO = ++ITEM_BEGIN;
	public static final short CG_LEVEL_UP_EQUIP = ++ITEM_BEGIN;
	public static final short CG_LEVEL_DOWN_EQUIP = ++ITEM_BEGIN;
	public static final short CG_OPEN_BAG_PAGE_COST = ++ITEM_BEGIN;
	public static final short GC_OPEN_BAG_PAGE_COST = ++ITEM_BEGIN;
	public static final short CG_OPEN_BAG_PAGE = ++ITEM_BEGIN;
	public static final short CG_SELL_ITEM = ++ITEM_BEGIN;
	public static final short CG_ITEM_FREEZE = ++ITEM_BEGIN;
	public static final short CG_ITEM_CANCEl_FREEZE = ++ITEM_BEGIN;
	public static final short CG_MEDAL_SYNTHETIC = ++ITEM_BEGIN;
	public static final short CG_ENHANCE_EQUIP = ++ITEM_BEGIN; 
	public static final short CG_ENHANCE_EQUIP_RATE = ++ITEM_BEGIN; 
	public static final short GC_ENHANCE_EQUIP_RATE = ++ITEM_BEGIN;  
	public static final short CG_SPAND_BAG = ++ITEM_BEGIN;  
	public static final short CG_SPAND_BAG_GOLD = ++ITEM_BEGIN;  
	public static final short GC_SPAND_BAG_GOLD = ++ITEM_BEGIN;  
	public static final short CG_ENHANCE_EQUIP_INFO = ++ITEM_BEGIN;  
	public static final short GC_ENHANCE_EQUIP_INFO = ++ITEM_BEGIN;  
	public static final short GC_ENHANCE_EQUIP = ++ITEM_BEGIN;  
	public static final short GC_USE_REEL = ++ITEM_BEGIN;  
	public static final short CG_GET_REEL_INFO = ++ITEM_BEGIN;  
	public static final short GC_GET_REEL_INFO = ++ITEM_BEGIN;  

	// //////////////
	// 邮件 //
	// //////////////
	public static short MAIL_BEGIN = (BASE_NUMBER += NUMBER_PER_SYS);
	public static final short CG_MAIL_LIST = ++MAIL_BEGIN;
	public static final short GC_MAIL_LIST = ++MAIL_BEGIN;
	public static final short GC_MAIL_UPDATE = ++MAIL_BEGIN;
	public static final short CG_READ_MAIL = ++MAIL_BEGIN;
	public static final short CG_SEND_MAIL = ++MAIL_BEGIN;
	public static final short GC_SEND_MAIL = ++MAIL_BEGIN;
	public static final short CG_SAVE_MAIL = ++MAIL_BEGIN;
	public static final short CG_DEL_MAIL = ++MAIL_BEGIN;
	public static final short CG_DEL_ALL_MAIL = ++MAIL_BEGIN;
	public static final short GC_HAS_NEW_MAIL = ++MAIL_BEGIN;
	public static final short CG_SEND_GUILD_MAIL = ++MAIL_BEGIN;

	// //////////////
	// 任务 //
	// //////////////
	public static short QUEST_BEGIN = (BASE_NUMBER += NUMBER_PER_SYS);
	public static final short CG_QUEST_LIST = ++QUEST_BEGIN;
	public static final short GC_QUEST_LIST = ++QUEST_BEGIN;
	public static final short GC_QUEST_UPDATE = ++QUEST_BEGIN;
	public static final short CG_ACCEPT_QUEST = ++QUEST_BEGIN;
	public static final short CG_GIVE_UP_QUEST = ++QUEST_BEGIN;
	public static final short CG_FINISH_QUEST = ++QUEST_BEGIN;
	public static final short GC_FINISH_QUEST = ++QUEST_BEGIN;

	// //////////////
	// 战斗 //
	// //////////////
	public static short BATTLE_BEGIN = (BASE_NUMBER += NUMBER_PER_SYS);
	public static final short GC_Battle_Init_Message = ++BATTLE_BEGIN;
	public static final short GC_Battle_Action_Message = ++BATTLE_BEGIN;
	public static final short GC_Battle_End_Message = ++BATTLE_BEGIN;
	public static final short CG_Battle_Begin_Message = ++BATTLE_BEGIN;
	
	

	
	
	// //////////////
	// 职业//
	// //////////////
	public static short VOCATION_BEGIN = (BASE_NUMBER += NUMBER_PER_SYS);
	public static final short GC_Vocation = ++VOCATION_BEGIN;
	public static final short CG_Default_Skill_Group = ++VOCATION_BEGIN;
	public static final short GC_Default_Skill_Group = ++VOCATION_BEGIN;

	// //////////////
	// 技能//
	// //////////////
	public static short SKILL_BEGIN = (BASE_NUMBER += NUMBER_PER_SYS);
	public static final short CG_Skill_Cooldown = ++SKILL_BEGIN;
	public static final short CG_Skill_Order = ++SKILL_BEGIN;
	public static final short CG_Skill_Upgrade = ++SKILL_BEGIN;
	public static final short GC_Skill_Cooldown = ++SKILL_BEGIN;
	public static final short GC_Skill_Upgrade = ++SKILL_BEGIN;
	public static final short GC_Skill_Group_Name = ++SKILL_BEGIN;
	public static final short GC_Skill_Order = ++SKILL_BEGIN;

	

	// //////////////
	// 阵型//
	// //////////////
	public static short FORM_BEGIN = (BASE_NUMBER += NUMBER_PER_SYS);
	public static final short GC_Form = ++FORM_BEGIN;
	public static final short CG_Form = ++FORM_BEGIN;
	public static final short CG_Form_Position = ++FORM_BEGIN;
	
	

	// /////////////////
	// 建筑系统
	// /////////////////
	private static short BUILDING_BEGIN = (BASE_NUMBER += NUMBER_PER_SYS);
	public static final short GC_SHOW_BUILDING_LIST = ++BUILDING_BEGIN;
	public static final short CG_CLICK_BUILDING = ++BUILDING_BEGIN;
	public static final short GC_SHOW_BUILDING_DLG = ++BUILDING_BEGIN;
	public static final short CG_CLICK_BUILDING_FUNC = ++BUILDING_BEGIN;
	public static final short CG_CHANGE_FLAG_WORD = ++BUILDING_BEGIN;
	public static final short CG_CHANGE_LEAVE_WORD = ++BUILDING_BEGIN;

	/** 基础功能 */
	private static short BASE_FUNC = (BASE_NUMBER += NUMBER_PER_SYS);

	/** 奖励系统开始 */
	public static short PRIZE_BEGIN = (BASE_NUMBER += NUMBER_PER_SYS);
	public static final short CG_PRIZE_PLATFORM_LIST = ++PRIZE_BEGIN;
	public static final short GC_PRIZE_PLATFORM_LIST = ++PRIZE_BEGIN;
	public static final short CG_PRIZE_PLATFORM_GET = ++PRIZE_BEGIN;
	public static final short GC_PRIZE_PLATFORM_GET_SUCCESS = ++PRIZE_BEGIN;
	public static final short CG_PRIZE_USER_LIST = ++PRIZE_BEGIN;
	public static final short GC_PRIZE_USER_LIST = ++PRIZE_BEGIN;
	public static final short CG_PRIZE_USER_GET = ++PRIZE_BEGIN;
	public static final short GC_PRIZE_USER_GET_SUCCESS = ++PRIZE_BEGIN;

	/** 商店系统开始 */
	public static short MALL_BEGIN = (BASE_NUMBER += NUMBER_PER_SYS);
	public static final short CG_MALL_LIST = ++MALL_BEGIN;
	public static final short GC_MALL_LIST = ++MALL_BEGIN;
	public static final short CG_REDEEM_LIST = ++MALL_BEGIN;
	public static final short GC_REDEEM_LIST = ++MALL_BEGIN;
	public static final short CG_MALL_BUY = ++MALL_BEGIN;
	public static final short GC_MALL_BUY = ++MALL_BEGIN;
	public static final short CG_REDEEM_BUY = ++MALL_BEGIN;
	public static final short GC_REDEEM_BUY = ++MALL_BEGIN;
	public static final short CG_MALL_SELL = ++MALL_BEGIN;
	public static final short GC_MALL_SELL = ++MALL_BEGIN;
	
	/** 副本系统开始 */
	public static short REP_BEGIN = (BASE_NUMBER += NUMBER_PER_SYS);
	public static final short CG_CREATE_REP = ++REP_BEGIN;
	public static final short GC_CREATE_REP = ++REP_BEGIN;
	public static final short CG_ENTRY_REP = ++REP_BEGIN;
	public static final short GC_ENTRY_REP = ++REP_BEGIN;
	public static final short CG_LEAVE_REP = ++REP_BEGIN;
	public static final short GC_LEAVE_REP = ++REP_BEGIN;
	public static final short CG_REP_INFO_LIST = ++REP_BEGIN;
	public static final short GC_REP_INFO_LIST = ++REP_BEGIN;
	public static final short GC_COMPLETE_REP = ++REP_BEGIN;
	public static final short CG_GET_PRIZE = ++REP_BEGIN;
	public static final short GC_GET_PRIZE = ++REP_BEGIN;
	public static final short GC_PRIZE_INFO = ++REP_BEGIN;
	public static final short GC_ALIVE_MONSTER = ++REP_BEGIN;
	public static final short CG_AGAINST_REP_INFO = ++REP_BEGIN;
	public static final short GC_AGAINST_REP_INFO = ++REP_BEGIN;
	public static final short CG_AGAINST_REP_START = ++REP_BEGIN;
	public static final short GC_AGAINST_REP_RESULT = ++REP_BEGIN;
	public static final short GC_AGAINST_REP_ERR = ++REP_BEGIN;
	public static final short GC_AGAINST_REP_START = ++REP_BEGIN;
	public static final short CG_AGAINST_REP_END = ++REP_BEGIN;
	public static final short CG_AGAINST_REP_ACC = ++REP_BEGIN;
	public static final short CG_AGAINST_REP_ACC_GOLD = ++REP_BEGIN;
	public static final short GC_AGAINST_REP_ACC_GOLD = ++REP_BEGIN;
	public static final short CG_SHIQU_ITEM = ++REP_BEGIN;
	public static final short GC_SHIQU_ITEM = ++REP_BEGIN;
	public static final short GC_AGAINST_REP_END = ++REP_BEGIN;
	
	
	/** BUFFER系统 */
	public static short BUFFER_BEGIN = (BASE_NUMBER += NUMBER_PER_SYS);
	public static final short GC_Buffer_Init_Message = ++BUFFER_BEGIN;
	public static final short CG_Hp_Bag_Message = ++BUFFER_BEGIN;
	public static final short GC_Buff_Change_Message = ++BUFFER_BEGIN;
	public static final short CG_Not_Hp_Bag_Message = ++BUFFER_BEGIN;
	public static final short GC_Hp_Bag_Message = ++BUFFER_BEGIN;
	public static final short GC_Hp_Not_Enough_Message = ++BUFFER_BEGIN;
	public static final short GC_Not_Hp_Bag_Message = ++BUFFER_BEGIN;
	public static final short CG_Romove_Buffer = ++BUFFER_BEGIN;
	
	/** 活力系统*/
	public static short VIT_BEGIN = (BASE_NUMBER += NUMBER_PER_SYS);
	public static final short CG_BUY_VIT = ++VIT_BEGIN;
	public static final short GC_BUY_VIT = ++VIT_BEGIN;
	public static final short GC_VIT_ZERO = ++VIT_BEGIN;
	
	/** 转职系统*/
	public static short Transfer_Vocation_BEGIN = (BASE_NUMBER += NUMBER_PER_SYS);
	public static final short CG_Transfer_Vocation_Message = ++Transfer_Vocation_BEGIN;
	public static final short GC_Transfer_Vocation_Result = ++Transfer_Vocation_BEGIN;
	
	
	/**交互系统 */
	
	public static short Interactive_BEGIN = (BASE_NUMBER += NUMBER_PER_SYS);
	
	public static final short CG_Look_Up_Message = ++Interactive_BEGIN;
	
	public static final short CG_Role_List_Message = ++Interactive_BEGIN;
	
	public static final short CG_Infomation_Message = ++Interactive_BEGIN;
	
	public static final short CG_Equipment_Infomation_Message = ++Interactive_BEGIN;
	
	public static final short CG_Transfer_Vocation_Information_Message = ++Interactive_BEGIN;
	
	public static final short GC_Role_List_Message = ++Interactive_BEGIN;
	
	public static final short GC_Infomation_Message = ++Interactive_BEGIN;
	
	public static final short GC_Look_Up_Message = ++Interactive_BEGIN;
	
	/**切磋*/
	public static short QieCuo_BEGIN = (BASE_NUMBER += NUMBER_PER_SYS);
	
	public static final short CG_QieCuo_Request_Message = ++QieCuo_BEGIN;
	
	public static final short CG_QieCuo_Answer_Message = ++QieCuo_BEGIN;
	
	public static final short GC_QieCuo_Request_Message = ++QieCuo_BEGIN;
	
	public static final short GC_QieCuo_Answer_Message = ++QieCuo_BEGIN;
	
	public static final short GC_QieCuo_Result_Message = ++QieCuo_BEGIN;
	
	
	/**剧情*/
	public static short STORY_BEGIN = (BASE_NUMBER += NUMBER_PER_SYS);
	public static final short CG_STORY_LIST = ++STORY_BEGIN;
	public static final short GC_STORY_LIST = ++STORY_BEGIN;
	public static final short GC_STORY_SHOW = ++STORY_BEGIN;
	
	
	/** 好友 **/
	public static short FRIEND_BEGIN = (BASE_NUMBER += NUMBER_PER_SYS);
	public static final short CG_FRIEND_ADD = ++FRIEND_BEGIN;
	public static final short CG_FRIEND_ADD_BLACK = ++FRIEND_BEGIN;
	public static final short CG_FRIEND_DEL = ++FRIEND_BEGIN;
	public static final short CG_FRIEND_DEL_BLACK = ++FRIEND_BEGIN;
	public static final short CG_FRIEND_GET_LIST = ++FRIEND_BEGIN;
	public static final short GC_FRIEND_LIST = ++FRIEND_BEGIN;
	public static final short GC_FRIEND_NEW_MESSAGE = ++FRIEND_BEGIN;
	public static final short GC_FRIEND_ADD_OR_UPDATE = ++FRIEND_BEGIN;
	public static final short GC_FRIEND_DEL = ++FRIEND_BEGIN;
	public static final short GC_FRIEND_BLACK_ADD = ++FRIEND_BEGIN;
	public static final short GC_FRIEND_BLACK_DEL = ++FRIEND_BEGIN;
	public static final short GC_FRIEND_BLACKS = ++FRIEND_BEGIN;
	public static final short GC_FRIEND_LATEST_LINK = ++FRIEND_BEGIN; 
	public static final short GC_FRIEND_LATEST_DEL = ++FRIEND_BEGIN; 
	public static final short GC_FRIEND_LATEST_ADD_OR_UPDATE = ++FRIEND_BEGIN; 
	public static final short CG_FRIEND_GET_INFO = ++FRIEND_BEGIN; 
	public static final short GC_FRIEND_GET_INFO = ++FRIEND_BEGIN; 
	public static final short GC_FRIEND_ADD_RES = ++FRIEND_BEGIN; 
	
	/** 神秘商店 **/
	public static short SECRET_BEGIN = (BASE_NUMBER += NUMBER_PER_SYS);
	public static final short CG_SECRET_SHOP_LIST = ++SECRET_BEGIN;
	public static final short GC_SECRET_SHOP_LIST = ++SECRET_BEGIN;
	public static final short CG_SECRET_SHOP_BUY = ++SECRET_BEGIN;
	public static final short GC_SECRET_SHOP_BUY = ++SECRET_BEGIN;
	public static final short GC_SECRET_SHOP_TIME_REMAIN = ++SECRET_BEGIN;
	public static final short GC_SECRET_SHOP_BUY_INFO = ++SECRET_BEGIN;
	public static final short CG_SECRET_SHOP_PRECIOUS_INFO = ++SECRET_BEGIN;
	public static final short GC_SECRET_SHOP_PRECIOUS_INFO = ++SECRET_BEGIN;
	
	
	/** 夺宝 **/
	public static short ROBBERY_BEGIN = (BASE_NUMBER += NUMBER_PER_SYS);
	public static final short CG_ROBBERY_BE_INVITE_FRIEND = ++ROBBERY_BEGIN;
	public static final short CG_ROBBERY_DETAIL_INFO = ++ROBBERY_BEGIN;
	public static final short CG_ROBBERY_FRIEND_LIST = ++ROBBERY_BEGIN;
	public static final short CG_ROBBERY_INDEX = ++ROBBERY_BEGIN;
	public static final short CG_ROBBERY_PLYAER_INDEX = ++ROBBERY_BEGIN;
	public static final short CG_ROBBERY_INVITE_FRIEND = ++ROBBERY_BEGIN;
	public static final short CG_ROBBERY_ROB = ++ROBBERY_BEGIN;
	public static final short CG_ROBBERY_START = ++ROBBERY_BEGIN;
	public static final short GC_ROBBERY_BE_FRIEND_INVITE_FRIEND = ++ROBBERY_BEGIN;
	public static final short GC_ROBBERY_BE_ROB = ++ROBBERY_BEGIN;
	public static final short GC_ROBBERY_DETAIL_INFO = ++ROBBERY_BEGIN;
	public static final short GC_ROBBERY_FRIEND_LIST = ++ROBBERY_BEGIN;
	public static final short GC_ROBBERY_INDEX = ++ROBBERY_BEGIN;
	public static final short GC_ROBBERY_INVITE_FRIEND = ++ROBBERY_BEGIN;
	public static final short GC_ROBBERY_PLYAER_INDEX = ++ROBBERY_BEGIN;
	public static final short CG_ROBBERY_REFRESH = ++ROBBERY_BEGIN;
	public static final short GC_ROBBERY_ROB = ++ROBBERY_BEGIN;
	public static final short GC_ROBBERY_END = ++ROBBERY_BEGIN;
	public static final short GC_ROBBERY_PROTECT_BE_ROB = ++ROBBERY_BEGIN;
	public static final short GC_ROBBERY_PROTECT_END = ++ROBBERY_BEGIN;
	public static final short GC_ROBBERY_INDEX_ADD = ++ROBBERY_BEGIN;
	public static final short GC_ROBBERY_ROB_RESULT = ++ROBBERY_BEGIN;
	public static final short GC_ROBBERY_BE_ROB_NEW = ++ROBBERY_BEGIN;
	
	/** 竞技场 */
	public static short ARENA_BEGIN = (BASE_NUMBER += NUMBER_PER_SYS);
	public static final short CG_ARENA_LIST = ++ ARENA_BEGIN;
	public static final short GC_ARENA_LIST = ++ ARENA_BEGIN;
	public static final short CG_ARENA_FIGHT = ++ ARENA_BEGIN;
	public static final short CG_ARENA_MODIFY_MSG = ++ ARENA_BEGIN;
	public static final short CG_ARENA_RANKING_LIST = ++ ARENA_BEGIN;
	public static final short GC_ARENA_RANKING_LIST = ++ ARENA_BEGIN;
	public static final short CG_ARENA_ACHIEVEMENT_LIST = ++ ARENA_BEGIN;
	public static final short GC_ARENA_ACHIEVEMENT_LIST = ++ ARENA_BEGIN;
	public static final short CG_ARENA_ADD_TIME = ++ ARENA_BEGIN;
	public static final short CG_ARENA_UPDATE_MSG = ++ ARENA_BEGIN;
	public static final short GC_ARENA_ADD_TIME = ++ ARENA_BEGIN;
	public static final short GC_ARENA_NOTICE = ++ ARENA_BEGIN;
	
	/** 活动 **/
	public static short ACTIVITY_BEGIN = (BASE_NUMBER += NUMBER_PER_SYS);
	public static final short CG_ACTIVITY_LIST = ++ACTIVITY_BEGIN;
	public static final short CG_ACTIVITY_JOIN = ++ACTIVITY_BEGIN;
	public static final short GC_ACTIVITY_LIST = ++ACTIVITY_BEGIN;
	public static final short CG_ACTIVITY_UNJOIN = ++ACTIVITY_BEGIN;
	public static final short CG_ACTIVITY_BOSS_RELIVE = ++ACTIVITY_BEGIN;
	public static final short GC_ACTIVITY_BOSS_CAN_MOVE = ++ACTIVITY_BEGIN;
	public static final short GC_ACTIVITY_BOSS_INFO = ++ACTIVITY_BEGIN;
	public static final short GC_ACTIVITY_BOSS_PLAYER_INFO = ++ACTIVITY_BEGIN;
	public static final short GC_ACTIVITY_BOSS_RANKS = ++ACTIVITY_BEGIN;
	public static final short CG_ACTIVITY_BOSS_ATTACK = ++ACTIVITY_BEGIN;
	public static final short CG_ACTIVITY_BOSS_GUWU_GOLD = ++ACTIVITY_BEGIN;
	public static final short GC_ACTIVITY_BOSS_GUWU_GOLD = ++ACTIVITY_BEGIN;
	public static final short CG_ACTIVITY_BOSS_GUWU_SEE = ++ACTIVITY_BEGIN;
	public static final short GC_ACTIVITY_BOSS_GUWU_SEE = ++ACTIVITY_BEGIN;
	public static final short GC_ACTIVITY_BOSS_KAOSHANG = ++ACTIVITY_BEGIN;
	public static final short CG_ACTIVITY_BOSS_KAOSHANG = ++ACTIVITY_BEGIN;
	public static final short GC_ACTIVITY_BOSS_KAOSHANG_SHANGHAI = ++ACTIVITY_BEGIN;
	public static final short CG_ACTIVITY_BOSS_BUY_TOWER = ++ACTIVITY_BEGIN;
	public static final short GC_ACTIVITY_BOSS_TOWER_LIST = ++ACTIVITY_BEGIN;
	public static final short GC_ACTIVITY_BOSS_TOWER_ADD = ++ACTIVITY_BEGIN;
	public static final short GC_ACTIVITY_BOSS_RANK_TOP3 = ++ACTIVITY_BEGIN;
	public static final short GC_ACTIVITY_BOSS_TOWER_ATTACK = ++ACTIVITY_BEGIN;
	public static final short GC_ACTIVITY_START = ++ACTIVITY_BEGIN;
	public static final short GC_ACTIVITY_STOP = ++ACTIVITY_BEGIN;
	public static final short CG_ACTIVITY_HERO_WAR_ATTACK = ++ACTIVITY_BEGIN;
	public static final short GC_ACTIVITY_HERO_WAR_LIST = ++ACTIVITY_BEGIN;
	public static final short CG_ACTIVITY_CAMP_WAR_ROB_BACK = ++ACTIVITY_BEGIN;
	public static final short CG_ACTIVITY_CAMP_WAR_ROB_CAMP = ++ACTIVITY_BEGIN;
	public static final short CG_ACTIVITY_CAMP_WAR_START_ROB = ++ACTIVITY_BEGIN;
	public static final short CG_ACTIVITY_CAMP_WAR_USE_SKILL = ++ACTIVITY_BEGIN;
	public static final short GC_ACTIVITY_CAMP_WAR_BUFF = ++ACTIVITY_BEGIN;
	public static final short GC_ACTIVITY_CAMP_WAR_INDEX = ++ACTIVITY_BEGIN;
	public static final short GC_ACTIVITY_CAMP_WAR_LOG = ++ACTIVITY_BEGIN;
	public static final short GC_ACTIVITY_CAMP_WAR_PROTECT_LIST = ++ACTIVITY_BEGIN;
	public static final short GC_ACTIVITY_CAMP_WAR_ROB_ADD_LOG = ++ACTIVITY_BEGIN;
	public static final short GC_ACTIVITY_CAMP_WAR_ROB_COMB_WIN = ++ACTIVITY_BEGIN;
	public static final short GC_ACTIVITY_CAMP_WAR_ROB_LIST = ++ACTIVITY_BEGIN;
	public static final short GC_ACTIVITY_CAMP_WAR_SCORE = ++ACTIVITY_BEGIN;
	public static final short GC_ACTIVITY_CAMP_WAR_SKILLS = ++ACTIVITY_BEGIN;
	public static final short GC_ACTIVITY_CAMP_WAR_TRANSPORT_ADD = ++ACTIVITY_BEGIN;
	public static final short GC_ACTIVITY_CAMP_WAR_START_TRANSPORT = ++ACTIVITY_BEGIN;
	public static final short GC_ACTIVITY_CAMP_WAR_TRANSPORT_DETAIL = ++ACTIVITY_BEGIN;
	public static final short CG_ACTIVITY_CAMP_WAR_TRANSPORT_DETAIL = ++ACTIVITY_BEGIN;
	public static final short GC_ACTIVITY_CAMP_WAR_ROB_INFO = ++ACTIVITY_BEGIN;
	public static final short GC_ACTIVITY_CAMP_WAR_TRANSPORT_DEL = ++ACTIVITY_BEGIN;
	public static final short GC_ACTIVITY_CAMP_WAR_ROB_CDTIME = ++ACTIVITY_BEGIN;
	public static final short GC_ACTIVITY_CAMP_WAR_HERO_BUFF_ADD = ++ACTIVITY_BEGIN;
	public static final short CG_ACTIVITY_GGZJ_RELIVE = ++ACTIVITY_BEGIN;
	public static final short CG_ACTIVITY_GGZJ_GUWU_SEE = ++ACTIVITY_BEGIN;
	public static final short CG_ACTIVITY_GGZJ_GUWU_GOLD = ++ACTIVITY_BEGIN;
	public static final short CG_ACTIVITY_GGZJ_PFCZ = ++ACTIVITY_BEGIN;
	public static final short CG_ACTIVITY_GGZJ_GET_TOWER = ++ACTIVITY_BEGIN;
	public static final short GC_ACTIVITY_GGZJ_CAN_MOVE = ++ACTIVITY_BEGIN;
	public static final short GC_ACTIVITY_GGZJ_GUWU_GOLD = ++ACTIVITY_BEGIN;
	public static final short GC_ACTIVITY_GGZJ_GUWU_SEE = ++ACTIVITY_BEGIN;
	public static final short GC_ACTIVITY_GGZJ_INFO = ++ACTIVITY_BEGIN;
	public static final short GC_ACTIVITY_GGZJ_PFCZ = ++ACTIVITY_BEGIN;
	public static final short GC_ACTIVITY_GGZJ_PLAYER_INFO = ++ACTIVITY_BEGIN;
	public static final short GC_ACTIVITY_GGZJ_RANKS = ++ACTIVITY_BEGIN;
	public static final short GC_ACTIVITY_GGZJ_GET_TOWER = ++ACTIVITY_BEGIN;
	public static final short GC_ACTIVITY_GGZJ_PROGRESS = ++ACTIVITY_BEGIN;
	public static final short GC_ACTIVITY_GGZJ_RANK_TOP3 = ++ACTIVITY_BEGIN;
	public static final short GC_ACTIVITY_WORLD_WAR_PLAYER_INFO = ++ACTIVITY_BEGIN;
	public static final short GC_ACTIVITY_WORLD_WAR_PLAYRES = ++ACTIVITY_BEGIN;
	public static final short GC_ACTIVITY_WORLD_WAR_INFO = ++ACTIVITY_BEGIN;
	public static final short GC_ACTIVITY_WORLD_WAR_BATTLE_ADD = ++ACTIVITY_BEGIN;
	public static final short CG_ACTIVITY_WORLD_WAR_GUWU = ++ACTIVITY_BEGIN;
	public static final short GC_ACTIVITY_WORLD_WAR_PLAYRES_ADD = ++ACTIVITY_BEGIN;
	public static final short CG_ACTIVITY_WORLD_WAR_AUTO_WAR = ++ACTIVITY_BEGIN;
	public static final short GC_ACTIVITY_WORLD_WAR_PLAYRES_DEL = ++ACTIVITY_BEGIN;
	public static final short GC_ACTIVITY_WORLD_WAR_RANKS_INFO = ++ACTIVITY_BEGIN;
	public static final short CG_ACTIVITY_WORLD_WAR_START = ++ACTIVITY_BEGIN;
	public static final short CG_ACTIVITY_WORLD_WAR_CANCEL = ++ACTIVITY_BEGIN;
	
	
	/** 每日任务 **/
	public static short DAY_TASK_BEGIN = (BASE_NUMBER += NUMBER_PER_SYS);
	public static final short CG_DAY_TASK_INFO = ++DAY_TASK_BEGIN;
	public static final short CG_GET_NEW_TASK = ++DAY_TASK_BEGIN;
	public static final short CG_DAY_TASK_DROP = ++DAY_TASK_BEGIN;
	public static final short GC_DAY_TASK_INFO = ++DAY_TASK_BEGIN;
	public static final short GC_GET_NEW_TASK = ++DAY_TASK_BEGIN;
	public static final short GC_DAY_TASK_COMPLETE = ++DAY_TASK_BEGIN;
	public static final short GC_DAY_TASK_DROP = ++DAY_TASK_BEGIN;
	
	/** 家族 **/
	public static short FAMILY_BEGIN = (BASE_NUMBER += NUMBER_PER_SYS);
	public static final short CG_APPLY_FAMILY = ++FAMILY_BEGIN;
	public static final short CG_APPLY_MEMBER_LIST = ++FAMILY_BEGIN;
	public static final short CG_CREATE_FAMILY = ++FAMILY_BEGIN;
	public static final short CG_DELETE_FAMILY = ++FAMILY_BEGIN;
	public static final short CG_APPLY_LEADER = ++FAMILY_BEGIN;
	public static final short CG_EXAMINE_MEMBER = ++FAMILY_BEGIN;
	public static final short CG_FAMILY_DETAIL_INFO = ++FAMILY_BEGIN;
	public static final short CG_FAMILY_INFO_LIST = ++FAMILY_BEGIN;
	public static final short CG_FAMILY_LOG = ++FAMILY_BEGIN;
	public static final short CG_LEAVE_FAMILY = ++FAMILY_BEGIN;
	public static final short CG_TRANSFER_LEADER = ++FAMILY_BEGIN;
	public static final short CG_UPDATE_FAMILY_INFO = ++FAMILY_BEGIN;
	public static final short CG_UPDATE_FAMILY_ROLE = ++FAMILY_BEGIN;
	public static final short GC_APPLY_MEMBER_LIST = ++FAMILY_BEGIN;
	public static final short GC_FAMILY_DETAIL_INFO = ++FAMILY_BEGIN;
	public static final short GC_FAMILY_INFO_LIST = ++FAMILY_BEGIN;
	public static final short GC_FAMILY_LOG = ++FAMILY_BEGIN;
	public static final short GC_CREATE_FAMILY = ++FAMILY_BEGIN;
	public static final short GC_APPlY_FAMILY_SUCCESS = ++FAMILY_BEGIN;
	
	
	/** 将星云路 **/
	public static short TOWER_BEGIN = (BASE_NUMBER += NUMBER_PER_SYS);
	public static final short CG_TOWER_INIT = ++TOWER_BEGIN;
	public static final short CG_TOWER_INFO = ++TOWER_BEGIN;
	public static final short GC_TOWER_INFO = ++TOWER_BEGIN;
	public static final short CG_TOWER_REFRESH = ++TOWER_BEGIN;
	public static final short GC_TOWER_REFRESH = ++TOWER_BEGIN;
	public static final short CG_TOWER_BATTLE = ++TOWER_BEGIN;
	public static final short GC_TOWER_BATTLE_RESULT = ++TOWER_BEGIN;
	public static final short GC_TOWER_BATTLE = ++TOWER_BEGIN;
	public static final short CG_TOWER_SPEEDUP = ++TOWER_BEGIN;
	public static final short GC_TOWER_SPEEDUP = ++TOWER_BEGIN;
	public static final short GC_TOWER_SECRETE = ++TOWER_BEGIN;
	public static final short GC_TOWER_BAG = ++TOWER_BEGIN;
	public static final short CG_TOWER_AUTO_START = ++TOWER_BEGIN;
	public static final short GC_TOWER_AUTO_START = ++TOWER_BEGIN;
	public static final short GC_TOWER_AUTO_COMPLETE_ONE = ++TOWER_BEGIN;
	public static final short GC_TOWER_AUTO_COMPLETE = ++TOWER_BEGIN;
	public static final short CG_TOWER_AUTO_CANCEL = ++TOWER_BEGIN;
	public static final short GC_TOWER_AUTO_CANCEL = ++TOWER_BEGIN;
	
	/** 星魂系统 **/
	public static short XINGHUN_BEGIN = (BASE_NUMBER += NUMBER_PER_SYS);
	public static final short CG_XINGHUN_ACTIVATE = ++XINGHUN_BEGIN; 
	public static final short CG_XINGHUN_DESTRUCT = ++XINGHUN_BEGIN; 
	public static final short CG_XINGHUN_XILIAN = ++XINGHUN_BEGIN; 
	public static final short CG_XINGHUN_ADD = ++XINGHUN_BEGIN; 
	public static final short CG_XINGHUN_RED = ++XINGHUN_BEGIN; 
	public static final short CG_XINGHUN_PILIANG_XILIAN = ++XINGHUN_BEGIN; 
	public static final short GC_XINGHUN_PILIANG_XILIAN = ++XINGHUN_BEGIN;
	public static final short CG_XINGHUN_PILIANG_XILIAN_REMAIN = ++XINGHUN_BEGIN;
	public static final short GC_XINGHUN_ACTIVATE = ++XINGHUN_BEGIN;
	public static final short GC_XINGHUN_PILIANG_XILIAN_REMAIN = ++XINGHUN_BEGIN;
	public static final short GC_XINGHUN_XILIAN = ++XINGHUN_BEGIN;
	
	
	
	
	
	/** 礼包奖励 **/
	public static short GIFT_BAG_BEGIN = (BASE_NUMBER += NUMBER_PER_SYS);
	public static final short CG_GET_GIFT_BAG_PRIZE = ++GIFT_BAG_BEGIN;
	public static final short CG_GIFT_BAG_INFO_LIST = ++GIFT_BAG_BEGIN;
	public static final short GC_GIFT_BAG_INFO_LIST = ++GIFT_BAG_BEGIN;
	
	/** 兵法**/
	public static short WARCRAFT_BEGIN = (BASE_NUMBER += NUMBER_PER_SYS);
	public static final short CG_DROP_WARCRAFT = ++WARCRAFT_BEGIN;
	public static final short CG_GET_ALL_WARCRAFT = ++WARCRAFT_BEGIN;
	public static final short CG_GET_PRIZE_WARCRAFT = ++WARCRAFT_BEGIN;
	public static final short CG_GET_WARCRAFT = ++WARCRAFT_BEGIN;
	public static final short CG_GRASP_WARCRAFT = ++WARCRAFT_BEGIN;
	public static final short CG_MOVE_WARCRAFT = ++WARCRAFT_BEGIN;
	public static final short CG_REQ_ALL_WARCRAFT_BAG_INFO = ++WARCRAFT_BEGIN;
	public static final short CG_REQ_WARCRAFT_INFO = ++WARCRAFT_BEGIN;
	public static final short CG_REQ_WARCRAFT_TEMP_INFO = ++WARCRAFT_BEGIN;
	public static final short CG_SELL_WARCRAFT = ++WARCRAFT_BEGIN;
	public static final short GC_REMOVE_WARCRAFT = ++WARCRAFT_BEGIN;
	public static final short GC_REQ_WARCRAFT_INFO = ++WARCRAFT_BEGIN;
	public static final short GC_RESET_WARCRAFT_CAPACITY = ++WARCRAFT_BEGIN;
	public static final short GC_WARCRAFT_BAG = ++WARCRAFT_BEGIN;
	public static final short GC_WARCRAFT_TEMP_INFO = ++WARCRAFT_BEGIN;
	public static final short GC_WARCRAFT_UPDATE = ++WARCRAFT_BEGIN;
	public static final short CG_COMPOSE_All_WARCRAFT = ++WARCRAFT_BEGIN;
	public static final short CG_SELL_All_WARCRAFT = ++WARCRAFT_BEGIN;
	public static final short GC_GRASP_WARCRAFT_RESULT = ++WARCRAFT_BEGIN;
	public static final short GC_PICK_WARCRAFT_RESULT = ++WARCRAFT_BEGIN;
	public static final short GC_SELL_WARCRAFT_RESULT = ++WARCRAFT_BEGIN;
	
	/** 摇钱树**/
	public static short TREE_BEGIN = (BASE_NUMBER += NUMBER_PER_SYS);
	public static final short CG_TREE_INFO = ++TREE_BEGIN;
	public static final short GC_TREE_INFO = ++TREE_BEGIN;
	public static final short CG_TREE_SHAKE = ++TREE_BEGIN;
	public static final short CG_TREE_SHAKE_BATCH = ++TREE_BEGIN;
	public static final short GC_TREE_SHAKE = ++TREE_BEGIN;
	public static final short CG_WATER_INFO = ++TREE_BEGIN;
	public static final short GC_WATER_INFO = ++TREE_BEGIN;
	public static final short CG_WATER_MYSELF = ++TREE_BEGIN;
	public static final short CG_WATER_FRIEND = ++TREE_BEGIN;
	public static final short GC_WATER_RESULT = ++TREE_BEGIN;
	public static final short CG_GET_FRUIT = ++TREE_BEGIN;
	public static final short GC_GET_FRUIT = ++TREE_BEGIN;
	public static final short CG_GET_WATER_RECORD = ++TREE_BEGIN;
	public static final short GC_GET_WATER_RECORD = ++TREE_BEGIN;
	public static final short CG_FRIEND_WATER_INFO = ++TREE_BEGIN;
	public static final short GC_FRIEND_WATER_INFO = ++TREE_BEGIN;
	public static final short CG_WATER_FRIEND_BATCH = ++TREE_BEGIN;
	public static final short CG_GET_FRIEND_WATER_RECORD = ++TREE_BEGIN;
	public static final short GC_GET_FRIEND_WATER_RECORD = ++TREE_BEGIN;
	
	

	/** 提示按钮**/
	public static short PROMPT_BUTTON_BEGIN = (BASE_NUMBER += NUMBER_PER_SYS);
	public static final short GC_PROMPT_BUTTON = ++PROMPT_BUTTON_BEGIN;
	public static final short CG_DELETE_PROMPT_BUTTON = ++PROMPT_BUTTON_BEGIN;
	public static final short GC_WINDOW_MESSAGE = ++PROMPT_BUTTON_BEGIN;
}
