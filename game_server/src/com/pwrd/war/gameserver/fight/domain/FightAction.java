package com.pwrd.war.gameserver.fight.domain;


public class FightAction {
	private int round;	//回合数
	private FightChangeLineAction[] changeLines;	//换线动作
	private FightLockAction[] superSkills;			//锁屏武将技动作
	private FightUnitAction[] roleActions;			//动作信息
	private FightBuffInfo[] areaBuffs;				//区域buff动作

	public FightAction() {
	}
	
	public FightAction(int round) {
		this.round = round;
		this.changeLines = new FightChangeLineAction[0];
	}

	public int getRound() {
		return round;
	}

	public void setRound(int round) {
		this.round = round;
	}

	public FightChangeLineAction[] getChangeLines() {
		return changeLines;
	}

	public void setChangeLines(FightChangeLineAction[] changeLines) {
		this.changeLines = changeLines;
	}

	public FightLockAction[] getSuperSkills() {
		return superSkills;
	}

	public void setSuperSkills(FightLockAction[] superSkills) {
		this.superSkills = superSkills;
	}

	public FightUnitAction[] getRoleActions() {
		return roleActions;
	}

	public void setRoleActions(FightUnitAction[] roleActions) {
		this.roleActions = roleActions;
	}

	public FightBuffInfo[] getAreaBuffs() {
		return areaBuffs;
	}

	public void setAreaBuffs(FightBuffInfo[] areaBuffs) {
		this.areaBuffs = areaBuffs;
	}
	
	

}
