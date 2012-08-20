package com.pwrd.war.robot.strategy.impl;

import com.pwrd.war.core.msg.IMessage;
import com.pwrd.war.robot.Robot;
import com.pwrd.war.robot.strategy.OnceExecuteStrategy;

public class CGFormUpdateStrategy extends OnceExecuteStrategy{

	public CGFormUpdateStrategy(Robot robot) {
		super(robot);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void doAction() {
		/**
		CGFormUpdate formUpdate =new CGFormUpdate();
		formUpdate.setFormSn("ZX10000");
		formUpdate.setFormLevel(2);
		sendMessage(formUpdate);
		**/
	}

	@Override
	public void onResponse(IMessage message) {
		// TODO Auto-generated method stub
		
	}

}
