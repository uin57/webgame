package com.pwrd.war.gameserver.command;

import com.pwrd.war.core.command.impl.CommandProcessorImpl;
import com.pwrd.war.core.session.MinaSession;

/**
 * GM后台使用的命令处理器
 *
 */
public class GMAdminCmdProcessor<T extends MinaSession> extends CommandProcessorImpl<T> {

	@Override
	protected boolean checkPermission(T sender, String cmd, String content) {
		// GM后台发来的命令不检查权限
		return true;
	}
}
