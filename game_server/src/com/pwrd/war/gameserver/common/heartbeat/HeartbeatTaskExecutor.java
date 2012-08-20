package com.pwrd.war.gameserver.common.heartbeat;

import com.pwrd.war.common.HeartBeatListener;

/**
 * 心跳任务执行器
 * 
 * @author li.liu
 * @since 2010-8-11
 */
public interface HeartbeatTaskExecutor extends HeartBeatListener {

	/**
	 * 提交一个任务
	 * 
	 * @param task
	 */
	void submit(HeartbeatTask task);

	/**
	 * 清除所有任务
	 */
	void clear();
}
