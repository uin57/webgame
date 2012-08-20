package com.pwrd.war.gameserver.startup;

import com.pwrd.war.core.session.ISession;
import com.pwrd.war.gameserver.player.Player;

/**
 * 与 GameServer 连接的客户端的会话信息
 * 
 */
public interface GameClientSession extends ISession {

	void setPlayer(Player player);

	Player getPlayer();

	String getIp();
}
