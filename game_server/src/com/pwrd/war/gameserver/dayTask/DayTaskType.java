package com.pwrd.war.gameserver.dayTask;

import java.util.List;

import com.pwrd.war.core.annotation.NotTranslate;
import com.pwrd.war.core.enums.IndexedEnum;
import com.pwrd.war.core.util.EnumUtil;

/**
 * 道具的稀有程度
 */
public enum DayTaskType implements IndexedEnum {
	/** 培养*/
	PEIYANG(1,"10001"),
	
	/** 研究升级 */
	YANJIU(2,"10002"),
	
	/** 强化装备 */
	ENHANCE(3,"10003"),
	
	/** 通关副本 */
	REP(4,"10004"),
	
	/** 元宝消费 */
	COST_GOLD(5,"10005"), 
	
	/** 获得奖励 */
	GET_RRIZE(6,"10006"),
	
	/** 完成任务 */
	COMPLETE(7,"10007"), 
	
	/** 脱靶 */
	NONE(8,"10008"), 
	
	;

	private DayTaskType(int index,String taskId) {
		this.index = index;
		this.taskId = taskId;
	}

	public final int index;
	
	@NotTranslate
	public final String taskId;

	@Override
	public int getIndex() {
		return index;
	}
	
	public String getValue() {
		return taskId;
	}
	private static final List<DayTaskType> values = IndexedEnumUtil
			.toIndexes(DayTaskType.values());

	public static DayTaskType valueOf(int index) {
		return EnumUtil.valueOf(values, index);
	}
	
}
