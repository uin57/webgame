package com.pwrd.war.robot.strategy.impl;

import com.pwrd.war.common.constants.SharedConstants;
import com.pwrd.war.core.msg.IMessage;
import com.pwrd.war.core.util.MathUtils;
import com.pwrd.war.gameserver.chat.msg.CGChatMsg;
import com.pwrd.war.gameserver.chat.msg.GCChatMsg;
import com.pwrd.war.robot.Robot;
import com.pwrd.war.robot.strategy.LoopExecuteStrategy;

/**
 * 聊天测试
 * 
 * @author haijiang.jin
 *
 */
public class ChatTestStrategy extends LoopExecuteStrategy {
	/** 类参数构造器 */
	public ChatTestStrategy(Robot robot, int delay, int s) {
		super(robot, delay, s);
	}

	@Override
	public void doAction() {
		CGChatMsg chatMsg = new CGChatMsg();

		chatMsg.setScope(SharedConstants.CHAT_SCOPE_WORLD);
		String contents = "";
		
		int _rnd = MathUtils.random(0, 100);
		if (_rnd <= 10) {
			contents = getRobot().getPassportId() + "现在报时, 现在是北京时间" + new java.util.Date();
        } else if (_rnd <= 20) {
        	contents =  "上哪儿接任务?";
        } else if (_rnd <= 50) {
        	contents = "我其实是个机器人";
        } else if (_rnd <= 80) {
        	contents = "fuck";
        } else {
        	contents = "我是一只小猪猪";
        }
        System.out.println(contents);
		chatMsg.setContent(contents);
		sendMessage(chatMsg);
		this.logInfo("chat");
	}


	@Override
	public void onResponse(IMessage message) {		
		if (message instanceof GCChatMsg)
		{
//			Robot.robotLogger.info(getRobot().getPassportId() + ((GCChatMsg)message).getContent()+"测试，我是机器人");
		}
	}
}
