package com.pwrd.war.core.object;

import com.pwrd.war.core.orm.BaseEntity;

/**
 * 可持久化的业务对象实现此接口
 * 
 * @param <IdType>
 *            主键类型
 * @param <T>
 *            对应的实体类型
 * 
 */
public interface PersistanceObject<IdType extends java.io.Serializable, T extends BaseEntity<IdType>> {

	/**
	 * 设置dbId的值
	 * 
	 * @param id
	 */
	void setDbId(IdType id);

	/**
	 * 取得DB Id的值
	 * 
	 * @return
	 */
	IdType getDbId();
	
	/**
	 * 取得Global Id的值
	 * 
	 * @return
	 */
	String getGUID();

	/**
	 * 数据对象是否已经在数据库中
	 * 
	 * @return true,已经在数据库;false,还未在数据库中
	 */
	boolean isInDb();

	/**
	 * 设置对象已经在数据库中了
	 * 
	 * @param inDB
	 *            true,已经在数据库中了;false,未在数据库中
	 */
	void setInDb(boolean inDb);

	/**
	 * 取得该对象所属的CharId
	 * 
	 * @return
	 */
	String getCharId();

	/**
	 * 将业务对象转为实体对象
	 * 
	 * @return
	 */
	T toEntity();

	/**
	 * 从实体对象转换到业务对象
	 */
	void fromEntity(T entity);

	/**
	 * 获得此持久化业务多谢的生命周期
	 * 
	 * @return
	 */
	LifeCycle getLifeCycle();

	/**
	 * 设置当前对象为已修改状态
	 */
	void setModified();
}
