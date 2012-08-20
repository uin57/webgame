package com.pwrd.war.logserver.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.pwrd.war.logserver.BaseLogMessage;
import com.pwrd.war.logserver.dao.LogDao;
import com.pwrd.war.logserver.model.OnlineTimeLog;

/**
 * 玩家在线时间日志的拆分工具，把跨天的在线日志分割成每天一条日志信息
 *
 * 
 */
public class OnlineTimeLogSeparator {
	/** 日志表的名称 */
	private static final String LOGNAME = LogDao
			.getLogNameByBeanClass(OnlineTimeLog.class);
	/** 1分钟转换成ms */
	private static final int MINUTE_TO_MISCOND = 60 * 1000;

	/**
	 * 根据最近一次登录和登出的时间，把玩家在线日志信息拆分成每天一条日志
	 */
	public static List<BaseLogMessage> getUpdateLogMsgs(
			OnlineTimeLog onlineTimeLog) {
		List<BaseLogMessage> _updateBaseLogMsgs = new ArrayList<BaseLogMessage>();
		SimpleDateFormat _dayFormat = new SimpleDateFormat("yyyy_MM_dd");
		long _endDayTime = 0;
		// 某天的在线总时间
		int _totalMinuteInOneDay = 0;
		long _lastLoginTimeTp = onlineTimeLog.getLastLoginTime();
		long _lastLogoutTimeTp = onlineTimeLog.getLastLogoutTime();
		// 登录时的在线总时间
		int _totalMinuteToOneDay = onlineTimeLog.getTotalMinute()
				- (int) ((_lastLogoutTimeTp - _lastLoginTimeTp) / MINUTE_TO_MISCOND);

		try {
			/* 从最后一次登录时间开始，循环每天的日志表操作 */
			while (_lastLoginTimeTp < _lastLogoutTimeTp) {
				String dayStamp = _dayFormat.format(_lastLoginTimeTp);
				_endDayTime = getDayEnd(_lastLoginTimeTp);// 获得当天的结束时间
				/* 登陆登出时间不在一天内 */
				if (_endDayTime <= _lastLogoutTimeTp) {
					_endDayTime += 1000;// 加1秒进入后一天
					_totalMinuteInOneDay = (int) ((_endDayTime - _lastLoginTimeTp) / MINUTE_TO_MISCOND);
					_totalMinuteToOneDay += _totalMinuteInOneDay;
					_lastLoginTimeTp = _endDayTime;

					if (!isValidTime(dayStamp, _lastLoginTimeTp, _dayFormat)) {
						/* 不是有效时间，为了防止死循环，抛出异常 */
						throw new IllegalStateException(
								"com.mop.lzr.logserver.model.OnlineTimeLog.getUpdateBaseLogMsg Parse time exception [dayStamp - "
										+ _lastLoginTimeTp + "]");
					}
				}
				/* 登录登出时间在一天内 */
				else {
					_totalMinuteInOneDay = (int) ((_lastLogoutTimeTp - _lastLoginTimeTp) / MINUTE_TO_MISCOND);
					_totalMinuteToOneDay += _totalMinuteInOneDay;
					_lastLoginTimeTp = _lastLogoutTimeTp;
				}

				if (_totalMinuteInOneDay > _totalMinuteToOneDay) {// 防止当天在线总时间大于在线总时间
					_totalMinuteInOneDay = _totalMinuteToOneDay;
				}

				OnlineTimeLog _onlineTimeLog = new OnlineTimeLog(onlineTimeLog
						.getLogTime(), onlineTimeLog.getRegionId(),
						onlineTimeLog.getServerId(), onlineTimeLog
								.getAccountId(),
						onlineTimeLog.getAccountName(), onlineTimeLog
								.getCharId(), onlineTimeLog.getCharName(),
						onlineTimeLog.getLevel(), onlineTimeLog.getAllianceId(),
						onlineTimeLog.getVipLevel(),
						onlineTimeLog.getReason(),onlineTimeLog.getSceneId(),onlineTimeLog.getX(),onlineTimeLog.getY(), 
						onlineTimeLog.getParam(),
						_totalMinuteInOneDay, _totalMinuteToOneDay,
						onlineTimeLog.getLastLoginTime(), onlineTimeLog
								.getLastLogoutTime());
				_onlineTimeLog.setTableName(LOGNAME + "_" + dayStamp);
				_updateBaseLogMsgs.add(_onlineTimeLog);
			}
		} catch (Exception e) {
			throw new IllegalStateException(
					"com.mop.lzr.logserver.OnlineTimeMessageHandler.getUpdateBaseLogMsg Parse time exception ["
							+ e + "]");
		}
		return _updateBaseLogMsgs;
	}

	/**
	 * 判断某天的后一天时间是否有效
	 * 
	 * @param dayStamp
	 *            某天时间的字符串格式
	 * @param tomorrowTime
	 *            后一天时间（ms）
	 * @return
	 */
	private static boolean isValidTime(String dayStamp, long tomorrowTime,
			SimpleDateFormat dayFormat) {
		String _tomorrowStamp = dayFormat.format(tomorrowTime);
		if (dayStamp.compareToIgnoreCase(_tomorrowStamp) < 0) {
			return true;
		}
		return false;
	}

	/**
	 * 获得一天的结束时间
	 * 
	 * @param curTime
	 * @return
	 */
	private static long getDayEnd(long curTime) {
		Calendar st = Calendar.getInstance();
		st.setTimeInMillis(curTime);
		st.set(Calendar.HOUR_OF_DAY, 23);
		st.set(Calendar.MINUTE, 59);
		st.set(Calendar.SECOND, 59);
		return st.getTimeInMillis();
	}
}
