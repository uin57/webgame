package com.pwrd.war.gameserver.common.listener;

import java.util.Comparator;

/**
 * @author zhengyisi
 * @since 2010-6-11
 */
public interface Listener {
	/** 比较器，优先级高到低排序*/
	public static final Comparator<Listener> comparator = new Comparator<Listener>() {
		
		@Override
		public int compare(Listener l1, Listener l2) {
			if (l1.priority() < l2.priority()) {
				return 1;
			}
			if (l1.priority() > l2.priority()) {
				return -1;
			}
			return 0;
		}
	};

	/**
	 * 当该监听者被注册时的操作
	 * 
	 * @param listening
	 *            注册的监听者
	 */
	public void onRegisted(Listenable<?> listening);

	/**
	 * 当该监听器被删除时的操作
	 * 
	 * @param listening
	 */
	public void onDeleted(Listenable<?> listening);

	/**
	 * 优先级
	 * 
	 * @return
	 */
	public int priority();
	
	
	public static class Priority{
		private static int PRIORITY_BASE = 100;
		
		public static final int PRIORITY_DEFAULT = --PRIORITY_BASE;
	}
}
