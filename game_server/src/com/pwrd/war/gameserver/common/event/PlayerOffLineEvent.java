package com.pwrd.war.gameserver.common.event;

import com.pwrd.war.core.event.IEvent;
import com.pwrd.war.gameserver.player.Player;

/**
 * 玩家下线的事件
 * @author xf
 */
public class PlayerOffLineEvent implements IEvent<Player> {
	Player player;
	public PlayerOffLineEvent(Player player) {
		super();
		this.player = player;
	}
	@Override
	public Player getInfo() { 
		return player;
	}

}
