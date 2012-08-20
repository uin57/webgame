package com.pwrd.war.db.dao;

import java.util.List;

import com.pwrd.war.core.orm.DBService;
import com.pwrd.war.db.model.DbVersion;

/**
 * 数据库中的版本号
 * 
 */
public class DbVersionDao extends BaseDao<DbVersion> {

	public DbVersionDao(DBService dbServcie) {
		super(dbServcie);
	}

	/**
	 * 取得数据库版本号
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public DbVersion getDbVersion() {
		List<DbVersion> _dbVersions = dbService.findByNamedQuery("queryDbVersion");
		if (_dbVersions == null || _dbVersions.isEmpty()) {
			return null;
		}
		return _dbVersions.get(0);
	}

	@Override
	protected Class<DbVersion> getEntityClass() {
		return DbVersion.class;
	}

}
