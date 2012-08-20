package com.pwrd.war.gameserver.human.template;

import com.pwrd.war.core.annotation.ExcelRowBinding;
import com.pwrd.war.core.template.TemplateObject;
import com.pwrd.war.common.exception.TemplateConfigException;
import com.pwrd.war.core.annotation.ExcelCellBinding;

/**
 * 角色升级经验
 * 
 * @author CodeGenerator, don't modify this file please.
 */

@ExcelRowBinding
public abstract class LevelUpExpVO extends TemplateObject {

	/** 等级 */
	@ExcelCellBinding(offset = 1)
	protected int level;

	/** 升级需要经验 */
	@ExcelCellBinding(offset = 2)
	protected int needExp;


	public int getLevel() {
		return this.level;
	}

	public void setLevel(int level) {
		if (level == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[等级]level不可以为0");
		}
		this.level = level;
	}
	
	public int getNeedExp() {
		return this.needExp;
	}

	public void setNeedExp(int needExp) {
		if (needExp == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[升级需要经验]needExp不可以为0");
		}
		this.needExp = needExp;
	}
	

	@Override
	public String toString() {
		return "LevelUpExpVO[level=" + level + ",needExp=" + needExp + ",]";

	}
}