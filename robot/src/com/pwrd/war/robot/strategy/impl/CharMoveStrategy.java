package com.pwrd.war.robot.strategy.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.math.RandomUtils;

import com.pwrd.war.core.msg.IMessage;
import com.pwrd.war.gameserver.activity.boss.Point;
import com.pwrd.war.gameserver.scene.msg.CGPlayerMove;
import com.pwrd.war.gameserver.scene.msg.GCPlayerMove;
import com.pwrd.war.robot.Robot;
import com.pwrd.war.robot.strategy.LoopExecuteStrategy;

public class CharMoveStrategy extends LoopExecuteStrategy {
 
	private Robot robot;
	List<Point> list = new ArrayList<Point>();
	public CharMoveStrategy(Robot robot, int delay, int interval) {
		super(robot, delay, interval);
		this.robot = robot;
		list.add(new Point(877,503));
		list.add(new Point(1070,644));
		list.add(new Point(1276,763));
		list.add(new Point(1862,1104));
		list.add(new Point(2562,1804));
		list.add(new Point(1562,1404));
		list.add(new Point(2062,1404));
		list.add(new Point(3062,1604));
		list.add(new Point(2462,1704));
		list.add(new Point(2162,1504));
		list.add(new Point(1276,763));
		list.add(new Point(1070,644));
		list.add(new Point(877,503));
		list.add(new Point(1074,279));
	}

	
	@Override
	public void doAction() {
		if(this.getRobot().isInAct){
			return;
		}
		int num = RandomUtils.nextInt(14);
		Point p = list.get(num);
		if(p.x == robot.x && p.y == robot.y){
			if(num >0){
				p = list.get(num -1);
			}else{
				p = list.get(num + 1);
			}
		}
		CGPlayerMove m = new CGPlayerMove();

		m.setSrcx(robot.x);
		m.setSrcy(robot.y);
		m.setTox(p.x);
		m.setToy(p.y);
		sendMessage(m);
//		robot.x = robot.x + 7;
//		robot.y  = robot.y + 7;
//		CGPlayerPos pos = new CGPlayerPos();
//		pos.setSceneId(robot.sceneId);
//		pos.setSrcx(robot.x);
//		pos.setSrcy(robot.y);
//		sendMessage(pos);
//		Loggers.clientLogger.info("开始移动："+robot.x+","+robot.y);
		
//		CGEntryScene m = new CGEntryScene();
//		m.setSceneId("11001");
//		sendMessage(m);
	}

	@Override
	public void onResponse(IMessage message) {
		// TODO Auto-generated method stub
		if(message instanceof GCPlayerMove){
//			System.out.println("收到移动恢复:"+message);
		}
	}

 

}
