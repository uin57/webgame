package com.pwrd.war.db.dao;

import java.util.List;

import com.pwrd.war.core.orm.DBService;
import com.pwrd.war.db.model.FinishedStoryEntity;

/**
 * FinishStory的Dao
 * 
 */
public class FinishedStoryDao extends BaseDao<FinishedStoryEntity> {
	
	private static final String GET_FINISHSTORIES_BY_CHARID = "queryFinishedStory";
	private static final String[] GET_FINISHSTORIES_BY_CHARID_PARAMS = new String[] { "charId" };

	public FinishedStoryDao(DBService dbService) {
		super(dbService);
	}



	@SuppressWarnings("unchecked")
	public List<FinishedStoryEntity> loadByCharId(String charId) {
		return this.dbService.findByNamedQueryAndNamedParam(
				GET_FINISHSTORIES_BY_CHARID, GET_FINISHSTORIES_BY_CHARID_PARAMS,
				new Object[] { charId });
	}

	@Override
	protected Class<FinishedStoryEntity> getEntityClass() {
		return FinishedStoryEntity.class;
	}

}
