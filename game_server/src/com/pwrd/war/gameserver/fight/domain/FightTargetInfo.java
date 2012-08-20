package com.pwrd.war.gameserver.fight.domain;

/**
 * 技能目标信息
 * @author zy
 *
 */
public class FightTargetInfo {
	public static int TYPE_HIT = 0;
	public static int TYPE_CRI = 1;
	public static int TYPE_DODGE = 2;
	
	protected int unitIndex;	//单元编号
	protected int type;		//命中类型(0命中，1暴击，2未命中)
	protected int hpLost;		//血量减少
	
	public FightTargetInfo() {
	}
	
	public FightTargetInfo(int unitIndex, int type, int hpLost) {
		this.unitIndex = unitIndex;
		this.type = type;
		this.hpLost = hpLost;
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
	
}
