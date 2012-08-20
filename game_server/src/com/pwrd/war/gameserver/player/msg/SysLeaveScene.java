package com.pwrd.war.gameserver.player.msg;

import com.pwrd.war.core.msg.SysInternalMessage;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.player.Player;
import com.pwrd.war.gameserver.scene.PlayerLeaveSceneCallback;
import com.pwrd.war.gameserver.scene.handler.SceneHandlerFactory;

/**
 * 离开场景请求，主线程发给场景线程
 * 
 */
public class SysLeaveScene extends SysInternalMessage {

	private int playerId; 
	
	private PlayerLeaveSceneCallback callback;

	public int getPlayerId() {
		return playerId;
	}

	public SysLeaveScene(int playerId , PlayerLeaveSceneCallback callback) {
		super();
		this.playerId = playerId; 
		this.callback = callback; 
	}


	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}

	 

	@Override
	public String getTypeName() {
		return "SysLeaveSceneMsg";
	}

	@Override
	public void execute() {
		Player player = Globals.getOnlinePlayerService().getPlayerByTempId(playerId);
		if (player == null) {//maybe the player has exit
			return;
		}
		SceneHandlerFactory.getHandler().handleLeaveScene(player );
		SysLeaveSceneResult resultMsg = new SysLeaveSceneResult(playerId,callback);
		Globals.getMessageProcessor().put(resultMsg);
	}

	 

}
