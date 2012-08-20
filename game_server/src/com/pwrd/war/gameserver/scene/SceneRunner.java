package com.pwrd.war.gameserver.scene;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

import com.pwrd.war.common.constants.Loggers;

public class SceneRunner implements Callable<Integer> {
	
	/** 绑定的场景 */
	private final Scene scene;
	
	/** future */
	private Future<Integer> future;
	
	public SceneRunner(Scene scene) {
		this.scene = scene;
	}

	@Override
	public Integer call() throws Exception {
		try {
			scene.tick();
		} catch (Throwable e) {
			Loggers.gameLogger.error("", e);
		}
		//TODO 
//		return scene.getSceneId();
		return 0;
	}
	
	public String getSceneId() {
		return scene.getSceneId();
	}
	
	public Future<Integer> getFuture() {
		return future;
	}

	public void setFuture(Future<Integer> future) {
		this.future = future;
	}

}
