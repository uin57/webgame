package com.pwrd.war.robot.strategy;

import com.pwrd.war.core.msg.IMessage;

/**
 * 将一个普通strategy封装成一个InnerStrategy
 * @author yue.yan
 *
 */
public class WrappedInnerStrategy implements IInnerStrategy{
	
	/** 内部策略 */
	private IRobotStrategy strategy;
	/** 执行间隔 */
	private int actionInterval;
	
	/** 上次操作时间 */
	private long lastActionTime;
	
	public WrappedInnerStrategy(IRobotStrategy strategy, int actionInterval) {
		this.strategy = strategy;
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
	public boolean canDoAction() {
		return strategy.canDoAction();
	}

	@Override
	public void doAction() {
		strategy.doAction();
	}

	@Override
	public int getDelay() {
		return strategy.getDelay();
	}

	@Override
	public int getPeriod() {
		return strategy.getPeriod();
	}

	@Override
	public boolean isRepeatable() {
		return strategy.isRepeatable();
	}

	@Override
	public void onResponse(IMessage message) {
		strategy.onResponse(message);
	}

}
