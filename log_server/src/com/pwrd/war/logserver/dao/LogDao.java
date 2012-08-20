package com.pwrd.war.logserver.dao;

import org.slf4j.Logger;

import com.pwrd.war.core.util.ErrorsUtil;
import com.pwrd.war.logserver.BaseLogMessage;

/**
 * 基本的Log Dao,负责创建新表以及插入记录
 * 
 */
public class LogDao {
	private static Logger logger = org.slf4j.LoggerFactory.getLogger(LogDao.class);

	private LogDao() {
	}

	/**
	 * 创建指定log的日志表
	 * 
	 * @param logName
	 *            log的名称,如 item
	 * @param tableSuffix
	 *            ,新创建表的后缀,如 _20090915
	 * 
	 */
	public static void create(String logName, String tableSuffix) {
		String _createCmd = String.format("createTable_%s", logName);
		String _tablename = String.format("%s_%s", logName, tableSuffix);
		if (logger.isInfoEnabled()) {
			logger.info("Create table [" + _tablename + "]");
		}
		try {
			DBConnection.getInstance().getSqlMap().update(_createCmd, _tablename);
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {
				logger.error(ErrorsUtil.error("CREATETABLE_EXCEPTION", "#GS.LogDao.create", "Errors" + e));
			}
		}
	}

	/**
	 * 插入日志到指定的日志表中
	 * 
	 * @param logName
	 *            log的名称,如 item
	 * @param item
	 * @exception RuntimeException
	 * 
	 */
	public static void insert(String logName, Object item) {

		String _insertCmd = String.format("insert_%s", logName);
		try {
			DBConnection.getInstance().getSqlMap().update(_insertCmd, item);
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {
				logger.error(ErrorsUtil.error("INSERT_EXCEPTION", "#GS.LogDao.insert", "Errors" + e));
			}
		}
	}

	/**
	 * 更新日志信息到指定的日志表中
	 * 
	 * @param logName
	 * 			log的名称,如 item
	 * @param item
	 */
	public static void update(String logName, Object item) {
		String _updateCmd = String.format("update_%s", logName);
		try {
			DBConnection.getInstance().getSqlMap().update(_updateCmd, item);
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {
				logger.error(ErrorsUtil.error("UPDATE_EXCEPTION", "#GS.LogDao.update", "Errors" + e));
			}
		}
	}
	
	/**
	 * 判断日志表中是否存在记录
	 * 
	 * @param logName
	 * @param item
	 * @return
	 */
	public static Object getExistedObject(String logName, Object item) {
		String _selectCmd = String.format("select_%s", logName);
		try {
			Object _dbItem = DBConnection.getInstance().getSqlMap().queryForObject(_selectCmd, item);
			return _dbItem;
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {
				logger.error(ErrorsUtil.error("SELECT_EXCEPTION", "#GS.LogDao.getExistedObject", "Errors" + e));
			}
		}
		return null;
	}
	
	/**
	 * 依据Log Bean的类型取得其对应的log名称
	 * 
	 * @param clazz
	 * @return
	 */
	public static String getLogNameByBeanClass(Class<? extends BaseLogMessage> clazz) {
		return DBConnection.getInstance().getTypeName(clazz);
	}
}
