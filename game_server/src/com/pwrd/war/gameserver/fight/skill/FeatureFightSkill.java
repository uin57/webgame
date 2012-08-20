package com.pwrd.war.gameserver.fight.skill;

import java.util.List;

import com.pwrd.war.gameserver.skill.effect.RealSkillEffect;
import com.pwrd.war.gameserver.skill.enums.SkillTriggerTypeEnum;
import com.pwrd.war.gameserver.skill.template.SkillSpecTemplate;

/**
 * 战斗中职业特性技能的封装实现
 * @author zy
 *
 */
public class FeatureFightSkill extends ActiveFightSkill {
	protected int counter;	//计数器
	protected List<RealSkillEffect> pasvEffects;	//被动效果
	protected SkillTriggerTypeEnum triggerType;		//被动触发类型
	
	/**
	 * 根据职业特性技能模板构造战斗中主动技能封装对象
	 * @param template
	 * @return
	 */
	public static FeatureFightSkill buildSkill(SkillSpecTemplate template) {
		if (template == null) {
			return null;
		}
		FeatureFightSkill skill = new FeatureFightSkill();
		skill.hasAttack = true;
		skill.realEffects = template.getRealEffects();
		skill.skillSn = template.getSkillSn();
		skill.coolDown = template.getCoolDown();
		skill.lockSkill = false;
		skill.featureSkill = true;
		skill.maxTargets = template.getMaxTargets();
		skill.targetScope = template.getTargetScope();
		skill.targetSort = template.getTargetSort();
		skill.allLineScope = template.isAllLineScope();
		skill.attackScope = template.getAttackScope();
		skill.realTargetType = template.getRealTargetType();
		skill.moraleCost = template.getMoraleCost();
		skill.counter = 0;
		skill.pasvEffects = template.getPasvSkillEffects();
		skill.triggerType = template.getTriggerType();
		skill.vocationSkill = false;
		skill.hasCoolDown = template.getHasCoolDown();
		skill.iamCenter = false;
		return skill;
	}
	
	/**
	 * 重置计数器
	 */
	public void resetCounter() {
		counter = 0;
	}

	public int getCounter() {
		return counter;
	}
	
	public void setCounter(int counter) {
		this.counter = counter;
	}

	public List<RealSkillEffect> getPasvEffects() {
		return pasvEffects;
	}

	public SkillTriggerTypeEnum getTriggerType() {
		return triggerType;
	}
	
}
