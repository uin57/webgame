package com.pwrd.war.gameserver.fight.skill;

import java.util.Comparator;
import java.util.List;

import com.pwrd.war.gameserver.fight.field.unit.FightUnit;
import com.pwrd.war.gameserver.skill.effect.RealSkillEffect;
import com.pwrd.war.gameserver.skill.enums.SkillTargetTypeEnum;
import com.pwrd.war.gameserver.skill.template.SkillTemplate;

/**
 * 战斗中主动技能的封装实现
 * @author zy
 *
 */
public class ActiveFightSkill {
	protected boolean hasAttack;
	protected List<RealSkillEffect> realEffects;
	protected int skillSn;
	protected int coolDown;
	protected boolean lockSkill;
	protected boolean featureSkill;
	protected int maxTargets;
	protected int targetScope;
	protected Comparator<FightUnit> targetSort;
	protected boolean allLineScope;
	protected int attackScope;
	protected SkillTargetTypeEnum realTargetType;
	protected int moraleCost;
	protected boolean vocationSkill;
	protected boolean hasCoolDown;
	protected boolean iamCenter;

	/**
	 * 根据技能模板构造战斗中主动技能封装对象
	 * @param template
	 * @return
	 */
	public static ActiveFightSkill buildSkill(SkillTemplate template) {
		if (template == null) {
			return null;
		}
		ActiveFightSkill skill = new ActiveFightSkill();
		skill.hasAttack = template.isHasAttack();
		skill.realEffects = template.getRealEffects();
		skill.skillSn = template.getSkillSn();
		skill.coolDown = template.getCoolDown();
		skill.lockSkill = template.isLockSkill();
		skill.featureSkill = false;
		skill.maxTargets = template.getMaxTargets();
		skill.targetScope = template.getTargetScope();
		skill.targetSort = template.getTargetSort();
		skill.allLineScope = template.isAllLineScope();
		skill.attackScope = template.getAttackScope();
		skill.realTargetType = template.getRealTargetType();
		skill.moraleCost = template.getMoraleCost();
		skill.vocationSkill = template.isVocationSkill();
		skill.hasCoolDown = template.getHasCoolDown();
		skill.iamCenter = template.getIamCenter();
		return skill;
	}
	
	public boolean isHasAttack() {
		return hasAttack;
	}

	public List<RealSkillEffect> getRealEffects() {
		return realEffects;
	}

	public int getSkillSn() {
		return skillSn;
	}

	public int getCoolDown() {
		return coolDown;
	}

	public boolean isLockSkill() {
		return lockSkill;
	}

	public int getMaxTargets() {
		return maxTargets;
	}

	public int getTargetScope() {
		return targetScope;
	}

	public Comparator<FightUnit> getTargetSort() {
		return targetSort;
	}

	public boolean isAllLineScope() {
		return allLineScope;
	}

	public int getAttackScope() {
		return attackScope;
	}

	public SkillTargetTypeEnum getRealTargetType() {
		return realTargetType;
	}

	public int getMoraleCost() {
		return moraleCost;
	}

	public boolean isFeatureSkill() {
		return featureSkill;
	}
	
	public boolean isVocationSkill() {
		return vocationSkill;
	}

	public boolean isHasCoolDown() {
		return hasCoolDown;
	}

	public boolean isIamCenter() {
		return iamCenter;
	}
	
}
