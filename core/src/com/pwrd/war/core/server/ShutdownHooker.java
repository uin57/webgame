package com.pwrd.war.core.server;

import java.util.ArrayList;
import java.util.List;

/**
 * JVM停止时的钩子基类
 * 
 */
public class ShutdownHooker implements Runnable {
	private final List<Runnable> hookers = new ArrayList<Runnable>();

	public boolean register(Runnable runnable) {
		if (runnable == this) {
			throw new IllegalArgumentException("Can't add the same object");
		}
		if (!this.hookers.contains(runnable)) {
			return this.hookers.add(runnable);
		} else {
			return false;
		}
	}

	public boolean remove(Runnable runnable) {
		return this.hookers.remove(runnable);
	}

	@Override
	public void run() {
		System.err.println("The JVM will be shutdown");
		for (Runnable _hook : this.hookers) {
			_hook.run();
		}
	}
}
