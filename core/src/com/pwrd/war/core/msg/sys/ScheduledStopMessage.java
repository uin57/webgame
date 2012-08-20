package com.pwrd.war.core.msg.sys;

/**
 * 取消某个任务
 * 
 * 
 */
public class ScheduledStopMessage extends ScheduledMessage {

	private ScheduledMessage msg;

	public ScheduledStopMessage(long now,ScheduledMessage msg) {
		super(now);
		this.msg = msg;
	}

	@Override
	public void execute() {
		msg.cancel();
	}
}
