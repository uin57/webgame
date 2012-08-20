package com.pwrd.war.robot.strategy;

import com.pwrd.war.core.msg.IMessage;
import com.pwrd.war.robot.Robot;

/**
 * 独立的内部策略
 * @author yue.yan
 *
 */
public abstract class InnerStrategy extends BaseRobotStrategry implements IInnerStrategy {

	/** 执行间隔 */
	private int actionInterval;
	
	/** 上次操作时间 */
	private long lastActionTime;
	
	public InnerStrategy(Robot robot, int actionInterval) {
		super(robot);
		this.actionInterval = actionInterval;
	}

	@Override
	public long getLastActionTime() {
		return lastActionTime;
	}

	@Override
	public void setLastActionTime(long lastActionTime) {
		this.lastActionTime = lastActionTime;
	}
	
	@Override
	public int getActionInterval() {
		return actionInterval;
	}

	@Override
	public boolean isRepeatable() {
		return false;
	}

	@Override
	public void onResponse(IMessage message) {

	}

}
