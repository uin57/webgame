package com.pwrd.war.gameserver.human.wealth;

import java.util.List;

import com.pwrd.war.core.enums.IndexedEnum;
import com.pwrd.war.core.util.EnumUtil;

/**
 * 
 * 有使用期限的物品，超过使用期限时便会过期，过期后此物品可能会被删除或者失去效用<br />
 * 使用期限的控制有两种方式<br />
 * 1.指定一个绝对的到期时间点，到此事件即过期<br />
 * 2.指定一个使用时长，和一个计时策略。计时策略包括两方面，<br />
 *   1)何时开始计时，可能为得到（购买）时或第一次使用（穿上）时。<br />
 *   2)计时时间，例如按时钟计时、 只在在线时计时等。<br />
 * 
 */
public interface Expireable {

	/**
	 * 是否已经到期
	 * 
	 * @return
	 */
	boolean isExpired();

	/**
	 * 到期时间，即到期的绝对时间点
	 * 
	 * @return 如果可以永久使用，返回{@link #CAN_USE_FOREVER}
	 */
	long getExpireTime();

	/**
	 * 开始计时的绝对时间点
	 * 
	 * @return 如果尚未开始计时返回NOT_START_YET
	 */
	long getStartTiming();

	/** 可以永久使用 */
	public static final long CAN_USE_FOREVER = Long.MAX_VALUE;

	/**
	 * 开始计时的策略
	 */
	public static enum StartStrategy implements IndexedEnum {
		/** 不计时 */
		NULL(0),
		/** 得到（购买）时开始计时 */
		START_ON_GET(1),
		/** 第一次使用时开始计时 */
		START_ON_USE(2), ;

		private StartStrategy(int index) {
			this.index = index;
		}

		public final int index;

		@Override
		public int getIndex() {
			return index;
		}

		private static final List<StartStrategy> values = IndexedEnumUtil.toIndexes(StartStrategy.values());

		public static StartStrategy valueOf(int index) {
			return EnumUtil.valueOf(values, index);
		}
	}

	/**
	 * 计时策略
	 */
	public static enum TimingStrategy implements IndexedEnum {
		/** 默认，永远计时 */
		DEFAULT(0),
		/** 在线时才计时 */
		ONLINE_ONLY(1),
		/** 离线时才计时 */
		OFFLINE_ONLY(2), ;

		private TimingStrategy(int index) {
			this.index = index;
		}

		public final int index;

		@Override
		public int getIndex() {
			return index;
		}

		private static final List<TimingStrategy> values = IndexedEnumUtil.toIndexes(TimingStrategy.values());

		public static TimingStrategy valueOf(int index) {
			return EnumUtil.valueOf(values, index);
		}
	}
}
