package com.pwrd.war.logserver.dao;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;
import com.ibatis.sqlmap.engine.builder.xml.SqlMapConfigParser;
import com.ibatis.sqlmap.engine.builder.xml.XmlParserState;
import com.pwrd.war.logserver.BaseLogMessage;

/**
 * 定义DBConnection
 * 
 * 
 */
@SuppressWarnings("unchecked")
public class DBConnection {
	private static final Logger log = LoggerFactory.getLogger("DBConnection");

	private final SqlMapClient sqlMap;

	private final Map<Class, String> types;

	private static volatile DBConnection instance;

	private DBConnection(SqlMapClient sqlMap, Map<Class, String> types) {
		if (sqlMap == null) {
			throw new IllegalStateException("The sqlMap must not be null");
		}
		this.sqlMap = sqlMap;
		this.types = types;
	}

	public SqlMapClient getSqlMap() {
		return sqlMap;
	}

	public Collection<String> getTypes() {
		return types.values();
	}

	public String getTypeName(Class clazz) {
		return types.get(clazz);
	}

	public static DBConnection getInstance() {
		return instance;
	}

	/**
	 * 使用指定的IBatis配置初始化DBConnection
	 * 
	 * @param ibatisConfigURL
	 */
	public static void initDBConnection(URL ibatisConfigURL) {
		if (instance != null) {
			throw new IllegalStateException("The DBConnection has been inited.");
		}
		InputStream _in = null;
		try {
			_in = ibatisConfigURL.openStream();
			SqlMapClient _sqlMap = SqlMapClientBuilder.buildSqlMapClient(_in);
			instance = new DBConnection(_sqlMap, parseType(ibatisConfigURL));
		} catch (Exception e) {
			log.error("DBConnection.SqlMapClient.Err", e);
			throw new RuntimeException(e);
		} finally {
			if (_in != null) {
				try {
					_in.close();
				} catch (IOException e) {
				}
			}
		}
	}

	/**
	 * 
	 * @param ibatisConfigURL
	 * @return
	 */
	private static Map<Class, String> parseType(URL ibatisConfigURL) {
		Map<Class, String> _types = new HashMap<Class, String>();
		SqlMapConfigParser _parser = new SqlMapConfigParser();
		InputStream _in = null;
		try {
			_in = ibatisConfigURL.openStream();
			_parser.parse(_in);
			Field _stateField = _parser.getClass().getDeclaredField("state");
			_stateField.setAccessible(true);
			XmlParserState _state = (XmlParserState) _stateField.get(_parser);
			Field _typeField = _state.getConfig().getTypeHandlerFactory().getClass().getDeclaredField("typeAliases");
			_typeField.setAccessible(true);
			Map<String, String> _typeMap = (Map<String, String>) _typeField.get(_state.getConfig()
					.getTypeHandlerFactory());
			for (Map.Entry<String, String> _entry : _typeMap.entrySet()) {
				try {
					Class _class = Class.forName(_entry.getValue());
					if (BaseLogMessage.class.isAssignableFrom(_class)) {
						String _typeName = _entry.getKey();
						_types.put(_class, _typeName);
					}
				} catch (Throwable e) {
				}
			}
		} catch (Exception e) {
			log.error("DBConnection.SqlMapClient.Err", e);
			throw new RuntimeException(e);
		} finally {
			if (_in != null) {
				try {
					_in.close();
				} catch (IOException e) {
				}
			}
		}
		return _types;
	}
}
