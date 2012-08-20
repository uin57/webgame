package com.pwrd.war.gameserver.status;

import java.util.ConcurrentModificationException;
import java.util.concurrent.TimeUnit;

import com.mchange.util.AssertException;
import com.pwrd.war.common.constants.Loggers;
import com.pwrd.war.core.util.ErrorsUtil;
import com.pwrd.war.core.util.ReportServerStatus.ServerStatusType;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.common.config.GameServerConfig;
import com.pwrd.war.gameserver.common.log.GameErrorLogInfo;

/**
 * 服务器状态服务
 *
 */
public class ServerStatusService {
	
	private ServerStatusType status = ServerStatusType.STATUS_INIT;
	
	private volatile boolean reporting;

	public ServerStatusType getStatus() {
		switch (status) {
		case STATUS_INIT:
		case STATUS_LIMITED:
		case STATUS_STOPPING:
		case STATUS_STOPPED:
		case STATUS_ERROR:
		case STATUS_RUN: {
			return this.status;
		}
		default:
			throw new AssertException("mismatch ServerStatusType");
		}
	}

	public void setStatus(ServerStatusType status) {
		this.status = status;
	}
	
	public void stopping() {
		this.status = ServerStatusType.STATUS_STOPPING;
	}
	
	public void stopped() {
		this.status = ServerStatusType.STATUS_STOPPED;
	}

	public void run() {
		this.status = ServerStatusType.STATUS_RUN;
	}
	
	public void limited() {
		this.status = ServerStatusType.STATUS_LIMITED;
	}
	
	public void reportToLocal() {
		reportToLocal(getStatus());
	}
	
	public void reportToLocalSync() {
		reportToLocalSync(getStatus());
	}
	
	public void reportToLocal(ServerStatusType status) {
		GameServerConfig config = Globals.getServerConfig();
		if (!config.isTurnOnLocalInterface()) {
			return;
		}
//		Globals.getAsyncLocalService().reportServerStatus(config.getLocalHostId(),	status.getStatusCode(), "");
	}
	
	public void reportToLocalSync(ServerStatusType status) {
		GameServerConfig config = Globals.getServerConfig();
		if (!config.isTurnOnLocalInterface()) {
			return;
		}		
		if (reporting) {
			throw new ConcurrentModificationException();
		}
		reporting = true;
//		Globals.getSynLocalService().reportServerStatus(config.getLocalHostId(),status.getStatusCode(), "", 
//				new ICallback() {
//
//					@Override
//					public void onSuccess(IResponse iresponse) {
//						reporting = false;
//					}
//
//					@Override
//					public void onFail(IResponse iresponse) {
//						reporting = false;
//					}
//				}
//		);
		while (reporting) {
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				Loggers.gameLogger.error(ErrorsUtil.error(GameErrorLogInfo.INVOKE_REPORT_LOCAL_ONLINE_ERR,
						"#GS.ServerStatusService.reportToLocalSync", "report return fail!"));
			}
		}
	}
	
}
