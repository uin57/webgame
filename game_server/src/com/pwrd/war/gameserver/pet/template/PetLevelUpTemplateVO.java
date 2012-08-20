package com.pwrd.war.gameserver.pet.template;

import com.pwrd.war.common.exception.TemplateConfigException;
import com.pwrd.war.core.annotation.ExcelCellBinding;
import com.pwrd.war.core.annotation.ExcelRowBinding;
import com.pwrd.war.core.template.TemplateObject;

/**
 * 武将升级配置模板
 * 
 * @author CodeGenerator, don't modify this file please.
 */

@ExcelRowBinding
public abstract class PetLevelUpTemplateVO extends TemplateObject {

	/** 升级经验 */
	@ExcelCellBinding(offset = 1)
	protected int exp;

	/** 对应的士兵等级 */
	@ExcelCellBinding(offset = 2)
	protected int soldierLevel;


	public int getExp() {
		return this.exp;
	}

	public void setExp(int exp) {
		if (exp == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[升级经验]exp不可以为0");
		}
		this.exp = exp;
	}
	
	public int getSoldierLevel() {
		return this.soldierLevel;
	}

	public void setSoldierLevel(int soldierLevel) {
		if (soldierLevel == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[对应的士兵等级]soldierLevel不可以为0");
		}
		this.soldierLevel = soldierLevel;
	}
	

	@Override
	public String toString() {
		return "PetLevelUpTemplateVO[exp=" + exp + ",soldierLevel=" + soldierLevel + ",]";

	}
}