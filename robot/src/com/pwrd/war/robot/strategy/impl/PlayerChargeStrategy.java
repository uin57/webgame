package com.pwrd.war.robot.strategy.impl;

import com.pwrd.war.core.msg.IMessage;
import com.pwrd.war.gameserver.player.msg.CGPlayerChargeDiamond;
import com.pwrd.war.gameserver.player.msg.GCPlayerChargeDiamond;
import com.pwrd.war.robot.Robot;
import com.pwrd.war.robot.strategy.OnceExecuteStrategy;

public class PlayerChargeStrategy extends OnceExecuteStrategy{
	
	public PlayerChargeStrategy(Robot robot,int delay) {
		super(robot,delay);
	}
	
	@Override
	public void doAction() {
		CGPlayerChargeDiamond CGPlayerChargeDiamond = new CGPlayerChargeDiamond();
		CGPlayerChargeDiamond.setMmCost(10);		
		getRobot().sendMessage(CGPlayerChargeDiamond);
	}

	@Override
	public void onResponse(IMessage message) {
		if(message instanceof GCPlayerChargeDiamond)
		{
			System.out.println(((GCPlayerChargeDiamond)message).getMmBalance());
		}
	}

}
