package com.pwrd.war.core.schedule;

import java.util.Date;

import com.pwrd.war.core.msg.sys.CronScheduledMessage;
import com.pwrd.war.core.msg.sys.ScheduledMessage;

/**
 * 定时任务管理器
 * 
 */
public interface ScheduleService {

	/**
	 * 
	 * @param msg
	 * @param delay 延迟时间(单位:豪秒)
	 */
	public abstract void scheduleOnce(ScheduledMessage msg, long delay);

	/**
	 * 
	 * @param msg
	 * @param d 指定日期
	 */
	public abstract void scheduleOnce(ScheduledMessage msg, Date d);

	/**
	 * 
	 * @param msg
	 * @param delay 延迟时间
	 * @param period 周期时间(单位:豪秒)
	 */
	public abstract void scheduleWithFixedDelay(ScheduledMessage msg, long delay, long period);
	
	/**
	 * 根据cron字符串建立定时任务
	 * @author xf
	 */
	public abstract void schedule(CronScheduledMessage msg);
}