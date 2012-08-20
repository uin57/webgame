package com.pwrd.war.gameserver.fight.enums;

public enum ActionType {
	NONE(6),			//无动作
	ATTACK(3),			//仅攻击
	MOVE(4),			//仅移动
	BACK(2),			//击退
	DEATH(1),			//死亡
	ATTACKED(5),		//被击
	SKILL_EFFECT(10),	//攻击效果
	LOCK_ATTACK(11),	//仅锁屏武将技攻击
	BUFF_INFO(6),		//仅有buff变化，与无动作编号一致
	;

	private int id;

	ActionType(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

}
