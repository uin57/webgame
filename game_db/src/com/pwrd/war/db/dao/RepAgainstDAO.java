package com.pwrd.war.db.dao;

import com.pwrd.war.core.orm.DBService;
import com.pwrd.war.db.model.RepAgainstEntity;

public class RepAgainstDAO extends BaseDao<RepAgainstEntity> {

	public RepAgainstDAO(DBService dbService) {
		super(dbService); 
	}

	@Override
	protected Class<RepAgainstEntity> getEntityClass() { 
		return RepAgainstEntity.class;
	}

}
