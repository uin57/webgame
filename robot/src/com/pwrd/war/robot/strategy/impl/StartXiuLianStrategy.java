/**
 * 
 */
package com.pwrd.war.robot.strategy.impl;

import com.pwrd.war.core.msg.IMessage;
import com.pwrd.war.gameserver.human.msg.CGStartXiulian;
import com.pwrd.war.robot.Robot;
import com.pwrd.war.robot.strategy.OnceExecuteStrategy;

/**
 * 修炼机器人策略
 * @author dengdan
 *
 */
public class StartXiuLianStrategy extends OnceExecuteStrategy {

	public StartXiuLianStrategy(Robot robot, int delay) {
		super(robot, delay);
	}

	/* (non-Javadoc)
	 * @see com.pwrd.war.robot.strategy.IRobotStrategy#doAction()
	 */
	@Override
	public void doAction() {
		CGStartXiulian msg = new CGStartXiulian();
		this.getRobot().sendMessage(msg);
	}

	/* (non-Javadoc)
	 * @see com.pwrd.war.robot.strategy.IRobotStrategy#onResponse(com.pwrd.war.core.msg.IMessage)
	 */
	@Override
	public void onResponse(IMessage message) {
		// TODO Auto-generated method stub

	}

}
