package com.pwrd.war.gameserver.common.world;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

import com.pwrd.war.common.constants.Loggers;

public class WorldRunner implements Callable<Integer> {
	
	private static final int UID = 102209;
	
	private final World world;
	
	/** future */
	private Future<Integer> future;
	
	public WorldRunner(World world) {
		this.world = world;
	}

	@Override
	public Integer call() throws Exception {
		try {
			world.tick();
		} catch (Throwable e) {
			Loggers.gameLogger.error("", e);
		}
		return UID;
	}


	public Future<Integer> getFuture() {
		return future;
	}

	public void setFuture(Future<Integer> future) {
		this.future = future;
	}

}
