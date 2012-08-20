package com.pwrd.war.common;

/**
 * 需要监听角色或者其它有心跳对象心跳行为的类实现此接口
 * 
 */
public interface HeartBeatListener {
	/**
	 * 在被监听者心跳时调用此方法
	 */
	void onHeartBeat();
}
