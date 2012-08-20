package com.pwrd.war.robot.strategy;

import com.pwrd.war.core.msg.IMessage;

/**
 * 机器人执行策略接口, 在该接口中定义机器人进入场景后可以做什么以及怎样做?
 * 
 * @author haijiang.jin
 *
 */
public interface IRobotStrategy {
	/**
	 * 是否可以循环执行
	 * 
	 * @return true = 可以循环执行的策略, false = 不可以循环执行的策略
	 */
	public boolean isRepeatable();
	
	
	/**
	 * 是否可以
	 * 
	 * @return
	 */
	public boolean canDoAction();

	/**
	 * 执行机器人动作
	 * 
	 */
	public void doAction();

	/**
	 * 获取第一次执行的延迟时间, 以毫秒为单位
	 * 
	 * @return
	 */
	public int getDelay();

	/**
	 * 获取循环执行的时间间隔, 以毫秒为单位
	 * 
	 * @return
	 */
	public int getPeriod();
	
	/**
	 * 处理返回消息的处理
	 * @param message
	 */
	public void onResponse(IMessage message);
	
}
