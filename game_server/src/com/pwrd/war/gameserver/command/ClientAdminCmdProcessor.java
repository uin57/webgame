package com.pwrd.war.gameserver.command;

import com.pwrd.war.common.constants.SharedConstants;
import com.pwrd.war.core.command.impl.CommandProcessorImpl;
import com.pwrd.war.core.session.ISession;
import com.pwrd.war.gameserver.player.Player;
import com.pwrd.war.gameserver.startup.GameClientSession;

/**
 * 
 * GameServer的命令处理器，客户端Debug
 * 
 * @param <T>
 * 
 */
public class ClientAdminCmdProcessor<T extends ISession> extends
		CommandProcessorImpl<T> {

	/**
	 * 检查权限是否足够执行命令
	 * 
	 * @return
	 */
	@Override
	protected boolean checkPermission(T sender, String cmd, String content) {
		if (!(sender instanceof GameClientSession)) {
			return false;
		}
		Player player = ((GameClientSession) sender).getPlayer();

		if (player.getPermission() != SharedConstants.ACCOUNT_ROLE_GM) {
			return false;
		}

		return true;
	}
}
