package com.pwrd.war.gameserver.player.async;

import org.slf4j.Logger;

import com.pwrd.war.common.constants.Loggers;
import com.pwrd.war.core.async.IIoOperation;
import com.pwrd.war.core.util.ErrorsUtil;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.common.log.GameErrorLogInfo;
import com.pwrd.war.gameserver.human.Human;
import com.pwrd.war.gameserver.player.Player;
import com.pwrd.war.gameserver.player.msg.GCPlayerQueryAccount;

/**
 * 查询玩家平台账户余额
 *
 */
public class PlayerQueryAccount implements IIoOperation {

	/** player的uuid */
	private String roleUUID;
	
	
	private int mmCount;
	private int errno = -1;
	private static final Logger logger = Loggers.chargeLogger;
	
	public PlayerQueryAccount(String roleUUID){
		this.roleUUID = roleUUID;
	}
	
	
	@Override
	public int doIo() {
		Player player = Globals.getOnlinePlayerService().getPlayer(roleUUID);
		if (player == null) {
			// 直接结束
			return STAGE_STOP_DONE;
		}
		
		Human human = player.getHuman();
		if (human == null) {
			// 直接结束
			return STAGE_STOP_DONE;
		}

		
//		QueryBalanceResponse _queryResponse = null;
//		//调用接口了
//		try {
//			_queryResponse = Globals.getSynLocalService().queryBalance(player.getPassportId(),
//					player.getHuman().getLastLoginIp());
//		} catch (Exception e) {
//			if (logger.isErrorEnabled()) {
//				logger.error(ErrorsUtil.error(GameErrorLogInfo.QUERY_ACCOUNT_INVOKE_FAIL, "#GS.PlayerQueryAccount.doIo", ""), e);
//			}
//			return IIoOperation.STAGE_IO_DONE;
//		}
		//处理返回结果
//		if (_queryResponse == null) {
//			if (logger.isErrorEnabled()) {
//				logger.error(ErrorsUtil.error(GameErrorLogInfo.QUERY_ACCOUNT_INVOKE_FAIL, "#GS.PlayerQueryAccount.doIo", ""));
//			}
//			return IIoOperation.STAGE_IO_DONE;
//		}
//
//		if (_queryResponse.isSuccess()) {
//			mmCount = _queryResponse.getBalance();
//			if (mmCount < 0) {
//				mmCount = 0;
//			}
//			errno = 0;
//			return IIoOperation.STAGE_IO_DONE;
//		}
//
//		errno = _queryResponse.getErrorCode();
		if (errno == 1) {
			//签名错误
			if (logger.isErrorEnabled()) {
				logger.error(ErrorsUtil.error(GameErrorLogInfo.QUERY_ACCOUNT_INVOKE_FAIL, "#GS.PlayerQueryAccount.doIo",
						"sign error!"));
			}
		} else if (errno == 2) {
			//时间戳错误
			if (logger.isErrorEnabled()) {
				logger.error(ErrorsUtil.error(GameErrorLogInfo.QUERY_ACCOUNT_INVOKE_FAIL, "#GS.PlayerQueryAccount.doIo",
						"timestamp error!"));
			}
		} else if (errno == 3) {
			//参数不正确
			if (logger.isErrorEnabled()) {
				logger.error(ErrorsUtil.error(GameErrorLogInfo.QUERY_ACCOUNT_INVOKE_FAIL, "#GS.PlayerQueryAccount.doIo",
						"parameter error!"));
			}
		} else if (errno == 999) {
			//系统异常，查询操作不成功
			if (logger.isErrorEnabled()) {
				logger.error(ErrorsUtil.error(GameErrorLogInfo.QUERY_ACCOUNT_INVOKE_FAIL, "#GS.PlayerQueryAccount.doIo",
						"system exception!"));
			}
		} else {
			//未知错误
			if (logger.isErrorEnabled()) {
				logger.error(ErrorsUtil.error(GameErrorLogInfo.QUERY_ACCOUNT_INVOKE_FAIL, "#GS.PlayerQueryAccount.doIo",
						"unknown error! result=" + errno));
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
		
		Player player = Globals.getOnlinePlayerService().getPlayer(roleUUID);
		if (player == null) {
			return IIoOperation.STAGE_STOP_DONE;
		}
		
		Human human = player.getHuman();
		if (human == null) {
			return IIoOperation.STAGE_STOP_DONE;
		}
		
		if(errno == 0){
			//发送查询结果
			GCPlayerQueryAccount _msg = new GCPlayerQueryAccount(mmCount);
			player.sendMessage(_msg);
		}else if(errno > 0){
			//查询失败
//			player.sendErrorMessage(CommonLangConstants.GAME_QUERY_ACCOUNT_FAIL);
		}else{
//			player.sendErrorMessage(LangConstants.GAME_QUERY_ACCOUNT_INVOKE_FAIL);
		}
		return IIoOperation.STAGE_STOP_DONE;
	}
	
}