package com.pwrd.war.robot.strategy.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.math.RandomUtils;

import com.pwrd.war.common.constants.SharedConstants;
import com.pwrd.war.core.msg.IMessage;
import com.pwrd.war.gameserver.activity.boss.Point;
import com.pwrd.war.gameserver.chat.msg.CGChatMsg;
import com.pwrd.war.gameserver.scene.msg.CGPlayerMove;
import com.pwrd.war.robot.Robot;
import com.pwrd.war.robot.strategy.LoopExecuteStrategy;

public class RobotMoveStrategy extends LoopExecuteStrategy {
	Robot robot;
	public RobotMoveStrategy(Robot robot, int delay, int s) {
		super(robot, delay, s);
		this.robot = robot;
		list.add(new Point(877,503));
		list.add(new Point(1070,644));
		list.add(new Point(1276,763));
		list.add(new Point(1862,1104));
		list.add(new Point(1276,763));
		list.add(new Point(1070,644));
		list.add(new Point(877,503));
		list.add(new Point(1074,279));
	}
	List<Point> list = new ArrayList<Point>();
	@Override
	public void doAction() { 
		Point p = list.get(0);		
		list.remove(0);
		
		CGPlayerMove msg = new CGPlayerMove(0, 0, p.x, p.y);
//		CGPlayerMove msg = new CGPlayerMove(0, 0, RandomUtils.nextInt(2000), RandomUtils.nextInt(2000));
		
		this.sendMessage(msg);
		list.add(p);
		
//		CGChatMsg cmsg = new CGChatMsg();
//		cmsg.setScope(SharedConstants.CHAT_SCOPE_WORLD);
//		cmsg.setContent("我是"+robot.getPassportId()+",正在移动到=>"+p);
//		sendMessage(cmsg);
	}

	@Override
	public void onResponse(IMessage message) {
		 

	}

}
