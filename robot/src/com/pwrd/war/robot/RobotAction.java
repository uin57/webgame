package com.pwrd.war.robot;

import java.util.concurrent.ScheduledFuture;

import com.pwrd.war.robot.strategy.IRobotStrategy;

/**
 * 机器人行为
 *
 */
public class RobotAction implements Runnable {
	/** 机器人执行策略 */
	private IRobotStrategy strategy = null;
	/** 执行结果 */
	private ScheduledFuture<?> future = null;

	/**
	 * 类参数构造器
	 * 
	 * @param strategy 机器人执行策略
	 * @throws IllegalArgumentException if strategy is null
	 */
	public RobotAction(IRobotStrategy strategy) {
		if (strategy == null) {
			throw new IllegalArgumentException("strategy is null");
		}

		this.strategy = strategy;
	}

	@Override
	public void run() {
		if(this.strategy.canDoAction())
		{
			this.strategy.doAction();
		}
	}

	/**
	 * 获取执行结果
	 * 
	 * @return
	 */
	public ScheduledFuture<?> getFuture() {
		return future;
	}

	/**
	 * 设置执行结果
	 * 
	 * @param future
	 */
	public void setFuture(ScheduledFuture<?> future) {
		this.future = future;
	}
}
