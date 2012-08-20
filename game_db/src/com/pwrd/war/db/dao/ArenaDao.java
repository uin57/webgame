package com.pwrd.war.db.dao;

import java.util.List;

import com.pwrd.war.core.orm.DBService;
import com.pwrd.war.db.model.ArenaEntity;

public class ArenaDao extends BaseDao<ArenaEntity> {
	
	@SuppressWarnings("unchecked")
	public List<ArenaEntity> getAllArenaInfos() {
		return this.dbService.findByNamedQuery("findAllArenaInfos");
	}

	public ArenaDao(DBService dbService) {
		super(dbService);
	}

	@Override
	protected Class<ArenaEntity> getEntityClass() {
		return ArenaEntity.class;
	}

}
