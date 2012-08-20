package com.pwrd.war.gameserver.fight.field.unit;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.common.enums.VocationType;
import com.pwrd.war.gameserver.fight.FightUtils;
import com.pwrd.war.gameserver.fight.buff.FightArea;
import com.pwrd.war.gameserver.fight.buff.FightBuff;
import com.pwrd.war.gameserver.fight.buff.FightBuffDelay;
import com.pwrd.war.gameserver.fight.buff.FightHot;
import com.pwrd.war.gameserver.fight.buff.IFightBuff;
import com.pwrd.war.gameserver.fight.domain.FightBuffInfo;
import com.pwrd.war.gameserver.fight.domain.FightLockAction;
import com.pwrd.war.gameserver.fight.domain.FightLockTargetInfo;
import com.pwrd.war.gameserver.fight.domain.FightRoleInfo;
import com.pwrd.war.gameserver.fight.domain.FightTargetInfo;
import com.pwrd.war.gameserver.fight.domain.FightUnitAction;
import com.pwrd.war.gameserver.fight.enums.ActionType;
import com.pwrd.war.gameserver.fight.field.BaseField;
import com.pwrd.war.gameserver.fight.skill.ActiveFightSkill;
import com.pwrd.war.gameserver.fight.skill.FeatureFightSkill;
import com.pwrd.war.gameserver.fight.skill.PassiveFightSkill;
import com.pwrd.war.gameserver.monster.Monster;
import com.pwrd.war.gameserver.monster.template.MonsterTemplate;
import com.pwrd.war.gameserver.pet.Pet;
import com.pwrd.war.gameserver.role.Role;
import com.pwrd.war.gameserver.role.RoleTypes;
import com.pwrd.war.gameserver.role.template.RoleToSkillTemplate;
import com.pwrd.war.gameserver.skill.effect.RealSkillEffect;
import com.pwrd.war.gameserver.skill.enums.SkillBuffTypeEnum;
import com.pwrd.war.gameserver.skill.enums.SkillEffectTypeEnum;
import com.pwrd.war.gameserver.skill.enums.SkillTargetTypeEnum;
import com.pwrd.war.gameserver.skill.enums.SkillTriggerTypeEnum;
import com.pwrd.war.gameserver.skill.template.SkillPasvTemplate;
import com.pwrd.war.gameserver.skill.template.SkillSpecTemplate;
import com.pwrd.war.gameserver.skill.template.SkillTemplate;

public class FightUnit {
	//战斗单元战斗数值属性
	private int initMaxHp;		//最大血量
	private int initSpd;		//速度
	private double initAtk;	//攻击力
	private double initDef;	//防御力
	private double initHit;	//命中率
	private double initCri;	//暴击率
	private double initDodge;	//闪避率
	private double initDmgEnhance;	//对他人伤害加深率
	private double initDmgResist;	//受到的伤害抵抗率
	private double initRenxing;	//韧性
	private double initCriDmgEnhance;	//暴击伤害加深率
	
	//战斗中计算后战斗数值属性
	private int curHp;	//当前血量
	private int maxHp;	//最大血量
	private int spd;		//速度，正整数
	private double atk;	//攻击力
	private double def;	//防御力
	private double hit;	//命中率
	private double cri;	//暴击率
	private double dodge;	//闪避率
	private double dmgEnhance;	//对他人伤害加深率
	private double dmgResist;		//受到的伤害抵抗率
	private double renxing;	//韧性
	
	//战斗单元基本属性
	private int unitLine;			//单元所属线编号
	private String unitSn;			//单元sn
	private String unitName;		//单元名称
	private int unitLevel;			//单元level
	private int unitStar;			//单元星级
	private int unitGender;			//单元性别
	private VocationType vocation;	//单元职业
	private String skeletonSn;		//骨骼编号
	private String attackedAnim;	//受击动画
	private int unitIndex;			//单元在战场中的唯一编号
	private int unitType;			//战斗单元类型
	private boolean isAttack;		//是否为攻击方标志
	private int formPos;			//初始阵型中位置
	private boolean isRemote;		//是否为远程攻击职业
	private int attackerStiff;		//对攻击者的硬直回合数
	private ActiveFightSkill vocationSkill;		//职业普通攻击技
	private ActiveFightSkill specialSkill;		//武将技
	private FeatureFightSkill featureSkill;		//职业特性技
	private List<PassiveFightSkill> passiveSkills = new ArrayList<PassiveFightSkill>();	//被动技能，包括职业特性技能
	private String castAnim;		//弹道动画
	private String vocationAnim;	//职业特性弹道动画
	private boolean specialSkillUsed = false;	//武将技是否使用过
	
	//战斗中变化的属性
	private int curPos;				//当前位置值
	private int lastPos;			//上个回合位置
	private int curMorale;			//当前士气
	private int maxMorale;			//最大士气
	private int skillMorale;		//武将技士气消耗
	private int actionCoolDown;		//行动冷却结束回合，冷却中只能换线，不能移动和攻击，可以被击退
	private int skillCoolDown;		//技能冷却结束回合，冷却中不能攻击和移动，不能被普通击退
	private ActiveFightSkill currentActiveSkill;	//当前选中的主动技能
	private Map<Integer, FightUnit> currentTargets = new HashMap<Integer, FightUnit>();		//当前选中的主动技能的目标列表
	private int currentTargetPos;	//当前选中的主动技能的目标位置
	private int currentTargetIndex;	//当前选中的主动技能的目标编号
	private boolean attacked;		//当前回合是否被击中
	private boolean attackDelay;	//第一次普通攻击是否延迟
	private int belongs;			//所属角色编号

	private List<IFightBuff> allBuffs = new ArrayList<IFightBuff>();		//普通buff列表
	private List<IFightBuff> featureBuffs = new ArrayList<IFightBuff>();	//职业特性buff;
	private List<IFightBuff> delayBuffs = new ArrayList<IFightBuff>();		//延迟buff
	private List<IFightBuff> hotBuffs = new ArrayList<IFightBuff>();		//hot、dot
	private Map<Integer, Integer> areaBuffs = new HashMap<Integer, Integer>();	//区域buff效果id、编号列表
	private Map<Integer, Integer> oldAreaBuffs = new HashMap<Integer, Integer>();	//临时区域buff效果id、编号列表
	private Map<SkillBuffTypeEnum, Integer> areaValues = new HashMap<SkillBuffTypeEnum, Integer>();	//区域buff、debuff效果值
	private Map<SkillBuffTypeEnum, Integer> tempValues = new HashMap<SkillBuffTypeEnum, Integer>();	//临时buff、debuff效果值
	private Map<SkillBuffTypeEnum, Integer> buffValues = new HashMap<SkillBuffTypeEnum, Integer>();	//普通buff、debuff效果值
	private Map<SkillBuffTypeEnum, Integer> featureValues = new HashMap<SkillBuffTypeEnum, Integer>();	//职业特性buff、debuff效果值
	
	/**
	 * 判断是否有被动技能触发，如果触发返回信息，并重新计算属性
	 * @param currentRound
	 * @param trigger
	 * @param field
	 * @return
	 */
	public List<FightBuffInfo> doPassiveSkill(int currentRound, SkillTriggerTypeEnum trigger,
			BaseField field, List<FightBuffInfo> areaInfos) {
		List<FightBuffInfo> infos = new ArrayList<FightBuffInfo>();
		for (PassiveFightSkill skill : passiveSkills) {
			//判断被动技能是否处于冷却中，触发类型是否一致
			if (skill.getCoolDownRound() < currentRound && skill.getTriggerType() == trigger) {
				//依次触发效果获取buff动作
				for (RealSkillEffect effect : skill.getRealSkillEffects()) {
					doRealEffect(effect, field, this, infos, false, false, areaInfos);
				}
				
				//成功触发设置冷却
				skill.setCoolDownRound(currentRound + skill.getCoolDown() - 1);
			}
		}
		
		//返回buff变化
		if (infos.size() > 0) {
			//成功触发，重新计算属性
			recountProperties(false);
		}
		return infos;
	}
	
	/**
	 * 判断是否触发职业特性被动效果，如果触发返回buff信息，并重新计算属性
	 * @param field
	 * @param trigger
	 * @param incValue
	 * @return
	 */
	private List<FightBuffInfo> doPassiveFeatureSkill(BaseField field, SkillTriggerTypeEnum trigger,
			int incValue, List<FightBuffInfo> areaInfos) {
		//产生的buff属性
		List<FightBuffInfo> buffs = new ArrayList<FightBuffInfo>();
		
		//如果职业特性存在并且触发类型正确，产生效果并更新计数器
		if (featureSkill != null && isFeatureAvaliable() && incValue > 0 && featureSkill.getTriggerType() == trigger) {
			//计算计数器值变化
			int counter = featureSkill.getCounter();
			int nextCounter = counter + incValue;
			
			//依次判断被动效果是否被触发
			for (RealSkillEffect effect : featureSkill.getPasvEffects()) {
				int effectRound = effect.getEffectRound();
				if (counter < effectRound && nextCounter >= effectRound) {
					doRealEffect(effect, field, this, buffs, false, true, areaInfos);
				}
			}
			
			//无论被动效果是否触发都设置计数器变化
			featureSkill.setCounter(nextCounter);
		}
		
		return buffs;
	}
	
	/**
	 * 获取当前使用中的技能编号
	 * @return
	 */
	public int getCurrentSkillSn() {
		if (currentActiveSkill != null) {
			return currentActiveSkill.getSkillSn();
		} else {
			return -1;
		}
	}
	
	/**
	 * 判断是否有可用技能，如果找到可用技能则设置当前技能相关参数
	 * 近战职业和远程职业判断技能的顺序不同
	 * @param currentRound
	 * @param attTeam
	 * @param defTeam
	 * @return
	 */
  	public boolean checkCanDoSkill(int currentRound, FightTeam attTeam, FightTeam defTeam) {
		if (isRemote) {
			//远程职业技能判断顺序为：武将技>职业特性>普通技能
			return checkCanDoSkill(currentRound, specialSkill, attTeam, defTeam) ||
					checkCanDoSkill(currentRound, featureSkill, attTeam, defTeam) ||
					checkCanDoSkill(currentRound, vocationSkill, attTeam, defTeam);
		} else {
			//近战职业技能判断顺序为：职业特性>武将技>普通技能
			return checkCanDoSkill(currentRound, featureSkill, attTeam, defTeam) ||
					checkCanDoSkill(currentRound, specialSkill, attTeam, defTeam) ||
					checkCanDoSkill(currentRound, vocationSkill, attTeam, defTeam);
		}
	}
	
	/**
	 * 判断指定技能本回合是否可以使用，如果可以使用则设置当前技能相关参数
	 * @param currentRound
	 * @param skill
	 * @param attTeam
	 * @param defTeam
	 * @return
	 */
	private boolean checkCanDoSkill(int currentRound, ActiveFightSkill skill, FightTeam attTeam, FightTeam defTeam) {
		//判断技能是否存在
		if (skill == null) {
			return false;
		}
		
		//判断士气是否足够
		if (curMorale < skill.getMoraleCost()) {
			return false;
		}
		
		//武将技只能使用一次
		if (!skill.isFeatureSkill() && !skill.isVocationSkill() && specialSkillUsed) {
			return false;
		}
		
		//如果是职业特性，判断职业特性buff是否存在
		if (skill.isFeatureSkill() && (!isFeatureAvaliable() || featureValues.isEmpty())) {
			return false;
		}
		
		//根据技能目标类型来寻找技能目标，加入目标列表，并且获取硬直回合数
		switch (skill.getRealTargetType()) {
		case MYSELF:
			//如果是普通攻击并且延迟一回合的话直接返回失败
			if (skill.isVocationSkill() && attackDelay) {
				attackDelay = false;
				return false;
			}
			
			//技能目标为自己，直接将自己加入目标列表，设置技能相关信息
			currentActiveSkill = skill;
			currentTargets.put(unitIndex, this);
			currentTargetPos = curPos;
			currentTargetIndex = unitIndex;
			skillCoolDown = currentRound + skill.getCoolDown() - 1;
			if (currentActiveSkill.isHasCoolDown()) {
				actionCoolDown = skillCoolDown + attackerStiff;
			} else {
				actionCoolDown = skillCoolDown;
			}
			curMorale = curMorale - skill.getMoraleCost();
			return true;
			
		case ENEMY:
		case ENEMY_ITEMS:
		case ENEMY_ALL:
			//技能目标为敌方，根据自己所属方从对方队伍中选择目标
			if (isAttack) {
				return findSkillTargets(currentRound, skill, defTeam);
			} else {
				return findSkillTargets(currentRound, skill, attTeam);
			}
			
		case TEAM:
		case TEAM_ITEMS:
		case TEAM_ALL:
			//技能目标为己方，根据自己所属方从己方队伍中选择目标
			if (isAttack) {
				return findSkillTargets(currentRound, skill, attTeam);
			} else {
				return findSkillTargets(currentRound, skill, defTeam);
			}
			
		default:
			return false;
		}
	}
	
	/**
	 * 根据技能目标配置从指定的队伍中找到目标，如果目标选取成功设置当前技能相关信息并返回成功
	 * @param currentRound
	 * @param skill
	 * @param team
	 * @param checkAlive
	 * @return
	 */
	private boolean findSkillTargets(int currentRound, ActiveFightSkill skill, FightTeam team) {
		//目标不为自己，首先以战斗单位当前位置为中心，技能攻击范围为半径，找到所有目标
		int minPos = curPos - skill.getAttackScope();
		int maxPos = curPos + skill.getAttackScope();
		
		//如果技能以自己为中心则选取自己身前范围内
		if (skill.isIamCenter()) {
			if (isAttack) {
				minPos = curPos - skill.getTargetScope();
				maxPos = curPos;
			} else {
				minPos = curPos;
				maxPos = curPos + skill.getTargetScope();
			}
		}
		
		//根据技能配置的列范围以及攻击范围从所有存活单位中找到全部目标，并获取距离最近的一个目标的位置
		int minDistance = FightUtils.MaxGrids;
		int nearPos = -1;
		boolean allLines = skill.isAllLineScope();
		SkillTargetTypeEnum targetType = skill.getRealTargetType();
		List<FightUnit> targets = new ArrayList<FightUnit>();
		for (FightUnit target : team.getAlives()) {
			//首先根据列范围筛选目标
			if (allLines || target.unitLine == unitLine) {
				//然后根据目标是否处于攻击范围内，并且目标是否为技能配置的目标类型进行筛选
				int targetPos = target.curPos;
				if (targetPos >= minPos && targetPos <= maxPos && checkTargetType(target.unitType, targetType)) {
					//目标符合条件，加入列表，并更新最近距离
					targets.add(target);
					int distance = Math.abs(targetPos - curPos);
					if (distance < minDistance) {
						minDistance = distance;
						nearPos = targetPos;
					}
				}
			}
		}
		
		//如果找到了目标，以距离最近的位置为中心，技能伤害范围为半径，去掉范围外的目标
		if (targets.size() > 0) {
			//如果是以自己为中心则无需再次过滤目标
			if (!skill.isIamCenter()) {
				Iterator<FightUnit> itor = targets.iterator();
				int targetScope = skill.getTargetScope();
				while (itor.hasNext()) {
					FightUnit target = itor.next();
					if (Math.abs(target.curPos - nearPos) > targetScope) {
						itor.remove();
					}
				}
			}
			
			//如果还存在目标，按照技能配置的排序方式进行排序，选择不超过技能目标数量的目标作为当前技能目标，设置当前技能信息
			if (targets.size() > 0) {
				//如果是普通攻击并且延迟一回合的话直接返回失败
				if (skill.isVocationSkill() && attackDelay) {
					attackDelay = false;
					return false;
				}
				
				//排序
				Collections.sort(targets, skill.getTargetSort());
				
				//选择不超过技能目标数量的目标，并计算最大硬直
				int maxTarget = skill.getMaxTargets();
				int stiffRound = 0;
				for (FightUnit target : targets) {
					currentTargets.put(target.unitIndex, target);
					currentTargetIndex = target.getUnitIndex();
					if (target.attackerStiff > stiffRound) {
						stiffRound = target.attackerStiff;
					}
					if (currentTargets.size() >= maxTarget) {
						break;
					}
				}
				
				//设置当前技能信息
				currentActiveSkill = skill;
				currentTargetPos = nearPos;
				skillCoolDown = currentRound + skill.getCoolDown() - 1;
				if (skill.isHasCoolDown()) {
					actionCoolDown = skillCoolDown + stiffRound;
				} else {
					actionCoolDown = skillCoolDown;
				}
				curMorale = curMorale - skill.getMoraleCost();
				return true;
			}
		}
		
		//找不到目标返回错误
		return false;
	}
	
	/**
	 * 根据当前技能再次寻找目标将当前技能目标数量填满到最大
	 * @param minPos
	 * @param maxPos
	 * @param team
	 * @param checkAlive
	 */
	private void refillSkillTargets(int minPos, int maxPos, FightTeam team, boolean checkAlive) {
		//以当前技能目标位置为中心，技能攻击范围为半径，找到所有目标
		int maxTargets = currentActiveSkill.getMaxTargets();
		boolean allLines = currentActiveSkill.isAllLineScope();
		SkillTargetTypeEnum targetType = currentActiveSkill.getRealTargetType();
		for (FightUnit target : team.getAlives()) {
			//根据参数决定是否判断目标存活情况
			if (checkAlive && !target.isAlive()) {
				continue;
			}
			
			//判断目标位置和类型是否符合要求
			if (allLines || target.unitIndex == unitIndex) {
				int targetIndex = target.unitIndex;
				int targetPos = target.curPos;
				if (!currentTargets.containsKey(targetIndex) && targetPos >= minPos &&
						targetPos <= maxPos && checkTargetType(target.unitType, targetType)) {
					currentTargets.put(targetIndex, target);
				}
				
				//已经填满则停止寻找
				if (currentTargets.size() >= maxTargets) {
					break;
				}
			}
		}
	}
	
	/**
	 * 判断目标角色类型是否与技能需求的类型相符合
	 * @param unitType
	 * @param typeEnum
	 * @return
	 */
	private boolean checkTargetType(int unitType, SkillTargetTypeEnum typeEnum) {
		switch (typeEnum) {
		case ENEMY_ALL:
		case TEAM_ALL:
			//全体类型都符合
			return true;
		
		case ENEMY:
		case TEAM:
			//只有人物、武将、怪物类型符合
			return (unitType != RoleTypes.MASHINE);
			
		case ENEMY_ITEMS:
		case TEAM_ITEMS:
			//只有障碍类型符合
			return (unitType == RoleTypes.MASHINE);
			
		default:
			return false;
		}
	}
	
	/**
	 * 如果当前使用的技能为非锁屏武将技，创建技能动作
	 * @param unitActions
	 */
	public void createAttackAction(Map<Integer, FightUnitAction> unitActions) {
		if (currentActiveSkill != null && !currentActiveSkill.isLockSkill()) {
			FightUnitAction action = unitActions.get(unitIndex);
			if (action != null) {
				action.updateActionType(ActionType.ATTACK);
				action.setSkillSn(currentActiveSkill.getSkillSn());
				action.setSkillTarget(currentTargetIndex);
			} else {
				unitActions.put(unitIndex, new FightUnitAction(unitIndex, currentActiveSkill.getSkillSn(),
						currentTargetIndex, ActionType.ATTACK.getId()));
			}
		}
	}
	
	/**
	 * 使用指定技能并产生动作
	 * @param currentRound
	 * @param field
	 * @param lockActions
	 * @param unitActions
	 */
	public void doSkill(BaseField field, List<FightLockAction> lockActions, Map<Integer,
			FightUnitAction> unitActions, List<FightBuffInfo> areaInfos) {
		//如果当前回合晕眩中返回
		if (isStun()) {
			return;
		}
		
		//判断当前技能存在
		if (currentActiveSkill == null) {
			return;
		}
		
		//获取当前技能信息
		boolean isLockSkill = currentActiveSkill.isLockSkill();
		boolean isFeature = currentActiveSkill.isFeatureSkill();
		boolean isNormal = !isLockSkill && !isFeature;	//普通攻击和普通武将技
		
		//判断传入队列
		if ((isLockSkill && lockActions == null) || (!isLockSkill && unitActions == null)) {
			return;
		}
		
		//武将技使用后设置标志
		if (!isFeature && !currentActiveSkill.isVocationSkill()) {
			specialSkillUsed = true;
		}
		
		//计算当前回合，如果是锁屏武将技默认所有效果都在第一回合触发
		int currentRound = field.getCurrentRound();
		int nowRound = (currentRound + currentActiveSkill.getCoolDown() - skillCoolDown);
		if (isLockSkill) {
			nowRound = -1;
		}
		
		//判断当前回合是否存在可使用的技能效果，寻找攻击前效果和攻击后效果并产生动作
		List<RealSkillEffect> beforeEffects = new ArrayList<RealSkillEffect>();
		List<RealSkillEffect> afterEffects = new ArrayList<RealSkillEffect>();
		List<RealSkillEffect> areaEffects = new ArrayList<RealSkillEffect>();
		if (findSkillEffect(nowRound, beforeEffects, afterEffects, areaEffects)) {
			//找到触发效果，但是如果当前技能不带攻击并且触发效果中没有攻击后效果或区域buff效果则仍然不算使用成功
			if (currentActiveSkill.isHasAttack() || afterEffects.size() > 0 || areaEffects.size() > 0) {
				//移除已经超出攻击范围的技能目标，如果是锁屏武将技还需要移除死亡的目标
				int attScope = currentActiveSkill.getTargetScope();
				int minPos = currentTargetPos - attScope;
				int maxPos = currentTargetPos + attScope;
				
				//如果技能以自己为中心则选取自己身前范围内
				if (currentActiveSkill.isIamCenter()) {
					if (isAttack) {
						minPos = curPos - attScope;
						maxPos = curPos;
					} else {
						minPos = curPos;
						maxPos = curPos + attScope;
					}
				}
				
				Iterator<Map.Entry<Integer, FightUnit>> itor = currentTargets.entrySet().iterator();
				while (itor.hasNext()) {
					Map.Entry<Integer, FightUnit> entry = itor.next();
					FightUnit unit = entry.getValue();
					int pos = unit.getCurPos();
					if (pos < minPos || pos > maxPos || (isLockSkill && !unit.isAlive())) {
						itor.remove();
					}
				}
				
				//如果技能目标数量不足则补充新的目标到技能目标中
				if (currentTargets.size() < currentActiveSkill.getMaxTargets()) {
					switch (currentActiveSkill.getRealTargetType()) {
					case ENEMY:
					case ENEMY_ITEMS:
					case ENEMY_ALL:
						//技能目标为敌方，根据自己所属方从对方队伍中选择目标
						if (isAttack) {
							refillSkillTargets(minPos, maxPos, field.getDefTeam(), isLockSkill);
						} else {
							refillSkillTargets(minPos, maxPos, field.getAttTeam(), isLockSkill);
						}
						break;
						
					case TEAM:
					case TEAM_ITEMS:
					case TEAM_ALL:
						//技能目标为己方，根据自己所属方从己方队伍中选择目标
						if (isAttack) {
							refillSkillTargets(minPos, maxPos, field.getAttTeam(), isLockSkill);
						} else {
							refillSkillTargets(minPos, maxPos, field.getDefTeam(), isLockSkill);
						}
						break;
					}
				}
				
				//如果技能目标存在构造动作，并使用技能
				if (currentTargets.size() > 0) {
					//构造目标和buff信息对象
					List<FightLockTargetInfo> lockTargets = new ArrayList<FightLockTargetInfo>();
					List<FightTargetInfo> unitTargets = new ArrayList<FightTargetInfo>();
					List<FightBuffInfo> infos = new ArrayList<FightBuffInfo>();
					
					//首先产生区域buff
					for (RealSkillEffect effect : areaEffects) {
						//根据效果目标类型进行处理
						if (effect.isToTarget()) {
							//对技能目标起作用的区域buff，取目标中的一个进行处理即可
							for (FightUnit target : currentTargets.values()) {
								doRealEffect(effect, field, target, infos, false, false, areaInfos);
								break;
							}
						} else {
							doRealEffect(effect, field, this, infos, false, false, areaInfos);
						}
					}
					
					//如果是技能职业特性攻击，获取职业特性击退距离。因为获取职业特性击退距离后击退距离重置，所以必须在攻击前就获取此值
					int featureBack = 0;
					if (isFeature) {
						featureBack = getFeatureBackDistance();
					}
					
					//如果存在攻击动作，首先使用攻击前效果再进行攻击
					if (currentActiveSkill.isHasAttack()) {
						if (beforeEffects.size() > 0) {
							for (RealSkillEffect effect : beforeEffects) {
								//根据效果目标类型进行处理
								if (effect.isToTarget()) {
									doRealEffect(effect, field, currentTargets.values(), infos, true, false, areaInfos);
								} else {
									doRealEffect(effect, field, this, infos, true, false, areaInfos);
								}
							}
							
							//重新计算攻击方数值
							recountProperties(!isLockSkill);
						}
						
						//执行攻击并设置技能目标
						for (FightUnit target : currentTargets.values()) {
							//重新计算目标数值
							if (beforeEffects.size() > 0) {
								target.recountProperties(!isLockSkill);
							}
							
							//计算命中，如果存在必中buff则无需判断命中逻辑
							int finalDmg = 0;
							int hitType = FightTargetInfo.TYPE_DODGE;
							if (buffValues.containsKey(SkillBuffTypeEnum.MUST_HIT_BUFF) ||
									FightUtils.isHit(this, target, isNormal)) {
								//计算伤害，如果存在坚硬效果只掉1点血
								double damage = 1D;
								boolean hasStrongBuff = target.buffValues.containsKey(SkillBuffTypeEnum.ONE_DMG_BUFF);
								if (!hasStrongBuff) {
									damage = FightUtils.calcDamage(this, target);
								}
								
								//计算暴击，暴击伤害增加50%，具有坚硬效果暴击也只有1点伤害
								if (FightUtils.isCri(this)) {
									if (!hasStrongBuff) {
										damage = damage * (FightUtils.CRI_DMG_ENHANCE + initCriDmgEnhance);
									}
									finalDmg = (int)(damage);
									target.reduceHp(finalDmg, true);
									hitType = FightTargetInfo.TYPE_CRI;
								} else {
									finalDmg = (int)damage;
									target.reduceHp(finalDmg, true);
									hitType = FightTargetInfo.TYPE_HIT;
								}
								
								//如果命中并且是职业特性攻击，加入职业特性击退效果
								if (isFeature && featureBack > 0) {
									target.addBuff(SkillBuffTypeEnum.SPECIAL_BACK,  new FightBuff(-1,
											SkillBuffTypeEnum.SPECIAL_BACK, featureBack, currentRound, false));
								}
							}
							
							//将目标信息加入目标队列
							if (isLockSkill) {
								//先计算击退距离
								int totalBack = target.getBackDistance(false);
								
								//如果击退则设置最终距离，并且打断目标技能
								if (totalBack > 0) {
									if (target.isAttack) {
										target.curPos = Math.min(FightUtils.flipPosition(0), target.curPos + totalBack);
									} else {
										target.curPos = Math.max(0, target.curPos - totalBack);
									}
									target.breakCurrentSkill(true, currentRound);
								}
								
								lockTargets.add(new FightLockTargetInfo(target.unitIndex, hitType,
										finalDmg, !target.isAlive(), totalBack, target.curPos));
							} else {
								unitTargets.add(new FightTargetInfo(target.unitIndex, hitType, finalDmg));
							}
							
							//重新计算目标数值
							if (beforeEffects.size() > 0) {
								target.recountProperties(!isLockSkill);
							}
						}
						
						//重新计算目标数值
						if (beforeEffects.size() > 0) {
							recountProperties(!isLockSkill);
						}
					} else {
						//没有攻击，也将目标加入目标队列以免没有被击动作
						for (FightUnit target : currentTargets.values()) {
							unitTargets.add(new FightTargetInfo(target.unitIndex, FightTargetInfo.TYPE_HIT, 0));
						}
					}
					
					//职业特性技能攻击后移除职业特性buff和效果
					if (isFeature) {
						//移除buff并产生动作
						for (IFightBuff buff : featureBuffs) {
							infos.add(new FightBuffInfo(unitIndex, buff.getBuffSn(), FightBuffInfo.TYPE_REMOVE, -1, -1, buff.getBuffId()));
						}
						
						//移除buff对象和值
						featureBuffs.clear();
						featureValues.clear();
						
						//重新计算数值
						recountProperties(!isLockSkill);
					}
					
					//使用攻击后效果
					if (afterEffects.size() > 0) {
						for (RealSkillEffect effect : afterEffects) {
							//根据效果目标类型进行处理
							if (effect.isToTarget()) {
								doRealEffect(effect, field, currentTargets.values(), infos, false, false, areaInfos);
							} else {
								doRealEffect(effect, field, this, infos, false, false, areaInfos);
							}
						}
						
						//重新计算双方数值
						recountProperties(!isLockSkill);
						for (FightUnit target : currentTargets.values()) {
							if (!isLockSkill || target.isAlive()) {
								target.recountProperties(!isLockSkill);
							}
						}
					}
					
					//创建动作并加入动作序列中
					if (isLockSkill) {
						FightLockAction action = new FightLockAction(unitIndex, currentActiveSkill.getSkillSn(),
								currentTargetIndex, ActionType.LOCK_ATTACK.getId());
						action.setTargets(lockTargets.toArray(new FightLockTargetInfo[0]));
						action.setBuffers(infos.toArray(new FightBuffInfo[0]));
						lockActions.add(action);
					} else {
						//如果是技能使用后第一回合则动作类型为技能使用，否则为技能生效
						ActionType actionType = (nowRound == 1) ? ActionType.ATTACK : ActionType.SKILL_EFFECT;
						FightUnitAction action = unitActions.get(unitIndex);
						if (action != null) {
							action.updateActionType(actionType);
							action.setSkillSn(currentActiveSkill.getSkillSn());
							action.setSkillTarget(currentTargetIndex);
						} else {
							action = new FightUnitAction(unitIndex, currentActiveSkill.getSkillSn(),
									currentTargetIndex, actionType.getId());
							unitActions.put(unitIndex, action);
						}
						action.addTargets(unitTargets);
						action.addBuffers(infos);
					}
				}
			}
		}
	}
	
	/**
	 * 获取单元击退距离，计算后移除击退效果，boss效果能被击退
	 * @param checkNormal	是否检查普通击退效果
	 * @return
	 */
	private int getBackDistance(boolean checkNormal) {
		//总击退距离
		int backDistance = 0;
		
		//首先检查普通击退距离，之后一定要移除普通击退效果，障碍不会被击退，具有免疫击退效果也不能被击退
		if (!isBlock() && !buffValues.containsKey(SkillBuffTypeEnum.ANTI_BACK_BUFF)) {
			if (checkNormal && buffValues.containsKey(SkillBuffTypeEnum.NORMAL_BACK)) {
				backDistance = buffValues.get(SkillBuffTypeEnum.NORMAL_BACK);
			}
			
			//检查特殊击退，取所有击退类型中距离最大的一个效果，之后一定要移除特殊击退效果
			if (buffValues.containsKey(SkillBuffTypeEnum.SPECIAL_BACK)) {
				backDistance = Math.max(backDistance, buffValues.get(SkillBuffTypeEnum.SPECIAL_BACK));
			}
		}
		
		//重置击退距离
		buffValues.remove(SkillBuffTypeEnum.NORMAL_BACK);
		buffValues.remove(SkillBuffTypeEnum.SPECIAL_BACK);
		
		//返回击退距离
		backDistance = Math.max(backDistance, 0);
		return backDistance;
	}
	
	/**
	 * 获取职业特性击退距离
	 * @return
	 */
	private int getFeatureBackDistance() {
		//总击退距离
		int backDistance = 0;
				
		//设置职业特性击退距离，障碍不会被击退，具有免疫击退效果也不能被击退
		if (!isBlock() && !buffValues.containsKey(SkillBuffTypeEnum.ANTI_BACK_BUFF)
				&& featureValues.containsKey(SkillBuffTypeEnum.SPECIAL_BACK)) {
			backDistance = featureValues.get(SkillBuffTypeEnum.SPECIAL_BACK);
		}
		
		//重置击退距离
		featureValues.remove(SkillBuffTypeEnum.SPECIAL_BACK);
		
		//返回击退距离
		return backDistance;
	}
	
	/**
	 * 判断当前技能在使用后的指定回合是否存在技能效果，如果存在技能效果将效果按照攻击前和攻击后两部分分开
	 * @param round
	 * @param beforeEffects
	 * @param afterEffects
	 * @param areaEffects
	 * @return
	 */
	private boolean findSkillEffect(int round, List<RealSkillEffect> beforeEffects,
			List<RealSkillEffect> afterEffects, List<RealSkillEffect> areaEffects) {
		//遍历技能全部效果，如果存在当前回合触发的效果无论是否成功都认为技能生效。计算触发几率，如果效果触发则按照攻击前后顺序加入相应的队列中
		boolean foundEffect = false;
		for (RealSkillEffect effect : currentActiveSkill.getRealEffects()) {
			if (round < 0 || effect.getEffectRound() == round) {
				//存在当前回合的效果，设置寻找成功标志
				foundEffect = true;
				
				//仅攻击类型效果不处理
				if (effect.getBuffType() != SkillBuffTypeEnum.NONE) {
					//将区域buff单独提出，其他类型按照攻击前后分开保存
					if (effect.getIsAreaBuff() <= 0) {
						if (effect.isBeforeAttack()) {
							beforeEffects.add(effect);
						} else {
							afterEffects.add(effect);
						}
					} else {
						areaEffects.add(effect);
					}
				}
			}
		}
		
		//返回寻找结果
		return foundEffect;
	}
	
	/**
	 * 触发技能实际效果
	 * @param effect
	 * @param field
	 * @param targets
	 * @param infos
	 * @param isBeforeEffect
	 * @param isFeatureEffect
	 */
	private void doRealEffect(RealSkillEffect effect, BaseField field, Collection<FightUnit> targets,
			List<FightBuffInfo> infos, boolean isBeforeEffect, boolean isFeatureEffect,
			List<FightBuffInfo> areaInfos) {
		for (FightUnit target : targets) {
			doRealEffect(effect, field, target, infos, isBeforeEffect, isFeatureEffect, areaInfos);
		}
	}
	
	/**
	 * 触发技能实际效果
	 * @param effect
	 * @param field
	 * @param targets
	 * @param infos
	 * @param isBeforeEffect
	 * @param isFeatureEffect
	 */
	public void doRealEffect(RealSkillEffect effect, BaseField field, FightUnit target,
			List<FightBuffInfo> infos, boolean isBeforeEffect, boolean isFeatureEffect,
			List<FightBuffInfo> areaInfos) {
		//判断效果触发几率，如果是职业特性击退效果则根据当前buff提高触发几率
		SkillBuffTypeEnum buffType = effect.getBuffType();
		if (isFeatureEffect && buffType == SkillBuffTypeEnum.SPECIAL_BACK
				&& buffValues.containsKey(SkillBuffTypeEnum.FEATURE_BACK_RATE_BUFF)) {
			if (!effect.canEffectChance(buffValues.get(SkillBuffTypeEnum.FEATURE_BACK_RATE_BUFF))) {
				return;
			}
		} else {
			if (!effect.canEffectChance()) {
				return;
			}
		}
		
		//获取基本信息
		int currentRound = field.getCurrentRound();
		int delayRound = effect.getDelayRound();
		SkillEffectTypeEnum effectType = effect.getBuffType().getEffect();
		int buffValue = effect.getBuffValue();
		boolean canRemove = effect.isCanRemove();
		int endRound = currentRound + effect.getTotalRound() - 1;
		int buffSn = effect.getBuffSn();
		int isAreaEffect = effect.getIsAreaBuff();
		int areaSn = effect.getAreaBuffSn();
		int delayStart = currentRound + delayRound - 1;
		
		//根据效果类型和buff类型进行不同的处理
		switch (effectType) {
		case BUFF_DEBUFF:
			//首先确定是否为区域buff
			if (isAreaEffect <= 0) {
				//非区域buff，产生buff、debuff
				if (isBeforeEffect) {
					//攻击前效果加入对象临时buff队列中，不产生动作，如果是延迟类型buff不做处理
					if (delayRound <= 0) {
						target.addTempBuff(buffType, new FightBuff(buffSn, buffType, buffValue, endRound, canRemove));
					}
				} else {
					//如果是延迟类型的buff加入延迟队列，否则加入持续buff队列并产生动作
					if (delayRound > 0) {
						target.addDelayBuff(new FightBuffDelay(buffSn, buffType, buffValue,
								delayStart, canRemove, delayStart));
					} else {
						IFightBuff buff = new FightBuff(buffSn, buffType, buffValue, endRound, canRemove);
						FightBuffInfo info = null;
						if (isFeatureEffect) {
							info = target.addFeatureBuff(buffType, buff);
						} else {
							info = target.addBuff(buffType, buff);
						}
						if (info != null) {
							infos.add(info);
							field.addBuffResource(buffSn);
						}
					}
				}
			} else {
				//区域buff，根据类型判断属于哪个区域，1为前场，2为中场，3为后场
				switch (isAreaEffect) {
				case 1:
					//前场区域buff，加入buff并产生动作
					if (target.isAttack) {
						areaInfos.add(field.addAreaBuff(new FightArea(buffSn, buffType, buffValue,
								endRound, canRemove, FightUtils.FrontAreaPosition, target.isAttack, areaSn)));
					} else {
						areaInfos.add(field.addAreaBuff(new FightArea(buffSn, buffType, buffValue,
								endRound, canRemove, FightUtils.RearAreaPosition, target.isAttack, areaSn)));
					}
					break;
					
				case 2:
					//中场区域buff，加入buff并产生动作
					areaInfos.add(field.addAreaBuff(new FightArea(buffSn, buffType, buffValue,
							endRound, canRemove, FightUtils.CenterAreaPosition, target.isAttack, areaSn)));
					break;
					
				case 3:
					//后场区域buff，加入buff并产生动作
					if (target.isAttack) {
						areaInfos.add(field.addAreaBuff(new FightArea(buffSn, buffType, buffValue,
								endRound, canRemove, FightUtils.RearAreaPosition, target.isAttack, areaSn)));
					} else {
						areaInfos.add(field.addAreaBuff(new FightArea(buffSn, buffType, buffValue,
								endRound, canRemove, FightUtils.FrontAreaPosition, target.isAttack, areaSn)));
					}
					break;
				}
			}
			break;
			
		case HOT_DOT:
			//产生hot、dot，如果是在攻击前不做处理
			if (!isBeforeEffect) {
				//加入hot、dot队列并产生动作，处理特殊类型
				if (buffType == SkillBuffTypeEnum.ATK_RATE_HOT) {
					//根据施法者攻击力百分比计算回血效果
					int value = (int)(atk * (double)buffValue / 1000D);
					infos.add(target.addHotDot(buffType, new FightHot(buffSn, SkillBuffTypeEnum.HOT,
							value, endRound, canRemove, effect.getInterval())));
				} else {
					//默认类型，直接按照配置的数值计算hot、dot效果
					infos.add(target.addHotDot(buffType, new FightHot(buffSn, buffType,
							buffValue, endRound, canRemove, effect.getInterval())));
				}
				field.addBuffResource(buffSn);
			}
			break;
			
		case FUNCTION:
			//特殊功能类技能，根据技能类型进行处理
			switch(buffType) {
			case NORMAL_BACK:
				//普通击退，加入buff但不产生动作
				if (!target.isBlock()) {
					//首先判断是否为延迟buff
					if (delayRound > 0) {
						target.addDelayBuff(new FightBuffDelay(buffSn, buffType, buffValue,
								delayStart, canRemove, delayStart));
					} else {
						target.addBuff(buffType, new FightBuff(buffSn, buffType, buffValue, endRound, canRemove));
					}
				}
				break;
				
			case SPECIAL_BACK:
				//特殊击退，加入buff但不产生动作，职业特性也可以产生特殊击退
				if (!target.isBlock()) {
					if (isFeatureEffect) {
						target.addFeatureBuff(buffType, new FightBuff(buffSn, buffType, buffValue, endRound, canRemove));
					} else {
						//首先判断是否为延迟buff
						if (delayRound > 0) {
							target.addDelayBuff(new FightBuffDelay(buffSn, buffType, buffValue,
									delayStart, canRemove, delayStart));
						} else {
							target.addBuff(buffType, new FightBuff(buffSn, buffType, buffValue, endRound, canRemove));
						}
					}
				}
				break;
				
			case ADD_BLOCK:
				//加入障碍，从怪物配置中获取障碍配置构造障碍对象，buff值为怪物编号
				MonsterTemplate blockTemplate = Globals.getMonsterService().getMonstersByMonsterSn(Integer.toString(buffValue));
				if (blockTemplate != null) {
					//构造怪物对象，计算目标位置，加入战场目标位置前
					Monster block = new Monster(blockTemplate, Globals.getMonsterService());
					block.init();
					
					//根据间隔回合参数确定目标位置（1为目标身前，2为前场，3为中场，4为后场）
					int initLine = target.unitLine;
					int targetPos = target.curPos;
					switch (effect.getInterval()) {
					case 1:	//目标身前，根据目标位置和所属方确定位置
						if (target.isAttack) {
							targetPos = Math.max(targetPos - FightUtils.MinBlockDistance, 0);
						} else {
							targetPos = Math.min(targetPos + FightUtils.MinBlockDistance, FightUtils.flipPosition(0));
						}
						break;
						
					case 2:	//前场，根据目标所属方确定位置
						if (target.isAttack) {
							targetPos = FightUtils.FrontAreaPosition;
						} else {
							targetPos = FightUtils.RearAreaPosition;
						}
						break;
						
					case 3:	//中场
						targetPos = FightUtils.CenterAreaPosition;
						break;
						
					case 4:	//后场
						if (target.isAttack) {
							targetPos = FightUtils.RearAreaPosition;
						} else {
							targetPos = FightUtils.FrontAreaPosition;
						}
						break;
						
					case 5: //特殊位置，对于进攻方来说目标位置
						if (target.isAttack) {
							targetPos = FightUtils.BlockPosition;
						} else {
							targetPos = FightUtils.flipPosition(FightUtils.BlockPosition) + 1;
						}
						break;
						
					case 6:	//自己身前，己方栅栏
						if (target.isAttack) {
							targetPos = targetPos - FightUtils.BlockInitDistance;
						} else {
							targetPos = targetPos + FightUtils.BlockInitDistance;
						}
						break;
					}

					//将障碍加入战场指定位置
					field.addFightUnit(block, 0, initLine * FightUtils.MaxLines, initLine, targetPos,
							isAttack, true, RoleTypes.MASHINE, false, unitIndex, true);
				}
				break;
				
			case ADD_MORALE:
				//增加目标士气
				target.addMorale(buffValue);
				break;
				
			case ADD_HP:
				//增加目标血量
				target.addHp(buffValue);
				break;
			}
			break;
		}
	}
	
	/**
	 * 增加士气，不超过最大士气
	 * @param value
	 */
	public void addMorale(int value) {
		if (value > 0) {
			curMorale = curMorale + value;
			if (curMorale > maxMorale) {
				curMorale = maxMorale;
			}
		}
	}
	
	/**
	 * 将buff加入临时列表，同类型效果叠加
	 * @param type
	 * @param buff
	 */
	private void addTempBuff(SkillBuffTypeEnum type, IFightBuff buff) {
		if (tempValues.containsKey(type)) {
			tempValues.put(type, tempValues.get(type) + buff.getValue());
		} else {
			tempValues.put(type, buff.getValue());
		}
	}
	
	/**
	 * 将buff加入职业特性列表，同类型buff效果叠加，返回动作
	 * @param type
	 * @param value
	 * @return
	 */
	private FightBuffInfo addFeatureBuff(SkillBuffTypeEnum type, IFightBuff buff) {
		featureBuffs.add(buff);
		if (featureValues.containsKey(type)) {
			featureValues.put(type, featureValues.get(type) + buff.getValue());
		} else {
			featureValues.put(type, buff.getValue());
		}
		return new FightBuffInfo(unitIndex, buff.getBuffSn(), FightBuffInfo.TYPE_ADD, -1, -1,  buff.getBuffId());
	}
	
	/**
	 * 将buff加入持续列表，同类型buff效果叠加，返回动作
	 * @param type
	 * @param buff
	 */
 	private FightBuffInfo addBuff(SkillBuffTypeEnum type, IFightBuff buff) {
 		//判断当前单元身上如果存在抵抗晕眩效果的buff则晕眩效果不能产生作用
 		if (type == SkillBuffTypeEnum.STUN_DEBUFF && buffValues.containsKey(SkillBuffTypeEnum.ANTI_STUN_BUFF)) {
 			return null;
 		}
 		
 		//将buff效果加入列表并产生动作
 		allBuffs.add(buff);
 		if (buffValues.containsKey(type)) {
 			buffValues.put(type, buffValues.get(type) + buff.getValue());
 		} else {
 			buffValues.put(type, buff.getValue());
 		}
		return new FightBuffInfo(unitIndex, buff.getBuffSn(), FightBuffInfo.TYPE_ADD, -1, -1, buff.getBuffId());
	}
 	
	/**
	 * 将buff加入延迟列表
	 * @param type
	 * @param buff
	 */
	private void addDelayBuff(IFightBuff buff) {
		delayBuffs.add(buff);
	}
	
	/**
	 * 加入新的hot或dot实例到队列中并产生动作
	 * @param hot
	 * @return
	 */
	private FightBuffInfo addHotDot(SkillBuffTypeEnum type, FightHot hot) {
		hotBuffs.add(hot);
		return new FightBuffInfo(unitIndex, type.getId(), FightBuffInfo.TYPE_ADD, -1, -1, hot.getBuffId());
	}
	
	/**
	 * 重新计算单元的战斗属性，包括buff的数值影响，计算后清空临时buff
	 * @param includeFeature
	 */
	private void recountProperties(boolean includeFeature) {
		//最大血量，无论何时都包括职业特性的效果
		double rate = 1D + (double)(getBuffValue(SkillBuffTypeEnum.MAX_HP_RATE_BUFF, true) -
				getBuffValue(SkillBuffTypeEnum.MAX_HP_RATE_DEBUFF, true)) / FightUtils.BuffRateBase;
		maxHp = (int)((double)initMaxHp * rate) + getBuffValue(SkillBuffTypeEnum.MAX_HP_BUFF, true) -
				getBuffValue(SkillBuffTypeEnum.MAX_HP_DEBUFF, true);
		
		//当前血量不超过最大血量
		if (curHp > maxHp) {
			curHp = maxHp;
		}
		
		//攻击
		rate = 1D + (double)(getBuffValue(SkillBuffTypeEnum.ATK_RATE_BUFF, includeFeature) -
				getBuffValue(SkillBuffTypeEnum.ATK_RATE_DEBUFF, includeFeature)) / FightUtils.BuffRateBase;
		atk = (int)((double)initAtk * rate) + getBuffValue(SkillBuffTypeEnum.ATK_BUFF, includeFeature) -
				getBuffValue(SkillBuffTypeEnum.ATK_DEBUFF, includeFeature);
		
		//防御
		rate = 1D + (double)(getBuffValue(SkillBuffTypeEnum.DEF_RATE_BUFF, includeFeature) -
				getBuffValue(SkillBuffTypeEnum.DEF_RATE_DEBUFF, includeFeature)) / FightUtils.BuffRateBase;
		def = (int)((double)initDef * rate) + getBuffValue(SkillBuffTypeEnum.DEF_BUFF, includeFeature) -
				getBuffValue(SkillBuffTypeEnum.DEF_DEBUFF, includeFeature);
		
		//速度，无论何时都包括职业特性的效果
		rate = 1D + (double)(getBuffValue(SkillBuffTypeEnum.SPD_RATE_BUFF, true) -
				getBuffValue(SkillBuffTypeEnum.SPD_RATE_DEBUFF, true)) / FightUtils.BuffRateBase;
		spd = (int)((double)initSpd * rate) + getBuffValue(SkillBuffTypeEnum.SPD_BUFF, true) -
				getBuffValue(SkillBuffTypeEnum.SPD_DEBUFF, true);
		
		//命中率
		hit = initHit + (double)(getBuffValue(SkillBuffTypeEnum.HIT_RATE_BUFF, includeFeature) -
				getBuffValue(SkillBuffTypeEnum.HIT_RATE_DEBUFF, includeFeature)) / FightUtils.BuffRateBase;
		
		//暴击率
		cri = initCri + (double)(getBuffValue(SkillBuffTypeEnum.CRI_RATE_BUFF, includeFeature) -
				getBuffValue(SkillBuffTypeEnum.CRI_RATE_DEBUFF, includeFeature)) / FightUtils.BuffRateBase;
		
		//闪避率
		dodge = initDodge + (double)(getBuffValue(SkillBuffTypeEnum.DODGE_RATE_BUFF, includeFeature) -
				getBuffValue(SkillBuffTypeEnum.DODGE_RATE_DEBUFF, includeFeature)) / FightUtils.BuffRateBase;
		
		//伤害加深率
		dmgEnhance = initDmgEnhance + (double)(getBuffValue(SkillBuffTypeEnum.DMG_ENHANCE_RATE_BUFF, includeFeature) -
				getBuffValue(SkillBuffTypeEnum.DMG_ENHANCE_RATE_DEBUFF, includeFeature)) / FightUtils.BuffRateBase;
		
		//伤害减免率
		dmgResist = initDmgResist + (double)(getBuffValue(SkillBuffTypeEnum.DMG_RESIST_RATE_BUFF, includeFeature) -
				getBuffValue(SkillBuffTypeEnum.DMG_RESIST_RATE_DEBUFF, includeFeature)) / FightUtils.BuffRateBase;
		
		//韧性
		renxing = initRenxing + (double)(getBuffValue(SkillBuffTypeEnum.RX_RATE_BUFF, includeFeature) -
				getBuffValue(SkillBuffTypeEnum.RX_RATE_DEBUFF, includeFeature)) / FightUtils.BuffRateBase;
		
		//清除临时buff
		tempValues.clear();
	}
	
	/**
	 * 从单元身上获取指定类型的buff累加值
	 * @param buffType
	 * @param includeFeature
	 * @return
	 */
	private int getBuffValue(SkillBuffTypeEnum buffType, boolean includeFeature) {
		int value = 0;
		//按照持续buff、临时buff、职业特性buff的顺序查找，命中率最优化
		if (buffValues.containsKey(buffType)) {
			value = buffValues.get(buffType);
		}
		if (tempValues.containsKey(buffType)) {
			value = value + tempValues.get(buffType);
		}
		if (areaValues.containsKey(buffType)) {
			value = value + areaValues.get(buffType);
		}
		if (includeFeature && featureValues.containsKey(buffType)) {
			value = value + featureValues.get(buffType);
		}
		return value;
	}
	
	/**
	 * 触发hot、dot
	 * @param currentRound
	 * @param actions
	 */
	public void doHot(int currentRound, Map<Integer, FightUnitAction> unitActions) {
		//遍历hot列表，判断是否存在可以触发的效果，进行触发
		List<FightBuffInfo> buffs = new ArrayList<FightBuffInfo>();
		for (IFightBuff hot : hotBuffs) {
			if (hot.isEffect(currentRound)) {
				//可以触发，根据hot类型处理血量变化
				switch (hot.getBuffType()) {
				case HOT:
					addHp(hot.getValue());
					buffs.add(new FightBuffInfo(unitIndex, hot.getBuffSn(), FightBuffInfo.TYPE_ADD, -1, -1, hot.getBuffId()));
					break;
					
				case DOT:
					reduceHp(hot.getValue(), false);
					buffs.add(new FightBuffInfo(unitIndex, hot.getBuffSn(), FightBuffInfo.TYPE_ADD, -1, -1, hot.getBuffId()));
					break;
				}
			}
		}
		
		//如果效果触发，创建或更新动作对象
		if (buffs.size() > 0) {
			//获取单元对应的动作对象，没有创建新对象
			FightUnitAction action = unitActions.get(unitIndex);
			if (action == null) {
				action = new FightUnitAction(unitIndex, -1, -1, ActionType.BUFF_INFO.getId());
				unitActions.put(unitIndex, action);
			} else {
				action.updateActionType(ActionType.BUFF_INFO);
			}
			action.addBuffers(buffs);
		}
		
	}
	
	/**
	 * 清空区域buff效果
	 */
	public void clearAreaValues() {
		areaValues.clear();
	}
	
	/**
	 * 加入区域buff值
	 * @param type
	 * @param value
	 */
	public void addAreaValues(SkillBuffTypeEnum type, int value) {
		if (areaValues.containsKey(type)) {
			areaValues.put(type, areaValues.get(type) + value);
 		} else {
 			areaValues.put(type, value);
 		}
	}
	
	/**
	 * 获取当前区域buff
	 * @return
	 */
	public Map<Integer, Integer> getAreaBuffs() {
		return areaBuffs;
	}
	
	/**
	 * 设置当前区域buff
	 * @param buffs
	 */
	public void setAreaBuffs(Map<Integer, Integer> buffs) {
		this.areaBuffs = buffs;
	}
	
	/**
	 * 回合结束进行结算，计算动作最终类型，移除失效的技能、buff，触发职业特性被动属性
	 * @param field
	 * @param unitActions
	 */
	public void doAccount(BaseField field, Map<Integer, FightUnitAction> unitActions, List<FightBuffInfo> areaInfos) {
		//获取当前回合数
		int currentRound = field.getCurrentRound();
		
		//创建或获取用户动作
		FightUnitAction action = unitActions.get(unitIndex);
		if (action == null) {
			action = new FightUnitAction(unitIndex, -1, -1, ActionType.NONE.getId());
			unitActions.put(unitIndex, action);
		}
		
		//判断延迟类buff是否生效，如果生效加入到持续buff列表中
		Iterator<IFightBuff> delayItor = delayBuffs.iterator();
		while (delayItor.hasNext()) {
			IFightBuff buff = delayItor.next();
			if (buff.isStart(currentRound)) {
				delayItor.remove();
				FightBuffInfo info = addBuff(buff.getBuffType(), buff);
				if (info != null) {
					action.updateActionType(ActionType.BUFF_INFO);
					action.addBuffer(info);
				}
			}
		}
		
		//判断本回合是否被攻击，如果没有被攻击则增加计数器，重置状态
		if (attacked) {
			//当前不处于技能过程中尝试更新状态为被击
			if (currentRound > skillCoolDown) {
				action.updateActionType(ActionType.ATTACKED);
			}
			
			//被攻击则重置计数器，重置被攻击标志
			if (featureSkill != null && isFeatureAvaliable() && featureSkill.getTriggerType() == SkillTriggerTypeEnum.NO_ATTACKED) {
				featureSkill.resetCounter();
			}
			attacked = false;
		} else {
			//未被攻击，更新连续未被攻击计数器并判断是否触发职业特性被动效果
			action.addBuffers(doPassiveFeatureSkill(field, SkillTriggerTypeEnum.NO_ATTACKED, 1, areaInfos));
		}
		
		//获取击退距离，如果当前状态是被击状态，则计算普通击退，障碍不会被击退
		int totalBack = getBackDistance(action.getActionType() == ActionType.ATTACKED.getId());
		
		//判断本回合是否存在移动，是前进还是后退，重置状态
		int distance = 0;
		if (isAttack) {
			//计算击退距离
			curPos = Math.min(FightUtils.flipPosition(0), curPos + totalBack);
			
			//进攻方坐标变小为前进，加入击退距离
			distance = curPos - lastPos;
			if (distance < 0) {
				//连续移动，更新连续移动计数器并判断是否触发职业特性被动效果
				action.updateActionType(ActionType.MOVE);
				action.addBuffers(doPassiveFeatureSkill(field, SkillTriggerTypeEnum.MOVE, 0 - distance, areaInfos));
			} else {
				//后退或停止移动，重置计数器，如果后退更新动作类型为后退并打断技能
				if (featureSkill != null && isFeatureAvaliable() && featureSkill.getTriggerType() == SkillTriggerTypeEnum.MOVE) {
					featureSkill.resetCounter();
				}
				if (distance > 0) {
					action.updateActionType(ActionType.BACK);
					breakCurrentSkill(true, currentRound);
				}
			}
		} else {
			//计算击退距离
			curPos = Math.max(0, curPos - totalBack);
			
			//防守方坐标变大为前进，加入击退距离
			distance = curPos - lastPos;
			if (distance > 0) {
				//连续移动，更新连续移动计数器并判断是否触发职业特性被动效果
				action.updateActionType(ActionType.MOVE);
				action.addBuffers(doPassiveFeatureSkill(field, SkillTriggerTypeEnum.MOVE, distance, areaInfos));
			} else {
				//后退或停止移动，重置计数器，如果后退更新动作类型为后退并打断技能
				if (featureSkill != null && featureSkill.getTriggerType() == SkillTriggerTypeEnum.MOVE) {
					featureSkill.resetCounter();
				}
				if (distance < 0) {
					action.updateActionType(ActionType.BACK);
					breakCurrentSkill(true, currentRound);
				}
			}
		}
		
		//移除buff列表中失效的效果，移除效果值，并产生动作
		Iterator<IFightBuff> buffItor = allBuffs.iterator();
		while (buffItor.hasNext()) {
			IFightBuff buff = buffItor.next();
			if (buff.isEnd(currentRound)) {
				//移除buff产生动作
				buffItor.remove();
				action.updateActionType(ActionType.BUFF_INFO);
				action.addBuffer(new FightBuffInfo(unitIndex, buff.getBuffSn(), FightBuffInfo.TYPE_REMOVE, -1, -1, buff.getBuffId()));
				
				//移除实际数值，如果此类型效果全部移除则直接将entry删除，击退效果不处理数值变化
				SkillBuffTypeEnum type = buff.getBuffType();
				if (type != SkillBuffTypeEnum.NORMAL_BACK && type != SkillBuffTypeEnum.SPECIAL_BACK) {
					if (buffValues.containsKey(type)) {
						int newValue = buffValues.get(type) - buff.getValue();
						if (newValue > 0) {
							buffValues.put(type, newValue);
						} else {
							buffValues.remove(type);
						}
					}
				}
			}
		}
		
		//移除hot列表中失效的效果，并产生动作
		Iterator<IFightBuff> hotItor = hotBuffs.iterator();
		while (hotItor.hasNext()) {
			IFightBuff buff = hotItor.next();
			if (buff.isEffect(currentRound)) {
				hotItor.remove();
				action.updateActionType(ActionType.BUFF_INFO);
				action.addBuffer(new FightBuffInfo(unitIndex, buff.getBuffSn(), FightBuffInfo.TYPE_REMOVE, -1, -1, buff.getBuffId()));
			}
		}
		
		//设置动作其他信息
		action.setCurHp(curHp);
		action.setCurMorale(curMorale);
		action.setCurPos(lastPos);
		action.setSpd(distance);
		
		//重新计算数值
		recountProperties(false);
		
		//重置位置
		lastPos = curPos;
		
		//士气增加，不超过最大士气上限，武将技已经使用后不再增加士气
		if (!specialSkillUsed && curMorale < maxMorale) {
			curMorale ++;
		}
		
		//如果当前技能失效，重置当前技能信息
		if (currentRound >= skillCoolDown) {
			breakCurrentSkill(false, currentRound);
		}
	}
	
	/**
	 * 打断当前技能
	 */
	public void breakCurrentSkill(boolean isBack, int currentRound) {
		currentActiveSkill = null;
		currentTargetPos = -1;
		currentTargetIndex = -1;
		currentTargets.clear();
		
		//击退后立即重置技能冷却时间，行动冷却也停止
		if (isBack) {
			skillCoolDown = 0;
			actionCoolDown = 0;
//			actionCoolDown = currentRound + 1;
		}
	}
	
	/**
	 * 设置血量减少，并且设置是否被攻击
	 * @param lostHp
	 * @param setAttacked
	 */
	public void reduceHp(int lostHp, boolean setAttacked) {
		curHp = curHp - lostHp;
		attacked = attacked || setAttacked;
	}
	
	/**
	 * 设置血量增加
	 * @param addHp
	 */
	public void addHp(int addHp) {
		curHp = curHp + addHp;
		if (curHp > maxHp) {
			curHp = maxHp;
		}
	}
	
	/**
	 * 判断战斗单元是否存活，即当前HP是否大于0
	 * @return
	 */
	public boolean isAlive() {
		return curHp > 0;
	}
	
	/**
	 * 判断当前回合是否可以行动，当不处于晕眩状态并且不在行动冷却中才可以行动，boss效果免疫晕眩
	 * @param currentRound
	 * @return
	 */
	public boolean canDoSkill(int currentRound) {
		return currentRound > actionCoolDown && !isStun();
	}
	
	/**
	 * 当前不处于晕眩，并且不在行动冷却时间内时可以尝试并使用攻击
	 * @param currentRound
	 * @return
	 */
	public boolean canMove(int currentRound) {
		return currentRound > actionCoolDown && !isStun();
	}
	
	/**
	 * 判断是否处于晕眩状态中，如果具有免疫晕眩效果则不能晕眩
	 * @return
	 */
	public boolean isStun() {
		return (buffValues.containsKey(SkillBuffTypeEnum.STUN_DEBUFF) &&
				!buffValues.containsValue(SkillBuffTypeEnum.ANTI_STUN_BUFF));
	}
	
	/**
	 * 判断职业特性技能是否可以使用并且可以产生效果
	 * @return
	 */
	public boolean isFeatureAvaliable() {
		return buffValues.containsKey(SkillBuffTypeEnum.FEATURE_AVAILABLE_BUFF);
	}
	
	/**
	 * 构造战斗单元信息对象
	 * @return
	 */
	public FightRoleInfo getRoleInfo(boolean isVisible) {
		return new FightRoleInfo(unitSn, unitName, skeletonSn, curMorale,
				unitLine, curPos, formPos, curHp, curHp, maxHp,
				isAttack, isRemote, unitIndex, unitType, unitLevel, unitStar,
				unitGender, attackedAnim, castAnim, vocationAnim, isVisible,
				maxMorale, skillMorale, belongs);
	}
	
	/**
	 * 获取战斗单元是否为障碍
	 * @return
	 */
	public boolean isBlock() {
		return unitType == RoleTypes.MASHINE;
	}
	
	/*
	 * Getter & Setter
	 */
	public int getCurHp() {
		return curHp;
	}
	
	public int getUnitLine() {
		return unitLine;
	}
	
	public void setUnitLine(int unitLine) {
		this.unitLine = unitLine;
	}
	
	public String getUnitSn() {
		return unitSn;
	}
	
	public int getUnitIndex() {
		return unitIndex;
	}
	
	public VocationType getVocation() {
		return vocation;
	}

	public int getCurPos() {
		return curPos;
	}
	
	public void setCurPos(int curPos) {
		this.curPos = curPos;
	}
	
	public int getLastPos() {
		return lastPos;
	}
	
	public void setLastPos(int lastPos) {
		this.lastPos = lastPos;
	}
	
	public boolean isRemote() {
		return isRemote;
	}
	
	public boolean isAttack() {
		return isAttack;
	}
	
	public int getUnitLevel() {
		return unitLevel;
	}

	public ActiveFightSkill getVocationSkill() {
		return vocationSkill;
	}

	public ActiveFightSkill getSpecialSkill() {
		return specialSkill;
	}
	
	public int getSpd() {
		return spd;
	}

	public double getAtk() {
		return atk;
	}

	public double getDef() {
		return def;
	}

	public double getHit() {
		return hit;
	}

	public double getCri() {
		return cri;
	}

	public double getDodge() {
		return dodge;
	}

	public double getDmgEnhance() {
		return dmgEnhance;
	}

	public double getDmgResist() {
		return dmgResist;
	}
	
	public double getRenxing() {
		return renxing;
	}

	/**
	 * 根据单元编号判断两个单元是否相等
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj != null && obj instanceof FightUnit) {
			FightUnit tarObj = (FightUnit)obj;
			return unitIndex == tarObj.unitIndex;
		}
		return false;
	}

	/**
	 * 根据角色创建战斗单位对象
	 * @param role
	 * @param initOrder
	 * @param formPos
	 * @param isAttack
	 * @param isInitFullHp
	 * @return
	 */
	public static FightUnit initFromRole(Role role, int unitIndex, int initOrder, int formPos,
			boolean isAttack, boolean isInitFullHp, boolean attackDelay, int RoleType, int belongs) {
		//创建战斗单位，设置战斗单元战斗数值属性
		FightUnit unit = new FightUnit();
		unit.initMaxHp = (int)role.getMaxHp();
		if (isInitFullHp) {
			unit.curHp = unit.initMaxHp;
		} else {
			//设置用户当前血量，如果当前血量在0与1之间则设置为1
			unit.curHp = (role.getCurHp() > 0D && role.getCurHp() < 1D) ? 1 : (int)role.getCurHp();
		}
		unit.initSpd = (int)role.getSpd();
		unit.initAtk = role.getAtk();
		unit.initDef = role.getDef();
		unit.initCri = role.getCri();
		unit.initDodge = role.getShanbi();
		unit.initDmgEnhance = role.getShanghai();
		unit.initDmgResist = role.getMianshang();
		unit.initHit = role.getMingzhong();
		unit.initRenxing = role.getRenxing();
		unit.initCriDmgEnhance = role.getCriShanghai();
		
		//战斗单元基本属性
		unit.unitSn = role.getUUID();
		unit.unitName = role.getName();
		unit.unitIndex = unitIndex;
		unit.unitLevel = role.getLevel();
		unit.unitGender = role.getSex().getCode();
		unit.unitStar = role.getLevel();	//TODO 实现用户星级
		unit.vocation = role.getVocationType();
		unit.unitType = RoleType;
		unit.isAttack = isAttack;
		unit.formPos = formPos;
		unit.isRemote = (unit.vocation == VocationType.MOUSHI || unit.vocation == VocationType.SHESHOU);
		unit.attackerStiff = role.getCooldownRound();
		unit.attackDelay = attackDelay;
		
		//获取角色受击和弹道动画
		unit.attackedAnim = role.getAttackedAnim();
		unit.castAnim = role.getCastAnim();
		unit.vocationAnim = role.getVocationAnim();
		
		//障碍没有技能
		if (!unit.isBlock()) {
			//设置职业普通技能
			SkillTemplate vocationTemplate = Globals.getSkillService().getVocationSkill(unit.vocation);
			unit.vocationSkill = ActiveFightSkill.buildSkill(vocationTemplate);
			
			//设置职业特性技能
			SkillSpecTemplate featureTemplate = Globals.getSkillService().getFeatureSkill(unit.vocation);
			unit.featureSkill = FeatureFightSkill.buildSkill(featureTemplate);
		}
		
		//根据角色类型不同设置骨骼和武将技
		switch (RoleType) {
		case RoleTypes.HUMAN:	//玩家
			unit.skeletonSn = new StringBuilder().append(role.getSex().getCode()).append(unit.vocation.getCode()).toString();
			
			//设置武将技和被动技
			RoleToSkillTemplate hrst =  Globals.getHumanService().getRoleToSkillTemplate(unit.vocation);
			if (hrst != null) {
				unit.specialSkill = ActiveFightSkill.buildSkill(Globals.getSkillService().getSpecialSkill(hrst.getPetSkill(), 1));
				
				SkillPasvTemplate pasvTemplate = Globals.getSkillService().getPassiveSkill(hrst.getPassiveSkill1(), 1);
				if (pasvTemplate != null) {
					unit.passiveSkills.add(PassiveFightSkill.buildSkill(pasvTemplate));
				}
				pasvTemplate = Globals.getSkillService().getPassiveSkill(hrst.getPassiveSkill2(), 1);
				if (pasvTemplate != null) {
					unit.passiveSkills.add(PassiveFightSkill.buildSkill(pasvTemplate));
				}
				pasvTemplate = Globals.getSkillService().getPassiveSkill(hrst.getPassiveSkill3(), 1);
				if (pasvTemplate != null) {
					unit.passiveSkills.add(PassiveFightSkill.buildSkill(pasvTemplate));
				}
				pasvTemplate = Globals.getSkillService().getPassiveSkill(hrst.getPassiveSkill4(), 1);
				if (pasvTemplate != null) {
					unit.passiveSkills.add(PassiveFightSkill.buildSkill(pasvTemplate));
				}
				pasvTemplate = Globals.getSkillService().getPassiveSkill(hrst.getPassiveSkill5(), 1);
				if (pasvTemplate != null) {
					unit.passiveSkills.add(PassiveFightSkill.buildSkill(pasvTemplate));
				}
				pasvTemplate = Globals.getSkillService().getPassiveSkill(hrst.getPassiveSkill6(), 1);
				if (pasvTemplate != null) {
					unit.passiveSkills.add(PassiveFightSkill.buildSkill(pasvTemplate));
				}
			}
			break;
			
		case RoleTypes.PET:		//武将
			Pet pet = (Pet)role;
			unit.skeletonSn = pet.getSkeletonId();
			RoleToSkillTemplate prst = pet.getSkillTemplate();
			if (prst != null) {
				unit.specialSkill = ActiveFightSkill.buildSkill(Globals.getSkillService().getSpecialSkill(prst.getPetSkill(), 1));
				
				SkillPasvTemplate pasvTemplate = Globals.getSkillService().getPassiveSkill(prst.getPassiveSkill1(), 1);
				if (pasvTemplate != null) {
					unit.passiveSkills.add(PassiveFightSkill.buildSkill(pasvTemplate));
				}
				pasvTemplate = Globals.getSkillService().getPassiveSkill(prst.getPassiveSkill2(), 1);
				if (pasvTemplate != null) {
					unit.passiveSkills.add(PassiveFightSkill.buildSkill(pasvTemplate));
				}
				pasvTemplate = Globals.getSkillService().getPassiveSkill(prst.getPassiveSkill3(), 1);
				if (pasvTemplate != null) {
					unit.passiveSkills.add(PassiveFightSkill.buildSkill(pasvTemplate));
				}
				pasvTemplate = Globals.getSkillService().getPassiveSkill(prst.getPassiveSkill4(), 1);
				if (pasvTemplate != null) {
					unit.passiveSkills.add(PassiveFightSkill.buildSkill(pasvTemplate));
				}
				pasvTemplate = Globals.getSkillService().getPassiveSkill(prst.getPassiveSkill5(), 1);
				if (pasvTemplate != null) {
					unit.passiveSkills.add(PassiveFightSkill.buildSkill(pasvTemplate));
				}
				pasvTemplate = Globals.getSkillService().getPassiveSkill(prst.getPassiveSkill6(), 1);
				if (pasvTemplate != null) {
					unit.passiveSkills.add(PassiveFightSkill.buildSkill(pasvTemplate));
				}
			}
			break;
			
		case RoleTypes.MONSTER:		//怪物
			Monster monster = (Monster)role;
			unit.skeletonSn = monster.getSkeletonId();
			unit.specialSkill = ActiveFightSkill.buildSkill(Globals.getSkillService().getSpecialSkill(monster.getPetSkill(), 1));
			
			//怪物根据类型判断是否需要修改为武将类型，1表示武将
			if (monster.getMonRoleType() == 1) {
				unit.unitType = RoleTypes.PET;
			}
			
			//增加怪物被动技能
			SkillPasvTemplate pasvTemplate = Globals.getSkillService().getPassiveSkill(monster.getPassiveSkill1(), 1);
			if (pasvTemplate != null) {
				unit.passiveSkills.add(PassiveFightSkill.buildSkill(pasvTemplate));
			}
			pasvTemplate = Globals.getSkillService().getPassiveSkill(monster.getPassiveSkill2(), 1);
			if (pasvTemplate != null) {
				unit.passiveSkills.add(PassiveFightSkill.buildSkill(pasvTemplate));
			}
			pasvTemplate = Globals.getSkillService().getPassiveSkill(monster.getPassiveSkill3(), 1);
			if (pasvTemplate != null) {
				unit.passiveSkills.add(PassiveFightSkill.buildSkill(pasvTemplate));
			}
			pasvTemplate = Globals.getSkillService().getPassiveSkill(monster.getPassiveSkill4(), 1);
			if (pasvTemplate != null) {
				unit.passiveSkills.add(PassiveFightSkill.buildSkill(pasvTemplate));
			}
			pasvTemplate = Globals.getSkillService().getPassiveSkill(monster.getPassiveSkill5(), 1);
			if (pasvTemplate != null) {
				unit.passiveSkills.add(PassiveFightSkill.buildSkill(pasvTemplate));
			}
			pasvTemplate = Globals.getSkillService().getPassiveSkill(monster.getPassiveSkill6(), 1);
			if (pasvTemplate != null) {
				unit.passiveSkills.add(PassiveFightSkill.buildSkill(pasvTemplate));
			}
			
			break;
			
		case RoleTypes.MASHINE:		//障碍，其实是怪物的一种，具有被动技能
			Monster mashine = (Monster)role;
			unit.skeletonSn = mashine.getSkeletonId();
			
			//增加怪物被动技能
			SkillPasvTemplate msTemplate = Globals.getSkillService().getPassiveSkill(mashine.getPassiveSkill1(), 1);
			if (msTemplate != null) {
				unit.passiveSkills.add(PassiveFightSkill.buildSkill(msTemplate));
			}
			msTemplate = Globals.getSkillService().getPassiveSkill(mashine.getPassiveSkill2(), 1);
			if (msTemplate != null) {
				unit.passiveSkills.add(PassiveFightSkill.buildSkill(msTemplate));
			}
			msTemplate = Globals.getSkillService().getPassiveSkill(mashine.getPassiveSkill3(), 1);
			if (msTemplate != null) {
				unit.passiveSkills.add(PassiveFightSkill.buildSkill(msTemplate));
			}
			msTemplate = Globals.getSkillService().getPassiveSkill(mashine.getPassiveSkill4(), 1);
			if (msTemplate != null) {
				unit.passiveSkills.add(PassiveFightSkill.buildSkill(msTemplate));
			}
			msTemplate = Globals.getSkillService().getPassiveSkill(mashine.getPassiveSkill5(), 1);
			if (msTemplate != null) {
				unit.passiveSkills.add(PassiveFightSkill.buildSkill(msTemplate));
			}
			msTemplate = Globals.getSkillService().getPassiveSkill(mashine.getPassiveSkill6(), 1);
			if (msTemplate != null) {
				unit.passiveSkills.add(PassiveFightSkill.buildSkill(msTemplate));
			}
			
			break;
		}
		
		//设置战斗中变化的属性
		unit.skillMorale = (unit.specialSkill == null) ? -1 : unit.specialSkill.getMoraleCost();
		unit.maxMorale = role.getMaxMorale();
		unit.curMorale = Math.min(40, unit.maxMorale);
		unit.curPos = 0;
		unit.lastPos = 0;
		unit.actionCoolDown = initOrder;	//初始行动冷却根据阵型位置设置
		unit.currentActiveSkill = null;
		unit.currentTargetPos = -1;
		unit.currentTargetIndex = -1;
		unit.attacked = false;
		unit.belongs = belongs;
		
		//重新计算属性
		unit.recountProperties(false);
		
		//返回战斗单元对象
		return unit;
	}
	
}
