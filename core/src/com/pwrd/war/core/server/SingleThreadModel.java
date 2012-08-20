package com.pwrd.war.core.server;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.mina.common.ExecutorThreadModel;
import org.apache.mina.common.IoFilterChain;
import org.apache.mina.common.ThreadModel;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.util.NamePreservingRunnable;

/**
 * 用一个线程实现的ThreadModel
 * 
 * 
 */
public class SingleThreadModel implements ThreadModel {
	private static SingleThreadModel instance = new SingleThreadModel();

	public static SingleThreadModel getInstance() {
		return instance;
	}

	private final ExecutorFilter filter;

	private SingleThreadModel() {
		filter = new ExecutorFilter(new ThreadPoolExecutor(1, 1, 60,
				TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>()));
		ThreadPoolExecutor tpe = (ThreadPoolExecutor) this.filter.getExecutor();
		final ThreadFactory originalThreadFactory = tpe.getThreadFactory();
		ThreadFactory singleThreadFactory = new ThreadFactory() {
			public Thread newThread(Runnable runnable) {
				Thread t = originalThreadFactory
						.newThread(new NamePreservingRunnable(runnable,
								"SingleIoService"));
				t.setDaemon(true);
				return t;
			}
		};
		tpe.setThreadFactory(singleThreadFactory);
	}

	public void buildFilterChain(IoFilterChain chain) throws Exception {
		chain.addFirst(ExecutorThreadModel.class.getName(), filter);
	}
}
