package com.pwrd.war.gameserver.common.heartbeat;

/**
 * 心跳任务
 * 
 * @author li.liu
 * @since 2010-8-11
 */
public interface HeartbeatTask extends Runnable {

	/**
	 * 任务执行的时间间隔，单位毫秒
	 * 
	 * @return
	 */
	long getRunTimeSpan();

	/**
	 * 取消任务
	 */
	void cancel();

}
