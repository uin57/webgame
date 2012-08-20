package com.pwrd.war.gameserver.currency;

import com.pwrd.war.core.enums.IndexedEnum;

public enum CurrencyCostType implements IndexedEnum {
	
	NULL(0, null),
	/**VIP*/
	VIP(1, "VIP"),
	/** 商城购买 */
	SHANGCHENG(2, "商城购买"),
	/** 活动*/
	HUODONG(3, "活动"),
	/** GM*/
	GM(4, "GM命令"), 
	/** 活动*/
	XITONG(5, "系统赠送"), 
	/** 守军争夺战*/
	SHOUJUN(6, "守军争夺战"), 
	/** 竞技场*/
	JINGJICHANG(7, "竞技场"), 
	/** 强化*/
	QIANGHUA(8, "强化"), 
	/** 提升成长*/
	TISHENGCZ(9, "提升成长"), 
	/** 修炼*/
	XIULIAN(10, "修炼"),
	/** 武将*/
	WUJIANG(11, "武将"), 
	/** 副本*/
	REP(12, "副本"), 
	/** 夺宝*/
	DUOBAO(13, "夺宝"), 
	/** 爬塔*/
	TOWER(14, "爬塔"), 
	/** 购买体力*/
	VIT(15, "购买体力"), 
	/** 兵法*/
	WARCRAFT(16, "兵法"), 
	/** 转职*/
	VOCATIONTRANFER(17, "转职"), 
	
	/** 摇钱树*/
	TREE(18, "摇钱树"), 
	/** 战斗 */
	PVMFIGHT(19, "战斗"),
	/** 南蛮入侵 */
	BOSSACTIVITY(20, "南蛮入侵"),
	
	/** 其他*/
	QITA(100, "其他"), 

	
	;
	
	/** 枚举的索引 */
	public final int index;
	
	/** 货币名称的key */
	private final String type;

	
	private CurrencyCostType(int index, String type) {
		this.index = index;
		this.type = type;
	}
	
	/**
	 * 获取类型索引
	 */
	@Override
	public int getIndex() {
		return index;
	}
	
	
	/**
	 * 获取购买类型
	 * @return
	 */
	public String getType() {
		return type;
	}
}
