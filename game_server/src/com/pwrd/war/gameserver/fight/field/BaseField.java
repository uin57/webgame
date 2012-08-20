package com.pwrd.war.gameserver.fight.field;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.pwrd.war.common.constants.Loggers;
import com.pwrd.war.core.async.IIoOperation;
import com.pwrd.war.core.util.StringUtils;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.fight.FightUtils;
import com.pwrd.war.gameserver.fight.buff.FightArea;
import com.pwrd.war.gameserver.fight.domain.FightAction;
import com.pwrd.war.gameserver.fight.domain.FightBuffInfo;
import com.pwrd.war.gameserver.fight.domain.FightChangeLineAction;
import com.pwrd.war.gameserver.fight.domain.FightLockAction;
import com.pwrd.war.gameserver.fight.domain.FightLockTargetInfo;
import com.pwrd.war.gameserver.fight.domain.FightLostItem;
import com.pwrd.war.gameserver.fight.domain.FightRoleInfo;
import com.pwrd.war.gameserver.fight.domain.FightTargetInfo;
import com.pwrd.war.gameserver.fight.domain.FightUnitAction;
import com.pwrd.war.gameserver.fight.enums.ActionType;
import com.pwrd.war.gameserver.fight.field.unit.FightLine;
import com.pwrd.war.gameserver.fight.field.unit.FightTeam;
import com.pwrd.war.gameserver.fight.field.unit.FightUnit;
import com.pwrd.war.gameserver.form.BattleForm;
import com.pwrd.war.gameserver.form.template.FormSnTemplate;
import com.pwrd.war.gameserver.human.Human;
import com.pwrd.war.gameserver.map.MapBattleBgService;
import com.pwrd.war.gameserver.role.Role;
import com.pwrd.war.gameserver.skill.effect.RealSkillEffect;
import com.pwrd.war.gameserver.skill.enums.SkillTriggerTypeEnum;

/**
 * 基础战场抽象类
 * @author zy
 *
 */
public abstract class BaseField implements IIoOperation {
	protected Map<Integer, FightLine> fightLines = new HashMap<Integer, FightLine>();	//分线
	protected FightTeam attTeam;	//进攻方队伍
	protected FightTeam defTeam;	//防守方队伍
	protected List<FightUnit> alives = new ArrayList<FightUnit>();		//双方全部存活单位，按照先攻值顺序排列
	protected List<FightUnit> attBlocks = new ArrayList<FightUnit>();	//进攻方障碍列表
	protected List<FightUnit> defBlocks = new ArrayList<FightUnit>();	//防守方障碍列表
	protected String sceneId;			//战斗场景id
	protected int currentRound = 1;		//当前回合数
	protected int unitIndexCounter = 1;		//单位编号计数器
	protected Map<String, FightRoleInfo> roleInfos = new HashMap<String, FightRoleInfo>();	//战斗单元信息
	protected List<FightAction> fightActions = new ArrayList<FightAction>();	//战斗动作列表
	protected Map<Integer, Integer> skillResources = new HashMap<Integer, Integer>();	//技能资源
	protected Map<Integer, Integer> buffResources = new HashMap<Integer, Integer>();	//buff资源
	protected List<FightArea> areaBuffs = new ArrayList<FightArea>();	//区域buff列表
	protected boolean isAttDelay;	//是否进攻方普通攻击延迟一回合
	protected long fightCost = 0L;	//本场战斗消耗的时间
	protected int expPrize = 0;		//经验奖励
	protected int moneyPrize = 0;	//金钱奖励
	protected int shengwangPrize = 0;	//声望奖励
	protected Map<String, FightLostItem> lostItems = new HashMap<String, FightLostItem>();	//物品奖励
	protected int npcId = Globals.getMapBattleBgService().getNpcIdByFuncId(MapBattleBgService.FUNC_DEFAULT);		//围观npc编号
	protected String bgId = Globals.getMapBattleBgService().getBattleBgByFuncId(MapBattleBgService.FUNC_DEFAULT);	//战斗背景id;
	
	private static long totalCost = 0L;
	private static long totalRounds = 0L;
	private static long totalFights = 0L;
	
	/**
	 * 构造战场，并加入进攻方
	 */
	public BaseField(Human attHuman, boolean attIsFullHp) {
		//防守方攻击延迟一回合
		isAttDelay = false;
		
		//设置战斗场景
		sceneId = attHuman.getSceneId();
		BattleForm form = attHuman.getDefaultForm();
		attTeam = new FightTeam(attHuman.getUUID(), String.valueOf(form.getFormSn()));
		
		//初始化分线
		for (int i = 0; i < FightUtils.MaxLines; i ++) {
			fightLines.put(i, new FightLine(i));
		}
		
		//根据用户阵型依次创建战斗单位
		String[] positions = form.getPositions();
		for (int i = 0; i < positions.length; i ++) {
			String petSn = positions[i];
			if (!StringUtils.isEmpty(petSn)) {
				Role role = attHuman.getRole(petSn);
				if (role != null) {
					//获取当前位置对应的阵型效果配置
					int position = i + 1;
					FormSnTemplate snTemplate = Globals.getFormService().getFormSnTemplate(position);
					
					//创建战斗单位并加入列表
					addFightUnit(role, snTemplate.getOrder(), position, true, attIsFullHp, role.getRoleType(), true);
				}
			}
		}
	}
	
	/**
	 * 加入buff资源
	 * @param resource
	 */
	public void addBuffResource(int resource) {
		buffResources.put(resource, resource);
	}
	
	/**
	 * 加入区域buff并产生区域buff增加动作
	 * @param buff
	 * @return
	 */
	public FightBuffInfo addAreaBuff(FightArea buff) {
		addBuffResource(buff.getBuffSn());
		areaBuffs.add(buff);
		return new FightBuffInfo(-1, buff.getAreaSn(), FightBuffInfo.TYPE_ADD, buff.getTargetPos(), -1, buff.getBuffId());
	}
	
	/**
	 * 加入战斗单位到战场，根据战斗单位的阵型位置初始化所在分线和起始位置
	 * @param role
	 * @param initOrder
	 * @param formPos
	 * @param isAttack
	 * @param isInitFullHp
	 * @param isAttDelay
	 * @param roleType
	 * @param isVisible
	 */
	public void addFightUnit(Role role, int initOrder, int formPos,
			boolean isAttack, boolean isInitFullHp, int roleType, boolean isVisible) {
		//根据单元在阵型中的位置获取单元的分线和初始位置配置
		int[] conf = FightUtils.getFormLinePosition(formPos);
		int initPos = isAttack ? FightUtils.flipPosition(conf[1]) : conf[1];
		addFightUnit(role, initOrder, formPos, conf[0], initPos, isAttack, isInitFullHp, roleType, isVisible, -1, false);
	}
	
	/**
	 * 加入战斗单位到战场，指定所在分线和起始位置
	 * @param role
	 * @param initOrder
	 * @param formPos
	 * @param initLine
	 * @param initPos
	 * @param isAttack
	 * @param isInitFullHp
	 * @param roleType
	 * @param isVisible
	 * @param belongs
	 * @param triggerPasvSkill
	 */
	public void addFightUnit(Role role, int initOrder, int formPos, int initLine,
			int initPos, boolean isAttack, boolean isInitFullHp, int roleType,
			boolean isVisible, int belongs, boolean triggerPasvSkill) {
		//构造战斗单位
		FightUnit unit = FightUnit.initFromRole(role, unitIndexCounter ++, initOrder, formPos,
				isAttack, isInitFullHp, (isAttack == isAttDelay), roleType, belongs);
		
		//加入列表
		if (isAttack) {
			//加入进攻方队伍
			attTeam.addUnit(unit);
			if (unit.isBlock()) {
				attBlocks.add(unit);
			} else if (unit.isAlive()) {
				alives.add(unit);
			}
		} else {
			//加入防守方队伍
			defTeam.addUnit(unit);
			if (unit.isBlock()) {
				defBlocks.add(unit);
			} else if (unit.isAlive()) {
				alives.add(unit);
			}
		}
		
		//计算单元所在的分线和位置，加入到分线指定位置上
		unit.setCurPos(initPos);
		unit.setLastPos(initPos);
		unit.setUnitLine(initLine);
		
		//加入分线
		FightLine line = fightLines.get(initLine);
		line.addUnit(unit);
		
		//加入初始信息列表
		roleInfos.put(unit.getUnitSn(), unit.getRoleInfo(isVisible));
		
		//触发被动技能但不产生动作
		if (triggerPasvSkill) {
			unit.doPassiveSkill(currentRound, SkillTriggerTypeEnum.FIGHT_START, this, new ArrayList<FightBuffInfo>());
		}
	}
	
	/**
	 * 战斗流程实现
	 */
	private void fight() {
		//初始化战场
		initField();
		
		//进入战斗循环，当达到最大回合限制或出现一方死亡时战斗结束
		long timeCost = System.nanoTime();
		
		while (currentRound < FightUtils.MaxRound && attTeam.isAlive() && defTeam.isAlive()) {
			//设置本回合动作
			FightAction roundAction = new FightAction(currentRound);
			Map<Integer, FightUnitAction> unitActions = new HashMap<Integer, FightUnitAction>();
			
			//设置区域buff变化信息
			List<FightBuffInfo> areaInfos = new ArrayList<FightBuffInfo>();
			
			//首回合处理
			if (currentRound == 1) {
				//触发全局区域buff
				doInitAreaBuff(areaInfos);
				
				//触发对象被动技能
				doFightStartPassiveSkill(unitActions, areaInfos);
			}
			
			//触发换线
			doChangeLine(roundAction);
			
			//在移动前检查本回合可以使用的技能，如果为锁屏武将技则优先使用，并记录动作
			checkAndDoLockSkill(roundAction, unitActions, areaInfos);
			
			//结算死亡
			removeDeadUnits(unitActions);
			  
			//本回合不使用技能的单元进行移动，移动后判断区域buff并产生动作
			doMove();
			
			//检查区域buff并产生动作
			checkAreaBuff(unitActions);
			
			//产生hot、dot效果，使用技能，记录用户动作
			doSkill(unitActions, areaInfos);
			
			//回合结束触发被动技能
			doRoundEndPassiveSkill(unitActions);
			
			//结算死亡，计算buff技能失效，并记录用户动作
			doAccount(unitActions, areaInfos);
			
			//回合末处理动作类型
			if (!attTeam.isAlive() || !defTeam.isAlive()) {
				roundEnd(unitActions);
			}
			
			//将动作加入动作列表中
			roundAction.setRoleActions(unitActions.values().toArray(new FightUnitAction[0]));
			roundAction.setAreaBuffs(areaInfos.toArray(new FightBuffInfo[0]));
			fightActions.add(roundAction);
			
			//回合增加
			currentRound ++;
		}
		
		//输出战报到logger
//		printBattleReport();
		
		//计算战斗消耗时间
		fightCost = (long)currentRound * FightUtils.TimePerRound;
		
		//战斗循环结束，输出信息
		timeCost = System.nanoTime() - timeCost;
		Loggers.battleLogger.info(new StringBuilder("Fight finished! Time costs=").append(timeCost / 1000000L)
				.append("ms, total rounds=").append(currentRound - 1).append(", attAlive=")
				.append(attTeam.isAlive()).append(", defAlive=").append(defTeam.isAlive()).toString());
		
		//增加统计
		totalCost = totalCost + timeCost;
		totalRounds = totalRounds + currentRound - 1;
		totalFights ++;
		Loggers.battleLogger.info(new StringBuilder("++++++++++Fight stats. totalFights=")
				.append(totalFights).append(", avgCost=").append(totalCost / totalFights / 1000000L)
				.append("ms, avgRounds=").append(totalRounds / totalFights).toString());
		
		//战斗结束实现，构造战报并发送
		boolean attWin = !(defTeam.isAlive() && !attTeam.isAlive());
		endImpl(attWin, timeCost);
	}
	
	/**
	 * 输出战报信息到logger
	 */
	private void printBattleReport() {
		StringBuilder sb = new StringBuilder();
		
		//输出roleInfos
		for (FightRoleInfo info : roleInfos.values()) {
			sb.append("role: index=").append(info.getIndex()).append(", isAtt=")
			.append(info.getIsAttack()).append(", line=").append(info.getLine())
			.append(", pos=").append(info.getInitPos()).append(", name=")
			.append(info.getRoleName()).append("\n");
		}
		sb.append("----------------------------\n");
		
		//输出每回合动作
		for (FightAction action : fightActions) {
			sb.append("round=").append(action.getRound()).append("\n");
			for (FightChangeLineAction ca : action.getChangeLines()) {
				sb.append("changes: index=").append(ca.getIndex()).append(", from=")
				.append(ca.getOldLine()).append(", to=").append(ca.getNewLine()).append("\n");
			}
			for (FightLockAction la : action.getSuperSkills()) {
				sb.append("lock: index=").append(la.getUnitIndex()).append(", skill=")
				.append(la.getSkillSn()).append(", targets=(");
				for (FightLockTargetInfo tar : la.getTargets()) {
					sb.append(tar.getUnitIndex()).append("/").append(tar.getHpLost()).append(",");
				}
				sb.append(")\n");
			}
			for (FightUnitAction ua : action.getRoleActions()) {
				sb.append("action: index=").append(ua.getUnitIndex()).append(", type=")
				.append(ua.getActionType()).append(", spd=").append(ua.getSpd())
				.append(", skill=").append(ua.getSkillSn()).append(", pos=")
				.append(ua.getCurPos() + ua.getSpd()).append(", targets=(");
				for (FightTargetInfo tar : ua.getTargets()) {
					sb.append(tar.getUnitIndex()).append("/").append(tar.getHpLost()).append(",");
				}
				sb.append(")\n");
			}
			sb.append("----------------------------\n");
		}
		
		//输出到logger
		Loggers.battleLogger.info(sb.toString());
	}
	
	/**
	 * 战斗结束子类扩展实现
	 */
	protected abstract void endImpl(boolean attWin, long timeCost);
	
	/**
	 * 初始化战场，将各个战斗单元放置在战场中，并返回角色初始化信息
	 */
	private void initField() {
		//TODO 按照先攻值重新排序存活对象
//		Collections.sort(alives, new Comparator<FightUnit>() {
//			@Override
//			public int compare(FightUnit u1, FightUnit u2) {
//				return RandomUtils.nextInt(3) - 1;
//			}
//		});
	}
	
	/**
	 * 战斗开始时触发全局区域buff效果
	 * @param areaInfos
	 */
	private void doInitAreaBuff(List<FightBuffInfo> areaInfos) {
		//构造全局区域buff效果，持续全场战斗
		RealSkillEffect areaEffect = new RealSkillEffect(currentRound, 1000,
				FightUtils.FightAreaEffectSn, 0, FightUtils.MaxRound + 1, 0, 1, 0, 1, 1, FightUtils.FightAreaBuffSn);
		areaEffect.check(Globals.getSkillService().getAllBuffs());
		
		//分别对双方产生区域buff效果
		List<FightBuffInfo> infos = new ArrayList<FightBuffInfo>();
		for (FightUnit unit : attTeam.getAlives()) {
			unit.doRealEffect(areaEffect, this, unit, infos, false, false, areaInfos);
			break;
		}
		for (FightUnit unit : defTeam.getAlives()) {
			unit.doRealEffect(areaEffect, this, unit, infos, false, false, areaInfos);
			break;
		}
	}
	
	/**
	 * 战斗开始时触发被动技能并记录
	 * @param unitActions
	 */
	private void doFightStartPassiveSkill(Map<Integer, FightUnitAction> unitActions,
			List<FightBuffInfo> areaInfos) {
		Iterator<FightUnit> itor = alives.iterator();
		while (itor.hasNext()) {
			//获取单元
			FightUnit unit = itor.next();
			if (!unit.isBlock()) {
				//如果存在战斗开始被动技能产生动作，加入到动作列表
				List<FightBuffInfo> infos = unit.doPassiveSkill(currentRound,
						SkillTriggerTypeEnum.FIGHT_START, this, areaInfos);
				if (infos.size() > 0) {
					int index = unit.getUnitIndex();
					FightUnitAction action = unitActions.get(index);
					if (action == null) {
						action = new FightUnitAction(index, -1, -1, ActionType.NONE.getId());
						unitActions.put(index, action);
					}
					action.addBuffers(infos);
				}
			}
		}
	}
	
	/**
	 * 回合结束结算前触发被动技能并记录
	 * @param unitActions
	 */
	private void doRoundEndPassiveSkill(Map<Integer, FightUnitAction> unitActions) {
		//TODO
	}
	
	/**
	 * 换线
	 * @param roundAction
	 */
	private void doChangeLine(FightAction roundAction) {
		//返回的动作列表
		List<FightChangeLineAction> actions = new ArrayList<FightChangeLineAction>();
		
		//首先遍历全部线找到进攻方需要换线单位以及存在防守方的目标线
		FightLine targetLine = null;
		List<FightUnit> changeUnits = new ArrayList<FightUnit>();
		for (FightLine line : fightLines.values()) {
			if (!line.isClosed()) {
				if (line.getDefUnitsSize() <= 0) {
					changeUnits.addAll(line.removeAllUnits(true));
					line.closeLine();
				} else {
					targetLine = line;
				}
			}
		}
		
		//如果进攻方需要换线，优先进行换线
		if (changeUnits.size() > 0 && targetLine != null) {
			int newPos = targetLine.getChangeLinePos(true);
			for (FightUnit unit : changeUnits) {
				//获取原线编号
				int oldLineSn = unit.getUnitLine();
				
				//设置新线和位置
				unit.setCurPos(newPos);
				unit.setLastPos(newPos);
				unit.setUnitLine(targetLine.getLineSn());
				targetLine.addUnit(unit);
				
				//清除冷却
				unit.breakCurrentSkill(true, currentRound);
				
				//触发换线被动技能，并产生动作
				actions.add(new FightChangeLineAction(unit.getUnitIndex(), oldLineSn, targetLine.getLineSn(),
						newPos, unit.doPassiveSkill(currentRound, SkillTriggerTypeEnum.CHANGE_LINE,
								this, new ArrayList<FightBuffInfo>())));
			}
		}
		
		//再次遍历找到防守方需要换的线，并且存在进攻方的目标线
		targetLine = null;
		changeUnits.clear();
		for (FightLine line : fightLines.values()) {
			if (!line.isClosed()) {
				if (line.getAttUnitsSize() <= 0) {
					changeUnits.addAll(line.removeAllUnits(false));
					line.closeLine();
				} else {
					targetLine = line;
				}
			}
		}
		
		//如果防守方需要换线，进行换线
		if (changeUnits.size() > 0 && targetLine != null) {
			int newPos = targetLine.getChangeLinePos(false);
			for (FightUnit unit : changeUnits) {
				//获取原线编号
				int oldLineSn = unit.getUnitLine();
				
				//设置新线和位置
				unit.setCurPos(newPos);
				unit.setLastPos(newPos);
				unit.setUnitLine(targetLine.getLineSn());
				targetLine.addUnit(unit);
				
				//清除冷却
				unit.breakCurrentSkill(true, currentRound);
				
				//触发换线被动技能，并产生动作
				actions.add(new FightChangeLineAction(unit.getUnitIndex(), oldLineSn, targetLine.getLineSn(),
						newPos, unit.doPassiveSkill(currentRound, SkillTriggerTypeEnum.CHANGE_LINE,
								this, new ArrayList<FightBuffInfo>())));
			}
		}
		
		//加入换线动作列表
		if (actions.size() > 0) {
			roundAction.setChangeLines(actions.toArray(new FightChangeLineAction[0]));
		} else {
			roundAction.setChangeLines(new FightChangeLineAction[0]);
		}
	}
	
	/**
	 * 检查技能是否可以使用，如果可以使用并且是锁屏武将技直接使用，如果是其他技能保存当前状态，并返回锁屏武将技动作
	 * @param roundAction
	 * @param unitActions
	 */
	private void checkAndDoLockSkill(FightAction roundAction, Map<Integer,
			FightUnitAction> unitActions, List<FightBuffInfo> areaInfos) {
		List<FightLockAction> lockActions = new ArrayList<FightLockAction>();
		Iterator<FightUnit> itor = alives.iterator();
		while(itor.hasNext()) {
			FightUnit unit = itor.next();
			if (unit.isAlive() && unit.canDoSkill(currentRound) && unit.checkCanDoSkill(currentRound, attTeam, defTeam)) {
				//如果本回合可以使用技能，并且技能为锁屏武将技，则立即使用并产生动作
				unit.doSkill(this, lockActions, null, areaInfos);
				
				//如果本回合使用的技能为非锁屏武将技，创建动作
				unit.createAttackAction(unitActions);
				
				//将使用的技能加入技能资源列表中
				int skillSn = unit.getCurrentSkillSn();
				if (skillSn > 0) {
					skillResources.put(skillSn, skillSn);
				}
			}
		}
		
		//返回锁屏武将技动作序列
		if (lockActions.size() > 0) {
			roundAction.setSuperSkills(lockActions.toArray(new FightLockAction[0]));
		} else {
			roundAction.setSuperSkills(new FightLockAction[0]);
		}
	}
	
	/**
	 * 移除死亡单元，不产生动作
	 * @param unitActions
	 */
	private void removeDeadUnits(Map<Integer, FightUnitAction> unitActions) {
		Iterator<FightUnit> itor = alives.iterator();
		while (itor.hasNext()) {
			FightUnit unit = itor.next();
			if (removeDeadUnit(unit)) {
				itor.remove();
				unitActions.remove(unit.getUnitIndex());
			}
		}
	}
	
	/**
	 * 移除死亡单元，返回是否移除成功
	 * @param unit
	 * @return
	 */
	private boolean removeDeadUnit(FightUnit unit) {
		if (!unit.isAlive()) {
			//获取分线
			FightLine line = fightLines.get(unit.getUnitLine());
			line.removeUnit(unit);
			
			//从队伍中移除死亡对象
			if (unit.isAttack()) {
				attTeam.removeUnit(unit);
			} else {
				defTeam.removeUnit(unit);
			}
			return true;
		}
		return false;
	}
	
	/**
	 * 进行移动
	 * @return
	 */
	private void doMove() {
		for (FightLine line : fightLines.values()) {
			if (!line.isClosed()) {
				line.doMove(currentRound);
			}
		}
	}
	
	/**
	 * 检查区域buff情况
	 * @param unitActions
	 */
	private void checkAreaBuff(Map<Integer, FightUnitAction> unitActions) {
		for (FightUnit unit : alives) {
			//首先清空原有区域buff
			unit.clearAreaValues();
			
			//判断区域buff是否产生效果，如果单元与buff所属方一致并且当前位置在buff区域范围内则生效
			if (!unit.isBlock()) {
				Map<Integer, Integer> oldBuffs = unit.getAreaBuffs();
				Map<Integer, Integer> newBuffs = new HashMap<Integer, Integer>();
				
				//根据当前单位所在位置判断区域buff生效情况
				int unitPos = unit.getCurPos();
				int index = unit.getUnitIndex();
				for (FightArea buff : areaBuffs) {
					int buffId = buff.getBuffId();
					if (unit.isAttack() == buff.isAttack()) {
						int buffPos = buff.getTargetPos();
						if (unitPos < buffPos + FightUtils.AreaRadio && unitPos >= buffPos - FightUtils.AreaRadio) {
							//增加buff
							unit.addAreaValues(buff.getBuffType(), buff.getValue());
							newBuffs.put(buffId, buff.getBuffSn());
							
							//对比判断是否为新增的buff
							if (!oldBuffs.containsKey(buffId)) {
								FightUnitAction action = unitActions.get(index);
								if (action == null) {
									action = new FightUnitAction(index, -1, -1, ActionType.BUFF_INFO.getId());
									unitActions.put(index, action);
								} else {
									action.updateActionType(ActionType.BUFF_INFO);
								}
								action.addBuffer(new FightBuffInfo(index, buff.getBuffSn(), FightBuffInfo.TYPE_ADD,
										buff.getTargetPos(), unit.getUnitLine(), buffId));
							}
						}
					}
				}
				
				//对比判断出所有移除的buff
				for (Map.Entry<Integer, Integer> entry : oldBuffs.entrySet()) {
					if (!newBuffs.containsKey(entry.getKey())) {
						FightUnitAction action = unitActions.get(index);
						if (action == null) {
							action = new FightUnitAction(index, -1, -1, ActionType.BUFF_INFO.getId());
							unitActions.put(index, action);
						} else {
							action.updateActionType(ActionType.BUFF_INFO);
						}
						action.addBuffer(new FightBuffInfo(index, entry.getValue(), FightBuffInfo.TYPE_REMOVE,
								-1, unit.getUnitLine(), entry.getKey()));
					}
				}
				
				//设置当前区域buff
				unit.setAreaBuffs(newBuffs);
			}
		}
	}
	
	/**
	 * 使用非锁屏武将技，并加入到动作列表中
	 * @param unitActions
	 */
	private void doSkill(Map<Integer, FightUnitAction> unitActions, List<FightBuffInfo> areaInfos) {
		Iterator<FightUnit> itor = alives.iterator();
		while(itor.hasNext()) {
			//首先触发hot、dot效果，然后使用技能
			FightUnit unit = itor.next();
			unit.doHot(currentRound, unitActions);
			unit.doSkill(this, null, unitActions, areaInfos);
		}
	}
	
	/**
	 * 回合结束结算
	 * 死亡结算、技能结算并触发技能失效导致的被动技能、buff效果结算，产生动作并重置临时变量
	 * @param unitActions
	 */
	private void doAccount(Map<Integer, FightUnitAction> unitActions, List<FightBuffInfo> areaInfos) {
		//遍历区域buff列表
		Iterator<FightArea> areaItor = areaBuffs.iterator();
		while (areaItor.hasNext()) {
			FightArea buff = areaItor.next();
			if (buff.isEnd(currentRound)) {
				areaItor.remove();
				areaInfos.add(new FightBuffInfo(-1, buff.getBuffSn(), FightBuffInfo.TYPE_REMOVE, buff.getTargetPos(), -1, buff.getBuffId()));
			}
		}
		
		//遍历存活障碍列表
		Iterator<FightUnit> blockItor = attBlocks.iterator();
		while (blockItor.hasNext()) {
			FightUnit block = blockItor.next();
			if (removeDeadUnit(block)) {
				//死亡，设置动作为死亡状态或创建死亡动作
				FightUnitAction action = unitActions.get(block.getUnitIndex());
				if (action != null) {
					action.updateActionType(ActionType.DEATH);
				} else {
					unitActions.put(block.getUnitIndex(), new FightUnitAction(block.getUnitIndex(), -1, -1, ActionType.DEATH.getId()));
				}
				
				//从存活列表中移除
				blockItor.remove();
			} else {
				block.doAccount(this, unitActions, areaInfos);
			}
		}
		blockItor = defBlocks.iterator();
		while (blockItor.hasNext()) {
			FightUnit block = blockItor.next();
			if (removeDeadUnit(block)) {
				//死亡，设置动作为死亡状态或创建死亡动作
				FightUnitAction action = unitActions.get(block.getUnitIndex());
				if (action != null) {
					action.updateActionType(ActionType.DEATH);
				} else {
					unitActions.put(block.getUnitIndex(), new FightUnitAction(block.getUnitIndex(), -1, -1, ActionType.DEATH.getId()));
				}
				
				//从存活列表中移除
				blockItor.remove();
			} else {
				block.doAccount(this, unitActions, areaInfos);
			}
		}
		
		//遍历存活列表，根据对象情况创建或更新动作
		Iterator<FightUnit> itor = alives.iterator();
		while (itor.hasNext()) {
			//获取单元及单元动作，如果单元动作不存在则创建一个加入列表
			FightUnit unit = itor.next();
			int unitIndex = unit.getUnitIndex();
			
			//优先判断死亡状态，如果单元死亡直接设置状态类型，无需进行单元结算
			if (removeDeadUnit(unit)) {
				//死亡，设置动作为死亡状态或创建死亡动作
				FightUnitAction action = unitActions.get(unitIndex);
				if (action != null) {
					action.updateActionType(ActionType.DEATH);
				} else {
					unitActions.put(unitIndex, new FightUnitAction(unitIndex, -1, -1, ActionType.DEATH.getId()));
				}
				
				//从存活列表中移除
				itor.remove();
			} else {
				//单元存活，进行结算
				unit.doAccount(this, unitActions, areaInfos);
			}
		}
	}
	
	/**
	 * 回合末处理动作，将最后一回合中移动动作类型都变为停止
	 * @param unitActions
	 */
	private void roundEnd(Map<Integer, FightUnitAction> unitActions) {
		for (Map.Entry<Integer, FightUnitAction> entry : unitActions.entrySet()) {
			FightUnitAction action = entry.getValue();
			if (action.getActionType() == ActionType.MOVE.getId()) {
				action.setActionType(ActionType.NONE.getId());
			}
		}
	}
	
	/*
	 * Getter & Setter
	 */
	public FightTeam getAttTeam() {
		return attTeam;
	}
	
	public FightTeam getDefTeam() {
		return defTeam;
	}
	
	public int getCurrentRound() {
		return currentRound;
	}
	
	@Override
	public int doStart() {
		return IIoOperation.STAGE_START_DONE;
	}

	@Override
	public int doIo() {
		try {
			 fight();
		} catch (Exception e) {
			Loggers.battleLogger.warn("BaseField.doIo()", e);
		}
		return IIoOperation.STAGE_IO_DONE;
	}

	@Override
	public int doStop() {
		return IIoOperation.STAGE_STOP_DONE;
	}

}
