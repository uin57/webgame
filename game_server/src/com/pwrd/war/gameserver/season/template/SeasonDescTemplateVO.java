package com.pwrd.war.gameserver.season.template;

import com.pwrd.war.core.annotation.ExcelRowBinding;
import com.pwrd.war.core.template.TemplateObject;
import com.pwrd.war.core.util.StringUtils;
import com.pwrd.war.common.exception.TemplateConfigException;
import com.pwrd.war.core.annotation.ExcelCellBinding;

/**
 * 季节说明
 * 
 * @author CodeGenerator, don't modify this file please.
 */

@ExcelRowBinding
public abstract class SeasonDescTemplateVO extends TemplateObject {

	/** 说明多语言Id */
	@ExcelCellBinding(offset = 1)
	protected int descLangId;

	/** 说明 */
	@ExcelCellBinding(offset = 2)
	protected String desc;


	public int getDescLangId() {
		return this.descLangId;
	}

	public void setDescLangId(int descLangId) {
		this.descLangId = descLangId;
	}
	
	public String getDesc() {
		return this.desc;
	}

	public void setDesc(String desc) {
		if (StringUtils.isEmpty(desc)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[说明]desc不可以为空");
		}
		this.desc = desc;
	}
	

	@Override
	public String toString() {
		return "SeasonDescTemplateVO[descLangId=" + descLangId + ",desc=" + desc + ",]";

	}
}