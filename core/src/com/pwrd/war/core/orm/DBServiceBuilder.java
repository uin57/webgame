package com.pwrd.war.core.orm;

import java.net.URL;

import com.pwrd.war.core.event.IEventListener;

/**
 * 数据服务构建器
 * 
 */
public class DBServiceBuilder {

	/**
	 * 构建直接访问数据库的数据服务 ： 按 Url
	 * 
	 * @param daoType
	 *            0: hibernate 1: ibatis 默认 ： hibernate
	 * @param URL
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static DBService buildDirectDBService(IEventListener eventListener,
			int daoType, URL configUrl, String... resourceNames) {
		// 使用Hibernate
		DBService _dbService = null;
		if (daoType == DBServiceConstants.DB_TYPE_HIBERNATE) {
			_dbService = new HibernateDBServcieImpl(eventListener, configUrl,
					resourceNames);
		}
		// 使用Ibatis
		if (daoType == DBServiceConstants.DB_TYPE_IBATIS) {
			_dbService = new IbatisDBServiceImpl(eventListener, configUrl);
		}
		if (_dbService != null) {
			_dbService.check();
			return _dbService;
		}
		// 如果初始化类型既不是 hibernate 也不是 ibatis ， 抛出异常
		throw new IllegalArgumentException("Not dao build type defined.");
	}

	/**
	 * 构建直接访问h2缓存的数据服务 ： 按 Url
	 * 
	 * @param daoType
	 *            0: hibernate 1: ibatis 默认 ： hibernate
	 * @param URL
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static DBService buildH2CacheService(IEventListener eventListener,
			int daoType, URL configUrl, String... resourceNames) {
		// 使用Hibernate
		DBService _dbService = null;
		if (daoType == DBServiceConstants.DB_TYPE_HIBERNATE) {
			_dbService = new HibernateH2ServiceImpl(eventListener, configUrl,
					resourceNames);
		}
		// 使用Ibatis
		if (daoType == DBServiceConstants.DB_TYPE_IBATIS) {
			throw new IllegalArgumentException("Do not support ibatis type cache");
		}
		if (_dbService != null) {
			_dbService.check();
			return _dbService;
		}
		// 如果初始化类型既不是 hibernate 也不是 ibatis ， 抛出异常
		throw new IllegalArgumentException("Not dao build type defined.");
	}
}
