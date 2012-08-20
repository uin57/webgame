package com.pwrd.war.gameserver.common.db;

import com.pwrd.war.core.orm.BaseEntity;


/**
 * 可持久化业务对象更新器
 * 
 * @see BaseEntity
 */
public interface EntityUpdater {
	
	/**
	 * 保存
	 * 
	 * @param obj
	 */
	public void save(BaseEntity<?> obj);

	/**
	 * 删除
	 * 
	 * @param obj
	 */
	public void delete(BaseEntity<?> obj);
	

}
