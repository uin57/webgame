package com.pwrd.war.robot.strategy.impl;

import com.pwrd.war.core.msg.IMessage;
import com.pwrd.war.gameserver.form.FormPosition;
import com.pwrd.war.gameserver.form.msg.CGFormPosition;
import com.pwrd.war.robot.Robot;
import com.pwrd.war.robot.strategy.OnceExecuteStrategy;

public class FormPositionStrategy extends OnceExecuteStrategy {

	public FormPositionStrategy(Robot robot) {
		super(robot);
	}

	@Override
	public void doAction() {
		CGFormPosition position = new CGFormPosition();
		FormPosition[] petPositions = new FormPosition[1];
		petPositions[0]=new FormPosition();
		//position.setFormSn("ZX10000");
		// 8a8284293369648901336964e6140001
		petPositions[0].setPetSn("8a8284293369648901336964e6140001");
		petPositions[0].setPosition(7);
		position.setPetPositions(petPositions);
		sendMessage(position);

	}

	@Override
	public void onResponse(IMessage message) {
		// TODO Auto-generated method stub

	}

}
