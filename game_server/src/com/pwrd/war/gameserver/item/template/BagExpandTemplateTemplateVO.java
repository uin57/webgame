package com.pwrd.war.gameserver.item.template;

import com.pwrd.war.core.annotation.ExcelRowBinding;
import com.pwrd.war.core.template.TemplateObject;
import com.pwrd.war.common.exception.TemplateConfigException;
import com.pwrd.war.core.annotation.ExcelCellBinding;

/**
 * 背包扩充模版
 * 
 * @author CodeGenerator, don't modify this file please.
 */

@ExcelRowBinding
public abstract class BagExpandTemplateTemplateVO extends TemplateObject {

	/** 背包类型 */
	@ExcelCellBinding(offset = 1)
	protected int bagType;

	/** 索引 */
	@ExcelCellBinding(offset = 2)
	protected int index;

	/** 需要的元宝 */
	@ExcelCellBinding(offset = 3)
	protected int gold;


	public int getBagType() {
		return this.bagType;
	}

	public void setBagType(int bagType) {
		if (bagType == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[背包类型]bagType不可以为0");
		}
		this.bagType = bagType;
	}
	
	public int getIndex() {
		return this.index;
	}

	public void setIndex(int index) {
		if (index == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[索引]index不可以为0");
		}
		this.index = index;
	}
	
	public int getGold() {
		return this.gold;
	}

	public void setGold(int gold) {
		if (gold == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[需要的元宝]gold不可以为0");
		}
		this.gold = gold;
	}
	

	@Override
	public String toString() {
		return "BagExpandTemplateTemplateVO[bagType=" + bagType + ",index=" + index + ",gold=" + gold + ",]";

	}
}