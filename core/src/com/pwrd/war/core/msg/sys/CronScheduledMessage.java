package com.pwrd.war.core.msg.sys;

import org.quartz.CronTrigger;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;


/**
 * 通过cron字符串建立 的定时消息
 */
public abstract class CronScheduledMessage extends ScheduledMessage {
	private String cronStr;
 
	private Scheduler sched ;
	private JobKey jobKey;
 
	private CronTrigger trigger;
	private long lastFireTime = 0;
	public CronScheduledMessage(String cronStr) {
		super(0); 
		this.cronStr = cronStr; 
	}
	

	@Override
	public synchronized void cancel() { 
		try {
			sched.deleteJob(jobKey);
		} catch (SchedulerException e) { 
			e.printStackTrace();
		}
		super.cancel();
	}


	public String getCronStr() {
		return cronStr;
	}


	public void setCronStr(String cronStr) {
		this.cronStr = cronStr;
	}

 
	public Scheduler getSched() {
		return sched;
	}


	public void setSched(Scheduler sched) {
		this.sched = sched;
	}


	public JobKey getJobKey() {
		return jobKey;
	}


	public void setJobKey(JobKey jobKey) {
		this.jobKey = jobKey;
	}


	public CronTrigger getTrigger() {
		return trigger;
	}


	public void setTrigger(CronTrigger trigger) {
		this.trigger = trigger;
	}
	
	public long getNextExecuteTime(){
		return this.trigger.getNextFireTime().getTime();
	}

 


	@Override
	public long getTrigerTimestamp() { 
		return this.trigger.getNextFireTime().getTime();
	}


	@Override
	public long getCreateTimestamp() { 
		return this.trigger.getStartTime().getTime();
	}


	public long getLastFireTime() {
		return lastFireTime;
	}


	public void setLastFireTime(long lastFireTime) {
		this.lastFireTime = lastFireTime;
	}


	@Override
	public void execute() { 
		lastFireTime = System.currentTimeMillis();
	}

}
