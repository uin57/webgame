package com.pwrd.war.gameserver.telnet.command;

import java.util.Map;

import org.apache.mina.common.IoSession;

import com.pwrd.war.common.constants.Loggers;
import com.pwrd.war.core.msg.IMessage;
import com.pwrd.war.core.msg.SysInternalMessage;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.common.event.PlayerOffLineEvent;
import com.pwrd.war.gameserver.player.Player;
import com.pwrd.war.gameserver.player.PlayerExitReason;
import com.pwrd.war.gameserver.player.PlayerState;

/**
 * 设置登陆墙打开/关闭
 * 
 * @author qianwp
 * 
 */
public class LoginOpenWallCommand extends LoginedTelnetCommand {

	public LoginOpenWallCommand() {
		super("LoginWall");
	}

	@Override
	protected void doExec(String command, Map<String, String> params,
			IoSession session) {
		String _param = "";
		if (params.get("enable") == null) {
			_param = "false";// 命令不带参数，则默认表示关闭登陆墙
		} else {
			_param = params.get("enable");
		}
	
		if (_param.trim().equals("true")) {
			Globals.getServerConfig().setLoginWallEnabled(true);
			Globals.getServerStatus().setLoginWallEnabled(true);
			for (Player player : Globals.getOnlinePlayerService().getOnlinePlayers()) {
				if (player != null && player.getState() != PlayerState.logouting) {
					player.disconnect();
				}
			}
		} else if (_param.trim().equals("false")) {
			Globals.getServerConfig().setLoginWallEnabled(false);
			Globals.getServerStatus().setLoginWallEnabled(false);
//			Globals.getOnlinePlayerService().offlineAllPlayers(PlayerExitReason.WALLOW_KICK);
			
		}
		
		

	}

}
