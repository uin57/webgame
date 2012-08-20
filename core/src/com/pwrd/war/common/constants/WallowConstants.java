package com.pwrd.war.common.constants;

import java.util.List;

import com.pwrd.war.core.enums.IndexedEnum;
import com.pwrd.war.core.util.EnumUtil;

/**
 * 防沉迷相关常量
 * 
 * 
 */
public class WallowConstants {

	/** 用户没有处于防沉迷保护 */
	public static int WALLOW_FLAG_OFF = 0;

	/** 用户处于防沉迷保护 */
	public static int WALLOW_FLAG_ON = 1;

	/** 批处理平均单位人数 */
	public final static int BATCH_SIZE = 50;
	/** 分批最大次数 */
	public final static int BATCH_COUNTER_MAX = 3;
	
	/** 时间区间一个单位5分钟 */
	public final static int TIME_EXTEND_MIN_UNIT = 5;
	
	/** 时间区间一个单位=5分钟:5 * 60; */
	public final static int TIME_EXTEND_UNIT = 5 * 60;
	
	
	/** 到36个单位进入减半收益状态 */
	public final static int NORMAL_UP_INDEX = 36;
	/** 到60个单位进入零收益状态 */
	public final static int WARN_UP_INDEX = 60;


	/**
	 * 反沉迷状态定义
	 * */
	public enum WallowStatus implements IndexedEnum {
		/** 正常收益 */
		NORMAL(0, 0.0f, 1),
		/** 减半收益 */
		WARN(1, -0.5f, 0),
		/** 零收益 */
		DANGER(2, -1.0f, 0);
		private int index;
		private float coeff; // 数值类系数
		private int swtch; // 离散类开关

		private WallowStatus(int index, float coeff, int swtch) {
			this.index = index;
			this.coeff = coeff;
			this.swtch = swtch;
		}

		public int getIndex() {
			return this.index;
		}

		public float getCoeff() {
			return this.coeff;
		}

		public int getSwtch() {
			return this.swtch;
		}

		private static final List<WallowStatus> values = IndexedEnumUtil
				.toIndexes(WallowStatus.values());

		public static WallowStatus valueOf(int index) {
			return EnumUtil.valueOf(values, index);
		}
	}

}
