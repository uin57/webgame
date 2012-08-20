package com.pwrd.war.gameserver.role.template;

import com.pwrd.war.common.exception.TemplateConfigException;
import com.pwrd.war.core.annotation.ExcelRowBinding;

@ExcelRowBinding
public class RolePropertyFactorTemplate extends RolePropertyFactorTemplateVO{
	public static final String SHEET_NAME = "成长等级对应成长值";
	
	@Override
	public void check() throws TemplateConfigException {
		
	}
}
