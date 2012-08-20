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
import com.pwrd.war.gameserver.skill.enums.SkillTypeEnum;

/**
 * 主动技能模板，扩展特殊变量
 * @author zy
 *
 */
@ExcelRowBinding
public class SkillTemplate extends SkillTemplateVO {
	protected boolean isLockSkill;		//是否为锁屏武将技，当技能类型为3时表示锁屏武将技
	protected boolean isRemoteAttack;	//是否为远程攻击，1为远程
	protected boolean isAllLineScope;	//是否为全部列范围，1为全部列
	protected boolean isHasAttack;		//是否带攻击动作，1为带攻击
	protected boolean isVocationSkill;	//是否为普通职业攻击技
	protected List<RealSkillEffect> realSkillEffects = new ArrayList<RealSkillEffect>();	//技能效果
	protected SkillTargetTypeEnum realTargetType;	//目标方类型，0敌方人，1敌方障碍，2敌方全体，3我方人，4我方障碍，5我方全体，6仅自己
	protected Comparator<FightUnit> targetSort;		//目标排序规则

	/**
	 * 构造技能效果
	 */
	public void checkEffect(Map<Integer, SkillBuffTemplate> buffs) {
		for (RealSkillEffect effect : realSkillEffects) {
			effect.check(buffs);
		}
	}
	
	@Override
	public void check() throws TemplateConfigException {
		//构造特殊参数
		isLockSkill = (skillType == SkillTypeEnum.LOCKED_SPECIAL.getId());
		isRemoteAttack = (attackType == 1);
		isAllLineScope = (lineScope == 1);
		isHasAttack = (hasAttack == 1);
		realTargetType = SkillTargetTypeEnum.getTargetTypeById(targetType);
		isVocationSkill = (skillType == SkillTypeEnum.VOCATION_NORMAL.getId());
		
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
		case VOCATION12:	//优先近战
			targetSort = new Comparator<FightUnit>() {
				@Override
				public int compare(FightUnit u1, FightUnit u2) {
					boolean u1b = (u1.getVocation() == VocationType.HAOJIE || u1.getVocation() == VocationType.MENGJIANG);
					boolean u2b = (u2.getVocation() == VocationType.HAOJIE || u2.getVocation() == VocationType.MENGJIANG);
					if (u1b && u2b) {
						return 0;
					} else if (u1b && !u2b) {
						return -1;
					} else if (!u1b && u2b) {
						return 1;
					} else {
						return RandomUtils.nextInt(3) - 1;
					}
				}
			};
			break;
		case VOCATION34:	//优先远程
			targetSort = new Comparator<FightUnit>() {
				@Override
				public int compare(FightUnit u1, FightUnit u2) {
					boolean u1b = (u1.getVocation() == VocationType.MOUSHI || u1.getVocation() == VocationType.SHESHOU);
					boolean u2b = (u2.getVocation() == VocationType.MOUSHI || u2.getVocation() == VocationType.SHESHOU);
					if (u1b && u2b) {
						return 0;
					} else if (u1b && !u2b) {
						return -1;
					} else if (!u1b && u2b) {
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

	public boolean isLockSkill() {
		return isLockSkill;
	}

	public boolean isAllLineScope() {
		return isAllLineScope;
	}

	public SkillTargetTypeEnum getRealTargetType() {
		return realTargetType;
	}

	public boolean isHasAttack() {
		return isHasAttack;
	}

	public Comparator<FightUnit> getTargetSort() {
		return targetSort;
	}

	public boolean isVocationSkill() {
		return isVocationSkill;
	}
	
}
