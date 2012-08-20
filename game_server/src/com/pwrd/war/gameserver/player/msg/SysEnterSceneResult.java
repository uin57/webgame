package com.pwrd.war.gameserver.player.msg;

import com.pwrd.war.common.constants.Loggers;
import com.pwrd.war.core.msg.SysInternalMessage;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.player.GamingStateIndex;
import com.pwrd.war.gameserver.player.Player;
import com.pwrd.war.gameserver.player.PlayerExitReason;
import com.pwrd.war.gameserver.player.PlayerState;

/**
 * 进入场景结果，场景线程发给主线程,主要是设置玩家状态
 * 
 * 
 */
public class SysEnterSceneResult extends SysInternalMessage {
	private boolean isSuccess;
	private int playerId;

	public SysEnterSceneResult(boolean isSuccess, int playerId) {
		this.isSuccess = isSuccess;
		this.playerId = playerId;
	}

	@Override
	public void execute() {
		Player player = Globals.getOnlinePlayerService().getPlayerByTempId(playerId);
		if (player == null) {
			return;
		}
		if (player.getState() == PlayerState.logouting) {
			//其他消息正在处理了，所以直接返回就行了
//			Globals.getOnlinePlayerService().offlinePlayer(player, player.exitReason);
			return;
		}
		if (!isSuccess) {
			Loggers.gameLogger
					.error("Enter scene result is false , will kick player:"
							+ player.getRoleUUID());
			Globals.getOnlinePlayerService().offlinePlayer(player, PlayerExitReason.SERVER_ERROR);
			return;
		}
		if(player.getState() == PlayerState.gaming &&
				player.getHuman().containsGamingState(GamingStateIndex.IN_SWITCHSCENE)){//切换地图时不需要设置为gaming
			player.getHuman().setGamingState(GamingStateIndex.IN_NOMAL);
		}else{//只有在第一次切换场景时需要设置为gaming
			player.setState(PlayerState.gaming);
		}
	}

}
