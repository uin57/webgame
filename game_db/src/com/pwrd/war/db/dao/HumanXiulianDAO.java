package com.pwrd.war.db.dao;

import com.pwrd.war.core.orm.DBService;
import com.pwrd.war.db.model.HumanXiulianEntity;

public class HumanXiulianDAO extends BaseDao<HumanXiulianEntity> {

	public HumanXiulianDAO(DBService dbService) {
		super(dbService); 
	}

	@Override
	protected Class<HumanXiulianEntity> getEntityClass() { 
		return HumanXiulianEntity.class;
	}

}
