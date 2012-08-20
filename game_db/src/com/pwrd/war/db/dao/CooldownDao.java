package com.pwrd.war.db.dao;

import java.util.List;

import com.pwrd.war.core.orm.DBService;
import com.pwrd.war.db.model.CooldownEntity;

public class CooldownDao extends BaseDao<CooldownEntity>{
	
	private static final String GET_BY_HUMAN = "getByHuman";
	private static final String[] GET_BY_HUMAN_PARAMS = new String[]{"humanUUID"};

	public CooldownDao(DBService dbService) {
		super(dbService);
	}

	@Override
	protected Class<CooldownEntity> getEntityClass() {
		return CooldownEntity.class;
	}
	
	@SuppressWarnings("unchecked")
	public List<CooldownEntity> getByHuman(String humanUUID){
		return this.dbService.findByNamedQueryAndNamedParam(GET_BY_HUMAN, GET_BY_HUMAN_PARAMS, new Object[] { humanUUID });
	}

}
