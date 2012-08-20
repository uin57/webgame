package com.pwrd.war.core.orm.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.hibernate.Session;
import org.hibernate.jdbc.Work;

import com.pwrd.war.core.orm.DBService;
import com.pwrd.war.core.orm.HibernateDBServcieImpl;
import com.pwrd.war.core.orm.HibernateDBServcieImpl.HibernateCallback;

/**
 * 查询MySql的状态数据
 * 
 * 
 */
public class MySqlStatusDao {
	/** 查询指定的表状态 */
	private static final String showStatusSql = "show table status like '%s'";

	/** 查询指定的全局变量 */
	private static final String showGlobalVar = " show global variables like '%s'";

	/**
	 * 取得指定数据库表的状态信息
	 * 
	 * @param dbService
	 * @param tableName
	 * @return
	 */
	public static TableStatus getTableStatus(DBService dbService, String tableName) {
		final TableStatus _status = new TableStatus();
		final String _sql = String.format(showStatusSql, tableName);
		HibernateDBServcieImpl _hibernate = (HibernateDBServcieImpl) dbService;
		Object _result = executeVarSql(_sql, "Auto_increment", _hibernate);
		if(_result!=null){
			_status.nextId = ((Number)_result).longValue(); 
		}
		return _status;
	}


	/**
	 * 取得MySql指定的全局变量的值
	 * @param dbService 
	 * @param name 全局变量的名称
	 * @return
	 */
	public static String getGlobalVariable(DBService dbService, String name) {
		final String _sql = String.format(showGlobalVar, name);
		return (String) executeVarSql(_sql, "Value", (HibernateDBServcieImpl) dbService);
	}
	
	/**
	 * 执行一个变量的查询
	 * @param sql
	 * @param columnName
	 * @param hibernate
	 * @return
	 */
	private static Object executeVarSql(final String sql, final String columnName, final HibernateDBServcieImpl hibernate) {
		return hibernate.call(new HibernateCallback<Object>() {
			@Override
			public Object doCall(Session session) {
				final Object[] _result = new Object[1];
				session.doWork(new Work() {
					@Override
					public void execute(Connection connection) throws SQLException {
						Statement _statement = null;
						ResultSet _resultSet = null;
						try {
							_statement = connection.createStatement();
							_resultSet = _statement.executeQuery(sql);
							if (_resultSet.next()) {
								_result[0] = _resultSet.getObject(columnName);
							}
						} finally {
							if (_resultSet != null) {
								try {
									_resultSet.close();
								} catch (Exception e) {

								}
							}
							if (_statement != null) {
								try {
									_statement.close();
								} catch (Exception e) {
								}
							}
						}
					}
				});
				return _result[0];
			}
		});
	}

	/**
	 * 数据库表的状态信息
	 * 
	  *
	 * 
	 */
	public static final class TableStatus {
		/**
		 * 下一个ID,默认值是-1,即无效的id
		 */
		public long nextId = -1;
	}
}
