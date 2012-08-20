package com.pwrd.war.common.model.template;

import com.pwrd.war.core.annotation.ExcelRowBinding;
import com.pwrd.war.core.template.TemplateObject;
import com.pwrd.war.core.util.StringUtils;
import com.pwrd.war.core.annotation.NotTranslate;
import com.pwrd.war.common.exception.TemplateConfigException;
import com.pwrd.war.core.annotation.ExcelCellBinding;

/**
 * 定时事件
 * 
 * @author CodeGenerator, don't modify this file please.
 */

@ExcelRowBinding
public abstract class TimeEventTemplateVO extends TemplateObject {

	/** 触发时间 HH:mm:ss */
	@NotTranslate
	@ExcelCellBinding(offset = 1)
	protected String triggerTime;


	public String getTriggerTime() {
		return this.triggerTime;
	}

	public void setTriggerTime(String triggerTime) {
		if (StringUtils.isEmpty(triggerTime)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[触发时间 HH:mm:ss]triggerTime不可以为空");
		}
		this.triggerTime = triggerTime;
	}
	

	@Override
	public String toString() {
		return "TimeEventTemplateVO[triggerTime=" + triggerTime + ",]";

	}
}