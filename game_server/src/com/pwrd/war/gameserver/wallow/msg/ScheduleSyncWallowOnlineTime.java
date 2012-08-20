package com.pwrd.war.gameserver.wallow.msg;

import com.pwrd.war.core.msg.sys.ScheduledMessage;
import com.pwrd.war.gameserver.common.Globals;

/**
 * 周期性的从local接口同步防沉迷玩家列表在线累计时长
 * 
 * 
 */
public class ScheduleSyncWallowOnlineTime extends ScheduledMessage {

	public ScheduleSyncWallowOnlineTime(long createTime) {
		super(createTime);
	}

	@Override
	public void execute() {
		Globals.getWallowService().syncWallowPlayerOnlineTime();
	}

	@Override
	public String getTypeName() {
		return "ScheduleSyncWallowOnlineTime";
	}

}
