package com.pwrd.war.gameserver.common.process;

import java.util.List;

import com.pwrd.war.core.enums.IndexedEnum;
import com.pwrd.war.core.util.EnumUtil;

public interface ProcessDef {
	
	/**
	 * 调度线程执行间隔
	 */
	public final static long SCHEDULE_INTERVAL = 300L;
		
	/**
	 * 每次取出的最大进程数
	 */
	public final static int MAX_PICK_NUMS = 500;
	
	/**
	 * 进程到时时间容错
	 */
	public final static long TIME_EXPAND = 1000L;
	
	
	public enum ProcessEventType implements IndexedEnum{
		
		
		/** 建筑升级 */
		BUILDING_LEVEL_UP(1),
		
		;
		
		public final int val;
		
		private ProcessEventType(int index) {
			this.val = index;
		}

		@Override
		public int getIndex() {
			return val;
		}
		
		private static final List<ProcessEventType> values = IndexedEnumUtil.toIndexes(ProcessEventType.values());

		public static ProcessEventType valueOf(int index) {
			return EnumUtil.valueOf(values, index);
		}
		
		
	}
	
	

}
