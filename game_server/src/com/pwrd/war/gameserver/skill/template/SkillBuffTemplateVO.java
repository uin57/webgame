package com.pwrd.war.gameserver.skill.template;

import com.pwrd.war.core.annotation.ExcelRowBinding;
import com.pwrd.war.core.template.TemplateObject;
import com.pwrd.war.common.exception.TemplateConfigException;
import com.pwrd.war.core.annotation.ExcelCellBinding;

/**
 * 技能buff模板
 * 
 * @author CodeGenerator, don't modify this file please.
 */

@ExcelRowBinding
public abstract class SkillBuffTemplateVO extends TemplateObject {

	/**  buffid */
	@ExcelCellBinding(offset = 1)
	protected Integer buffId;

	/**  buff类型 */
	@ExcelCellBinding(offset = 2)
	protected Integer buffType;

	/**  buff值 */
	@ExcelCellBinding(offset = 3)
	protected Integer buffValue;


	public Integer getBuffId() {
		return this.buffId;
	}

	public void setBuffId(Integer buffId) {
		if (buffId == null) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[ buffid]buffId不可以为空");
		}	
		this.buffId = buffId;
	}
	
	public Integer getBuffType() {
		return this.buffType;
	}

	public void setBuffType(Integer buffType) {
		if (buffType == null) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[ buff类型]buffType不可以为空");
		}	
		this.buffType = buffType;
	}
	
	public Integer getBuffValue() {
		return this.buffValue;
	}

	public void setBuffValue(Integer buffValue) {
		if (buffValue == null) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[ buff值]buffValue不可以为空");
		}	
		this.buffValue = buffValue;
	}
	

	@Override
	public String toString() {
		return "SkillBuffTemplateVO[buffId=" + buffId + ",buffType=" + buffType + ",buffValue=" + buffValue + ",]";

	}
}