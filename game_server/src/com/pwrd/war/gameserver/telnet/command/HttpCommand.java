package com.pwrd.war.gameserver.telnet.command;

import java.util.Map;

import org.apache.mina.common.IoSession;

/**
 * 开关Http监听
 * 
 * @author Fancy
 * @version 2010-6-24 下午12:00:11
 */
public class HttpCommand extends LoginedTelnetCommand {

	public HttpCommand() {
		super("HTTP_SWITCH");
	}

	@Override
	protected void doExec(String command, Map<String, String> params,
			IoSession session) {

		String _param = this.getCommandParam(command);
		if (_param.length() == 0) {
			sendError(session, "No param");
			return;
		}

		//TODO
		// if(_param.equals("0")){ // 打开Http监听
		// Globals.getHttpIoHandler().setOpen(true);
		// sendMessage(session , "Http Open Ok.");
		// }else if(_param.equals("1")){ // 关闭Http监听
		// Globals.getHttpIoHandler().setOpen(false);
		// sendMessage(session , "Http close Ok.");
		// }else {
		// sendError(session, "Wrong param");
		// }

	}

}
