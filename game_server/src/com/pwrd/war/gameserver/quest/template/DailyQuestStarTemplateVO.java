package com.pwrd.war.gameserver.quest.template;

import com.pwrd.war.core.annotation.ExcelRowBinding;
import com.pwrd.war.core.template.TemplateObject;
import com.pwrd.war.core.template.ExcelCollectionMapping;
import com.pwrd.war.common.exception.TemplateConfigException;

/**
 * 日常任务星数变化模版
 * 
 * @author CodeGenerator, don't modify this file please.
 */

@ExcelRowBinding
public abstract class DailyQuestStarTemplateVO extends TemplateObject {

	/** 升星不变降星概率 */
	@ExcelCollectionMapping(clazz = int.class, collectionNumber = "1;2;3")
	protected int[] rateArray;


	public int[] getRateArray() {
		return this.rateArray;
	}

	public void setRateArray(int[] rateArray) {
		if (rateArray == null) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[升星不变降星概率]rateArray不可以为空");
		}	
		this.rateArray = rateArray;
	}
	

	@Override
	public String toString() {
		return "DailyQuestStarTemplateVO[rateArray=" + rateArray + ",]";

	}
}