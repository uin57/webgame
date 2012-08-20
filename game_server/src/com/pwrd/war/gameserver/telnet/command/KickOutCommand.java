package com.pwrd.war.gameserver.telnet.command;

import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.mina.common.IoSession;

import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.common.i18n.constants.CommonLangConstants_10000;
import com.pwrd.war.gameserver.player.Player;
import com.pwrd.war.gameserver.player.PlayerExitReason;

public class KickOutCommand extends LoginedTelnetCommand {

	/** 角色ID key字符串 */
	private static final String CHAR_ID_KEY = "id";
	/** passport ID key字符串 */
	private static final String PASSPORT_ID_KEY = "pid";
	
	public KickOutCommand() {
		super("KICKOUT");
	}

	@Override
	protected void doExec(String command, Map<String, String> params,
			IoSession session) {
		String _param = getCommandParam(command);
		if (_param.length() == 0) {
			sendError(session, "No param");
			return;
		}
		
		JSONObject _json = JSONObject.fromObject(_param);
		
//		long _charId = 0;		
		String _charId = "";		
		if (_json.containsKey(CHAR_ID_KEY)) {
			String _strCharId = _json.getString(CHAR_ID_KEY);
//			_charId = Long.parseLong(_strCharId);
			_charId = _strCharId;
		} else if (_json.containsKey(PASSPORT_ID_KEY)) {
//			final long _pid = Long.parseLong(_json.getString(PASSPORT_ID_KEY));
			final String _pid = _json.getString(PASSPORT_ID_KEY);
			if (_pid.equals("")) {
				sendError(session, "Bad Passport Id");
				return;
			}

			Player _oUser = Globals.getOnlinePlayerService().getPlayerByPassportId(_pid);
			if (_oUser != null) {
				_charId = _oUser.getRoleUUID();
			}
		}
		
//		if (_charId <= 0) {
		if (_charId.equals("")) {
			sendError(session, "Bad charId");
			return;
		}
		
		Player _onlinePlayer = Globals.getOnlinePlayerService().getPlayerById(_charId);
		if (_onlinePlayer == null) {
			sendError(session, "The player found,but offline");
			return;
		}
		
//		_onlinePlayer.sendMessage(new GCNotifyException(
//				CommonLangConstants_10000.GM_KICK, Globals.getLangService().read(CommonLangConstants_10000.GM_KICK)));
		_onlinePlayer.sendSystemMessage(CommonLangConstants_10000.GM_KICK);
		_onlinePlayer.exitReason = PlayerExitReason.GM_KICK;
		_onlinePlayer.disconnect();
		
		this.sendMessage(session, "Sended");
		
	}
	
}
