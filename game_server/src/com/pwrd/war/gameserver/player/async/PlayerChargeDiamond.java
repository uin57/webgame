package com.pwrd.war.gameserver.player.async;

import org.slf4j.Logger;

import com.pwrd.war.common.LogReasons;
import com.pwrd.war.common.constants.Loggers;
import com.pwrd.war.common.constants.PMKey;
import com.pwrd.war.core.async.IIoOperation;
import com.pwrd.war.core.util.ErrorsUtil;
import com.pwrd.war.core.util.PM;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.common.db.operation.BindUUIDIoOperation;
import com.pwrd.war.gameserver.common.log.GameErrorLogInfo;
import com.pwrd.war.gameserver.human.Human;
import com.pwrd.war.gameserver.player.Player;

/**
 * 玩家充钻石
 * 
 * 
 */
public class PlayerChargeDiamond implements BindUUIDIoOperation {
	private static final Logger logger = Loggers.chargeLogger;

	/** player的uuid */
	private String roleUUID;
	
	/** 兑换的平台币数量 */
	private int mmCost;
	/** 兑换的钻石数量 */
	private int diamondConv;
	/** 兑换后平台币余数 */
	private volatile int mmLeft;
	/** 错误号 */
	private volatile int errno = -1;
	private volatile LogReasons.ChargeLogReason _reason;

	public PlayerChargeDiamond(String roleUUID, int mmCost, int diamondConv) {
		this.roleUUID = roleUUID;		
		this.mmCost = mmCost;
		this.diamondConv = diamondConv;
	}

	@Override
	public int doIo() {
		Player player = Globals.getOnlinePlayerService().getPlayer(roleUUID);
		if (player == null) {
			// 直接结束
			return STAGE_STOP_DONE;
		}
		Human _human = player.getHuman();
		if (_human == null) {
			// 直接结束
			return STAGE_STOP_DONE;
		}
		
//		TransferResponse _transferResponse = null;
		//调用接口了
//		try {
//			_transferResponse = Globals.getSynLocalService().transfer(_human.getPassportId(), "" + _human.getId(), mmCost,
//					_human.getLastLoginIp());
//		} catch (Exception e) {
//			_reason = LogReasons.ChargeLogReason.CHARGE_DIAMOND_INVOKE_FAIL;
//			if (logger.isErrorEnabled()) {
//				logger.error(ErrorsUtil.error(GameErrorLogInfo.CHARGE_DIAMOND_INVOKE_FAIL, "#GS.PlayerChargeDiamond.doIo", ""), e);
//			}
//			return IIoOperation.STAGE_IO_DONE;
//		}
//
//		if (_transferResponse == null) {
//			if (logger.isErrorEnabled()) {
//				logger.error(ErrorsUtil.error(GameErrorLogInfo.CHARGE_DIAMOND_INVOKE_FAIL, "#GS.PlayerChargeDiamond.doIo", ""));
//			}
//			return IIoOperation.STAGE_IO_DONE;
//		}

//		if (_transferResponse.isSuccess()) {
//			long _userId = _transferResponse.getUserId();
//			mmLeft = _transferResponse.getBalance();
//			if (_userId == _human.getPassportId()) {
//				//加钻石放入doStop主线程中处理
//				errno = 0;
//				_reason = LogReasons.ChargeLogReason.CHARGE_DIAMOND_SUCCESS;
//			} else {
//				//返回结果格式不对
//			}
//
//			return IIoOperation.STAGE_IO_DONE;
//		}

//		errno = _transferResponse.getErrorCode();
		_reason = LogReasons.ChargeLogReason.CHARGE_DIAMOND_FAIL;
		if (errno == 1) {
			//签名错误
			if (logger.isErrorEnabled()) {
				logger.error(ErrorsUtil.error(GameErrorLogInfo.CHARGE_DIAMOND_INVOKE_FAIL, "#GS.PlayerChargeDiamond.doIo", PM.kv(
						PMKey.REASON, "Timestamp error!").tos()));
			}
		} else if (errno == 2) {
			//时间戳错误
			if (logger.isErrorEnabled()) {
				logger.error(ErrorsUtil.error(GameErrorLogInfo.CHARGE_DIAMOND_INVOKE_FAIL, "#GS.PlayerChargeDiamond.doIo", PM.kv(
						PMKey.REASON, "Sign error!").tos()));
			}
		} else if (errno == 3) {
			//参数格式错误
			if (logger.isErrorEnabled()) {
				logger.error(ErrorsUtil.error(GameErrorLogInfo.CHARGE_DIAMOND_INVOKE_FAIL, "#GS.PlayerChargeDiamond.doIo", PM.kv(
						PMKey.REASON, "Parameter error!").tos()));
			}
		} else if (errno == 4) {
			//余额不足
			if (logger.isErrorEnabled()) {
				logger.error(ErrorsUtil.error(GameErrorLogInfo.CHARGE_DIAMOND_INVOKE_FAIL, "#GS.PlayerChargeDiamond.doIo", PM.kv(
						PMKey.REASON, "left mmo not enough error!").tos()));
			}
		} else {
			//未知错误
			if (logger.isErrorEnabled()) {
				logger.error(ErrorsUtil.error(GameErrorLogInfo.CHARGE_DIAMOND_INVOKE_FAIL, "#GS.PlayerChargeDiamond.doIo", PM.kv(
						PMKey.REASON, "Unknown error!").tos()));
			}
		}

		return IIoOperation.STAGE_IO_DONE;
	}

	@Override
	public int doStart() {
		return IIoOperation.STAGE_START_DONE;
	}

	@Override
	public int doStop() {
		//TODO 
//		Player player = Globals.getOnlinePlayerService().getPlayer(roleUUID);
//		if (player == null) {
//			// 人已经不在无法继续，记录日志，可以通过日志帮玩家恢复充值
//			if (errno == 0) {// 平台返回成功，但是给不了钱了
//				Loggers.chargeLogger.error(LogUtils.buildLogInfoStr(roleUUID,
//						"调用充值接口成功，但是给用户金钱时失败"));
//			}
//			return IIoOperation.STAGE_STOP_DONE;
//		}
//		
//		
//		Human _human = player.getHuman();
//		if (_human == null) {
//			// 人已经不在无法继续，记录日志，可以通过日志帮玩家恢复充值
//			if (errno == 0) {// 平台返回成功，但是给不了钱了
//				Loggers.chargeLogger.error(LogUtils.buildLogInfoStr(roleUUID,
//						"调用充值接口成功，但是给用户金钱时失败"));
//			}
//			return IIoOperation.STAGE_STOP_DONE;
//		}
//		
//		int _diamondBefore = _human.getGold();
//		if (errno == 0) {
//			//加钻石
//			int _diamondConvMod = diamondConv;
//			int _diamondWill = _diamondBefore + diamondConv;
//			if (_diamondWill <= 0 || _diamondWill > SharedConstants.MAX_DIAMOND_CARRY_AMOUNT) {
//				//钻石溢出，给玩家钻石加到最大值
//				_reason = LogReasons.ChargeLogReason.CHARGE_DIAMOND_OVERFLOW;
//				_diamondConvMod = SharedConstants.MAX_DIAMOND_CARRY_AMOUNT - _diamondBefore;
//			}
//		
//			CurrencyProcessor.instance.giveMoney(_human, _diamondConvMod, Currency.GOLD, false, MoneyLogReason.REASON_MONEY_CHARGE_DIAMOND, 
//					MessageFormat.format(MoneyLogReason.REASON_MONEY_CHARGE_DIAMOND.getReasonText(),mmCost));
//			_human.snapChangedProperty(true);
//			//发送成功消息
//			GCPlayerChargeDiamond _msg = new GCPlayerChargeDiamond(mmLeft);
//			player.sendMessage(_msg);
//			player.sendImportantMessage(LangConstants.GAME_CHARGE_DIAMOND_SUCCESS,"" + _diamondConvMod);
//			if (_reason == LogReasons.ChargeLogReason.CHARGE_DIAMOND_OVERFLOW) {
//				//如果钻石溢出，给玩家发送提醒
//				player.sendErrorMessage(LangConstants.GAME_AFTER_CHARGE_DIAMOND_OVER_FLOW);
//			}
//
//			//处理任务兑换钻石
//			PlayerChargeDiamondEvent _event = new PlayerChargeDiamondEvent(_human, _diamondConvMod);
//			Globals.getEventService().fireEvent(_event);
//		} else if (errno == 4) {
//			//发送失败消息，余额不足
//			player.sendErrorMessage(LangConstants.GAME_CHARGE_DIAMOND_FAIL);
//		} else {
//			//调用接口异常
//			player.sendErrorMessage(LangConstants.GAME_CHARGE_DIAMOND_INVOKE_FAIL);
//		}
//		int _diamondAfter = _human.getGold();

		//发送充值日志
//		Globals.getLogService().sendChargeLog(_human, _reason, "errno=" + errno, Currency.GOLD.getIndex(), _diamondBefore, _diamondAfter, mmCost, "");

		return IIoOperation.STAGE_STOP_DONE;
	}

	@Override
	public String getBindUUID() {	
		return roleUUID;
	}

}