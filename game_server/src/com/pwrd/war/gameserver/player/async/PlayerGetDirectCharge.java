package com.pwrd.war.gameserver.player.async;

import org.slf4j.Logger;

import com.pwrd.war.common.LogReasons;
import com.pwrd.war.common.constants.Loggers;
import com.pwrd.war.core.async.IIoOperation;
import com.pwrd.war.core.util.LogUtils;
import com.pwrd.war.db.model.ChargeInfo;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.common.db.operation.BindUUIDIoOperation;
import com.pwrd.war.gameserver.human.Human;
import com.pwrd.war.gameserver.player.Player;

public class PlayerGetDirectCharge implements BindUUIDIoOperation{
	
	private final static Logger logger = Loggers.directChargeLogger;
	
	/** 充值玩家 */
	private Player player;
	/** 奖励编号 */
	private long orderId;	
	/** -1 非玩家错误 1 玩家错误 0 成功 */
	private volatile int errno;
	
	/** 充值条目 */
	private volatile ChargeInfo chargeInfo;
	
	private volatile LogReasons.ChargeLogReason _reason;
	
	public PlayerGetDirectCharge(Player player, long orderId) {
		this.player = player;
		this.orderId = orderId;
	}

	@Override
	public int doStart() {
		return IIoOperation.STAGE_START_DONE;
	}
	
	@Override
	public int doIo() {
		try {
			// 查询游戏补偿具体内容
			try {
				chargeInfo = Globals.getDaoService().getChargeInfoDao().getChargeInfoByOrderId(player.getPassportId(), orderId);
			} catch (IllegalArgumentException ex1) {
				errno = -1;
				_reason = LogReasons.ChargeLogReason.CHARGE_DIAMOND_INVOKE_FAIL;
				logger.error(LogUtils.buildLogInfoStr(player.getRoleUUID(),
						"取出直充数据异常，PlayerGetDirectCharge.doIo error, passportId : "
								+ player.getPassportId()), ex1);
				return STAGE_IO_DONE;
			} catch (Exception ex2) {
				errno = -1;
				_reason = LogReasons.ChargeLogReason.CHARGE_DIAMOND_INVOKE_FAIL;
				logger.error(LogUtils.buildLogInfoStr(player.getRoleUUID(),
						"取出直充数据异常，PlayerGetDirectCharge.doIo error, passportId : "
								+ player.getPassportId()), ex2);
				return STAGE_IO_DONE;
			}
			
			// 没有找到奖励
			if (chargeInfo == null) {
				errno = -1;
				_reason = LogReasons.ChargeLogReason.CHARGE_DIAMOND_FAIL;
				return IIoOperation.STAGE_IO_DONE;
			}
			
			// 更新数据库记录
			try {
				if (!Globals.getDaoService().getChargeInfoDao().updateChargeInfoStatus(orderId)) {
					errno = 1;
					_reason = LogReasons.ChargeLogReason.CHARGE_DIAMOND_FAIL;
				}
				else
				{
					errno = 0;
					_reason = LogReasons.ChargeLogReason.CHARGE_DIAMOND_SUCCESS;
				}
			} catch (Exception ex) {
				errno = -1;
				_reason = LogReasons.ChargeLogReason.CHARGE_DIAMOND_INVOKE_FAIL;
				logger.error(LogUtils.buildLogInfoStr(player.getRoleUUID(),
						"更新直充数据异常，PlayerGetDirectCharge.doIo error, passportId : "
								+ player.getPassportId()), ex);
			}
		} catch (Exception ex) {
			errno = -1;
			_reason = LogReasons.ChargeLogReason.CHARGE_DIAMOND_INVOKE_FAIL;
			logger.error(LogUtils.buildLogInfoStr(player.getRoleUUID(),
					"领取直充数据其它错误，PlayerGetDirectCharge.doIo error, passportId : "
							+ player.getPassportId()), ex);
			return IIoOperation.STAGE_IO_DONE;
		}
		return IIoOperation.STAGE_IO_DONE;
	}
	
	@Override
	public int doStop() {
		Human _human = player.getHuman();
		
		if (_human == null) {
			// 人已经不在无法继续，记录日志，可以通过日志帮玩家恢复充值
			if (errno == 0) {// 平台返回成功，但是给不了钱了
				Loggers.chargeLogger.error(LogUtils.buildLogInfoStr(player.getPassportId(),
						"调用更新直充数据接口成功，但是给用户金钱时失败"));
			}
			return IIoOperation.STAGE_STOP_DONE;
		}
		//TODO
//		try {
//			
//			int _diamondBefore = _human.getGold();
//			
//	
//			// 领取成功
//			if (errno == 0) {
//				/** 兑换的钻石数量 */
//				int diamondConv = chargeInfo.getMoney() * SharedConstants.DIRECT_CHARGE_MM_2_GOLD_RATE;
//				int _diamondConvMod = diamondConv;
//				int _diamondWill = _diamondBefore + diamondConv;
//				if (_diamondWill <= 0 || _diamondWill > SharedConstants.MAX_DIAMOND_CARRY_AMOUNT) {
//					//钻石溢出，给玩家钻石加到最大值
//					_reason = LogReasons.ChargeLogReason.CHARGE_DIAMOND_OVERFLOW;
//					_diamondConvMod = SharedConstants.MAX_DIAMOND_CARRY_AMOUNT - _diamondBefore;
//				}
//				
//				CurrencyProcessor.instance.giveMoney(_human, _diamondConvMod, Currency.GOLD, false, MoneyLogReason.REASON_MONEY_CHARGE_DIAMOND, 
//						MessageFormat.format(MoneyLogReason.REASON_MONEY_CHARGE_DIAMOND.getReasonText(),chargeInfo.getMoney()));
//				_human.snapChangedProperty(true);
//				
//				player.sendImportantMessage(LangConstants.GAME_CHARGE_DIAMOND_SUCCESS,"" + _diamondConvMod);				
//				if (_reason == LogReasons.ChargeLogReason.CHARGE_DIAMOND_OVERFLOW) {
//					//如果钻石溢出，给玩家发送提醒
//					player.sendErrorMessage(LangConstants.GAME_AFTER_CHARGE_DIAMOND_OVER_FLOW);
//				}
//
//				//处理任务兑换钻石
//				PlayerChargeDiamondEvent _event = new PlayerChargeDiamondEvent(_human, _diamondConvMod);
//				Globals.getEventService().fireEvent(_event);
//			} 
//			
//			int _diamondAfter = _human.getGold();
//
//			//发送充值日志
//			Globals.getLogService().sendChargeLog(_human, _reason, "errno=" + errno, Currency.GOLD.getIndex(), _diamondBefore, _diamondAfter, chargeInfo.getMoney(), "");
//
//			
//		} catch (Exception ex) {
//			logger.error(LogUtils.buildLogInfoStr(player.getRoleUUID(),
//					"领取奖励其它错误，PlayerGetDirectCharge.doStop error, passportId : "
//							+ player.getPassportId()), ex);
//		} 
//		
		return IIoOperation.STAGE_STOP_DONE;
	}
	
	@Override
	public String getBindUUID() {
		return player.getRoleUUID();
	}

}
