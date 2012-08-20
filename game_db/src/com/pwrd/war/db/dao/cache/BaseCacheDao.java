package com.pwrd.war.db.dao.cache;

import java.io.Serializable;

import com.pwrd.war.core.orm.BaseEntity;
import com.pwrd.war.core.orm.CacheEntity;
import com.pwrd.war.core.orm.DBService;
import com.pwrd.war.core.orm.DataAccessException;
import com.pwrd.war.core.orm.SoftDeleteEntity;

/**
 * 基础的CacheDao
 * @author yue.yan
 *
 * @param <E>
 */
public abstract class BaseCacheDao<E extends CacheEntity<?>> {

	
	/** h2缓存库服务 */
	protected final DBService cacheService;

	public BaseCacheDao(DBService cacheService) {
		this.cacheService = cacheService;
	}
	
	public DBService getCacheService() {
		return cacheService;
	}
	
	/**
	 * 保存一个实体
	 * 
	 * @param obj
	 * @exception DataAccessException
	 */
	public Serializable save(E obj) {
		return this.cacheService.save(obj);
	}
	
	/**
	 * 更新一个实体
	 * 
	 * @param obj
	 * @exception DataAccessException
	 */
	public void update(E obj) {
		this.cacheService.update(obj);
	}

	/**
	 * 删除一个实体
	 * 
	 * @param obj
	 * @exception DataAccessException
	 */
	@SuppressWarnings("unchecked")
	public void delete(E obj) {
		if (obj instanceof SoftDeleteEntity) {
			this.cacheService.softDelete((SoftDeleteEntity) obj);
		} else {
			this.cacheService.delete(obj);
		}
	}
	
	/**
	 * 根据主键获取一个实体
	 * 
	 * @param id
	 * @exception DataAccessException
	 * @return
	 */
	public E get(java.io.Serializable id) {
		return this.cacheService.get(this.getEntityClass(), id);
	}
	
	/**
	 * 根据指定类型的指定ID查询实体
	 * @param entityClass
	 * @param id
	 * @return
	 */
	public Object get(Class<? extends BaseEntity<Serializable>> entityClass,Serializable id){
		return this.cacheService.get(entityClass, id);
	}

	
	
	/**
	 * 取得该Dao所操作的对象类型
	 * 
	 * @return
	 */
	protected abstract Class<E> getEntityClass();

}
