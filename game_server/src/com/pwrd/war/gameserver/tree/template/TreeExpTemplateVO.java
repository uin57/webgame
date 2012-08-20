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
public abstract class TreeExpTemplateVO extends TemplateObject {

	/** 摇钱树等级 */
	@ExcelCellBinding(offset = 1)
	protected int level;

	/** 升级经验 */
	@ExcelCellBinding(offset = 2)
	protected int exp;


	public int getLevel() {
		return this.level;
	}

	public void setLevel(int level) {
		if (level < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[摇钱树等级]level的值不得小于0");
		}
		this.level = level;
	}
	
	public int getExp() {
		return this.exp;
	}

	public void setExp(int exp) {
		if (exp < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[升级经验]exp的值不得小于0");
		}
		this.exp = exp;
	}
	

	@Override
	public String toString() {
		return "TreeExpTemplateVO[level=" + level + ",exp=" + exp + ",]";

	}
}