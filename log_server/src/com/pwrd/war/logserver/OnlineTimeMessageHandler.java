package com.pwrd.war.logserver;

import java.util.List;

import org.apache.mina.common.IoHandlerAdapter;
import org.apache.mina.common.IoSession;
import org.slf4j.Logger;

import com.pwrd.war.core.util.ErrorsUtil;
import com.pwrd.war.logserver.dao.LogDao;
import com.pwrd.war.logserver.model.OnlineTimeLog;
import com.pwrd.war.logserver.util.OnlineTimeLogSeparator;

/**
 * 玩家在线日志消息处理器
 * 
 * 
 */
public class OnlineTimeMessageHandler extends IoHandlerAdapter {
	private static Logger logger = org.slf4j.LoggerFactory.getLogger(OnlineTimeMessageHandler.class);

	@Override
	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		if (logger.isErrorEnabled()) {
			logger.error(ErrorsUtil.error("LOGSERVER_EXCEPTION", "#GS.UpdateMessageHandler.exceptionCaught", cause
					.toString()), cause);
		}
	}

	@Override
	public void messageReceived(IoSession session, Object obj) throws Exception {
		if (obj instanceof OnlineTimeLog) {
			OnlineTimeLog msg = (OnlineTimeLog) obj;
			handleRecord(msg);
		}
	}

	/**
	 * 从最后一次登录时间开始更新日志表，如果存在日志表中则更新，不存在则插入日志表
	 * 
	 * @param msg
	 */
	private void handleRecord(OnlineTimeLog msg) {
		String _logName = LogDao.getLogNameByBeanClass(msg.getClass());
		if (_logName == null) {
			throw new IllegalStateException("Can't find the log name for class [" + msg.getClass() + "]");
		}
		List<BaseLogMessage> _baseUpdateLogMsgs = OnlineTimeLogSeparator.getUpdateLogMsgs(msg);
		if (_baseUpdateLogMsgs == null || _baseUpdateLogMsgs.isEmpty()) {
			return;
		}
		/* 根据更新日志消息更新相关日志表 */
		for (BaseLogMessage _logMsg : _baseUpdateLogMsgs) {
			_logMsg.setCreateTime(System.currentTimeMillis());
			try {
				//查询当前日志表中是否有玩家的记录，有则返回当天的在线时间
				OnlineTimeLog _queryMsg = (OnlineTimeLog) LogDao.getExistedObject(_logName, _logMsg);

				if (_queryMsg != null) { //如果已经存在则更新
					OnlineTimeLog _needUpdateMsg = (OnlineTimeLog) _logMsg;
					int _oldMinute = _queryMsg.getMinute(); //当天已经上线时间
					int _newMinute = _needUpdateMsg.getMinute() + _oldMinute;//当天最新的上线时间
					int _totalMinute = _needUpdateMsg.getTotalMinute();//截止到当天的上线总时间

					if (_newMinute > _totalMinute) {//避免当天时间大于总时间
						_newMinute = _totalMinute;
					}

					_needUpdateMsg.setMinute(_newMinute);
					LogDao.update(_logName, _needUpdateMsg);

				} else { //不存在则插入新记录
					LogDao.insert(_logName, _logMsg);
				}
			} catch (Exception e) {
				//FIXME 1.如果是表不存在捕获的异常,应该继续下条日志的更新 2.其他异常处理
			}
		}
	}
}
