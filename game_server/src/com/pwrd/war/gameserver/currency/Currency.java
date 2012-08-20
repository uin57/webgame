package com.pwrd.war.gameserver.currency;

import java.util.List;

import com.pwrd.war.core.enums.IndexedEnum;
import com.pwrd.war.core.util.EnumUtil;
import com.pwrd.war.gameserver.common.i18n.constants.CommonLangConstants_10000;
import com.pwrd.war.gameserver.role.properties.HumanNumProperty;
import com.pwrd.war.gameserver.role.properties.RoleBaseIntProperties;

public enum Currency implements IndexedEnum {
	
	NULL(0, 0, 0),
	/** 元宝 */
	GOLD(3, HumanNumProperty.GOLD, CommonLangConstants_10000.CURRENCY_NAME_GOLD),
	/** 系统元宝，礼券 */
	COUPON(2, HumanNumProperty.COUPON, CommonLangConstants_10000.CURRENCY_NAME_COUPON),
	/** 铜钱 */
	COINS(1, HumanNumProperty.COINS, CommonLangConstants_10000.CURRENCY_NAME_COINS),  
	
	;
	
	/** 枚举的索引 */
	public final int index;
	
	/** 此货币类型在任务属性常量中的索引 @see {@link RoleBaseIntProperties} */
	private final int roleBaseIntPropIndex;
	
	/** 货币名称的key */
	private final Integer nameKey;
	
	/** 按索引顺序存放的枚举数组 */
	private static final List<Currency> indexes = IndexedEnum.IndexedEnumUtil.toIndexes(Currency.values());

	
	private Currency(int index, int roleBaseIntPropIndex, Integer nameKey) {
		this.index = index;
		this.roleBaseIntPropIndex = roleBaseIntPropIndex;
		this.nameKey = nameKey;
	}
	
	/**
	 * 获取货币索引
	 */
	@Override
	public int getIndex() {
		return index;
	}
	
	/**
	 * 取得货币的名称key
	 * 
	 * @return
	 */
	public Integer getNameKey() {
		return this.nameKey;
	}
	
	/**
	 * 获取货币的基本属性索引
	 * @return
	 */
	public int getRoleBaseIntPropIndex() {
		return roleBaseIntPropIndex;
	}

	/**
	 * 根据指定的索引获取枚举的定义
	 * 
	 * @param index
	 *            枚举的索引
	 * @return
	 */
	public static Currency valueOf(final int index) {
		return EnumUtil.valueOf(indexes, index);
	}
	
}
