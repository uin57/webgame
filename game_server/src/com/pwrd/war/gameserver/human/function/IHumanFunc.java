package com.pwrd.war.gameserver.human.function;

import java.util.List;

import com.pwrd.war.core.enums.IndexedEnum;
import com.pwrd.war.core.util.EnumUtil;
import com.pwrd.war.gameserver.common.function.IGameFunc;
import com.pwrd.war.gameserver.common.function.IGameFuncType;

/**
 * 玩家角色功能
 * 
 * @author haijiang.jin 
 *
 */
public interface IHumanFunc extends IGameFunc<IHumanFunc.TypeEnum, Object> {
	/**
	 * 玩家角色功能类型
	 * 
	 */
	public static enum TypeEnum implements IGameFuncType, IndexedEnum {
		// 主界面功能
		// 只是为了控制客户端显示, 所以没有绑定具体的功能类
		// 
		///////////////////////////////////////////////////////////////

		/** 主城 */
		GAME_MAIN_CITY(11, 1),
		/** 征战 */
		GAME_MISSION(12, 1),
		/** 区域 */
		GAME_DISTRICT(13, 1), 
		/** 世界 */
		GAME_WORLD(14, 1),
		/** 农田 */
		GAME_FARM(15, 1),

		/** 招募武将 */
		HUMAN_HIRE_PET(21, 1), 
		/** 装备 */
		HUMAN_EQUIP(22, 1),
		/** 布阵 */
		HUMAN_ARRAY(23, 1),
		/** 主线任务 */
		HUMAN_MAIN_QUEST(24, 1),
		/** 日常任务 */
		HUMAN_DAILY_QUEST(25, 1),
		/** 活动 */
		HUMAN_ACTIVITY(26, 1), 
		/** 军团 */
		HUMAN_GUILD(27, 1),
		/** 引导员 */
		HUMAN_TOUR_GUIDE(28, 1),
;
		/**
		 * 枚举参数构造器
		 * 
		 * @param index
		 * @param priority
		 */
		private TypeEnum(int index, int priority) {
			this._index = index;
			this._priority = priority;
		}

		/** 索引位置 */
		public final int _index;
		/** 功能列表中显示的优先级, 越小越靠上 */
		public final int _priority;

		/** 所有枚举值 */
		private static final List<TypeEnum> 
			_values = IndexedEnumUtil.toIndexes(TypeEnum.values());

		@Override
		public int getIndex() {
			return this._index;
		}

		/**
		 * 获取优先级
		 * 
		 * @return
		 */
		public int getPriority() {
			return this._priority;
		}

		/**
		 * 将整型数转换成枚举类型
		 * 
		 * @param index
		 * @return
		 */
		public static TypeEnum valueOf(int index) {
			return EnumUtil.valueOf(_values, index);
		}
	}
}
