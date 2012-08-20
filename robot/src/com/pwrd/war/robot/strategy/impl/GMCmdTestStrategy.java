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
public class GMCmdTestStrategy extends OnceExecuteStrategy {
	/** GM 命令字符串 */
	private String gmCmdStr = null;
	
	/** 类参数构造器 */
	public GMCmdTestStrategy(Robot robot, int delay, String gmCmdStr) {
		super(robot, delay);
		this.gmCmdStr = gmCmdStr;
	}

	@Override
	public void doAction() {
		CGChatMsg cgMsg = new CGChatMsg();
		
		cgMsg.setScope(SharedConstants.CHAT_SCOPE_WORLD);
		cgMsg.setContent(this.gmCmdStr);

		this.sendMessage(cgMsg);
	}

	@Override
	public void onResponse(IMessage message) {
	}
}
