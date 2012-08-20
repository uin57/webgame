package com.pwrd.war.core.server;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 实现一个可以命名的ThreadFactory，以易于定位线程池，参照{@link Executors#defaultThreadFactory()}
 * 
 * 
 */
public class NamedThreadFactory implements ThreadFactory {

	static final AtomicInteger poolNumber = new AtomicInteger(1);
	final ThreadGroup group;
	final AtomicInteger threadNumber = new AtomicInteger(1);
	final String namePrefix;

	public NamedThreadFactory(Class<?> clazz) {
		SecurityManager s = System.getSecurityManager();
		group = (s != null) ? s.getThreadGroup() : Thread.currentThread()
				.getThreadGroup();
		// 根据创建类的类名来命名线程池中线程名字的前缀
		if (clazz == null) {
			namePrefix = "pool-" + poolNumber.getAndIncrement() + "-thread-";
		} else {
			namePrefix = clazz.getSimpleName() + "-thread-";
		}
	}

	public Thread newThread(Runnable r) {
		Thread t = new Thread(group, r, namePrefix
				+ threadNumber.getAndIncrement(), 0);
		if (t.isDaemon())
			t.setDaemon(false);
		if (t.getPriority() != Thread.NORM_PRIORITY)
			t.setPriority(Thread.NORM_PRIORITY);
		return t;
	}
}