package com.pwrd.war.gameserver.status;

import com.pwrd.war.common.model.GameServerStatus;
import com.pwrd.war.core.msg.sys.ScheduledMessage;
import com.pwrd.war.gameserver.common.Globals;

public class ScheduleReportStatus extends ScheduledMessage{
	
	public ScheduleReportStatus(long createTime) {
		super(createTime);	
	}

	@Override
	public void execute() {
		int _onlines = Globals.getOnlinePlayerService().getOnlinePlayerCount();		
		GameServerStatus serverStatus = Globals.getServerStatus();
		
		serverStatus.setOnlinePlayerCount(_onlines);
		serverStatus.setLoginWallEnabled(Globals.getServerConfig().isLoginWallEnabled());
		serverStatus.setWallowControlled(Globals.getServerConfig().isWallowControlled());
		serverStatus.refresh();
		
		if (serverStatus.getLoginWallEnabled()) {
			Globals.getServerStatusService().limited();
		} else {
			Globals.getServerStatusService().run();
		}
		
		Globals.getServerStatusService().reportToLocal();	
		
		// 采集在线人数
	}

}
