package com.pwrd.war.db.dao;

import java.util.List;

import com.pwrd.war.core.orm.DBService;
import com.pwrd.war.db.model.FinishedQuestEntity;

/**
 * FinishTaskInfo的Dao
 * 
 */
public class FinishedQuestDao extends BaseDao<FinishedQuestEntity> {
	
	private static final String GET_FINISHTASKS_BY_CHARID = "queryPlayerFinishedQuest";
	private static final String[] GET_FINISHTASKS_BY_CHARID_PARAMS = new String[] { "charId" };

	public FinishedQuestDao(DBService dbService) {
		super(dbService);
	}



	@SuppressWarnings("unchecked")
	public List<FinishedQuestEntity> loadByCharId(String charId) {
		return this.dbService.findByNamedQueryAndNamedParam(
				GET_FINISHTASKS_BY_CHARID, GET_FINISHTASKS_BY_CHARID_PARAMS,
				new Object[] { charId });
	}

	@Override
	protected Class<FinishedQuestEntity> getEntityClass() {
		return FinishedQuestEntity.class;
	}

}
