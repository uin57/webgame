package com.pwrd.war.core.command.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pwrd.war.core.command.ICommand;
import com.pwrd.war.core.command.ICommandProcessor;
import com.pwrd.war.core.session.ISession;

/**
 * 聊天命令处理器
 *
 * 
 */
public class CommandProcessorImpl<T extends ISession> implements
		ICommandProcessor<T> {
	private static final Logger logger = LoggerFactory.getLogger("cmd");
	private Map<String, ICommand<T>> cmds;

	public CommandProcessorImpl() {
		cmds = new HashMap<String, ICommand<T>>();
	}

	@Override
	public void registerCommand(ICommand<T> ac) {
		cmds.put(ac.getCommandName().toLowerCase(), ac);
	}

	public ICommand<T> getAdminCommand(String command) {
		return cmds.get(command.toLowerCase());
	}

	@Override
	public void execute(T sender, String content) {
		String[] commands = content.trim().split("\\s+");
		/* 命令有效性检查 */
		if (0 == commands.length) {
			return;
		} else if (commands[0].length() <= ICommand.CMD_PREFIX.length()) {// 命令以"!"开始
			return;
		} else if (commands[0].substring(0, ICommand.CMD_PREFIX.length())
				.equals(ICommand.CMD_PREFIX)) {
			commands[0] = commands[0].substring(ICommand.CMD_PREFIX.length()); // 获得命令名
		} else {
			return;
		}

		/* 处理各个命令 */
		try {
			ICommand<T> cmd = getAdminCommand(commands[0]);

			if (cmd != null) {
				// 检查权限
				if (checkPermission(sender, commands[0], content)) {
					String[] params = Arrays.copyOfRange(commands, 1,
							commands.length);// 命令参数
					if (logger.isDebugEnabled()) {
						logger.debug("command " + cmd.getCommandName()
								+ "'s parameter array : "
								+ Arrays.toString(params));
					}
					cmd.execute(sender, params);
				}
			} else {
				// player.sendSystemMessage("unknown command : " + commands[0]);
			}
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {
				logger.error("Execute Command Fail", e);
			}
		}
	}

	/**
	 * 检查权限是否足够执行命令 ： 默认为true
	 * 
	 * 注：如果实现中需要自定义权限判断逻辑，请Override此方法
	 * 
	 * @return
	 */
	protected boolean checkPermission(T sender, String cmd, String content) {
		return true;
	}

}
