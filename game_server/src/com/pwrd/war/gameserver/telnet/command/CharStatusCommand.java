package com.pwrd.war.gameserver.telnet.command;

import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.mina.common.IoSession;

import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.player.OnlinePlayerService;
import com.pwrd.war.gameserver.player.Player;

public class CharStatusCommand extends LoginedTelnetCommand {
	/** 玩家在线服务 */
	private OnlinePlayerService onlinePlayerService = Globals.getOnlinePlayerService();

	public CharStatusCommand() {
		super("CHAR_STATUS");
	}

	@Override
	protected void doExec(String command, Map<String, String> params,
			IoSession session) {
		String jsonParam = getCommandParam(command);
		JSONObject param = JSONObject.fromObject(jsonParam);
		String roleUUId = param.getString("id");
		// 玩家是否在线
		boolean isOnline = playerIsOnline(roleUUId);

		if (isOnline) {
			sendMessage(session, "online");
		} else {
			sendMessage(session, null);
		}
	}
	
	/**
	 * 判断玩家是否在线
	 * @param roleUUId
	 * @return
	 */
	public boolean playerIsOnline(String roleUUId) {
		boolean result = true;
		Player player = onlinePlayerService.getPlayer(roleUUId);
		
		if (player == null) {
			result = false;
		} 
		
		return result;
	}
}
