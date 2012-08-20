package com.pwrd.war.gameserver.behavior.template;

import com.pwrd.war.core.annotation.ExcelRowBinding;
import com.pwrd.war.core.template.TemplateObject;
import com.pwrd.war.common.exception.TemplateConfigException;
import com.pwrd.war.core.annotation.ExcelCellBinding;

/**
 * 行为配置模板
 * 
 * @author CodeGenerator, don't modify this file please.
 */

@ExcelRowBinding
public abstract class BehaviorTemplateVO extends TemplateObject {

	/**  建筑名称多语言 Id */
	@ExcelCellBinding(offset = 1)
	protected int opCountMax;

	/**  重置时间戳 */
	@ExcelCellBinding(offset = 2)
	protected long resetTime;


	public int getOpCountMax() {
		return this.opCountMax;
	}

	public void setOpCountMax(int opCountMax) {
		if (opCountMax < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[ 建筑名称多语言 Id]opCountMax的值不得小于0");
		}
		this.opCountMax = opCountMax;
	}
	
	public long getResetTime() {
		return this.resetTime;
	}

	public void setResetTime(long resetTime) {
		if (resetTime < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[ 重置时间戳]resetTime的值不得小于0");
		}
		this.resetTime = resetTime;
	}
	

	@Override
	public String toString() {
		return "BehaviorTemplateVO[opCountMax=" + opCountMax + ",resetTime=" + resetTime + ",]";

	}
}