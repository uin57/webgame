package com.pwrd.war.gameserver.common.heartbeat;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.pwrd.war.common.constants.Loggers;
import com.pwrd.war.common.constants.SharedConstants;
import com.pwrd.war.core.server.NamedThreadFactory;
import com.pwrd.war.core.util.ExecutorUtil;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.common.world.WorldRunner;
import com.pwrd.war.gameserver.scene.SceneRunner;

public class HeartbeatThread extends Thread {
	
	/** 线程池 */
	private final ExecutorService pool1;
	
	/** 线程池 */
	private final ExecutorService pool2;
	
	
	/** 是否繁忙 */
	private volatile boolean isBusy;
	
	/** 是否处于活动状态,默认为true，shutdown后变为false */
	private volatile boolean isLive = true;
	
	private boolean isDebug = Globals.getServerConfig().getIsDebug();
	
	/**
	 * 根据服务器配置文件创建线程池
	 * 
	 */
	public HeartbeatThread(int threadNums) {
		pool1 = Executors.newFixedThreadPool(threadNums, new NamedThreadFactory(getClass()));
		pool2 = Executors.newFixedThreadPool(threadNums, new NamedThreadFactory(getClass()));
	}
	
	@Override
	public void run() {		
		try {
			while (isLive) {
				Globals.getTimeService().update();// 更新缓存的时间为当前系统时间
				
				List<SceneRunner> sceneRunners = Globals.getSceneService().getAllSceneRunners();
				for (SceneRunner runner : sceneRunners) {
					Future<Integer> curFuture = runner.getFuture();
					if (curFuture == null || curFuture.isDone()) {
						Future<Integer> future = pool1.submit(runner);
						runner.setFuture(future);
					}
				}	
				
				sceneRunners = Globals.getRepService().getAllSceneRunners();
				for (SceneRunner runner : sceneRunners) {
					Future<Integer> curFuture = runner.getFuture();
					if (curFuture == null || curFuture.isDone()) {
						Future<Integer> future = pool2.submit(runner);
						runner.setFuture(future);
					}
				}	
				
				sleep(SharedConstants.GS_HEART_BEAT_INTERVAL);
				
				boolean _busy = false;
				for (SceneRunner runner : sceneRunners) {
					if (!runner.getFuture().isDone()) {
						_busy = true;
						break;
					}
				}
				isBusy = _busy;
				
				
				
				WorldRunner worldRunner = Globals.getWorldService().getWorldRunner();
				Future<Integer> curFuture = worldRunner.getFuture();
				if (curFuture == null || curFuture.isDone()) {
					Future<Integer> future = pool1.submit(worldRunner);
					worldRunner.setFuture(future);
				}
				
				sleep(SharedConstants.GS_HEART_BEAT_INTERVAL);
				
				if(!worldRunner.getFuture().isDone())
				{
					if (!isDebug) {
						Loggers.gameLogger.error("world is busy");
					}
				}				
				
				if (_busy) {					
					for (SceneRunner runner : sceneRunners) {
						if (!runner.getFuture().isDone()) {
							if (!isDebug) {
								Loggers.gameLogger.error("scene:" + runner.getSceneId()
										+ " is busy");
							}
						}
					}
				}
				
				
			}
		} catch (Exception e) {
			Loggers.gameLogger.error("", e);
			shutdown();
		}
	}
	
	/**
	 * 调度器是否繁忙
	 * 
	 * @return
	 */
	public boolean isBusy() {
		return isBusy;
	}
	
	/**
	 * 关闭SceneTaskScheduler
	 */
	public void shutdown() {
		// 关闭SceneTaskScheduler，不再向线程池中提交新的任务
		this.isLive = false;
		// 等待5分钟，尽量保证已提交的任务都tick完，再关闭线程池
		ExecutorUtil.shutdownAndAwaitTermination(this.pool1);
		ExecutorUtil.shutdownAndAwaitTermination(this.pool2);
	}
	

}
