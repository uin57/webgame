package com.pwrd.war.gameserver.mail.template;

import com.pwrd.war.common.exception.TemplateConfigException;
import com.pwrd.war.core.annotation.ExcelRowBinding;
import com.pwrd.war.gameserver.mail.MailDef.SysMailReasonType;

@ExcelRowBinding
public class MailContentTemplate extends MailContentTemplateVO{

	@Override
	public void check() throws TemplateConfigException {		
		SysMailReasonType mailReasonType = SysMailReasonType.valueOf(this.getReasonId());
		if(mailReasonType == null)
		{
			throw new TemplateConfigException(this.getSheetName(), getId(), String
					.format("触发原因Id配置错误reasonId =%d", this.getReasonId()));
		}	
		
		boolean behaviorIdValid = false;
		for(int behaviorId :mailReasonType.behaviorIds)
		{
			if(this.getBehaviorId() == behaviorId)
			{
				behaviorIdValid = true;
				break;
			}
		}
		if(!behaviorIdValid)
		{
			throw new TemplateConfigException(this.getSheetName(), getId(), String
					.format("行为原因Id配置错误behaviorId =%d", this.getBehaviorId()));			
		}
	}

}
