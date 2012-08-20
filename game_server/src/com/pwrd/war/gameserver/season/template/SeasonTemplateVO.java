package com.pwrd.war.gameserver.season.template;

import com.pwrd.war.core.annotation.ExcelRowBinding;
import com.pwrd.war.core.template.TemplateObject;
import com.pwrd.war.common.exception.TemplateConfigException;
import com.pwrd.war.core.annotation.ExcelCellBinding;

/**
 * 季节系统
 * 
 * @author CodeGenerator, don't modify this file please.
 */

@ExcelRowBinding
public abstract class SeasonTemplateVO extends TemplateObject {

	/** 初始年份 */
	@ExcelCellBinding(offset = 1)
	protected int initYear;

	/** 触发变化时间点 */
	@ExcelCellBinding(offset = 2)
	protected int triggerTimeEventId;


	public int getInitYear() {
		return this.initYear;
	}

	public void setInitYear(int initYear) {
		if (initYear == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[初始年份]initYear不可以为0");
		}
		if (initYear < 1) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[初始年份]initYear的值不得小于1");
		}
		this.initYear = initYear;
	}
	
	public int getTriggerTimeEventId() {
		return this.triggerTimeEventId;
	}

	public void setTriggerTimeEventId(int triggerTimeEventId) {
		if (triggerTimeEventId == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[触发变化时间点]triggerTimeEventId不可以为0");
		}
		if (triggerTimeEventId < 1) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[触发变化时间点]triggerTimeEventId的值不得小于1");
		}
		this.triggerTimeEventId = triggerTimeEventId;
	}
	

	@Override
	public String toString() {
		return "SeasonTemplateVO[initYear=" + initYear + ",triggerTimeEventId=" + triggerTimeEventId + ",]";

	}
}