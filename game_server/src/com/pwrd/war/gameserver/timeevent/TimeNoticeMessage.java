package com.pwrd.war.gameserver.timeevent;

import java.util.List;

import com.pwrd.war.core.async.IIoOperation;
import com.pwrd.war.core.msg.sys.ScheduledMessage;
import com.pwrd.war.db.model.TimeNoticeInfo;
import com.pwrd.war.gameserver.common.Globals;

/**
 * 定时公告服务
 * @author xf
 */
public class TimeNoticeMessage extends ScheduledMessage {

	public TimeNoticeMessage() {
		super(0);
	}

	@Override
	public void execute() {
		IIoOperation op = new IIoOperation() { 
			@Override
			public int doStop() { 
				return STAGE_STOP_DONE;
			}			
			@Override
			public int doStart() {				
				return STAGE_START_DONE;
			}			
			@Override
			public int doIo() { 
				TimeNoticeMessage.this.doit();
				return STAGE_IO_DONE;
			}
		};
		Globals.getAsyncService().createOperationAndExecuteAtOnce(op);
	}
	
	public void doit(){
		List<TimeNoticeInfo> all = Globals.getDaoService().getTimeNoticeDAO().findAll();
		if(all != null && all.size() > 0){
			long now = Globals.getTimeService().now();
			for(TimeNoticeInfo t : all){
				if(t.getStartTime().getTime() <= now && t.getEndTime().getTime() >= now){
					long st = t.getStartTime().getTime();
					long df = now - st;
					df = df/1000;//s
					df = df/60;//min
					if(df % t.getIntervalTime() == 0){
						//可以
						Globals.getNoticeService().sendNotice(t.getType(), t.getContent());
					}
					
				}
			}
		}
	}

}
