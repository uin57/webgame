package com.pwrd.war.gameserver.player.msg;

import com.pwrd.war.common.constants.Loggers;
import com.pwrd.war.core.msg.SysInternalMessage;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.player.GamingStateIndex;
import com.pwrd.war.gameserver.player.Player;
import com.pwrd.war.gameserver.player.PlayerState;
import com.pwrd.war.gameserver.scene.PlayerLeaveSceneCallback;

/**
 * 离开场景结果，场景线程发给主线程
 * 
 */
public class SysLeaveSceneResult extends SysInternalMessage {
	private int playerId;
	
	private PlayerLeaveSceneCallback callback;


	public SysLeaveSceneResult(int playerId,PlayerLeaveSceneCallback callback) {
		this.playerId = playerId;
		this.callback = callback;
	}

	@Override
	public void execute() {
		Player player = Globals.getOnlinePlayerService().getPlayerByTempId(
				playerId);
		if (player == null) {
			return;
		}
		
		
		
		if (player.getState() == PlayerState.logouting) {
			if (callback != null) {
				callback.afterLeaveScene(player);
			}
			Globals.getOnlinePlayerService().offlinePlayer(player,player.exitReason);
			return;
		}
		if (player.getState() != PlayerState.leaving && 
				!player.getHuman().containsGamingState(GamingStateIndex.IN_SWITCHSCENE)) {
			Loggers.gameLogger.warn("玩家离开场景回到主线程之后的场景不是leaving状态和switching状态"+player.getState());
		}
		if (callback != null) {
			callback.afterLeaveScene(player);
		}
	}

}
