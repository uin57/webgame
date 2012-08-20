/**
 * 
 */
package com.pwrd.war.robot.strategy.impl;

import com.pwrd.war.core.msg.IMessage;
import com.pwrd.war.gameserver.robbery.msg.CGRobberyPlyaerIndex;
import com.pwrd.war.gameserver.robbery.msg.CGRobberyStart;
import com.pwrd.war.gameserver.robbery.msg.GCRobberyPlyaerIndex;
import com.pwrd.war.robot.Robot;
import com.pwrd.war.robot.strategy.OnceExecuteStrategy;

/**
 * @author 运镖策略
 *
 */
public class RobberyStrategy extends OnceExecuteStrategy {

	public RobberyStrategy(Robot robot, int delay) {
		super(robot, delay);
	}
	
	@Override
	public void doAction() {
		CGRobberyPlyaerIndex msg = new CGRobberyPlyaerIndex();
		this.sendMessage(msg);
	}

	@Override
	public void onResponse(IMessage message) {
		if(message instanceof GCRobberyPlyaerIndex){
			CGRobberyStart msg = new CGRobberyStart();
			this.sendMessage(msg);
		}
		
	}

}
