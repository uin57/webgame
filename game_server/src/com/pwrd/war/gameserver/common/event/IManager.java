package com.pwrd.war.gameserver.common.event;

/**
 * 管理器的接口
 * 
  *
 * 
 */
public interface IManager {
	/**
	 * 执行初始化的操作
	 */
	public void init();

	/**
	 * 在初始化后进行检查操作
	 * 
	 * @exception RuntimeException
	 *                如果未通过校验,则抛出RuntimeException异常
	 */
	public void check();

	/**
	 * 初始化后的启动操作
	 */
	public void start();

	/**
	 * 停止操作
	 */
	public void stop();
}
