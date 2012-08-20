package com.pwrd.war.gameserver.fight.domain;

import java.util.ArrayList;
import java.util.List;

import com.pwrd.war.gameserver.fight.enums.ActionType;

/**
 * 战斗单元的动作
 * @author zy
 *
 */
public class FightUnitAction {
	private int unitIndex;		//单元编号
	private int actionType;		//动作类型
	private int spd;			//移动速度
	private int curPos;			//回合结束时位置
	private int curHp;			//回合结束时血量
	private int curMorale;		//回合结束时士气
	private int skillSn;		//技能编号
	private int skillTarget;	//技能目标点
	private boolean skillEnd;	//技能是否结束
	private List<FightTargetInfo> targets = new ArrayList<FightTargetInfo>();	//目标
	private List<FightBuffInfo> tempBuffs = new ArrayList<FightBuffInfo>();		//技能产生的buff
	
	public FightUnitAction() {
	}
	
	public FightUnitAction(int unitIndex, int skillSn, int skillTarget, int actionType) {
		this.unitIndex = unitIndex;
		this.skillSn = skillSn;
		this.skillTarget = skillTarget;
		this.actionType = actionType;
	}
	
	/**
	 * 更新动作类型
	 * @param targetType
	 */
	public void updateActionType(ActionType targetType) {
		//根据变化目标类型判断更新类型
		switch (targetType) {
		case ATTACK:
		case MOVE:
		case SKILL_EFFECT:
			//这三种状态互斥，都可以从无动作、buff效果和受击状态变化过来
			if (actionType == ActionType.NONE.getId() ||
			actionType == ActionType.BUFF_INFO.getId() ||
			actionType == ActionType.ATTACKED.getId()) {
				actionType = targetType.getId();
			}
			break;
			
		case ATTACKED:
			//只能从无状态或buff效果变化过来
			if (actionType == ActionType.NONE.getId() || actionType == ActionType.BUFF_INFO.getId()) {
				actionType = targetType.getId();
			}
			break;
			
		case BUFF_INFO:
			//只能从无状态更新到仅有buff效果
			if (actionType == ActionType.NONE.getId()) {
				actionType = targetType.getId();
			}
			break;
			
		case DEATH:
			//任何时候都可以变为死亡
			actionType = targetType.getId();
			break;
			
		case BACK:
			//只有不是死亡时才能变成击退
			if (actionType != ActionType.DEATH.getId()) {
				actionType = targetType.getId();
			}
			break;
		}
	}
	
	public int getUnitIndex() {
		return unitIndex;
	}
	public void setUnitIndex(int unitIndex) {
		this.unitIndex = unitIndex;
	}
	public int getActionType() {
		return actionType;
	}
	public void setActionType(int actionType) {
		this.actionType = actionType;
	}
	public int getSpd() {
		return spd;
	}
	public void setSpd(int spd) {
		this.spd = spd;
	}
	public int getCurPos() {
		return curPos;
	}
	public void setCurPos(int curPos) {
		this.curPos = curPos;
	}
	public int getCurHp() {
		return curHp;
	}
	public void setCurHp(int curHp) {
		this.curHp = curHp;
	}
	public int getSkillSn() {
		return skillSn;
	}
	public void setSkillSn(int skillSn) {
		this.skillSn = skillSn;
	}
	public int getSkillTarget() {
		return skillTarget;
	}

	public void setSkillTarget(int skillTarget) {
		this.skillTarget = skillTarget;
	}

	public boolean getSkillEnd() {
		return skillEnd;
	}

	public void setSkillEnd(boolean skillEnd) {
		this.skillEnd = skillEnd;
	}

	public FightTargetInfo[] getTargets() {
		return targets.toArray(new FightTargetInfo[0]);
	}
	public void setTargets(FightTargetInfo[] list) {
		if (list != null) {
			for (FightTargetInfo target : list) {
				targets.add(target);
			}
		}
	}
	public void addTargets(List<FightTargetInfo> list) {
		this.targets.addAll(list);
	}
	public int getBuffersSize() {
		return tempBuffs.size();
	}
	public FightBuffInfo[] getBuffers() {
		return tempBuffs.toArray(new FightBuffInfo[0]);
	}
	public void setBuffers(FightBuffInfo[] buffers) {
		if (buffers != null) {
			for (FightBuffInfo info : buffers) {
				tempBuffs.add(info);
			}
		}
	}
	public void addBuffers(List<FightBuffInfo> list) {
		tempBuffs.addAll(list);
	}
	public void addBuffer(FightBuffInfo info) {
		tempBuffs.add(info);
	}

	public int getCurMorale() {
		return curMorale;
	}

	public void setCurMorale(int curMorale) {
		this.curMorale = curMorale;
	}
	
}
