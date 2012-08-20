package com.pwrd.war.robot.strategy.impl;

import com.pwrd.war.core.msg.IMessage;
import com.pwrd.war.gameserver.item.msg.CGUseItem;
import com.pwrd.war.robot.Robot;
import com.pwrd.war.robot.strategy.OnceExecuteStrategy;

public class ItemUseTestStrategy extends OnceExecuteStrategy{
	
	/**
	 * 类参数构造器
	 * 
	 * @param robot
	 * @param delay
	 */
	public ItemUseTestStrategy(Robot robot, int delay) {
		super(robot, delay);
	}

	@Override
	public void doAction() {		
		CGUseItem cgUseItemMsg = new CGUseItem();
		cgUseItemMsg.setBagId((short)1);
		cgUseItemMsg.setIndex((short)0);
		cgUseItemMsg.setParams("true,");
		sendMessage(cgUseItemMsg);	
	}

	@Override
	public void onResponse(IMessage message) {
		// TODO Auto-generated method stub
		
	}

}
