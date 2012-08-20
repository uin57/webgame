package com.pwrd.war.gameserver.common.db;

import com.pwrd.war.core.object.PersistanceObject;

/**
 * 可持久化业务对象更新器
 * 
 * @see PersistanceObject
 */
public interface POUpdater {

	/**
	 * 保存
	 * 
	 * @param obj
	 */
	public void save(PersistanceObject<?, ?> obj);

	/**
	 * 删除
	 * 
	 * @param obj
	 */
	public void delete(PersistanceObject<?, ?> obj);
}
