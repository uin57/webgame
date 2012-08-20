package com.pwrd.war.gameserver.common.cache;

import java.net.URL;
import java.util.HashMap;

import com.google.common.collect.Maps;
import com.pwrd.war.core.event.EventListenerAdapter;
import com.pwrd.war.core.orm.CacheEntity;
import com.pwrd.war.core.orm.DBAccessEvent;
import com.pwrd.war.core.orm.DBAccessEventListener;
import com.pwrd.war.core.orm.DBService;
import com.pwrd.war.core.orm.DBServiceBuilder;
import com.pwrd.war.db.dao.cache.BaseCacheDao;
import com.pwrd.war.db.dao.cache.HumanCacheDao;
import com.pwrd.war.db.model.cache.HumanCacheEntity;
import com.pwrd.war.gameserver.common.config.GameServerConfig;

/**
 * GameServer中访问H2Cache的Service
 * @author yue.yan
 *
 */
public class GameCacheService {

	/** 辅助初始化类 */
	private CacheDaoHelper daoHelper;
	
	public GameCacheService(GameServerConfig config) {		
		daoHelper = new CacheDaoHelper(config);
	}
	
	public boolean isTurnedOn(){
		return getDBService() != null;
	}
	
	/**
	 * 获取缓存服务中实际使用的数据库访问对象,本系统即为H2数据服务
	 * 
	 * @return
	 */
	public DBService getDBService() {
		return daoHelper.cacheService;
	}
	
	/**
	 * 获取角色缓存信息数据访问对象
	 * 
	 * @return
	 */
	public HumanCacheDao getHumanCacheDao() {
		return daoHelper.humanCacheDao;
	}
	
	/**
	 * 根据PersistanceObject获取Dao
	 * 
	 * @param poClass
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T extends CacheEntity<?>> BaseCacheDao<T> getCacheDaoByPOClass(Class<T> poClass) {
		return (BaseCacheDao<T>)daoHelper.clazzDaoMap.get(poClass);
	}
	
	/**
	 * CacheDao 配置，初始化 - dao
	 * 
	 */
	private static final class CacheDaoHelper {

		/** 数据库连接 */
		private final DBService cacheService;
		
		private final HumanCacheDao humanCacheDao;
		
		/** 不同POclass对应的dao */
		private HashMap<Class<? extends CacheEntity<?>>, BaseCacheDao<? extends CacheEntity<?>>> clazzDaoMap;


		private CacheDaoHelper(GameServerConfig config) {
			/**资源初始化*/
			EventListenerAdapter eventAdapter = new EventListenerAdapter();
			eventAdapter.addListener(DBAccessEvent.class,
					new DBAccessEventListener(config));
			ClassLoader _classLoader = Thread.currentThread()
					.getContextClassLoader();
			
			int daoType = config.getDbInitType();
			
			//cache config
			String[] _cacheConfig = config.getH2ConfigName().split(",");
			URL _cacheUrl = _classLoader.getResource(_cacheConfig[0]);
			String[] _cacheResources = new String[_cacheConfig.length - 1];
			if (_cacheConfig.length > 1) {
				System.arraycopy(_cacheConfig, 1, _cacheResources, 0,
						_cacheConfig.length - 1);
			}	
			
			//cache 初始化
			DBService _cacheService = null;
			//判断是否使用H2Cache
			//如使用，初始化H2Cache
			if(config.isTurnOnH2Cache()){
				_cacheService = DBServiceBuilder.buildH2CacheService(eventAdapter, daoType, _cacheUrl, _cacheResources);
			}
			
			cacheService = _cacheService;
			
			/* dao管理类初始化 */
			humanCacheDao = new HumanCacheDao(cacheService);
			
			clazzDaoMap = Maps.newHashMap();
			clazzDaoMap.put(HumanCacheEntity.class, humanCacheDao);
		}
	}
}
