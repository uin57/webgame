package com.pwrd.war.db.dao;

import java.util.List;

import com.pwrd.war.core.orm.DBService;
import com.pwrd.war.db.model.ProbEntity;

public class ProbInfoDAO extends BaseDao<ProbEntity> {

	public ProbInfoDAO(DBService dbService) {
		super(dbService); 
	}

	@Override
	protected Class<ProbEntity> getEntityClass() { 
		return ProbEntity.class;
	}

	public List<ProbEntity> getByPlayerUUID(String playerUUID){
		return dbService.findByNamedQueryAndNamedParam("getPlayerProbList", 
				new String[]{"playerUUID"}, new Object[]{playerUUID});
	}
}
