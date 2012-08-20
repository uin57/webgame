package com.pwrd.war.db.dao;

import java.util.List;

import com.pwrd.war.core.orm.DBService;
import com.pwrd.war.db.model.RobberyEntity;

public class RobberyDAO extends BaseDao<RobberyEntity> {

	public RobberyDAO(DBService dbService) {
		super(dbService); 
	}

	@Override
	protected Class<RobberyEntity> getEntityClass() { 
		return RobberyEntity.class;
	}
	@SuppressWarnings("unchecked")
	public List<RobberyEntity> findAll() { 
		return this.dbService.findByNamedQuery("findAllRobbery");
	}
}
