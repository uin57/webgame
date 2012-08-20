package com.pwrd.war.gameserver.behavior;

import java.util.List;

import com.pwrd.war.core.enums.IndexedEnum;
import com.pwrd.war.core.util.EnumUtil;

/**
 * 行为类型枚举
 * 
 * @author haijiang.jin
 *
 */
public enum BehaviorTypeEnum implements IndexedEnum {
	/** 未知行为 */
	UNKNOWN(0), 
	/** 投资 */
	INVEST(1), 
	/** 领取俸禄 */
	DRAW_SALARY(2), 
	/** 完成日常任务 */
	FINISH_DAILY_QUEST(3),
	/** 军团农场收获 */
	GUILD_FARM_REAP(4),
	/** 领取季节收益 */
	DRAW_SEASON_INCOME(5), 
	/** 节日活动参与次数 */
	FESTIVAL_ATTEND_COUNT(6),
	/** 登陆引导 */
	LOGIN_GUIDE(7),
	/** 领取活动奖励 */
	DRAW_ACTIVITY_PRIZE(8),
	/** 购买军令 */
	BUY_TOKEN(9),
	/** 义兵 */
	FREE_RECRUIT(10),
	;
	/** 枚举索引 */
	private int index;
	/** 枚举值数组 */
	private static final List<BehaviorTypeEnum> 
		values = IndexedEnumUtil.toIndexes(BehaviorTypeEnum.values());

	/**
	 * 类参数构造器
	 * 
	 * @param index
	 */
	private BehaviorTypeEnum(int index) {
		this.index = index;
	}

	@Override
	public int getIndex() {
		return index;
	}

	/**
	 * 将 int 类型值转换成枚举类型
	 * 
	 * @param index
	 * @return
	 */
	public static BehaviorTypeEnum valueOf(int index) {
		return EnumUtil.valueOf(values, index);
	}
}