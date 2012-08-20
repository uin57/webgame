package com.pwrd.war.gameserver.common.event;

import com.pwrd.war.core.event.IEvent;
import com.pwrd.war.gameserver.monster.VisibleMonster;
import com.pwrd.war.gameserver.player.Player;

public class VisibleMonsterFindPlayerEvent implements IEvent<VisibleMonster> {
	private VisibleMonster monster;
	private Player player;
	
	public VisibleMonsterFindPlayerEvent(VisibleMonster monster, Player player) {
		super();
		this.monster = monster;
		this.player = player;
	}

	@Override
	public VisibleMonster getInfo() { 
		return monster;
	}

	public VisibleMonster getMonster() {
		return monster;
	}

	public void setMonster(VisibleMonster monster) {
		this.monster = monster;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

}
