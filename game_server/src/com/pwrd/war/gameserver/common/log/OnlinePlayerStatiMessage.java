package com.pwrd.war.gameserver.common.log;

import com.pwrd.op.LogOp;
import com.pwrd.op.LogOpChannel;
import com.pwrd.war.core.async.IIoOperation;
import com.pwrd.war.core.msg.sys.ScheduledMessage;
import com.pwrd.war.core.util.TimeUtils;
import com.pwrd.war.db.dao.HumanDao;
import com.pwrd.war.gameserver.common.Globals;

/**
 * 定时人数统计
 */
public class OnlinePlayerStatiMessage extends ScheduledMessage {

	public OnlinePlayerStatiMessage() {
		super(0);
	}
	private boolean isDoing = false;

	@Override
	public void execute() { 
		if(isDoing)return;
		isDoing = true;
		IIoOperation op = new IIoOperation() {			
			@Override
			public int doStop() {
				OnlinePlayerStatiMessage.this.isDoing = false;
				return STAGE_STOP_DONE;
			}			
			@Override
			public int doStart() {
				return STAGE_START_DONE;
			}			
			@Override
			public int doIo() {
				OnlinePlayerStatiMessage.this.doit();
				return STAGE_IO_DONE;
			}
		};
		Globals.getAsyncService().createOperationAndExecuteAtOnce(op); 
	}
	
	public void doit(){
		//GM统计
		int onlinePlayerNum = Globals.getOnlinePlayerService().getOnlinePlayerCount();
		
		HumanDao humanDao = Globals.getDaoService().getHumanDao();
		int totalPlayerNum = humanDao.getTotalPlayer();
		
		long now = Globals.getTimeService().now();
		LogOp.log(LogOpChannel.ONLINE, totalPlayerNum, onlinePlayerNum, now, TimeUtils.formatYMDTime(now));
	}

}
