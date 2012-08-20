package com.pwrd.war.gameserver.player.msg;

import com.pwrd.war.core.msg.sys.SessionClosedMessage;
import com.pwrd.war.gameserver.player.Player;
import com.pwrd.war.gameserver.player.handler.PlayerHandlerFactory;
import com.pwrd.war.gameserver.startup.GameClientSession;
import com.pwrd.war.gameserver.startup.GameServerIoHandler;

/**
 * 处理玩家连接和断开连接的消息，此消息在系统主线程中执行
 * 
 * @see GameServerIoHandler
 */
public class GameClientSessionClosedMsg extends
		SessionClosedMessage<GameClientSession> {

	/**
	 * @param type
	 * @param session
	 */
	public GameClientSessionClosedMsg(GameClientSession session) {
		super(session);
	}

	@Override
	public void execute() {
		Player player = session.getPlayer();
		PlayerHandlerFactory.getHandler().handlePlayerCloseSession(player);
	}
}
