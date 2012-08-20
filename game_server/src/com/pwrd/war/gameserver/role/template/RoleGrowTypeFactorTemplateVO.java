package com.pwrd.war.gameserver.role.template;

import com.pwrd.war.core.annotation.ExcelRowBinding;
import com.pwrd.war.core.template.TemplateObject;
import com.pwrd.war.common.exception.TemplateConfigException;
import com.pwrd.war.core.annotation.ExcelCellBinding;

/**
 * 武将类别成长表
 * 
 * @author CodeGenerator, don't modify this file please.
 */

@ExcelRowBinding
public abstract class RoleGrowTypeFactorTemplateVO extends TemplateObject {

	/** 成长等级 */
	@ExcelCellBinding(offset = 1)
	protected int growType;

	/** 类别对应成长 */
	@ExcelCellBinding(offset = 2)
	protected int baseGrow;


	public int getGrowType() {
		return this.growType;
	}

	public void setGrowType(int growType) {
		if (growType == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[成长等级]growType不可以为0");
		}
		this.growType = growType;
	}
	
	public int getBaseGrow() {
		return this.baseGrow;
	}

	public void setBaseGrow(int baseGrow) {
		if (baseGrow == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[类别对应成长]baseGrow不可以为0");
		}
		this.baseGrow = baseGrow;
	}
	

	@Override
	public String toString() {
		return "RoleGrowTypeFactorTemplateVO[growType=" + growType + ",baseGrow=" + baseGrow + ",]";

	}
}