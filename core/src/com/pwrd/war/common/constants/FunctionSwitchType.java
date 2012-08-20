package com.pwrd.war.common.constants;

import java.util.List;

import com.pwrd.war.core.enums.IndexedEnum;
import com.pwrd.war.core.util.EnumUtil;

/**
 * 功能开关定义
 *
 */
public enum FunctionSwitchType implements IndexedEnum {
	
	/** 充值 */
	CHARGE(0,true),
	/** 战报输出到文件 */
	BATTLE_REPORT_FILE_OUTPUT(1,false),
	/** 是否开启封测礼包 **/
	TEST_GIFT(2, true),
	;
	
	private int index;
	
	/** 默认状态 */
	private boolean defaultState;
	
	private static final List<FunctionSwitchType> values = IndexedEnumUtil.toIndexes(FunctionSwitchType.values());

	
	private FunctionSwitchType(int index,boolean defaultState){
		this.index = index;
		this.defaultState = defaultState;
	}
	

	@Override
	public int getIndex() {
		return index;
	}

	
	public boolean getDefaultState(){
		return this.defaultState;
	}

	public static FunctionSwitchType valueOf(int index) {
		return EnumUtil.valueOf(values, index);
	}
}
