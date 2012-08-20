package com.pwrd.war.gameserver.command;

/**
 * GM命令相关常量
 * 
 */
public class CommandConstants {

	/** 重新加载配置 格式: //reload name params */
	public static final String GM_RELOAD = "reload";

	/** 玩家 */
	public static final String GM_CMD_PLAYER = "player";
	
	/** 公告 */
	public static final String GM_CMD_NOTICE = "notice";

	/** 给金钱 */
	public static final String GM_CMD_GIVE_MONEY = "givemoney";

	/** 给道具 */
	public static final String GM_CMD_GIVE_ITEM = "giveitem";
	
	/** 给武将 */
	public static final String GM_CMD_GIVE_PET = "givepet";
	
	/** 给军令 */
	public static final String GM_CMD_GIVE_TOKEN = "givetoken";
	
	/** 改变vip级别 */
	public static final String GM_CMD_CHANGE_VIPLEVEL = "changevip";

	/** 按名字给道具 */
	public static final String GM_CMD_GIVE_ITEM_BY_NAME = "giveitembn";

	/** 显示角色ID */
	public static final String GM_CMD_SHOW_ROLE_ID = "showroleid";

	/** 增加冷却队列 */
	public static final String GM_CMD_ADD_CD = "addcd";
	/** 清除冷却队列冷却时间 */
	public static final String GM_CMD_KILL_CD_TIME = "killcdtime";
	/** 减少冷却时间 */
	public static final String GM_CMD_SUB_CD_TIME = "subcdtime";
	/** 刷新所有npc*/
	public static final String GM_CMD_REFRESH_ENEMIES = "refreshnpc";
	/** 直接过关 */
	public static final String GM_CMD_CLEAR_MISSION = "clearmission";
	/** 开启所有建筑 */
	public static final String GM_CMD_BUILDING = "building";
	/** 给军功 */
	public static final String GM_CMD_GIVE_EXPLOIT = "giveexploit";
	/** 给威望 */	
	public static final String GM_CMD_GIVE_PRESTIGE = "giveprestige";
	/** 给荣誉 */	
	public static final String GM_CMD_GIVE_HONOR = "givehonor";
	/** 武将系统 */
	public static final String GM_CMD_PET = "pet";
	/** 新手引导 */
	public static final String GM_CMD_GUIDE = "guide";
	/** 军团系统 */
	public static final String GM_CMD_GUILD = "guild";	
	/** 开放功能 */
	public static final String GM_CMD_FUNC = "func";
	/** 开启活动 */
	public static final String GM_CMD_ACTIVITY = "activity";
	/** 修改迁徙等级 */
	public static final String GM_CMD_CHANGE_MOVE_LEVEL = "changemovelevel";
	/** 清空背包 */
	public static final String GM_CMD_CLEAR_ITEM = "clearitem";
	/** 清空背包 */
	public static final String GM_CMD_CLEAR_MONEY = "clearmoney";
	/** 清空背包 */
	public static final String GM_CMD_CLEAR_FOOD = "clearfood";
	/** 给粮食 */
	public static final String GM_CMD_GIVE_FOOD = "givefood";
	/** 节日活动 */
	public static final String GM_CMD_FESTIVAL = "festival";
	/** 科技 */
	public static final String GM_CMD_TECH = "tech";
	/** 商队 */
	public static final String GM_CMD_TRADE = "trade";
	/** 季节更替 */
	public static final String GM_CMD_CHANGE_SEASON = "changeseason";
	/** 城市迁徙 */
	public static final String GM_CMD_CITY_MOVE = "citymove";
	/** 战斗模拟 */
	public static final String GM_CMD_BATTLE_EMU = "battleemu";
	/** 矿战 */
	public static final String GM_CMD_MINE_WAR = "minewar";
	/** 直接领日常任务 */
	public static final String GM_CMD_GIVE_DAILY_QUEST = "givedailyquest";
	/** 清除日常任务次数 */
	public static final String GM_CMD_CLEAR_DAILY_QUEST_COUNT = "cleardailyquestcount";
	
	/** 设置绑定的时间*/
	public static final String GM_CMD_SET_FREEZETIME = "setfreezetime";
	
	/** 增加等级*/
	public static final String GM_CMD_ADD_LEVEL = "addlevel";
	/** 增加装备*/
	public static final String GM_CMD_ADD_ITEM = "additem";
	/** 增加技能*/
	public static final String GM_CMD_ADD_SKILL = "addskill";
	/** 增加武将*/
	public static final String GM_CMD_ADD_PET = "addpet";
	/** 增加武将等级*/
	public static final String GM_CMD_ADD_PET_LEVEL = "addpetlevel";
	
	public static final String GM_CMD_REMOVE_PET = "removepet";
	
	public static final String GM_CMD_MOVE = "move";
	/** 添加武将到阵法中*/
	public static final String ADD_PET_TO_FORM = "addPetToForm";
	
	/** 增加用户充值金额 */
	public static final String GM_CMD_ADD_CHARGE = "addCharge";
	
	/** 兵法 */
	public static final String GM_CMD_WARCRAFT = "warcraft";
	
	/**星魂*/
	public static final String GM_CMD_XINGHUN = "addxinghun";
	
	/**摇钱次数*/
	public static final String GM_CMD_TREE_SHAKE = "treeshake";
	
	/**战斗杀怪*/
	public static final String KILL_MONSTER = "kill";
	
	/**摇钱次数*/
	public static final String GM_CMD_SET_TREE_EXP = "treeExp";
		
}
