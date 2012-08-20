package com.pwrd.war.robot.strategy.impl;

import com.pwrd.war.common.constants.SharedConstants;
import com.pwrd.war.core.msg.IMessage;
import com.pwrd.war.gameserver.chat.msg.CGChatMsg;
import com.pwrd.war.robot.Robot;
import com.pwrd.war.robot.strategy.OnceExecuteStrategy;

/**
 * GM 命令测试
 * 
 * @author haijiang.jin
 * 
 */
public class GMTestStrategy extends OnceExecuteStrategy {
	/** 聊天消息对象 */
	private CGChatMsg _chatmsg = new CGChatMsg(); 

	/**
	 * 类参数构造器
	 * 
	 * @param robot
	 * @param delay
	 * @param cmd 
	 * 
	 */
	public GMTestStrategy(Robot robot, int delay, String cmd) {
		super(robot, delay);

		this._chatmsg.setScope(SharedConstants.CHAT_SCOPE_PRIVATE);
		this._chatmsg.setContent(cmd);
	}

	@Override
	public void doAction() {
		this.sendMessage(this._chatmsg);
	}

	@Override
	public void onResponse(IMessage message) {
	}
}
