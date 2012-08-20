package com.pwrd.war.gameserver.tree.template;

import com.pwrd.war.core.annotation.ExcelRowBinding;
import com.pwrd.war.core.template.TemplateObject;
import com.pwrd.war.common.exception.TemplateConfigException;
import com.pwrd.war.core.annotation.ExcelCellBinding;

/**
 * 摇钱树模版类
 * 
 * @author CodeGenerator, don't modify this file please.
 */

@ExcelRowBinding
public abstract class TreeCostTemplateVO extends TemplateObject {

	/** 摇钱次数 */
	@ExcelCellBinding(offset = 1)
	protected int shakeTimes;

	/** 消耗元宝数 */
	@ExcelCellBinding(offset = 2)
	protected int costGold;


	public int getShakeTimes() {
		return this.shakeTimes;
	}

	public void setShakeTimes(int shakeTimes) {
		if (shakeTimes < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[摇钱次数]shakeTimes的值不得小于0");
		}
		this.shakeTimes = shakeTimes;
	}
	
	public int getCostGold() {
		return this.costGold;
	}

	public void setCostGold(int costGold) {
		if (costGold < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[消耗元宝数]costGold的值不得小于0");
		}
		this.costGold = costGold;
	}
	

	@Override
	public String toString() {
		return "TreeCostTemplateVO[shakeTimes=" + shakeTimes + ",costGold=" + costGold + ",]";

	}
}