package com.pwrd.war.robot.strategy.impl;

import com.pwrd.war.core.msg.IMessage;
import com.pwrd.war.gameserver.common.msg.CGPing;
import com.pwrd.war.gameserver.common.msg.GCPing;
import com.pwrd.war.robot.Robot;
import com.pwrd.war.robot.strategy.LoopExecuteStrategy;

/**
 * Ping 操作
 * 
 * @author haijiang.jin
 *
 */
public class PingStrategy extends LoopExecuteStrategy {
	
	/** 类参数构造器 */
	public PingStrategy(Robot robot, int delay, int period) {
		super(robot, delay, period);
	}

	@Override
	public void doAction() {
		sendMessage(new CGPing());
	}


	@Override
	public void onResponse(IMessage message) {
		if(message instanceof GCPing)
		{
//			Robot.robotLogger.info(getRobot().getPassportId() + " get ping time : " + ((GCPing)message).getTimestamp() );
		}		
	}	
	
}
