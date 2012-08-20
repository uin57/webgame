package com.pwrd.war.core.enums;

import java.util.List;

import com.pwrd.war.core.util.EnumUtil;

/**
 * 玩家账号状态
 * 
 * 
 */
public enum AccountState implements IndexedEnum {
	
	/** 正常 */
	NORMAL(0),
	/** 已锁定 */
	LOCKED(1);

	private final int index;
	/** 按索引顺序存放的枚举数组 */
	private static final List<AccountState> indexes = IndexedEnum.IndexedEnumUtil.toIndexes(AccountState.values());

	/**
	 * 
	 * @param index
	 *            枚举的索引,从0开始
	 */
	private AccountState(int index) {
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
	public static AccountState indexOf(final int index) {
		return EnumUtil.valueOf(indexes, index);
	}

}
