package com.pwrd.war.core.uuid;

import java.util.List;

import com.pwrd.war.core.enums.IndexedEnum;
import com.pwrd.war.core.util.EnumUtil;

/**
 * 游戏中的UUID类型
 *
 * 
 */
public enum UUIDType implements IndexedEnum {
	/** 玩家角色的UUID */
	HUMAN(0),
	/** 物品的UUID */
    ITEM(1),
    /** 宠物的UUID */
    PET(2),
	/** 场景的UUID */
	SCENE(3),
	;
	
	private final int index;
	/** 按索引顺序存放的枚举数组 */
	private static final List<UUIDType> values = IndexedEnumUtil.toIndexes(UUIDType.values());

	/**
	 * 
	 * @param index
	 *            枚举的索引,从0开始
	 */
	private UUIDType(int index) {
		this.index = index;
	}

	@Override
	public int getIndex() {
		return this.index;
	}

	/**
	 * 根据指定的索引获取枚举的定义
	 * 
	 * @param index
	 * @return
	 */
	public static UUIDType valueOf(final int index) {
		return EnumUtil.valueOf(values, index);
	}
}
