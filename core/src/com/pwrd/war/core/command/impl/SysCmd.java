package com.pwrd.war.core.command.impl;

import com.pwrd.war.core.command.IAdminCommand;
import com.pwrd.war.core.session.MinaSession;
import com.pwrd.war.core.util.LogUtils;

/**
 * 系统维护指令
 * 
 */
public class SysCmd implements IAdminCommand<MinaSession> {
	/** JVM停机({@link System#exit(int)}) */
	private static final String SUB_CMD_SHUTDOWN = "shutdown";
	/** 触发JVM进行垃圾收集 */
	private static final String SUB_CMD_GC = "gc";
	/** 动态的修改日志的级别 */
	private static final String SUB_CMD_CHANGE_LOGLVL = "changeloglvl";

	public SysCmd() {
	}

	@Override
	public void execute(MinaSession player, String[] commands) {
		if (commands == null || commands.length < 1) {
			return;
		}
		final String _subCommand = commands[0];
		if (_subCommand == null) {
			return;
		}
		if (SUB_CMD_SHUTDOWN.equals(_subCommand)) {
			//新起一个线程处理,因为System.exit是不会返回的
			new Thread() {
				@Override
				public void run() {
					System.exit(0);
				}
			}.start();
		} else if (SUB_CMD_GC.equals(_subCommand)) {
			System.gc();
		} else if (SUB_CMD_CHANGE_LOGLVL.equals(_subCommand)) {
			String _logName = commands[1];
			String _logLevel = commands[2];
			LogUtils.changeLogLevel(_logName, _logLevel);
		}
	}

	@Override
	public String getCommandName() {
		return "sys";
	}
}
