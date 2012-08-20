/**
 * 
 */
package com.pwrd.war.robot.strategy.impl;

import com.pwrd.war.core.msg.IMessage;
import com.pwrd.war.gameserver.family.msg.CGCreateFamily;
import com.pwrd.war.robot.Robot;
import com.pwrd.war.robot.strategy.OnceExecuteStrategy;

/**
 * 
 * 创建家族机器人策略
 * @author dengdan
 *
 */
public class CreateFamilyStrategy extends OnceExecuteStrategy {
	
	private Robot robot;
	
	public CreateFamilyStrategy(Robot robot, int delay) {
		super(robot, delay);
		this.robot = robot;
	}

	/* (non-Javadoc)
	 * @see com.pwrd.war.robot.strategy.IRobotStrategy#doAction()
	 */
	@Override
	public void doAction() {
		CGCreateFamily msg = new CGCreateFamily();
		msg.setName("三国" + this.robot.getPid());
		this.robot.sendMessage(msg);
	}

	/* (non-Javadoc)
	 * @see com.pwrd.war.robot.strategy.IRobotStrategy#onResponse(com.pwrd.war.core.msg.IMessage)
	 */
	@Override
	public void onResponse(IMessage message) {
		// TODO Auto-generated method stub

	}

}
