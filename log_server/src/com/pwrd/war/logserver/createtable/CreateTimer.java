package com.pwrd.war.logserver.createtable;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 建立数据表的定时器
 * 
 * 
 */
public class CreateTimer {
	
	/**
	 * 启动一个Timer
	 * 
	 * @param createTabaleTask
	 * @param delay
	 *            3600000
	 * @param period
	 *            3600000
	 */
	public static void scheduleTask(TimerTask timerTask, long delay, long period) {
		try {
			Timer timer = new Timer();
			timer.schedule(timerTask, delay, period);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
