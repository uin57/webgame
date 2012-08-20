package com.pwrd.war.gameserver.player;

/**
 * 用户在gaming下的小状态
 * 
 * @author gameuser
 *
 */

public enum GamingStateIndex {
	
	/*
	 * 用户在正常游戏时候的状态  
	 * 表示方式为2的幂   如 00001 和 00010
	 * 最多支持31个状态 0x7fffffff（最高位不能为1）
	 */
	
	IN_BATTLE(1),  						//战斗
	IN_PRACTISE(1 << 1),				//修来呢
	IN_GJ(1 << 2),				//副本扫荡
	IN_STALL(1 << 3),			//摆摊
	IN_REP(1<< 4),			//副本
	IN_TEAM(1 << 5),			//组队
	IS_DEAD(1 << 6),			//死亡
	IN_COUNTRYWAR(1 << 7),		//国战 
	IN_SWITCHSCENE(1 << 8),		//切换地图 
	IN_NOMAL(0x7fffffff);		//普通状态
	
	
	private int value;
	public int getValue() {
        return value;
    }
	GamingStateIndex(int value) {
		this.value = value;
	}
}
