package com.pwrd.war.gameserver.timeevent;

import java.util.List;

import com.pwrd.war.core.enums.IndexedEnum;
import com.pwrd.war.core.util.EnumUtil;

/**
 * 
 * 时间波动变化类型
 * 
 */
public enum TimeWaveChangeType implements IndexedEnum {
	
	EQUIP_ENHANCE(1),/** 装备强化 */
	
	GRAIN_PRICE(2),/** 粮价变化 */
	
	;
	
	private final int index;
	
	private TimeWaveChangeType(int index) {
		this.index = index;
	}

	@Override
	public int getIndex() {
		return this.index;
	}
	
	private static final List<TimeWaveChangeType> values = IndexedEnumUtil.toIndexes(TimeWaveChangeType.values());

	public static TimeWaveChangeType valueOf(int index) {
		return EnumUtil.valueOf(values, index);
	}

}
