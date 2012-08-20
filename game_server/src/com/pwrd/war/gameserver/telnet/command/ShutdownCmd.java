package com.pwrd.war.gameserver.telnet.command;

import java.util.Map;

import org.apache.mina.common.IoSession;

/**
 * 停机命令,该命令将执行{@link System#exit(int)}将JVM退出,因此要慎用
 * 
 * 
 */
public class ShutdownCmd extends LoginedTelnetCommand {

	public ShutdownCmd() {
		super("SHUTDOWN");
	}

	@Override
	protected void doExec(String command, Map<String, String> params,
			IoSession session) {
		System.exit(0);
	}
}
