package com.pwrd.war.gameserver.role.properties.amend;

import java.util.List;

import com.pwrd.war.core.enums.IndexedEnum;
import com.pwrd.war.core.util.EnumUtil;
import com.pwrd.war.core.util.MathUtils;

/**
 * 
 * 指明如何修正一个值，加一个因子还是乘以一个因子
 * 
 * 
 */
public enum AmendMethod implements IndexedEnum {
	/** 加 */
	ADD(1) {
		@Override
		public String formatDesc(float value) {
			int intValue = MathUtils.float2Int(value);
			String desc = String.format("+%d", intValue);
			return desc;
		}

		@Override
		public String getSymbol() {
			return "+";
		}
	},
	/** 乘 */
	MULIPLY(2) {
		@Override
		public String formatDesc(float value) {
			int intValue = MathUtils.float2Int(value);
			return String.format("*%d", intValue);
		}

		@Override
		public String getSymbol() {
			return "*";
		}
	},
	/** 按百分比加成 */
	ADD_PER(3) {
		@Override
		public String formatDesc(float value) {
			int intValue = MathUtils.float2Int(value);
			return String.format("+%d%%", intValue);
		}

		@Override
		public String getSymbol() {
			return "%";
		}
	};

	private AmendMethod(int index) {
		this.index = index;
	}

	/**
	 * 将对应的修正数值格式化成客户端显示的描述字串
	 * 
	 * @param value
	 * @return
	 */
	public abstract String formatDesc(float value);
	
	/**
	 * 获取修正计算规则所表示的符号
	 * 
	 * @return
	 */
	public abstract String getSymbol();

	public final int index;

	@Override
	public int getIndex() {
		return index;
	}

	private static final List<AmendMethod> values = IndexedEnumUtil
			.toIndexes(AmendMethod.values());

	public static AmendMethod valueOf(int index) {
		return EnumUtil.valueOf(values, index);
	}

}
