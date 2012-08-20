package com.pwrd.war.gameserver.quest.template;

import com.pwrd.war.core.annotation.ExcelRowBinding;
import com.pwrd.war.core.template.BeanFieldNumber;
import com.pwrd.war.gameserver.quest.bonus.IQuestBonus;

/**
 * 
 * 并非真正的奖励，此对象主要是用来加载任务表中的特殊奖励部分
 * 
 * 
 */
@ExcelRowBinding
public class SpecialBonus {
	/** 特殊奖励类型编号 */
	@BeanFieldNumber(number = 1)
	private int type;
	/** 奖励参数1 */
	@BeanFieldNumber(number = 2)
	private String param1st;
	/** 奖励参数2 */
	@BeanFieldNumber(number = 3)
	private String param2st;
	
	/**
	 * 获取该对象所对应的奖励对象
	 * @param questId
	 * @return
	 */
	public IQuestBonus buildQuestBonus(int questId) {
		//TODO
		IQuestBonus bonus = null;
		return bonus;
	}

	public String getParam1st() {
		return param1st;
	}

	public void setParam1st(String param1st) {
		this.param1st = param1st;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getParam2st() {
		return param2st;
	}

	public void setParam2st(String param2st) {
		this.param2st = param2st;
	}

}
