package com.pwrd.war.core.enums;

import java.util.List;

import com.pwrd.war.core.util.EnumUtil;

/**
 * 选择类型定义
 * 
 * 
 */
public enum Option implements IndexedEnum {
	/** 取消 */
	CANCEL(0),
	/** 确定 */
	OK(1);
	private final int index;
	/** 按索引顺序存放的枚举数组 */
	private static final List<Option> indexes = IndexedEnum.IndexedEnumUtil.toIndexes(Option.values());

	/**
	 * 
	 * @param index
	 *            枚举的索引,从0开始
	 */
	private Option(int index) {
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
	public static Option indexOf(final int index) {
		return EnumUtil.valueOf(indexes, index);
	}
}