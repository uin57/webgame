package com.pwrd.war.gameserver.telnet.command;

import java.util.Map;

import org.apache.mina.common.IoSession;

import com.pwrd.war.common.model.GameServerStatus;
import com.pwrd.war.core.util.ServerVersion;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.common.config.GameServerConfig;

/**
 * 取得Game Server的状态
 * 
 * 消息名字为WS_STATUS,主要为了兼容多服务器架构,gm系统都是通过world server进行状态查询,获取服务器版本号等信息
 * 
 */
public class GameStatusCommand extends LoginedTelnetCommand {
	public GameStatusCommand() {
		super("WS_STATUS");
	}

	@Override
	protected void doExec(String command, Map<String, String> params,
			IoSession session) {
		final GameServerStatus serverStatus = new GameServerStatus();
		final GameServerConfig _config = Globals.getServerConfig();
		serverStatus.setServerId(_config.getServerId());
		serverStatus.setServerName(_config.getServerName());
		serverStatus.setIp(_config.getBindIp());
		serverStatus.setPort(_config.getPorts());
		serverStatus.setVersion(ServerVersion.getServerVersion());
		serverStatus.setLoginWallEnabled(_config.isLoginWallEnabled());
		serverStatus.setWallowControlled(_config.isWallowControlled());
		serverStatus.setTimestamp(System.currentTimeMillis());
		serverStatus.refresh();
		sendMessage(session, serverStatus.getStatusJSON().toString());
	}
}
