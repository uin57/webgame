package com.pwrd.war.gameserver.quest;

import java.util.List;

import com.pwrd.war.core.enums.IndexedEnum;
import com.pwrd.war.core.util.EnumUtil;
import com.pwrd.war.gameserver.human.Human;

/**
 * 任务接口
 * 
 * 
 */
public interface IQuest<T> {
	/**
	 * @return 任务Id
	 */
	int getQuestId();

	/**
	 * @return 任务标题
	 */
	String getTitle();
	
	/**
	 * 获取任务类型
	 * 
	 * @return
	 */
	QuestType getType();
	
	/**
	 * 此任务是否可以被human接受
	 * 
	 */
	boolean canAccept(Human human, boolean showTip);

	/**
	 * 接受任务
	 * 
	 * @return 若接受失败返回false，成功返回true
	 */
	boolean accept(Human human);

	/**
	 * 是否可以完成任务，即是否任务的所有目标都已经达成
	 */
	boolean canFinish(Human human, boolean showTip);

	/**
	 * 完成任务
	 * 
	 * @param human
	 * @return 
	 *         若完成失败返回false，成功返回true，canFinish为true时,finish不一定返回true,因为可能存在背包满等意外
	 */
	boolean finish(Human human);
	
	/**
	 * 放弃任务
	 */
	boolean giveUp(Human human);
	
	/**
	 * 返回任务的模版对象
	 * @return
	 */
	public T getTemplate();

	public static enum QuestType implements IndexedEnum {
		/** 默认任务 */
		NULL(0),
		/** 主线任务 */
		MAIN(1),
		/** 支线任务 */
		BRANCH(2),
		/** 日常任务 */
		DAILY(5),
		/** 周常任务 */
		WEEKLY(6),
		/** 帮会任务 */
		GANG(10),
		/** 国家任务 */
		COUNTRY(11);

		private final int index;

		private QuestType(int index) {
			this.index = index;
		}

		@Override
		public int getIndex() {
			return index;
		}

		private static final List<QuestType> indexes = IndexedEnum.IndexedEnumUtil
				.toIndexes(QuestType.values());

		/**
		 * 根据指定的索引获取枚举的定义
		 * 
		 * @param index
		 * @return
		 */
		public static QuestType indexOf(final int index) {
			return EnumUtil.valueOf(indexes, index);
		}

	}
	
	public static enum QuestSubType implements IndexedEnum {
		/** 默认子类型 */
		NULL(0),
		/** 副本子类型 */
		REP(1),
		/** 征战 */
		ATTACKNPC(210);
		

		private final int index;

		private QuestSubType(int index) {
			this.index = index;
		}

		@Override
		public int getIndex() {
			return index;
		}

		private static final List<QuestSubType> indexes = IndexedEnum.IndexedEnumUtil
				.toIndexes(QuestSubType.values());

		/**
		 * 根据指定的索引获取枚举的定义
		 * 
		 * @param index
		 * @return
		 */
		public static QuestSubType indexOf(final int index) {
			return EnumUtil.valueOf(indexes, index);
		}
	}
	
	public static enum DailyQuestStatus implements IndexedEnum {
		/** 默认状态 */
		GIVEUP(0),
		/** 不可见状态 */
		INVISIBLE(1),
		/** 可接受状态 */
		CANACCEPT(2),
		/** 已接受但不可完成状态 */
		ACCEPTED(3),
		/** 可完成状态 */
		CANFINISH(4);

		private final int index;

		private DailyQuestStatus(int index) {
			this.index = index;
		}

		@Override
		public int getIndex() {
			return index;
		}

		private static final List<DailyQuestStatus> indexes = IndexedEnum.IndexedEnumUtil
				.toIndexes(DailyQuestStatus.values());

		/**
		 * 根据指定的索引获取枚举的定义
		 * 
		 * @param index
		 * @return
		 */
		public static DailyQuestStatus indexOf(final int index) {
			return EnumUtil.valueOf(indexes, index);
		}

	}

}
