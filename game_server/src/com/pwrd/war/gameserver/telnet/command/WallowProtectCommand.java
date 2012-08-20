package com.pwrd.war.gameserver.telnet.command;

import java.util.Map;

import org.apache.mina.common.IoSession;

import com.pwrd.war.gameserver.common.Globals;

/**
 * 沉迷控制
 * 
 * @author wenpin.qian
 * 
 */
public class WallowProtectCommand extends LoginedTelnetCommand {

	public WallowProtectCommand() {
		super("wallow");
	}

	@Override
	protected void doExec(String command, Map<String, String> params,
			IoSession session) {
		String _param = params.get("enable");
		if (_param.trim().equals("true")) {
			//XXX 现在有两个地方记录了防沉迷开启状态
			//GameServerConfig和GameServerStatus
			//暂时保留这个结构，ServerStatus只作汇报用，而ServerConfig实际控制
			Globals.getServerConfig().setWallowControlled(true);
			Globals.getServerStatus().setWallowControlled(true);
			//开启防沉迷
			Globals.getWallowService().onWallowOpened();
			sendMessage(session, "ok");
		} else if (_param.trim().equals("false")) {
			Globals.getServerConfig().setWallowControlled(false);
			Globals.getServerStatus().setWallowControlled(false);
			//关闭防沉迷
			Globals.getWallowService().onWallowClosed();
			sendMessage(session, "ok");
		} else {
			sendError(session, "Command param error!");
		}

	}

}
