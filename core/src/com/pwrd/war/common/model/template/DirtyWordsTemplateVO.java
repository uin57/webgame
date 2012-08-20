package com.pwrd.war.common.model.template;

import com.pwrd.war.core.annotation.ExcelRowBinding;
import com.pwrd.war.core.template.TemplateObject;
import com.pwrd.war.core.util.StringUtils;
import com.pwrd.war.core.annotation.NotTranslate;
import com.pwrd.war.common.exception.TemplateConfigException;
import com.pwrd.war.core.annotation.ExcelCellBinding;

/**
 * 脏话中间层
 * 
 * @author CodeGenerator, don't modify this file please.
 */

@ExcelRowBinding
public abstract class DirtyWordsTemplateVO extends TemplateObject {

	/** 要过滤的词 */
	@NotTranslate
	@ExcelCellBinding(offset = 1)
	protected String dirtyWords;


	public String getDirtyWords() {
		return this.dirtyWords;
	}

	public void setDirtyWords(String dirtyWords) {
		if (StringUtils.isEmpty(dirtyWords)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[要过滤的词]dirtyWords不可以为空");
		}
		this.dirtyWords = dirtyWords;
	}
	

	@Override
	public String toString() {
		return "DirtyWordsTemplateVO[dirtyWords=" + dirtyWords + ",]";

	}
}