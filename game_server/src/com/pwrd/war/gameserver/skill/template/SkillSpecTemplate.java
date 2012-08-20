package com.pwrd.war.gameserver.skill.template;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.math.RandomUtils;

import com.pwrd.war.common.exception.TemplateConfigException;
import com.pwrd.war.core.annotation.ExcelRowBinding;
import com.pwrd.war.gameserver.common.enums.VocationType;
import com.pwrd.war.gameserver.fight.field.unit.FightUnit;
import com.pwrd.war.gameserver.skill.effect.RealSkillEffect;
import com.pwrd.war.gameserver.skill.enums.SkillTargetSortStrategyEnum;
import com.pwrd.war.gameserver.skill.enums.SkillTargetTypeEnum;
import com.pwrd.war.gameserver.skill.enums.SkillTriggerTypeEnum;

/**
 * 职业特性技能模板
 * @author zy
 *
 */
@ExcelRowBinding
public class SkillSpecTemplate extends SkillSpecTemplateVO {
	protected boolean isRemoteAttack;	//是否为远程攻击，1为远程
	protected boolean isAllLineScope;	//是否为全部列范围，1为全部列
	protected List<RealSkillEffect> realSkillEffects = new ArrayList<RealSkillEffect>();	//主动技能效果
	protected SkillTargetTypeEnum realTargetType;	//目标方类型，0敌方人，1敌方障碍，2敌方全体，3我方人，4我方障碍，5我方全体，6仅自己
	protected Comparator<FightUnit> targetSort;		//目标排序规则
	protected List<RealSkillEffect> pasvSkillEffects = new ArrayList<RealSkillEffect>();	//被动技能效果
	protected SkillTriggerTypeEnum triggerType;		//被动触发类型

	/**
	 * 构造技能效果
	 */
	public void checkEffect(Map<Integer, SkillBuffTemplate> buffs) {
		for (RealSkillEffect effect : realSkillEffects) {
			effect.check(buffs);
		}
		for (RealSkillEffect effect : pasvSkillEffects) {
			effect.check(buffs);
		}
	}
	
	@Override
	public void check() throws TemplateConfigException {
		//构造特殊参数
		isRemoteAttack = (attackType == 1);
		isAllLineScope = (lineScope == 1);
		realTargetType = SkillTargetTypeEnum.getTargetTypeById(targetType);
		triggerType = SkillTriggerTypeEnum.getTypeById(pasvTrigger);
		
		//构造目标排序规则
		initSortComparator();
		
		//解析获得最终效果对象列表
		if (skillEffects != null) {
			for (String param : skillEffects) {
				RealSkillEffect effect = RealSkillEffect.buildEffect(param);
				if (effect != null) {
					realSkillEffects.add(effect);
				}
			}
		}
		
		//解析获得最终被动效果对象列表
		if (pasvEffects != null) {
			for (String param : pasvEffects) {
				RealSkillEffect effect = RealSkillEffect.buildEffect(param);
				if (effect != null) {
					pasvSkillEffects.add(effect);
				}
			}
		}
	}
	
	/**
	 * 构造目标排序规则
	 */
	private void initSortComparator() {
		//构造目标排序规则。0随机，1血量从少到多，2血量从多到少，3优先职业1，4优先职业2，5优先职业3，6优先职业4
		SkillTargetSortStrategyEnum sortEnum = SkillTargetSortStrategyEnum.getStrategyTypeById(targetFilter);
		switch (sortEnum) {
		case HP_ASC:	//血量从少到多
			targetSort = new Comparator<FightUnit>() {
				@Override
				public int compare(FightUnit u1, FightUnit u2) {
					return u1.getCurHp() - u2.getCurHp();
				}
			};
			break;
		case HP_DESC:	//血量从多到少
			targetSort = new Comparator<FightUnit>() {
				@Override
				public int compare(FightUnit u1, FightUnit u2) {
					return u2.getCurHp() - u1.getCurHp();
				}
			};
			break;
		case VOCATION1:	//优先职业1
			targetSort = new Comparator<FightUnit>() {
				@Override
				public int compare(FightUnit u1, FightUnit u2) {
					if (u1.getVocation() == VocationType.MENGJIANG && u2.getVocation() == VocationType.MENGJIANG) {
						return 0;
					} else if (u1.getVocation() == VocationType.MENGJIANG && u2.getVocation() != VocationType.MENGJIANG) {
						return -1;
					} else if (u1.getVocation() != VocationType.MENGJIANG && u2.getVocation() == VocationType.MENGJIANG) {
						return 1;
					} else {
						return RandomUtils.nextInt(3) - 1;
					}
				}
			};
			break;
		case VOCATION2:	//优先职业2
			targetSort = new Comparator<FightUnit>() {
				@Override
				public int compare(FightUnit u1, FightUnit u2) {
					if (u1.getVocation() == VocationType.HAOJIE && u2.getVocation() == VocationType.HAOJIE) {
						return 0;
					} else if (u1.getVocation() == VocationType.HAOJIE && u2.getVocation() != VocationType.HAOJIE) {
						return -1;
					} else if (u1.getVocation() != VocationType.HAOJIE && u2.getVocation() == VocationType.HAOJIE) {
						return 1;
					} else {
						return RandomUtils.nextInt(3) - 1;
					}
				}
			};
			break;
		case VOCATION3:	//优先职业3
			targetSort = new Comparator<FightUnit>() {
				@Override
				public int compare(FightUnit u1, FightUnit u2) {
					if (u1.getVocation() == VocationType.SHESHOU && u2.getVocation() == VocationType.SHESHOU) {
						return 0;
					} else if (u1.getVocation() == VocationType.SHESHOU && u2.getVocation() != VocationType.SHESHOU) {
						return -1;
					} else if (u1.getVocation() != VocationType.SHESHOU && u2.getVocation() == VocationType.SHESHOU) {
						return 1;
					} else {
						return RandomUtils.nextInt(3) - 1;
					}
				}
			};
			break;
		case VOCATION4:	//优先职业4
			targetSort = new Comparator<FightUnit>() {
				@Override
				public int compare(FightUnit u1, FightUnit u2) {
					if (u1.getVocation() == VocationType.MOUSHI && u2.getVocation() == VocationType.MOUSHI) {
						return 0;
					} else if (u1.getVocation() == VocationType.MOUSHI && u2.getVocation() != VocationType.MOUSHI) {
						return -1;
					} else if (u1.getVocation() != VocationType.MOUSHI && u2.getVocation() == VocationType.MOUSHI) {
						return 1;
					} else {
						return RandomUtils.nextInt(3) - 1;
					}
				}
			};
			break;
		default:		//默认随机
			targetSort = new Comparator<FightUnit>() {
				@Override
				public int compare(FightUnit u1, FightUnit u2) {
					return RandomUtils.nextInt(3) - 1;
				}
				
			};
		}
	}

	public List<RealSkillEffect> getRealEffects() {
		return realSkillEffects;
	}

	public boolean isRemoteAttack() {
		return isRemoteAttack;
	}

	public boolean isAllLineScope() {
		return isAllLineScope;
	}

	public SkillTargetTypeEnum getRealTargetType() {
		return realTargetType;
	}

	public Comparator<FightUnit> getTargetSort() {
		return targetSort;
	}

	public List<RealSkillEffect> getPasvSkillEffects() {
		return pasvSkillEffects;
	}

	public SkillTriggerTypeEnum getTriggerType() {
		return triggerType;
	}
}
