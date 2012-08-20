package com.pwrd.war.gameserver.startup;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.pwrd.war.core.server.NamedThreadFactory;
import com.pwrd.war.core.util.ExecutorUtil;

/**
 * 系统线程池服务
 * 
 */
public class GameExecutorService {
	
	/** 用于处理进程相关的线程池 */
	private final ScheduledExecutorService processExecutorService;

	/** 用于执行定时任务的线程池 */
	private final ScheduledExecutorService scheduleExecutorService;

	public GameExecutorService() {
		this.processExecutorService = Executors.newScheduledThreadPool(1, new NamedThreadFactory(getClass()));
		this.scheduleExecutorService = Executors.newScheduledThreadPool(1, new NamedThreadFactory(getClass()));
	}

	/**
	 * 取得处理进程事件的线程池
	 * 
	 * @return
	 */
	public ScheduledExecutorService getProcessExcecutor() {
		return processExecutorService;
	}

	/**
	 * 增加按指定的周期执行的任务
	 * 
	 * @param task
	 *            任务
	 * @param period
	 *            执行周期,单位为毫秒
	 */
	public void scheduleTask(Runnable task, long period) {
		this.scheduleExecutorService.scheduleAtFixedRate(task, 0, period,
				TimeUnit.MILLISECONDS);
	}

	public void stop() {
		ExecutorUtil.shutdownAndAwaitTermination(this.processExecutorService, 0,
				TimeUnit.MILLISECONDS);
		ExecutorUtil.shutdownAndAwaitTermination(this.scheduleExecutorService,0, 
				TimeUnit.MILLISECONDS);
	}
}
