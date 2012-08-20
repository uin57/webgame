package com.pwrd.war.robot.strategy.impl;

import com.pwrd.war.common.constants.SharedConstants;
import com.pwrd.war.core.msg.IMessage;
import com.pwrd.war.core.util.MathUtils;
import com.pwrd.war.gameserver.chat.msg.CGChatMsg;
import com.pwrd.war.robot.Robot;
import com.pwrd.war.robot.strategy.OnceExecuteStrategy;

/**
 * 压力测试前期准备工作
 * 
 * @author haijiang.jin
 *
 */
public class LoadTestPrepareStrategy extends OnceExecuteStrategy {
	/** 道具 Id 数组 */
	private static int[] ITEM_IDS = {
		10110001, 11120001, 11130001, 11140001, 11150001, 11160001, 
		11160002, 11160003, 11160004, 11160005, 11170001, 11170002, 
		11170003, 11170004, 11170005, 11170006, 20000001,
	};

	/**
	 * 类参数构造器
	 * 
	 * @param robot
	 * @param delay
	 */
	public LoadTestPrepareStrategy(Robot robot, int delay) {
		super(robot, delay);
	}

	@Override
	public void doAction() {
		CGChatMsg chatmsg = new CGChatMsg();
		chatmsg.setScope(SharedConstants.CHAT_SCOPE_PRIVATE);

		// 建筑全开
		chatmsg.setContent("!building openall");
		this.sendMessage(chatmsg);
		// 并升级主城
		chatmsg.setContent("!building addlevel zc 50");
		this.sendMessage(chatmsg);
		// 功能全开
		chatmsg.setContent("!func openall");
		this.sendMessage(chatmsg);
		// 加钱(金币和钻石)
		chatmsg.setContent("!givemoney 1 9999");
		this.sendMessage(chatmsg);
		chatmsg.setContent("!givemoney 2 9999");
		this.sendMessage(chatmsg);
		// 给粮食
		chatmsg.setContent("!givefood 9999");
		this.sendMessage(chatmsg);
		// 加军功
		chatmsg.setContent("!giveexploit 9999");
		this.sendMessage(chatmsg);

		if (this.getRobot().getPetManager().getPetCount() == 1) {
			// 如果没有武将, 就给两个武将
			chatmsg.setContent("!givepet " + 101);
			this.sendMessage(chatmsg);
			chatmsg.setContent("!givepet " + MathUtils.random(102, 106));
			this.sendMessage(chatmsg);
		}

		
		// 清空道具
		chatmsg.setContent("!clearitem");
		this.sendMessage(chatmsg);

		for (int i = 0; i < 50; i++) {
			int itemIndex = MathUtils.random(0, ITEM_IDS.length);
			int itemId = ITEM_IDS[itemIndex];

			// 给道具
			chatmsg.setContent("!giveitem " + itemId +" 1");
			this.sendMessage(chatmsg);
		}
	}

	@Override
	public void onResponse(IMessage msg) {
	}	
}
