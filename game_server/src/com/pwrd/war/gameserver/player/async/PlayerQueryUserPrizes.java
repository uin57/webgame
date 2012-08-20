package com.pwrd.war.gameserver.player.async;

import java.util.List;

import org.slf4j.Logger;

import com.pwrd.war.common.constants.Loggers;
import com.pwrd.war.core.async.IIoOperation;
import com.pwrd.war.core.util.LogUtils;
import com.pwrd.war.db.model.UserPrize;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.player.Player;
import com.pwrd.war.gameserver.prize.UserPrizeInfo;
import com.pwrd.war.gameserver.prize.msg.GCPrizeUserList;

/**
 * 查询游戏补偿
 * 
 * 
 */
public class PlayerQueryUserPrizes implements IIoOperation {

	private final static Logger logger = Loggers.prizeLogger;

	private Player player;

	private int errno; // -1 非玩家错误 1 玩家问题 0 成功

	/**
	 * 奖励名称列表
	 */
	private List<UserPrize> prizeList;

	public PlayerQueryUserPrizes(Player player) {
		this.player = player;
	}

	@Override
	public int doIo() {

		try {
			// 查询数据库
			this.prizeList = Globals.getPrizeService()
					.fetchUserPrizeNameListByPassportId(player.getPassportId());
		} catch (Exception e) {
			errno = -1;
			if (logger.isErrorEnabled()) {
				logger.error(LogUtils.buildLogInfoStr(player.getRoleUUID(),
						"访问奖励数据库异常，PlayerQueryUserPrizes.doIo，passportId = "
								+ player.getPassportId()));
			}
		}

		// 没有奖励补偿
		if (prizeList == null || prizeList.size() == 0) {
			errno = 1;
		}

		return IIoOperation.STAGE_IO_DONE;
	}

	@Override
	public int doStart() {

		return IIoOperation.STAGE_START_DONE;
	}

	@Override
	public int doStop() {
		// 查询成功
		if (errno == 0) {
			List<UserPrizeInfo> _basicInfos = UserPrizeInfo
					.getFromUserPrizes(prizeList);
			GCPrizeUserList msg = new GCPrizeUserList(_basicInfos
					.toArray(new UserPrizeInfo[0]));
			player.sendMessage(msg);
		} else if (errno == -1) {
			// 查询不到奖励补偿，系统问题
//			player.sendErrorMessage(LangConstants.PRIZE_GET_FAIL);
		} else {
			// 没有奖励补偿
			//player.sendErrorMessage(LangConstants.PRIZE_USER_NOT_EXIST);
		}
		return IIoOperation.STAGE_STOP_DONE;
	}

}
