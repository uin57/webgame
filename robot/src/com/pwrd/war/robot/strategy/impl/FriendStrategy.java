package com.pwrd.war.robot.strategy.impl;

import com.pwrd.war.core.msg.IMessage;
import com.pwrd.war.gameserver.friend.msg.CGFriendAdd;
import com.pwrd.war.gameserver.friend.msg.CGFriendAddBlack;
import com.pwrd.war.gameserver.friend.msg.CGFriendDel;
import com.pwrd.war.gameserver.friend.msg.CGFriendDelBlack;
import com.pwrd.war.gameserver.friend.msg.CGFriendGetList;
import com.pwrd.war.robot.Robot;
import com.pwrd.war.robot.strategy.OnceExecuteStrategy;

public class FriendStrategy extends OnceExecuteStrategy {

	public FriendStrategy(Robot robot) {
		super(robot); 
	}

	@Override
	public void doAction() { 
		CGFriendAdd add  = new CGFriendAdd();
		add.setFriendName("Hi19");
		this.sendMessage(add);
		
//		CGFriendDel del = new CGFriendDel();
//		del.setFriendUUID("8a828424357abb6101357abc314b0000");
//		this.sendMessage(del);
		
//		CGFriendAddBlack addBlack = new CGFriendAddBlack();
//		addBlack.setFriendUUID("8a828424357abb6101357abc314b0000");
//		this.sendMessage(addBlack);
		
		CGFriendDelBlack delblack = new CGFriendDelBlack();
		delblack.setFriendUUID("8a828424357abb6101357abc314b0000");
		this.sendMessage(delblack);
		
		CGFriendGetList getList = new CGFriendGetList();
		this.sendMessage(getList);
	}

	@Override
	public void onResponse(IMessage message) {
		 

	}

}
