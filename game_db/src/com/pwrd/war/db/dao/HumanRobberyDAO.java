package com.pwrd.war.db.dao;

import com.pwrd.war.core.orm.DBService;
import com.pwrd.war.db.model.HumanRobberyEntity;

public class HumanRobberyDAO extends BaseDao<HumanRobberyEntity> {

	public HumanRobberyDAO(DBService dbService) {
		super(dbService); 
	}

	@Override
	protected Class<HumanRobberyEntity> getEntityClass() {
		return HumanRobberyEntity.class;
	}

}
