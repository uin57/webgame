package com.pwrd.war.core.util;

import java.util.Map;

import org.apache.log4j.Level;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.impl.Log4jLoggerAdapter;
import org.slf4j.impl.Log4jLoggerFactory;

/**
 * 日志相关工具类
 * 
 * 
 */
public class LogUtils {
	
	/** 统一日志模板 */
	private static final String LOG_TEMPLATE = "[player=%s]%s";
	
	/**
	 * 生成统一格式的日志字串
	 * 
	 * @param roleUUID
	 * @param logInfo
	 * @return
	 */
	public static String buildLogInfoStr(String roleUUID, String logInfo) {
		return String.format(LOG_TEMPLATE, roleUUID, logInfo);
	}
	
	/**
	 * 修改日志的级别
	 * 
	 * @param logName
	 *            日志的名称
	 * @param level
	 *            日志的级别
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static boolean changeLogLevel(String logName, String level) {
		ILoggerFactory _loggerFactory = LoggerFactory.getILoggerFactory();
		if (_loggerFactory instanceof Log4jLoggerFactory) {
			try {
				Map _loggerMap = (Map) RefUtil.getFieldValue(_loggerFactory, "loggerMap");
				if (_loggerMap == null) {
					return false;
				}
				Log4jLoggerAdapter _logger = null;
				if(Logger.ROOT_LOGGER_NAME.equalsIgnoreCase(logName)){
					_logger = (Log4jLoggerAdapter) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
				}else{
					_logger = (Log4jLoggerAdapter) _loggerMap.get(logName);
				}
				if (_logger == null) {
					return false;
				}
				org.apache.log4j.Logger _log4jLogger = (org.apache.log4j.Logger) RefUtil.getFieldValue(_logger,
						"logger");
				if (_log4jLogger == null) {
					return false;
				}
				_log4jLogger.setLevel(Level.toLevel(level));
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		return false;
	}
}
