package com.pwrd.war.gameserver.pet.template;

import com.pwrd.war.common.exception.TemplateConfigException;
import com.pwrd.war.core.annotation.ExcelRowBinding;

/**
 * 原生武将模板
 * @author yue.yan
 *
 */
@ExcelRowBinding
public class PetTemplate extends PetTemplateVO {

	public static final String SHEET_NAME = "武将模板";
	
	@Override
	public void check() throws TemplateConfigException {
		
	}
}
