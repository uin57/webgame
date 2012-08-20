package com.pwrd.war.core.enums;

import java.util.List;

import com.pwrd.war.core.util.EnumUtil;

/**
 * 查询数据类别
 * 
 * 
 */
public enum QueryInfoType implements IndexedEnum {

	/** 基本信息 + 装备信息 */
	BASE_INFO_AND_EQUIPS(0),
	/** 单个物品信息 */
	ITEM(1),
	/** 单个宠物信息 */
	PET(2),
	/** 模板物品信息 */
	TMPL_ITEM(3),
	/** 宠物列表信息 */
	PET_LIST(4);

	private final int index;

	private QueryInfoType(int index) {
		this.index = index;
	}

	@Override
	public int getIndex() {
		return this.index;
	}

	private static final List<QueryInfoType> values = IndexedEnumUtil
			.toIndexes(QueryInfoType.values());

	public static QueryInfoType valueOf(int index) {
		return EnumUtil.valueOf(values, index);
	}
}
