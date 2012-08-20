package com.pwrd.war.gameserver.wallow.msg;

import com.pwrd.war.core.msg.SysInternalMessage;
import com.pwrd.war.gameserver.common.Globals;

/**
 * 定时同步防沉迷玩家列表在线累计时长服务开始
 * 
 *
 */
public class SysWallowTickerServiceStart extends SysInternalMessage {

	@Override
	public void execute() {
		Globals.getWallowService().startService();
	}

	@Override
	public String getTypeName() {
		return "SysWallowTickerServiceStart";
	}

}
