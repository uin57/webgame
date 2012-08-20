package com.pwrd.war.gameserver.pet.template;

import com.pwrd.war.core.annotation.ExcelRowBinding;
import com.pwrd.war.core.template.TemplateObject;
import com.pwrd.war.common.exception.TemplateConfigException;
import com.pwrd.war.core.annotation.ExcelCellBinding;

/**
 * 敬酒价格模板
 * 
 * @author CodeGenerator, don't modify this file please.
 */

@ExcelRowBinding
public abstract class JingjiuPriceTemplateVO extends TemplateObject {

	/** 敬酒次数 */
	@ExcelCellBinding(offset = 1)
	protected int jingjiuTimes;

	/** 花费元宝 */
	@ExcelCellBinding(offset = 2)
	protected int costGold;


	public int getJingjiuTimes() {
		return this.jingjiuTimes;
	}

	public void setJingjiuTimes(int jingjiuTimes) {
		if (jingjiuTimes == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[敬酒次数]jingjiuTimes不可以为0");
		}
		this.jingjiuTimes = jingjiuTimes;
	}
	
	public int getCostGold() {
		return this.costGold;
	}

	public void setCostGold(int costGold) {
		if (costGold == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[花费元宝]costGold不可以为0");
		}
		this.costGold = costGold;
	}
	

	@Override
	public String toString() {
		return "JingjiuPriceTemplateVO[jingjiuTimes=" + jingjiuTimes + ",costGold=" + costGold + ",]";

	}
}