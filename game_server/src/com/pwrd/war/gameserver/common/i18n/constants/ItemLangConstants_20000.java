package com.pwrd.war.gameserver.common.i18n.constants;

import com.pwrd.war.core.annotation.SysI18nString;

public class ItemLangConstants_20000 {
 
	public static Integer ITEM_BASE = 20000;
	@SysI18nString(content = "背包",comment="主背包")
	public static final Integer BAG_NAME_PRIM = ++ITEM_BASE;
	@SysI18nString(content = "临时包裹")
	public static final Integer BAG_NAME_TEMP = ++ITEM_BASE;
	@SysI18nString(content = "军官身上装备")
	public static final Integer BAG_NAME_PET_EQUIP = ++ITEM_BASE;
	@SysI18nString(content = "仓库")
	public static final Integer BAG_NAME_DEPOT = ++ITEM_BASE;
	@SysI18nString(content = "将星云路")
	public static final Integer BAG_NAME_TOWER = ++ITEM_BASE;
	@SysI18nString(content = "星魂镶嵌背包")
	public static final Integer BAG_NAME_XIANGQIAN = ++ITEM_BASE;
	@SysI18nString(content = "此道具当前不可用")
	public static final Integer ITEM_NOT_AVAILABLE = ++ITEM_BASE;
	@SysI18nString(content = "您的背包没有足够的空间")
	public static final Integer ITEM_NOT_ENOUGH_SPACE = ++ITEM_BASE;
	@SysI18nString(content = "{0}中需要{1}个空位", comment = "{0}需要腾出空间的包的名字{1}留出空位个数")
	public static final Integer ITEM_MAKE_SPACE = ++ITEM_BASE;
	@SysI18nString(content = "该道具不能丢弃")
	public static final Integer ITEM_CANNOT_DROP = ++ITEM_BASE;
	@SysI18nString(content = "您的等级不够，无法使用该物品")
	public static final Integer ITEM_USEFAIL_LEVEL = ++ITEM_BASE;
	@SysI18nString(content = "该军官阵营不符合要求")
	public static final Integer ITEM_USEFAIL_JOB = ++ITEM_BASE;
	@SysI18nString(content = "该军官性别不符合要求")
	public static final Integer ITEM_USEFAIL_SEX = ++ITEM_BASE;
	@SysI18nString(content = "装备已经损坏，请修理后再使用")
	public static final Integer ITEM_NEED_REPIRE = ++ITEM_BASE;
	@SysI18nString(content = "当前不需要恢复")
	public static final Integer ITEM_NO_NEED_TO_RECOVER = ++ITEM_BASE;
	@SysI18nString(content = "该物品不存在")
	public static final Integer ITEM_NOT_EXIST = ++ITEM_BASE;
	@SysI18nString(content = "您的{0}因过使用期限而被删除", comment = "{0}道具名称")
	public static final Integer ITEM_DELETE_SINCE_EXPIRED = ++ITEM_BASE;
	@SysI18nString(content = "还可以使用{0}天", comment = "{0}剩余使用天数")
	public static final Integer ITEM_LEFT_DAYS = ++ITEM_BASE;
	@SysI18nString(content = "还可以使用不足1天")
	public static final Integer ITEM_LESS_THAN_ONE_DAY = ++ITEM_BASE;
	@SysI18nString(content = "已过期，即将被删除")
	public static final Integer ITEM_EXPIRED = ++ITEM_BASE;
	@SysI18nString(content = "当前状态不可以拆分道具")
	public static final Integer ITEM_CANNOT_SLIT = ++ITEM_BASE;
	@SysI18nString(content = "当前状态不可以整理背包")
	public static final Integer ITEM_CANNOT_TIDY_BAG = ++ITEM_BASE;
	@SysI18nString(content = "当前状态不可以丢弃道具")
	public static final Integer ITEM_CANNOT_DROP_NOW = ++ITEM_BASE;
	@SysI18nString(content = "该道具已经绑定")
	public static final Integer ITEM_BIND_YET = ++ITEM_BASE;
	@SysI18nString(content = "该物品不可以使用")
	public static final Integer ITEM_CANNOT_USE = ++ITEM_BASE;
	@SysI18nString(content = "该道具不可以交易")
	public static final Integer ITEM_CANNOT_TRADE = ++ITEM_BASE;
	@SysI18nString(content = "您当前的状态不能开始交易")
	public static final Integer YOU_CANNOT_TRADE = ++ITEM_BASE;
	@SysI18nString(content = "对方当前的状态不能开始交易")
	public static final Integer TARGET_CANNOT_TRADE = ++ITEM_BASE;
	@SysI18nString(content = "移动道具失败")
	public static final Integer MOVE_ITEM_FAIL = ++ITEM_BASE;
	@SysI18nString(content = "扩充背包")
	public static final Integer OPEN_BAG_SIZE = ++ITEM_BASE;
	@SysI18nString(content = "钻石不足，无法扩充背包")
	public static final Integer OPEN_BAG_MONEY_NOT_ENOUGTH = ++ITEM_BASE;
	@SysI18nString(content = "该装备强化等级过高,不可卖出,请先降级到新兵1级后再卖")
	public static final Integer CAN_NOT_SELL_ENHANCE_LEVEL = ++ITEM_BASE;
	@SysI18nString(content = "绑定物品不可以出售")
	public static final Integer CAN_NOT_SELL_FREEZE = ++ITEM_BASE;
	@SysI18nString(content = "没有足够的勋章")
	public static final Integer NOT_ENOUGH_MEDAL = ++ITEM_BASE;
	@SysI18nString(content = "{0}满了")
	public static final Integer BAG_FULL = ++ITEM_BASE;
	@SysI18nString(content = "{0}数量不足{1}")
	public static final Integer ITEM_NOT_ENOUGH = ++ITEM_BASE;
	@SysI18nString(content = "获得了{0}经验")
	public static final Integer GET_EXP = ++ITEM_BASE;
	@SysI18nString(content = "{0}变身卡")
	public static final Integer BUFF_CHANGE_NAME = ++ITEM_BASE;
	@SysI18nString(content = "变为{0}，持续{1}分钟")
	public static final Integer BUFF_CHANGE_DESC = ++ITEM_BASE;
	@SysI18nString(content = "兵法背包")
	public static final Integer WARCRAFT_BAG = ++ITEM_BASE;
	@SysI18nString(content = "兵法装备位")
	public static final Integer HUMAN_WARCRAFT_EQUIP_BAG = ++ITEM_BASE;
	@SysI18nString(content = "兵法装备位")
	public static final Integer PET_WARCRAFT_EQUIP_BAG = ++ITEM_BASE;
	@SysI18nString(content = "已经拥有相同的武将，不能再次使用此卡")
	public static final Integer PET_CARD_USED = ++ITEM_BASE;
	@SysI18nString(content = "武将卡使用成功")
	public static final Integer PET_CARD_OK = ++ITEM_BASE;
	@SysI18nString(content = "成功扩充{0}<font color='#fff600'>{1}</font>格")
	public static final Integer BAG_EXPAND_OK = ++ITEM_BASE;
	@SysI18nString(content = "{0}经过一番努力打造出{1}")
	public static final Integer USE_RELL_SUCC = ++ITEM_BASE;
	
	public static Integer ITEM_BASE_2000 = 20000 + 2000;
	/** 商店商城相关的常量 20000 ~ 22000 */
	@SysI18nString(content = "您购买了{0}个{1}", comment = "{0}购买物品数量{1}购买物品名称")
	public static final Integer SHOP_BUY_ITEM = ++ITEM_BASE_2000;
	@SysI18nString(content = "您出售了{0}个{1}", comment = "{0}出售物品数量{1}出售物品名称")
	public static final Integer SHOP_SELL_ITEM = ++ITEM_BASE_2000;
	@SysI18nString(content = "购买失败")
	public static final Integer SHOP_BUY_FAIL = ++ITEM_BASE_2000;
	@SysI18nString(content = "出售失败")
	public static final Integer SHOP_SELL_FAIL = ++ITEM_BASE_2000;
	@SysI18nString(content = "该物品不能出售")
	public static final Integer SHOP_ITEM_CANNOT_SELL = ++ITEM_BASE_2000;
	@SysI18nString(content = "修理成功")
	public static final Integer SHOP_ITEM_REPAIR = ++ITEM_BASE_2000;
	@SysI18nString(content = "没有可修理的装备")
	public static final Integer SHOP_NO_NEED_REPAIR = ++ITEM_BASE_2000;
	@SysI18nString(content = "购买物品")
	public static final Integer SHOP_BUY_COST = ++ITEM_BASE_2000;
	@SysI18nString(content = "用金币购买该物品")
	public static final Integer SHOP_BUY_DESC = ++ITEM_BASE_2000;
	@SysI18nString(content = "金币不足,不能购买")
	public static final Integer SHOP_BUY_ERR_ENOUGH_GOLD = ++ITEM_BASE_2000;
	@SysI18nString(content = "购买该装备需要商店等级达到{0}级")
	public static final Integer SHOP_BUY_NEED_LEVEL = ++ITEM_BASE_2000;
	@SysI18nString(content = "背包已满,不能购卖")
	public static final Integer SHOP_BUY_ERR_INVENTORY_IS_FULL = ++ITEM_BASE_2000;
	@SysI18nString(content = "用钻石购买该物品")
	public static final Integer SHOPMALL_BUY_DESC = ++ITEM_BASE_2000;
	@SysI18nString(content = "未知原因不能购买该物品")
	public static final Integer SHOPMALL_BUY_ERR = ++ITEM_BASE_2000;
	@SysI18nString(content = "购买该物品需要商店等级达到{0}级")
	public static final Integer SHOPMALL_BUY_NEED_LEVEL = ++ITEM_BASE_2000;
	@SysI18nString(content = "购买该物品需要VIP等级达到{0}级")
	public static final Integer SHOPMALL_BUY_NEED_VIP_LEVEL = ++ITEM_BASE_2000;
	@SysI18nString(content = "物品库存为零")
	public static final Integer COUNT_IS_ZERO = ++ITEM_BASE_2000;
	@SysI18nString(content = "钻石不足,不能购买")
	public static final Integer SHOPMALL_BUY_ERR_ENOUGH_DIAMOND = ++ITEM_BASE_2000;
	@SysI18nString(content = "背包已满,不能购卖")
	public static final Integer SHOPMALL_BUY_ERR_INVENTORY_IS_FULL = ++ITEM_BASE_2000;
	@SysI18nString(content = "购买物品数量不合法")
	public static final Integer SHOPMALL_BUY_ERR_ITEM_NUM = ++ITEM_BASE_2000;
	@SysI18nString(content = "扣钱过程失败")
	public static final Integer SHOPMALL_DEDUCT_MONEY_ERR = ++ITEM_BASE_2000;
	@SysI18nString(content = "刷新神秘商店")
	public static final Integer SHOPMALL_REFRESH_ITEM = ++ITEM_BASE_2000;
	@SysI18nString(content = "购买成功")
	public static final Integer SUCCESS_BUY = ++ITEM_BASE_2000;
	
	public static Integer ITEM_BASE_3000 = 20000 + 3000;
	/**********************************/
	@SysI18nString(content = "该装备已经被穿上")
	public static final Integer WEARED_THIS_EQUIP = ++ITEM_BASE_3000;	
	@SysI18nString(content = "背包类型错误")
	public static final Integer BAG_TYPE_ERROR = ++ITEM_BASE_3000;	
	@SysI18nString(content = "已经开启全部格子了")
	public static final Integer BAG_EXPAND_ALL = ++ITEM_BASE_3000;	
	@SysI18nString(content = "已经达到最大强化等级")
	public static final Integer ENHANCE_MAX_LEVEL = ++ITEM_BASE_3000;	
	@SysI18nString(content = "强化队列已满")
	public static final Integer ENHANCE_QUEUE_FULL = ++ITEM_BASE_3000;
	@SysI18nString(content = "强化等级不能超过玩家等级")
	public static final Integer ENHANCE_LEVEL_GREAT_HUMAN = ++ITEM_BASE_3000;
	@SysI18nString(content = "强化失败，返还{0}铜钱")
	public static final Integer ENHANCE_FAIL = ++ITEM_BASE_3000;
	@SysI18nString(content = "恭喜,强化成功!")
	public static final Integer ENHANCE_SUCCESS = ++ITEM_BASE_3000;
}
