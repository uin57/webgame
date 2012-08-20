package com.pwrd.war.gameserver.human.template;

import com.pwrd.war.core.annotation.ExcelRowBinding;
import com.pwrd.war.core.template.TemplateObject;
import com.pwrd.war.common.exception.TemplateConfigException;
import com.pwrd.war.core.annotation.ExcelCellBinding;

/**
 * 修炼模版
 * 
 * @author CodeGenerator, don't modify this file please.
 */

@ExcelRowBinding
public abstract class XiulianTemplateVO extends TemplateObject {

	/** 等级 */
	@ExcelCellBinding(offset = 1)
	protected int level;

	/** 最长时间 */
	@ExcelCellBinding(offset = 2)
	protected int maxTime;

	/** 每20秒增加经验 */
	@ExcelCellBinding(offset = 3)
	protected int perAddExp;


	public int getLevel() {
		return this.level;
	}

	public void setLevel(int level) {
		if (level < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[等级]level的值不得小于0");
		}
		this.level = level;
	}
	
	public int getMaxTime() {
		return this.maxTime;
	}

	public void setMaxTime(int maxTime) {
		if (maxTime < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[最长时间]maxTime的值不得小于0");
		}
		this.maxTime = maxTime;
	}
	
	public int getPerAddExp() {
		return this.perAddExp;
	}

	public void setPerAddExp(int perAddExp) {
		if (perAddExp < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[每20秒增加经验]perAddExp的值不得小于0");
		}
		this.perAddExp = perAddExp;
	}
	

	@Override
	public String toString() {
		return "XiulianTemplateVO[level=" + level + ",maxTime=" + maxTime + ",perAddExp=" + perAddExp + ",]";

	}
}