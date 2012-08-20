package com.pwrd.war.robot.strategy;

import com.pwrd.war.robot.Robot;

/**
 * 循环执行策略
 *
 */
public abstract class LoopExecuteStrategy extends BaseRobotStrategry {

	/**
	 * 类参数构造器
	 * 
	 * @param robot 发起操作的机器人对象
	 */
	public LoopExecuteStrategy(Robot robot) {
		super(robot);
	}

	/**
	 * 类参数构造器
	 * 
	 * @param robot 发起操作的机器人对象
	 * @param delay 第一次执行的延迟时间
	 * @param period 循环执行时的时间间隔
	 */
	public LoopExecuteStrategy(Robot robot, int delay, int interval) {
		super(robot,delay,interval);
	}
	


	@Override
	public final boolean isRepeatable() {
		return true;
	}


}
