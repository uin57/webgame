package com.pwrd.war.core.command;

import com.pwrd.war.core.session.ISession;

/**
 * 命令{@link ICommand}的注册及执行接口
 * 
 * 
 */
public interface ICommandProcessor<T extends ISession> {

	/**
	 * 注册命令,以{@link ICommand#getCommandName()}作为注册时的主键
	 * 
	 * @param command
	 */
	public abstract void registerCommand(ICommand<T> command);

	/**
	 * 
	 * 解析并执行命令
	 * 
	 * 
	 * @param sender
	 *            命令发送者
	 * @param content
	 *            命令格式：//命令名 参数1 参数2 ...
	 */
	public abstract void execute(T sender, String content);

}