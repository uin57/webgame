package com.pwrd.war.gameserver.skill.template;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.pwrd.war.common.exception.TemplateConfigException;
import com.pwrd.war.core.annotation.ExcelRowBinding;
import com.pwrd.war.gameserver.skill.effect.RealSkillEffect;
import com.pwrd.war.gameserver.skill.enums.SkillTargetTypeEnum;
import com.pwrd.war.gameserver.skill.enums.SkillTriggerTypeEnum;

/**
 * 被动技能模板
 * @author zy
 *
 */
@ExcelRowBinding
public class SkillPasvTemplate extends SkillPasvTemplateVO {
	protected List<RealSkillEffect> realSkillEffects = new ArrayList<RealSkillEffect>();	//技能效果
	protected SkillTriggerTypeEnum triggerType;
	protected SkillTargetTypeEnum realTargetType;	//目标方类型，0敌方人，1敌方障碍，2敌方全体，3我方人，4我方障碍，5我方全体，6仅自己

	public void checkEffect(Map<Integer, SkillBuffTemplate> buffs) {
		for (RealSkillEffect effect : realSkillEffects) {
			effect.check(buffs);
		}
	}
	
	@Override
	public void check() throws TemplateConfigException {
		//构造特殊参数
		triggerType = SkillTriggerTypeEnum.getTypeById(pasvTrigger);
		realTargetType = SkillTargetTypeEnum.getTargetTypeById(targetType);
		
		//解析获得最终效果对象列表
		if (skillEffects != null) {
			for (String param : skillEffects) {
				RealSkillEffect effect = RealSkillEffect.buildEffect(param);
				if (effect != null) {
					realSkillEffects.add(effect);
				}
			}
		}
	}

	public List<RealSkillEffect> getRealSkillEffects() {
		return realSkillEffects;
	}

	public SkillTriggerTypeEnum getTriggerType() {
		return triggerType;
	}

	public SkillTargetTypeEnum getRealTargetType() {
		return realTargetType;
	}
	
}
