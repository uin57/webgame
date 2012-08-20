package com.pwrd.war.gameserver.telnet.command;

import java.util.Map;

import org.apache.mina.common.IoSession;

import com.pwrd.war.gameserver.common.Globals;

/**
 * 清奖励缓存
 * 
 * 
 */
public class PrizeClearCacheCommand extends LoginedTelnetCommand {
	public PrizeClearCacheCommand() {
		super("PrizeClear");
	}

	@Override
	protected void doExec(String command, Map<String, String> params,
			IoSession session) {
		
		Globals.getPrizeService().clearCache();
	}
}
