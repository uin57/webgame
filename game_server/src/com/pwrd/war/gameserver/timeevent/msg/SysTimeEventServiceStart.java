package com.pwrd.war.gameserver.timeevent.msg;

import com.pwrd.war.core.msg.SysInternalMessage;
import com.pwrd.war.gameserver.common.Globals;

/**
 * 定时事件服务开启消息
 *
 *
 */
public class SysTimeEventServiceStart extends SysInternalMessage {

	@Override
	public void execute() {
		Globals.getTimeEventService().startService();
	}

}
