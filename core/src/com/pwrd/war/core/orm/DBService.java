package com.pwrd.war.core.orm;

import java.io.Serializable;
import java.util.List;

import com.pwrd.war.core.event.IEventTrigger;

/**
 * 数据库服务接口
 * 
  *
 */
@SuppressWarnings("unchecked")
public interface DBService extends IEventTrigger<DBAccessEvent> {
	/**
	 * 根据<tt>id</tt>获取指定类型的实体
	 * 
	 * @param <T>
	 * @param entityClass
	 *            实体的类型
	 * @param id
	 *            实体的id
	 * @return
	 * @throws DataAccessException
	 */
	public <T extends BaseEntity> T get(Class<T> entityClass, Serializable id)
			throws DataAccessException;

	/**
	 * 保存一个实体
	 * 
	 * @param entity
	 * @return
	 * @throws DataAccessException
	 */
	public abstract Serializable save(BaseEntity entity)
			throws DataAccessException;

	/**
	 * 更新一个实体
	 * 
	 * @param entity
	 * @throws DataAccessException
	 */
	public abstract void update(BaseEntity entity) throws DataAccessException;

	/**
	 * 删除一个实体
	 * 
	 * @param entity
	 * @throws DataAccessException
	 */
	public abstract void delete(BaseEntity entity) throws DataAccessException;

	/**
	 * 根据ID删除一个实体
	 * 
	 * @param entityClass
	 * @param id
	 * @deprecated
	 */
	public abstract void deleteById(Class<? extends BaseEntity> entityClass,
			Serializable id);

	/**
	 * 软删除一个实体,即在数据库并不真正的删除实体,只是将{@link SoftDeleteEntity#getDeleted()}标示为
	 * {@link SoftDeleteEntity#DELETED}, 并记录删除的时间
	 * {@link SoftDeleteEntity#getDeleteDate()}
	 * 
	 * @param entity
	 * @throws DataAccessException
	 */
	public void softDelete(SoftDeleteEntity entity) throws DataAccessException;

	/**
	 * 软删除一个实体,即在数据库并不真正的删除实体,只是将{@link SoftDeleteEntity#getDeleted()}标示为
	 * {@link SoftDeleteEntity#DELETED}, 并记录删除的时间
	 * {@link SoftDeleteEntity#getDeleteDate()}
	 * 
	 * @param entityClass
	 * @throws DataAccessException
	 * @deprecated
	 */
	public void softDeleteById(Class<? extends SoftDeleteEntity> entityClass,
			Serializable id) throws DataAccessException;

	/**
	 * 执行指定名称的查询
	 * 
	 * @param queryName
	 *            查询名称
	 * @return
	 * @throws DataAccessException
	 */
	public abstract List findByNamedQuery(String queryName)
			throws DataAccessException;

	/**
	 * 执行指定名称的查询
	 * 
	 * @param queryName
	 *            查询的名称
	 * @param paramNames
	 *            查询名称对应的查询语句中的参数名称
	 * @param values
	 *            与paramNames 对应的参数值
	 * @return
	 * @throws DataAccessException
	 */
	public abstract List findByNamedQueryAndNamedParam(String queryName,
			String[] paramNames, Object[] values) throws DataAccessException;

	/**
	 * 执行指定的更新查询
	 * 
	 * DBSDBServiceImpl , HibernateDBServcieImpl 中实现了该方法
	 * {@link IbatisDBServiceImpl} 未实现
	 * 
	 * @param queryName
	 *            查询更新的名称
	 * @param paramNames
	 *            查询更新名称对应的查询语句中的参数名称
	 * @param values
	 *            与 paramNames对应的参数值
	 * @return 更新的结果数
	 * @throws DataAccessException
	 * 
	 */
	public abstract int queryForUpdate(String queryName, String[] paramNames,
			Object[] values) throws DataAccessException;

	/**
	 * 执行指定名称的查询,供分页查询使用
	 * 
	 * @param queryName
	 *            查询的名称
	 * @param paramNames
	 *            查询名称对应的查询语句中的参数名称
	 * @param values
	 *            与paramNames对应的参数值
	 * @param maxResult
	 *            返回的最大结果数量,-1返回全部
	 * @param start
	 *            返回结果的起始索引,-1忽略该参数
	 * @return
	 * @throws DataAccessException
	 */
	public abstract List findByNamedQueryAndNamedParam(String queryName,
			String[] paramNames, Object[] values, int maxResult, int start)
			throws DataAccessException;

	/**
	 * 保存或者更新(如果已经存在)一个实体 当前只有 HibernateDBServiceImpl实现了该方法，DBSDBServiceImpl ,
	 * IbatisDBSServiceImp 均未实现
	 * 
	 * @param entity
	 * @return
	 * @throws DataAccessException
	 */
	public abstract void saveOrUpdate(BaseEntity entity)
			throws DataAccessException;

	/**
	 * 检测服务是否正确初始化
	 */
	public void check();
}