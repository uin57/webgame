package com.pwrd.war.gameserver.timeevent.msg;

import com.pwrd.war.core.msg.sys.ScheduledMessage;

public class TimeEventTaskMsg extends ScheduledMessage {

	private Runnable run;
	
	public TimeEventTaskMsg(long createTime, Runnable run) {
		super(createTime);
		this.run = run;
	}

	@Override
	public void execute() {
		this.run.run();
	}

}
