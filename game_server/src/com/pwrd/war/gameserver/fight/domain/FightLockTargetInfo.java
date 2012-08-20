package com.pwrd.war.gameserver.fight.domain;

/**
 * 锁屏技能目标信息
 * @author zy
 *
 */
public class FightLockTargetInfo {
	protected int unitIndex;	//单元编号
	protected int type;		//命中类型(0命中，1暴击，2未命中)
	protected int hpLost;		//血量减少
	protected boolean isDead;	//是否死亡
	protected int backDistance;	//击退距离
	protected int curPos;		//当前位置
	
	public FightLockTargetInfo() {
		super();
	}
	
	public FightLockTargetInfo(int unitIndex, int type, int hpLost, boolean isDead, int backDistance, int curPos) {
		this.unitIndex = unitIndex;
		this.type = type;
		this.hpLost = hpLost;
		this.isDead = isDead;
		this.backDistance = backDistance;
		this.curPos = curPos;
	}
	
	public boolean getIsDead() {
		return isDead;
	}
	public void setIsDead(boolean isDead) {
		this.isDead = isDead;
	}

	public int getUnitIndex() {
		return unitIndex;
	}

	public void setUnitIndex(int unitIndex) {
		this.unitIndex = unitIndex;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getHpLost() {
		return hpLost;
	}

	public void setHpLost(int hpLost) {
		this.hpLost = hpLost;
	}

	public int getBackDistance() {
		return backDistance;
	}

	public void setBackDistance(int backDistance) {
		this.backDistance = backDistance;
	}

	public int getCurPos() {
		return curPos;
	}

	public void setCurPos(int curPos) {
		this.curPos = curPos;
	}
	
}
