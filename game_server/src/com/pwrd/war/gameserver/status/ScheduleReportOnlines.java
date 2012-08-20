package com.pwrd.war.gameserver.status;

import com.pwrd.war.core.msg.sys.ScheduledMessage;
import com.pwrd.war.gameserver.common.Globals;

public class ScheduleReportOnlines extends ScheduledMessage{
	
	public ScheduleReportOnlines(long createTime) {
		super(createTime);	
	}

	@Override
	public void execute() {
		if (Globals.getServerConfig().isTurnOnLocalInterface()) {
			int onlineCount = Globals.getOnlinePlayerService().getOnlinePlayerCount();
//			Globals.getAsyncLocalService().reportOnlinePlayers(onlineCount);
		}
	}

}
