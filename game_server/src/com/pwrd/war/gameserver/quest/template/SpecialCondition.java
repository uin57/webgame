package com.pwrd.war.gameserver.quest.template;

import org.apache.commons.lang.StringUtils;

import com.pwrd.war.core.annotation.ExcelRowBinding;
import com.pwrd.war.core.template.BeanFieldNumber;
import com.pwrd.war.gameserver.quest.condition.IQuestCondition;
import com.pwrd.war.gameserver.quest.condition.IQuestCondition.QuestConditionType;
import com.pwrd.war.gameserver.quest.condition.ItemCondition;
import com.pwrd.war.gameserver.quest.condition.KillCondition;

/**
 * 接任务时的特殊条件限制，此处并非真正的条件对象，而是用于读取表格时用的对象
 * 
 * 
 */
@ExcelRowBinding
public class SpecialCondition {
	
	/** 条件编号 */
	@BeanFieldNumber(number = 1)
	private int type;
	/** 参数1 */
	@BeanFieldNumber(number = 2)
	private String param1;
	/** 参数2 */
	@BeanFieldNumber(number = 3)
	private int param2;

	/**
	 * 获取该对象所对应的任务条件
	 * @param questId
	 * @return
	 */
	public IQuestCondition buildQuestCondition(int questId) {
		//根据条件编号获取任务条件类型
		QuestConditionType typeEnum = QuestConditionType.indexOf(type);
		if (typeEnum != null) {
			switch (typeEnum) {
			case KILL:	//杀怪数量条件，参数1为目标怪物sn，参数2为数量
				if (StringUtils.isNotEmpty(param1)) {
					return new KillCondition(param1, param2);
				}
				break;
				
			case ITEM:	//物品数量条件，参数1为物品sn，参数2为数量
				if (StringUtils.isNotEmpty(param1)) {
					return new ItemCondition(param1, param2);
				}
				break;
			}
		}
		
		return null;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getParam1() {
		return param1;
	}

	public void setParam1(String param1) {
		this.param1 = param1;
	}

	public int getParam2() {
		return param2;
	}

	public void setParam2(int param2) {
		this.param2 = param2;
	}

}
