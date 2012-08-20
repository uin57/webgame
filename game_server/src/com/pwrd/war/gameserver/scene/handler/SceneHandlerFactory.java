package com.pwrd.war.gameserver.scene.handler;

import com.pwrd.war.gameserver.common.Globals;

/**
 * 场景消息处理器静态工厂
 *  
 *
 */
public class SceneHandlerFactory {
	
	private static SceneMessageHandler handler = new SceneMessageHandler(
			Globals.getSceneService(), Globals.getOnlinePlayerService());
	
	public static SceneMessageHandler getHandler() {
		return handler;
	}
}
