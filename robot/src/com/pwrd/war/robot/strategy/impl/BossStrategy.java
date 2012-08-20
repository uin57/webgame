/**
 * 
 */
package com.pwrd.war.robot.strategy.impl;

import com.pwrd.war.core.msg.IMessage;
import com.pwrd.war.gameserver.activity.msg.CGActivityJoin;
import com.pwrd.war.gameserver.activity.msg.GCActivityStart;
import com.pwrd.war.gameserver.activity.msg.GCActivityStop;
import com.pwrd.war.robot.Robot;
import com.pwrd.war.robot.strategy.OnceExecuteStrategy;

/**
 * 
 * 南蛮入侵策略
 * @author dengdan
 *
 */
public class BossStrategy extends OnceExecuteStrategy {
	
	public BossStrategy(Robot robot, int delay) {
		super(robot, delay);
	}

	@Override
	public void doAction(){
		//发送参加活动信息
		CGActivityJoin msg = new CGActivityJoin();
		msg.setActId("1");
		this.sendMessage(msg);
	}

	/**
	 * 接收到南蛮入侵活动开始的消息
	 */
	@Override
	public void onResponse(IMessage message) {
		//接收到活动开始的消息
		if(message instanceof GCActivityStart){
			GCActivityStart msg = (GCActivityStart)message;
			String actId = msg.getActId();
			//南蛮入侵开始，参加活动
			if("1".equals(actId)){
				CGActivityJoin joinMsg = new CGActivityJoin();
				joinMsg.setActId("1");
				this.sendMessage(joinMsg);
				this.getRobot().setInAct(true);
			}
		}
		
		if(message instanceof GCActivityStop){
			GCActivityStop msg = (GCActivityStop) message;
			String actId = msg.getActId();
			if("1".equals(actId)){
				this.getRobot().setInAct(false);
			}
		}
	}

}
