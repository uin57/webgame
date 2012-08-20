package com.pwrd.war.core.event;

/**
 * 事件接口
 * 
  *
 * 
 * @param <T>
 *            触发事件时附加的信息类型
 */
public interface IEvent<T> {
	/**
	 * 取得该事件所绑定的信息
	 * 
	 * @return
	 */
	public T getInfo();
}
