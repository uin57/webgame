package com.pwrd.war.gameserver.human.wealth;

import java.util.List;

import com.pwrd.war.core.enums.IndexedEnum;
import com.pwrd.war.core.util.EnumUtil;

/**
 * 可绑定接口
 * 
 */
public interface Bindable {

	/**
	 * 是否已经绑定
	 * 
	 * @return
	 */
	BindStatus getBindStatus();

	/**
	 * 设置绑定
	 * 
	 * @param bind
	 *            true表示绑定，false表示不绑定
	 */
	void setBindStatus(BindStatus bind);

	/**
	 * 绑定类型
	 * 
	 * @return
	 */
	BindMode getBindMode();

	public static enum BindMode implements IndexedEnum {
		/** 永不绑定 */
		NO_BIND(0) {
			@Override
			public BindStatus getBindStatusAfterOper(BindStatus curStatus, Oper oper) {
				// 无论什么操作都是不绑定状态
				return BindStatus.NOT_BIND;
			}
		},
		/** 获取绑定 */
		GET_BIND(1) {
			@Override
			public BindStatus getBindStatusAfterOper(BindStatus curStatus, Oper oper) {
				if (oper == Oper.GET) {
					// 只有拾取可以改变绑定状态
					return BindStatus.BIND_YET;
				} else {
					return curStatus;
				}
			}
		},
		/** 使用绑定，使用可能表现为很多种形式，使用，穿上装备等等*/
		USE_BIND(2) {
			@Override
			public BindStatus getBindStatusAfterOper(BindStatus curStatus, Oper oper) {
				if (oper == Oper.USE) {
					// 只有使用可以改变绑定状态
					return BindStatus.BIND_YET;
				} else {
					return curStatus;
				}
			}
		}, ;

		private BindMode(int index) {
			this.index = index;
		}

		public final int index;

		@Override
		public int getIndex() {
			return index;
		}
		
		/**
		 * 得到当前绑定状态经过oper操作后的绑定状态
		 * 
		 * @param curStatus
		 *            当前状态
		 * @param oper
		 *            操作
		 * @return
		 */
		public abstract BindStatus getBindStatusAfterOper(BindStatus curStatus, Oper oper);

		private static final List<BindMode> values = IndexedEnumUtil.toIndexes(BindMode.values());

		public static BindMode valueOf(int index) {
			return EnumUtil.valueOf(values, index);
		}
	}

	/**
	 * 绑定状态
	 * 
	 */
	public static enum BindStatus implements IndexedEnum {
		/** 未绑定 */
		NOT_BIND(0),
		/** 已绑定 */
		BIND_YET(1), ;

		private BindStatus(int index) {
			this.index = index;
		}

		public final int index;

		@Override
		public int getIndex() {
			return index;
		}

		private static final List<BindStatus> values = IndexedEnumUtil.toIndexes(BindStatus.values());

		public static BindStatus valueOf(int index) {
			return EnumUtil.valueOf(values, index);
		}
	}
	
	/**
	 * 可能导致绑定状态变更的操作
	 */
	public static enum Oper {
		/** 拾取 */
		GET,
		/** 使用 */
		USE,
		/** 购买*/
		BUY,;
	}
}
