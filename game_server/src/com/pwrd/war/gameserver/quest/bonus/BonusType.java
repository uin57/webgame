package com.pwrd.war.gameserver.quest.bonus;

import java.util.List;

import com.pwrd.war.core.enums.IndexedEnum;
import com.pwrd.war.core.util.EnumUtil;

/**
 * 
 * 奖励常量
 * 
 */
public enum BonusType implements IndexedEnum {
	
	/** 固定物品奖励 */
	FIX_ITEM_BONUS(0)
	;

	private int index;

	private BonusType(int index) {
		this.index = index;
	}

	@Override
	public int getIndex() {
		return index;
	}

	private static final List<BonusType> values = IndexedEnumUtil.toIndexes(BonusType.values());

	public static BonusType valueOf(int index) {
		return EnumUtil.valueOf(values, index);
	}

}

