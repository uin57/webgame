package com.pwrd.war.robot.strategy.impl;

import com.pwrd.war.core.msg.IMessage;
import com.pwrd.war.gameserver.player.msg.CGPlayerQueryAccount;
import com.pwrd.war.gameserver.player.msg.GCPlayerQueryAccount;
import com.pwrd.war.robot.Robot;
import com.pwrd.war.robot.strategy.OnceExecuteStrategy;

public class PlayerAccountQueryStrategy extends OnceExecuteStrategy{

	public PlayerAccountQueryStrategy(Robot robot,int delay) {
		super(robot,delay);
	}

	@Override
	public void doAction() {
		CGPlayerQueryAccount cgPlayerQueryAccount = new CGPlayerQueryAccount();
		getRobot().sendMessage(cgPlayerQueryAccount);
	}

	@Override
	public void onResponse(IMessage message) {
		if(message instanceof GCPlayerQueryAccount)
		{
			System.out.println(((GCPlayerQueryAccount)message).getMmBalance());
		}
	}

}
