package com.pwrd.war.db.dao;

import java.util.List;

import com.pwrd.war.core.orm.DBService;
import com.pwrd.war.db.model.DailyQuestEntity;

/**
 * DailyTask的Dao
 * 
 */
public class DailyQuestDao extends BaseDao<DailyQuestEntity> {
	private static final String GET_DAILY_TASKS_BY_CHARID = "queryPlayerDailyQuest";
	private static final String[] GET_DAILY_TASKS_BY_CHARID_PARAMS = new String[] { "charId" };

	public DailyQuestDao(DBService dbService) {
		super(dbService);
	}

	@SuppressWarnings("unchecked")
	public List<DailyQuestEntity> loadByCharId(String charId) {
		return this.dbService.findByNamedQueryAndNamedParam(
				GET_DAILY_TASKS_BY_CHARID, GET_DAILY_TASKS_BY_CHARID_PARAMS,
				new Object[] { charId });
	}

	@Override
	protected Class<DailyQuestEntity> getEntityClass() {
		return DailyQuestEntity.class;
	}

}
