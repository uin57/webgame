package com.pwrd.war.gameserver.skill.template;

import com.pwrd.war.core.annotation.ExcelRowBinding;
import com.pwrd.war.core.template.TemplateObject;
import com.pwrd.war.core.template.ExcelCollectionMapping;
import com.pwrd.war.common.exception.TemplateConfigException;
import com.pwrd.war.core.annotation.ExcelCellBinding;

/**
 * 被动技能模板
 * 
 * @author CodeGenerator, don't modify this file please.
 */

@ExcelRowBinding
public abstract class SkillPasvTemplateVO extends TemplateObject {

	/**  技能id */
	@ExcelCellBinding(offset = 1)
	protected Integer skillSn;

	/**  技能名称 */
	@ExcelCellBinding(offset = 2)
	protected String skillName;

	/**  技能说明 */
	@ExcelCellBinding(offset = 3)
	protected String skillDescription;

	/**  技能图标 */
	@ExcelCellBinding(offset = 4)
	protected String skillIcon;

	/**  技能等级 */
	@ExcelCellBinding(offset = 5)
	protected Integer skillLevel;

	/**  目标方，0敌方人，1敌方障碍，2敌方全体，3我方人，4我方障碍，5我方全体，6仅自己 */
	@ExcelCellBinding(offset = 6)
	protected Integer targetType;

	/**  被动技能触发点 */
	@ExcelCellBinding(offset = 7)
	protected Integer pasvTrigger;

	/**  冷却回合 */
	@ExcelCellBinding(offset = 8)
	protected Integer coolDown;

	/**  效果数组 */
	@ExcelCollectionMapping(clazz = String.class, collectionNumber = "9;10;11;12;13")
	protected String[] skillEffects;


	public Integer getSkillSn() {
		return this.skillSn;
	}

	public void setSkillSn(Integer skillSn) {
		if (skillSn == null) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[ 技能id]skillSn不可以为空");
		}	
		this.skillSn = skillSn;
	}
	
	public String getSkillName() {
		return this.skillName;
	}

	public void setSkillName(String skillName) {
		this.skillName = skillName;
	}
	
	public String getSkillDescription() {
		return this.skillDescription;
	}

	public void setSkillDescription(String skillDescription) {
		this.skillDescription = skillDescription;
	}
	
	public String getSkillIcon() {
		return this.skillIcon;
	}

	public void setSkillIcon(String skillIcon) {
		this.skillIcon = skillIcon;
	}
	
	public Integer getSkillLevel() {
		return this.skillLevel;
	}

	public void setSkillLevel(Integer skillLevel) {
		this.skillLevel = skillLevel;
	}
	
	public Integer getTargetType() {
		return this.targetType;
	}

	public void setTargetType(Integer targetType) {
		this.targetType = targetType;
	}
	
	public Integer getPasvTrigger() {
		return this.pasvTrigger;
	}

	public void setPasvTrigger(Integer pasvTrigger) {
		this.pasvTrigger = pasvTrigger;
	}
	
	public Integer getCoolDown() {
		return this.coolDown;
	}

	public void setCoolDown(Integer coolDown) {
		this.coolDown = coolDown;
	}
	
	public String[] getSkillEffects() {
		return this.skillEffects;
	}

	public void setSkillEffects(String[] skillEffects) {
		if (skillEffects == null) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					10, "[ 效果数组]skillEffects不可以为空");
		}	
		this.skillEffects = skillEffects;
	}
	

	@Override
	public String toString() {
		return "SkillPasvTemplateVO[skillSn=" + skillSn + ",skillName=" + skillName + ",skillDescription=" + skillDescription + ",skillIcon=" + skillIcon + ",skillLevel=" + skillLevel + ",targetType=" + targetType + ",pasvTrigger=" + pasvTrigger + ",coolDown=" + coolDown + ",skillEffects=" + skillEffects + ",]";

	}
}