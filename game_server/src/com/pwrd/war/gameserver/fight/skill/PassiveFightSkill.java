package com.pwrd.war.gameserver.fight.skill;

import java.util.List;

import com.pwrd.war.gameserver.skill.effect.RealSkillEffect;
import com.pwrd.war.gameserver.skill.enums.SkillTargetTypeEnum;
import com.pwrd.war.gameserver.skill.enums.SkillTriggerTypeEnum;
import com.pwrd.war.gameserver.skill.template.SkillPasvTemplate;



/**
 * 战斗中被动技能的封装实现
 * @author zy
 *
 */
public class PassiveFightSkill {
	protected int skillSn;
	protected int coolDown;
	protected List<RealSkillEffect> realSkillEffects;
	protected SkillTriggerTypeEnum triggerType;
	protected SkillTargetTypeEnum realTargetType;
	protected int coolDownRound;
	
	/**
	 * 根据被动技能模板创建被动技能封装对象
	 * @param template
	 * @return
	 */
	public static PassiveFightSkill buildSkill(SkillPasvTemplate template) {
		PassiveFightSkill skill = new PassiveFightSkill();
		skill.skillSn = template.getSkillSn();
		skill.coolDown = template.getCoolDown();
		skill.realSkillEffects = template.getRealSkillEffects();
		skill.triggerType = template.getTriggerType();
		skill.realTargetType = template.getRealTargetType();
		skill.coolDownRound = 0;
		return skill;
	}

	public int getSkillSn() {
		return skillSn;
	}

	public int getCoolDown() {
		return coolDown;
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
	
	public int getCoolDownRound() {
		return coolDownRound;
	}
	
	public void setCoolDownRound(int coolDownRound) {
		this.coolDownRound = coolDownRound;
	}
	
}
