package com.pwrd.war.gameserver.story;

import java.util.List;

import com.pwrd.war.core.enums.IndexedEnum;
import com.pwrd.war.core.util.EnumUtil;

/**
 * 剧情触发事件类型
 * @author zy
 *
 */
public enum StoryEventType implements IndexedEnum {
	/** 接受任务后触发 */
	ACCEPT_QUEST(1),
	
	/** 完成任务后触发*/
	FINISH_QUEST(2),
	
	/** 进入副本后触发*/
	ENTER_REP(3),
	
	/** 杀死怪物后触发*/
	AFTER_KILL(4),
	
	/** 杀死怪物前出发*/
	BEFORE_KILL(5)
	;

	private final int index;

	private StoryEventType(int index) {
		this.index = index;
	}

	@Override
	public int getIndex() {
		return index;
	}

	private static final List<StoryEventType> indexes = IndexedEnum.IndexedEnumUtil
			.toIndexes(StoryEventType.values());

	/**
	 * 根据指定的索引获取枚举的定义
	 * 
	 * @param index
	 * @return
	 */
	public static StoryEventType indexOf(final int index) {
		return EnumUtil.valueOf(indexes, index);
	}

}
