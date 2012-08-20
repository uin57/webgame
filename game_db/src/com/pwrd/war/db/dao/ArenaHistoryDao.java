package com.pwrd.war.db.dao;

import java.util.List;

import com.pwrd.war.core.orm.DBService;
import com.pwrd.war.db.model.ArenaHistoryEntity;

public class ArenaHistoryDao extends BaseDao<ArenaHistoryEntity> {
	private static final String GET_CHAR_HISTORIES = "findAllArenaHistories";
	private static final String[] GET_CHAR_HISTORIES_PARAMS = new String[] { "charId" };
	
	@SuppressWarnings("unchecked")
	public List<ArenaHistoryEntity> getAllArenaHistories(String charId) {
		return this.dbService.findByNamedQueryAndNamedParam(GET_CHAR_HISTORIES,
				GET_CHAR_HISTORIES_PARAMS, new Object[] { charId });
	}

	public ArenaHistoryDao(DBService dbService) {
		super(dbService);
	}

	@Override
	protected Class<ArenaHistoryEntity> getEntityClass() {
		return ArenaHistoryEntity.class;
	}

}
