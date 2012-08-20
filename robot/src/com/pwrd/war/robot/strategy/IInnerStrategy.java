package com.pwrd.war.robot.strategy;


/**
 * 某个strategy内部的substrategy，由外部的strategy进行调度
 * @author yue.yan
 *
 */
public interface IInnerStrategy extends IRobotStrategy{

	public long getLastActionTime();
	
	public void setLastActionTime(long lastActionTime);
	
	public int getActionInterval();
}
