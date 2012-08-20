package com.pwrd.war.gameserver.item.template;

import com.pwrd.war.common.exception.TemplateConfigException;
import com.pwrd.war.core.annotation.ExcelCellBinding;
import com.pwrd.war.core.annotation.ExcelRowBinding;
import com.pwrd.war.core.template.TemplateObject;

/**
 * 打开背包模板中间层
 * 
 * @author CodeGenerator, don't modify this file please.
 */

@ExcelRowBinding
public abstract class OpenBagTemplateVO extends TemplateObject {

	/**  开该页的价格 */
	@ExcelCellBinding(offset = 1)
	protected int goldCost;


	public int getGoldCost() {
		return this.goldCost;
	}

	public void setGoldCost(int goldCost) {
		if (goldCost == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[ 开该页的价格]goldCost不可以为0");
		}
		if (goldCost > 50000 || goldCost < 100) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[ 开该页的价格]goldCost的值不合法，应为100至50000之间");
		}
		this.goldCost = goldCost;
	}
	

	@Override
	public String toString() {
		return "OpenBagTemplateVO[goldCost=" + goldCost + ",]";

	}
}