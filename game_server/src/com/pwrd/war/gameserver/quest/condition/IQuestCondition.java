package com.pwrd.war.gameserver.quest.condition;

import java.util.List;

import com.pwrd.war.common.model.quest.QuestDestInfo;
import com.pwrd.war.core.enums.IndexedEnum;
import com.pwrd.war.core.util.EnumUtil;
import com.pwrd.war.gameserver.human.Human;

/**
 * 
 * 任务条件接口
 * 
 * 
 */
public interface IQuestCondition {

	/**
	 * 检查玩家是否满足条件
	 * 
	 * @param human
	 * @param questId
	 * @param showError
	 * @return
	 */
	boolean isMeet(Human human, int questId, boolean showError);

	/**
	 * 作为接受任务的条件，在满足条件并接受任务后的处理
	 * 
	 * @param human
	 * @return
	 */
	void onAccept(Human human);
	
	/**
	 * 作为完成任务的条件，在满足条件并完成任务后的处理
	 * 
	 * @param human
	 * @return
	 */
	void onFinish(Human human);
	
	/**
	 * 根据任务条件初始化任务当前完成状态
	 * @param human
	 * @return
	 */
	int initParam(Human human);
	
	/**
	 * 获取条件类型
	 * @return
	 */
	QuestConditionType getType();
	
	/**
	 * 获取条件参数
	 * @return
	 */
	String getParam();
	
	/**
	 * 获取条件当前情况
	 * @return
	 */
	QuestDestInfo getDestInfo(Human human, int questId);
	
	/**
	 * 任务条件类型枚举
	 * @author zy
	 *
	 */
	public static enum QuestConditionType implements IndexedEnum {
		/** 杀怪任务条件 */
		KILL(1),
		
		/** 物品数量条件(不扣除物品) */
		ITEM(2)
		;

		private final int index;

		private QuestConditionType(int index) {
			this.index = index;
		}

		@Override
		public int getIndex() {
			return index;
		}

		private static final List<QuestConditionType> indexes = IndexedEnum.IndexedEnumUtil
				.toIndexes(QuestConditionType.values());

		/**
		 * 根据指定的索引获取枚举的定义
		 * 
		 * @param index
		 * @return
		 */
		public static QuestConditionType indexOf(final int index) {
			return EnumUtil.valueOf(indexes, index);
		}
		
	}

}
