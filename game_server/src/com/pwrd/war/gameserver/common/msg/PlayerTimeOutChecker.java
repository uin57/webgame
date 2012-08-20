package com.pwrd.war.gameserver.common.msg;

import com.pwrd.war.core.msg.sys.ScheduledMessage;

/**
 * 检查是否有玩家长时间处于Gaming以外的状态
 * 
 */
public class PlayerTimeOutChecker extends ScheduledMessage {
	
	/** 检查周期，默认为5分钟 */
	public static final int CHECK_PERIOD = 5 * 60 * 1000;

	public PlayerTimeOutChecker(long createTime) {
		super(createTime);
	}

	@Override
	public void execute() {
		System.out.println("PlayerTimeOutChecker");
	}

}
