package com.pwrd.war.gameserver.telnet.command;

import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.mina.common.IoSession;

import com.pwrd.war.common.constants.NoticeTypes;
import com.pwrd.war.gameserver.common.Globals;

public class NoticeCommand extends LoginedTelnetCommand {
	
	public NoticeCommand() {
		super("notice");
	}

	@Override
	protected void doExec(String command, Map<String, String> params,
			IoSession session) {
		
		String jsonParam = getCommandParam(command);
		if (jsonParam.length() == 0) {
			this.sendError(session, "No parram");
			return;
		}
		JSONObject obj = JSONObject.fromObject(jsonParam);
		String content = (String) obj.get("content");
		short type = NoticeTypes.system;
		try {
			type = Short.parseShort((String) obj.get("type"));
		} catch (Exception e) {

		}
		if (content == null) {
			this.sendError(session, "No content");
			return;
		}
		
		Globals.getNoticeService().sendNotice(type, content);
		this.sendMessage(session, "ok");
	}
}
