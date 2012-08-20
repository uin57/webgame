package com.pwrd.war.common.model.template;

import com.pwrd.war.common.exception.TemplateConfigException;
import com.pwrd.war.core.annotation.ExcelRowBinding;

/**
 * 名字过滤词
 * 
 */
@ExcelRowBinding
public class NameDirtyWordsTemplate extends DirtyWordsTemplateVO {

	@Override
	public void check() throws TemplateConfigException {
		// TODO Auto-generated method stub

	}
}
