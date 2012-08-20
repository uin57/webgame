package com.pwrd.war.robot.strategy.impl;

import com.pwrd.war.core.msg.IMessage;
import com.pwrd.war.core.msg.SysFloodBytesAttack;
import com.pwrd.war.robot.Robot;
import com.pwrd.war.robot.strategy.OnceExecuteStrategy;

/**
 * 洪水攻击字节数检查
 * 
 * @author haijiang.jin
 *
 */
public class FloodBytesTestStrategy extends OnceExecuteStrategy {
	/** 类参数构造器 */
	public FloodBytesTestStrategy(Robot robot, int delay) {
		super(robot, delay);
	}

	@Override
	public void doAction() {
		for (int i = 0; i < 10; i++) {
			sendMessage(new SysFloodBytesAttack(4096));
		}
	}
	
	@Override
	public void onResponse(IMessage message) {
		// TODO Auto-generated method stub
		
	}

	
}
