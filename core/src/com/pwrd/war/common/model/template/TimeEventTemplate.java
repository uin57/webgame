package com.pwrd.war.common.model.template;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.pwrd.war.common.exception.TemplateConfigException;
import com.pwrd.war.core.annotation.ExcelRowBinding;

@ExcelRowBinding
public class TimeEventTemplate extends TimeEventTemplateVO{
	
	private  SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
	
	
	/** 定时时间点 */
	protected java.util.Calendar triggerTimeCalendar;
	
	
	public void setTriggerTime(String triggerTime)
	{
		super.setTriggerTime(triggerTime);
		try {
			Date dateTime = sdf.parse(triggerTime);
			triggerTimeCalendar = Calendar.getInstance();
			triggerTimeCalendar.setTime(dateTime);
		} catch (ParseException e) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					1, "[触发时间 HH:mm:ss]triggerTime 无法解析");
		}
	}
	
	public java.util.Calendar getTriggerTimePoint() {
		return triggerTimeCalendar;
	}

	
	@Override
	public void check() throws TemplateConfigException {
		
	}

}
