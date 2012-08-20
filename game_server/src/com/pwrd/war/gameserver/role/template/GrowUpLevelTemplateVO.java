package com.pwrd.war.gameserver.role.template;

import com.pwrd.war.core.annotation.ExcelRowBinding;
import com.pwrd.war.core.template.TemplateObject;
import com.pwrd.war.common.exception.TemplateConfigException;
import com.pwrd.war.core.annotation.ExcelCellBinding;

/**
 * 成长上升模版
 * 
 * @author CodeGenerator, don't modify this file please.
 */

@ExcelRowBinding
public abstract class GrowUpLevelTemplateVO extends TemplateObject {

	/** 成长 */
	@ExcelCellBinding(offset = 1)
	protected Integer grow;

	/** 需要钱币 */
	@ExcelCellBinding(offset = 2)
	protected Integer money;


	public Integer getGrow() {
		return this.grow;
	}

	public void setGrow(Integer grow) {
		if (grow == null) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[成长]grow不可以为空");
		}	
		this.grow = grow;
	}
	
	public Integer getMoney() {
		return this.money;
	}

	public void setMoney(Integer money) {
		if (money == null) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[需要钱币]money不可以为空");
		}	
		this.money = money;
	}
	

	@Override
	public String toString() {
		return "GrowUpLevelTemplateVO[grow=" + grow + ",money=" + money + ",]";

	}
}