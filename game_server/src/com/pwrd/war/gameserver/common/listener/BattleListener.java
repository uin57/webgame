package com.pwrd.war.gameserver.common.listener;

import com.pwrd.war.gameserver.human.Human;

public interface BattleListener {
		
	public void onOccupyFarm(Human human);
	
	public void onOccupyOil(Human human);
	
	public void onAttackEnemy(Human human);
	
	public void onPvpConquest(Human human);
	
	public void onAttackPlayer(Human human);
	
}
