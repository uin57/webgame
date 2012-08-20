package com.pwrd.war.gameserver.pet.template;

import com.pwrd.war.common.exception.TemplateConfigException;
import com.pwrd.war.core.annotation.ExcelRowBinding;

/**
 * 武将升级经验配置模板
 * @author yue.yan
 *
 */
@ExcelRowBinding
public class PetLevelUpTemplate extends PetLevelUpTemplateVO {

	public static final String SHEET_NAME = "武将升级经验配置表";
	
	@Override
	public void check() throws TemplateConfigException {

	}

}
