package com.pwrd.war.gameserver.player.async;

import org.slf4j.Logger;

import com.pwrd.war.common.LogReasons;
import com.pwrd.war.common.constants.Loggers;
import com.pwrd.war.core.async.IIoOperation;
import com.pwrd.war.core.util.LogUtils;
import com.pwrd.war.gameserver.common.db.operation.BindUUIDIoOperation;
import com.pwrd.war.gameserver.player.Player;
import com.pwrd.war.gameserver.prize.PrizeDef;
import com.pwrd.war.gameserver.prize.UserPrizeHolder;
import com.pwrd.war.gameserver.prize.msg.GCPrizeUserGetSuccess;

/**
 * 领取GM补偿
 * 
 */
public class PlayerGetUserPrize implements BindUUIDIoOperation {
	
	private final static Logger logger = Loggers.prizeLogger;

	private Player player;
	/** 奖励编号 */
	private int prizeId;
	/** -1 非玩家错误 1 玩家错误 0 成功 */
	private volatile int errno;
	/** 奖励原因 */
	private volatile LogReasons.PrizeLogReason reason;
	/** 补偿内容 */
	private volatile UserPrizeHolder holder;

	public PlayerGetUserPrize(Player player, int prizeId) {
		this.player = player;
		this.prizeId = prizeId;
	}

	@Override
	public int doStart() {
		return IIoOperation.STAGE_START_DONE;
	}

	@Override
	public int doIo() {
//		try {
			// 查询游戏补偿具体内容
//			try {
//				holder = Globals.getPrizeService().fetchUserPrizeByPassportId(
//						player.getPassportId(), prizeId);
//			} catch (IllegalArgumentException ex1) {
//				errno = -1;
//				logger.error(LogUtils.buildLogInfoStr(player.getRoleUUID(),
//						"数据奖励配置异常，PlayerGetUserPrize.doIo error, passportId : "
//								+ player.getPassportId()), ex1);
//
//				player.sendErrorMessage(LangConstants.PRIZE_GET_FAIL);
//				return STAGE_IO_DONE;
//			} catch (Exception ex2) {
//				errno = -1;
//				logger.error(LogUtils.buildLogInfoStr(player.getRoleUUID(),
//						"访问奖励数据库异常，PlayerGetUserPrize.doIo error, passportId : "
//								+ player.getPassportId()), ex2);
//				player.sendErrorMessage(LangConstants.PRIZE_GET_FAIL);
//				return STAGE_IO_DONE;
//			}
//
//			// 没有找到奖励
//			if (holder == null) {
//				errno = -1;
//				player.sendErrorMessage(LangConstants.PRIZE_USER_NOT_EXIST);
//				return IIoOperation.STAGE_IO_DONE;
//			}
//
//			// 不满足领取条件
//			if (!holder.checkPlayerCanGet(player)) {
//				errno = 1;
//				return IIoOperation.STAGE_IO_DONE;
//			}
//
//			// 更新数据库记录
//			try {
//				if (!Globals.getPrizeService().updateUserPrizeStatus(prizeId)) {
//					errno = 1;
//					player.sendErrorMessage(LangConstants.PRIZE_USER_ALREADY_FETCHED);
//				}
//			} catch (Exception ex) {
//				errno = -1;
//				logger.error(LogUtils.buildLogInfoStr(player.getRoleUUID(),
//						"访问奖励数据库异常，PlayerGetUserPrize.doIo error, passportId : "
//								+ player.getPassportId()), ex);
//				player.sendErrorMessage(LangConstants.PRIZE_GET_FAIL);
//			}
//		} catch (Exception ex) {
//			errno = -1;
//			logger.error(LogUtils.buildLogInfoStr(player.getRoleUUID(),
//					"领取奖励其它错误，PlayerGetUserPrize.doIo error, passportId : "
//							+ player.getPassportId()), ex);
//			return IIoOperation.STAGE_IO_DONE;
//		}
		return IIoOperation.STAGE_IO_DONE;
	}

	@Override
	public int doStop() {
//		Human human = player.getHuman();
		try {
			// 领取成功
			if (errno == 0) {
				// 再次检查是否可以领取奖励
				if (!holder.checkPlayerCanGet(player)) {
					reason = LogReasons.PrizeLogReason.PRIZE_FAIL_USER_PRIZE_AFTER_UNMEET;
				} else {
					holder.doPrizePlayer(player);
					reason = LogReasons.PrizeLogReason.PRIZE_SUCCESS;
//					player.sendSystemMessage(LangConstants.PRIZE_USER_FETCH_SUCCESS);

					// 向玩家发送奖励领取成功信息
					GCPrizeUserGetSuccess success = new GCPrizeUserGetSuccess(
							PrizeDef.PRIZE_TYPE_GM, prizeId);
					player.sendMessage(success);
				}
			}

			// 发送领取奖励的日志
			if (reason != null) {
//				Globals.getLogService().sendPrizeLog(human, reason, "",
//						human.getSceneId(), PrizeDef.PRIZE_TYPE_GM, prizeId);
			}
		} catch (Exception ex) {
			logger.error(LogUtils.buildLogInfoStr(player.getRoleUUID(),
					"领取奖励其它错误，PlayerGetUserPrize.doStop error, passportId : "
							+ player.getPassportId()), ex);
		} 
		return IIoOperation.STAGE_STOP_DONE;
	}

	@Override
	public String getBindUUID() {
		return player.getRoleUUID();
	}

}
