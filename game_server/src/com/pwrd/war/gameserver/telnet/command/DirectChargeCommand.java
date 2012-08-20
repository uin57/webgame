package com.pwrd.war.gameserver.telnet.command;

import java.sql.Timestamp;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.mina.common.IoSession;
import org.slf4j.Logger;

import com.pwrd.war.common.constants.CommonErrorLogInfo;
import com.pwrd.war.common.constants.Loggers;
import com.pwrd.war.common.constants.SharedConstants;
import com.pwrd.war.core.orm.DataAccessException;
import com.pwrd.war.core.util.ErrorsUtil;
import com.pwrd.war.core.util.MD5Util;
import com.pwrd.war.db.model.ChargeInfo;
import com.pwrd.war.db.model.UserInfo;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.player.Player;
import com.pwrd.war.gameserver.player.async.PlayerGetDirectCharge;

public class DirectChargeCommand extends AbstractTelnetCommand {
	
	/** 流水号 key字符串 */
	private static final String ORDER_ID_KEY = "orderId";
	/** 用户名 key字符串 */
	private static final String USER_ID_KEY = "userId";
	/** 充值金额 key字符串 */
	private static final String MONEY_KEY = "money";
	/** 时间戳 key字符串 */
	private static final String TIME_KEY = "time";
	/** MD5签名 key字符串 */
	private static final String SIGN_KEY = "sign";
	
	/** 充值成功 */
	private static final String RESULT_CODE_SUCCESS = "0";
	/** 充值失败,未知原因 */
	private static final String RESULT_CODE_FAIL_UNKNOWN_REASON = "1";
	/** 用户名不存在 */
	private static final String RESULT_CODE_FAIL_USER_NOT_EXIST = "2";	
	/** 充值金额不在范围内 */
	private static final String RESULT_CODE_FAIL_OVER_LIMIT = "3";
	/** 订单已经存在 */
	private static final String RESULT_CODE_FAIL_SAME_ORDER = "4";
//	/** IP限制 */
//	private static final String RESULT_CODE_FAIL_IP_LIMIT = "5";
	/** SIGN错误 */
	private static final String RESULT_CODE_FAIL_SIGN_ERR = "7";
	/** 参数个数不对 */
	private static final String RESULT_CODE_FAIL_PARAM_ERR = "8";
	
	private final Logger logger = Loggers.directChargeLogger;

	public DirectChargeCommand() {
		super("DIRECT_CHARGE");
	}

	@Override
	protected void doExec(String command, Map<String, String> params, IoSession session) {
		String _param = getCommandParam(command);
		if (_param.length() == 0) {
			sendError(session, RESULT_CODE_FAIL_PARAM_ERR);
			return;
		}
		
		JSONObject _json = JSONObject.fromObject(_param);
		
		
		if (!_json.containsKey(ORDER_ID_KEY)||
			!_json.containsKey(USER_ID_KEY)||
			!_json.containsKey(MONEY_KEY)||
			!_json.containsKey(TIME_KEY)||
			!_json.containsKey(SIGN_KEY)) 
		{
			sendError(session, RESULT_CODE_FAIL_PARAM_ERR);
			return;
		}
		
		long _orderId = 0;		
		try{
			String _strOrderId = _json.getString(ORDER_ID_KEY);
			_orderId = Long.parseLong(_strOrderId);
		
		}catch(Exception ex)
		{
			sendError(session, RESULT_CODE_FAIL_UNKNOWN_REASON);
			return;
		}
		
		String _userId = "";
		try{
			String _strUserId = _json.getString(USER_ID_KEY);
//			_userId = Long.parseLong(_strUserId);
			_userId = _strUserId;
		
		}catch(Exception ex)
		{
			sendError(session, RESULT_CODE_FAIL_UNKNOWN_REASON);
			return;
		}
		
		int _money = 0;
		try{
			String _strMoney = _json.getString(MONEY_KEY);
			_money = Integer.parseInt(_strMoney);
		
		}catch(Exception ex)
		{
			sendError(session, RESULT_CODE_FAIL_UNKNOWN_REASON);
			return;
		}
		
		if(_money <= 0 || _money > SharedConstants.MAX_CHARGE_AMOUNT)
		{
			sendError(session, RESULT_CODE_FAIL_OVER_LIMIT);
			return;
		}
		
		long _time = 0;
		try{
			String _strTime = _json.getString(TIME_KEY);
			_time = Long.parseLong(_strTime);
		
		}catch(Exception ex)
		{
			sendError(session, RESULT_CODE_FAIL_UNKNOWN_REASON);
			return;
		}
		
		//参数中的MD5串
		String _strSign = _json.getString(SIGN_KEY);				
		//自己拼接的MD5原始串
		String _inputString = String.valueOf(_orderId) + String.valueOf(_userId) + String.valueOf(_money) + String.valueOf(_time) + SharedConstants.HITHERE_MD5_KEY;
		
		if(!MD5Util.authMD5String(_strSign, _inputString))
		{
			sendError(session, RESULT_CODE_FAIL_SIGN_ERR);
			return;
		}
		
		UserInfo _userInfo = Globals.getDaoService().getUserInfoDao().get(_userId);
		if(_userInfo == null)
		{
			//用户不存在
			sendError(session, RESULT_CODE_FAIL_USER_NOT_EXIST);
			return;
		}
		
		ChargeInfo _chargeInfo = Globals.getDaoService().getChargeInfoDao().get(_orderId);
		if(_chargeInfo != null)
		{
			//订单已经存在
			sendError(session, RESULT_CODE_FAIL_SAME_ORDER);
			return;
		}
		
		ChargeInfo newRecord = new ChargeInfo();
		newRecord.setId(_orderId);
		newRecord.setUserId(_userId);
		newRecord.setMoney(_money);
		newRecord.setStatus(0);
		newRecord.setTime(new Timestamp(_time));
		newRecord.setCreateTime(new Timestamp(Globals.getTimeService().now()));
		
		try{
			Globals.getDaoService().getChargeInfoDao().save(newRecord);		
			sendMessage(session, RESULT_CODE_SUCCESS);			
			
			Player onlinePlayer = Globals.getOnlinePlayerService().getPlayerByPassportId(_userId);
			if(onlinePlayer != null)
			{
				if(onlinePlayer.isInScene())
				{
					PlayerGetDirectCharge _chargOper = new PlayerGetDirectCharge(onlinePlayer, _orderId);
					Globals.getAsyncService().createOperationAndExecuteAtOnce(_chargOper,onlinePlayer.getRoleUUID());
				}		
			}
			
			
		}catch (DataAccessException e) {
			if (logger.isErrorEnabled()) {
				logger.error(ErrorsUtil.error(
						CommonErrorLogInfo.DB_OPERATE_FAIL,
						"#GS.DirectChargeCommand.doExec", null), e);
			}
			sendError(session, RESULT_CODE_FAIL_UNKNOWN_REASON);
			return;
		}catch (Exception e) {
			sendError(session, RESULT_CODE_FAIL_UNKNOWN_REASON);
			return;
		}
		
		
	}

}
