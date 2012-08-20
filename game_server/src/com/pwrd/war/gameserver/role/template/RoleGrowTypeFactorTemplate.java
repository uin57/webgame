package com.pwrd.war.gameserver.role.template;

import com.pwrd.war.common.exception.TemplateConfigException;
import com.pwrd.war.core.annotation.ExcelRowBinding;

@ExcelRowBinding
public class RoleGrowTypeFactorTemplate extends RoleGrowTypeFactorTemplateVO{
public static final String SHEET_NAME = "职业系数值表";
	
	@Override
	public void check() throws TemplateConfigException {
		
	}
}
