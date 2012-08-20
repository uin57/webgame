package com.pwrd.war.gameserver.fight.domain;

/**
 * 技能目标信息
 * @author zy
 *
 */
public class TargetInfo {
	private int unitIndex;	//目标编号
	private int hpLost;		//目标损血量
	private FightBuffInfo[] buffInfos;	//buff变化信息
}
