package com.pwrd.war.logserver.telnet.command;

import java.util.Map;

import org.apache.mina.common.IoSession;

import com.pwrd.war.logserver.telnet.command.AbstractTelnetCommand;

/**
 * Telnet登录命令,该命令应该第一条被执行的命令
 * 
 * 
 */
public class LoginCommand extends AbstractTelnetCommand {
	private static final String NAME_PARAM = "test";
	private static final String PASSWORD_PARAM = "test";

	public LoginCommand() {
		super("LOGIN");
	}

	@Override
	protected void doExec(String command, Map<String, String> params, IoSession session) {
		String name = params.get("name");
		String password = params.get("password");
		if (NAME_PARAM.equalsIgnoreCase(name) && PASSWORD_PARAM.equalsIgnoreCase(password)) {
			session.setAttachment(true);			
		} else {
			sendError(session, "Bad User");			
		}
	}
}
