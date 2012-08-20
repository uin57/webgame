package com.pwrd.war.gameserver.economy;

import java.util.List;

import com.pwrd.war.core.enums.IndexedEnum;

/**
 * 经济调节系统定义
 * @author yue.yan
 *
 */
public interface EconomyDef {

	public static enum EconomyConfigType implements IndexedEnum
	{
		/** 未知 */
		UNKNOWN(0),
		/** npc刷新数量加成百分比 */
		NPC_REFRESH_COUNTRATIO(1),
		/** 训练经验加成百分比 */
		TRAINING_EXP_RATIO(2),
		/** 强化费用降低百分比 */
		ENHANCE_COST_RATIO(3),
		/** 农田产量提高百分比 */
		FARM_CROP_RATIO(4),
		/** 银矿产量提高百分比 */
		ORE_CROP_RATIO(5),
		;
		
		private final int index;
		
		/** 按索引顺序存放的枚举数组 */
		private static final List<EconomyConfigType> indexes = IndexedEnumUtil.toIndexes(EconomyConfigType.values());
		
		private EconomyConfigType(int index) {
			this.index = index;
		}
		
		/**
		 * 根据指定的索引获取枚举的定义
		 * 
		 * @param index
		 * @return
		 */
		public static EconomyConfigType indexOf(final int index) {
			return indexes.get(index);
		}
		
		public int getIndex() {
			return index;
		}
		
		public static boolean check(int type) {
			return type > 0 && type < indexes.size();
		}
	}
}
