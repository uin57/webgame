package com.pwrd.war.gameserver.fight.domain;


/**
 * 锁屏时战斗单元的动作
 * @author zy
 *
 */
public class FightLockAction {
	protected int unitIndex;		//单元编号
	protected int actionType;		//动作类型
	protected int skillSn;		//技能编号
	protected int skillTarget;		//技能目标点
	protected FightLockTargetInfo[] targets;	//目标
	protected FightBuffInfo[] buffers;	//技能产生的buff
	
	public FightLockAction() {
	}
	
	public FightLockAction(int unitIndex, int skillSn, int skillTarget, int actionType) {
		this.unitIndex = unitIndex;
		this.skillSn = skillSn;
		this.skillTarget = skillTarget;
		this.actionType = actionType;
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

	public FightLockTargetInfo[] getTargets() {
		return targets;
	}
	public void setTargets(FightLockTargetInfo[] targets) {
		this.targets = targets;
	}
	public FightBuffInfo[] getBuffers() {
		return buffers;
	}
	public void setBuffers(FightBuffInfo[] buffers) {
		this.buffers = buffers;
	}
	
}
