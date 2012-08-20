package com.pwrd.war.gameserver.skill.template;

import com.pwrd.war.common.exception.TemplateConfigException;
import com.pwrd.war.core.annotation.ExcelRowBinding;
import com.pwrd.war.gameserver.skill.enums.SkillBuffTypeEnum;

/**
 * 技能buff模板
 * @author zy
 *
 */
@ExcelRowBinding
public class SkillBuffTemplate extends SkillBuffTemplateVO {
	protected SkillBuffTypeEnum realBuffType;	//技能buff枚举类型
	
	@Override
	public void check() throws TemplateConfigException {
		//构造特殊参数
		realBuffType = SkillBuffTypeEnum.getTypeById(buffType.intValue());
	}
	
	public SkillBuffTypeEnum getRealBuffType() {
		return realBuffType;
	}
	
}
