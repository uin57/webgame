package com.pwrd.war.gameserver.common.listener;

/**
 * @author zhengyisi
 * @since 2010-6-11
 */
public interface Listenable<T extends Listener> {
	
	/**
	 * 注册监听器,该方法的实现有义务调用 {@link Listener#onRegisted(Listenable)}
	 * 
	 * @param listener 监听者
	 */
	public void registerListener(T listener);
	
	/**
	 * 删除监听者,该方法的实现有义务调用 {@link Listener#onDeleted(Listenable)}
	 * 
	 * @param listener 监听者
	 */
	public void deleteListener(T listener);
}
