package com.pwrd.war.gameserver.scene.listener;

import com.pwrd.war.gameserver.common.listener.Listener;
import com.pwrd.war.gameserver.human.Human;
import com.pwrd.war.gameserver.scene.Scene;

/**
 * 场景监听器
 * 
 * @author zhengyisi
 * @since 2010-6-13
 */
public interface SceneListener extends Listener {
	/** 场景监听器集合默认容量 */
	public static final int DEFAULT_LISTENER_CONTAINER_CAPACITY = 2;
	
	/**
	 * 在玩家进入场景后调用
	 */
	public void afterHumanEnter(Scene scene, Human human);

	/**
	 * 在玩家离开场景时调用
	 * 
	 * @param scene
	 * @param human
	 */
	public void beforeHumanLeave(Scene scene, Human human);
	
	/**
	 * 以登出的方式离开场景
	 * 
	 * @param scene
	 * @param human
	 */
	public void leaveOnLogoutSaving(Scene scene,Human human);
}
