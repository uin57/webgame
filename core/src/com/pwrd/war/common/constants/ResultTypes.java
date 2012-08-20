package com.pwrd.war.common.constants;

import java.util.List;

import com.pwrd.war.core.enums.IndexedEnum;
import com.pwrd.war.core.util.EnumUtil;

public enum ResultTypes implements IndexedEnum{
	
	/** 1：操作成功 */
	SUCCESS((byte)1),
	/** 2：操作失败 */
	FAIL((byte)2);
	
	;
	
	public final byte val;
	
	/** 按索引顺序存放的枚举数组 */
	private static final List<ResultTypes> indexes = IndexedEnumUtil.toIndexes(ResultTypes.values());

	
	/**
	 * 
	 * @param index
	 *            枚举的索引,从0开始
	 */
	private ResultTypes(byte index) {
		this.val = index;
	}

	@Override
	public int getIndex() {
		return this.val;
	}
	
	/**
	 * 根据指定的索引获取枚举的定义
	 * 
	 * @param index
	 * @return
	 */
	public static ResultTypes indexOf(final int index) {
		return EnumUtil.valueOf(indexes, index);
	}


}
