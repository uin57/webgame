package com.pwrd.war.gameserver.wallow.async;

import java.util.ArrayList;
import java.util.List;

import com.pwrd.war.common.constants.SharedConstants;
import com.pwrd.war.core.async.IIoOperation;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.common.config.GameServerConfig;
import com.pwrd.war.gameserver.player.Player;
import com.pwrd.war.gameserver.wallow.msg.WallowOnlineTimeMsg;

/**
 * 获取用户累计在线时长
 *
 *
 */
public class FetchOnlineTimeOperation implements IIoOperation {

	/** 要获取在线时长的用户的passportId */
	private List<String> passportIds;
	
	/** 时长 :秒*/
	private List<Long> seconds;
	
	private int addTime = 0;
	public FetchOnlineTimeOperation(List<String> passportIds, int addTime) {
		this.passportIds = passportIds;
		this.seconds = new ArrayList<Long>(passportIds.size());
		this.addTime = addTime;
	}
	
	@Override
	public int doIo() {
		
		for (String ppId : passportIds) {
			long sec = fetchOnlineTimeForPlayer(ppId);
			seconds.add(sec);
		}
		
		return IIoOperation.STAGE_IO_DONE;
	}

	@Override
	public int doStart() {
		return IIoOperation.STAGE_START_DONE;
	}

	@Override
	public int doStop() {
		
		//更新游戏中玩家在线时间,进行防沉迷提醒和处理
		WallowOnlineTimeMsg _timeMsg = new WallowOnlineTimeMsg();
		_timeMsg.setPlayers(passportIds);
		_timeMsg.setSeconds(seconds);
		
		Globals.getMessageProcessor().put(_timeMsg);
		return IIoOperation.STAGE_STOP_DONE;
	}
	
	private long fetchOnlineTimeForPlayer(String passportId) {
//		Player player = Globals.getOnlinePlayerService().getPlayerByPassportId(passportId);
//		if (player == null) {
//			return -1; // 下线了
//		}
		
		GameServerConfig config = Globals.getServerConfig();
		if (!config.isTurnOnLocalInterface() || config.getAuthType() != SharedConstants.AUTH_TYPE_INTERFACE) {
			return -1;
		}
		
//		return Globals.getSynLocalService().queryOnlineTime(player.getPassportId());
		return Globals.getWallowService().getWallowTime(passportId, addTime);
	}

}
