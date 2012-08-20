package com.pwrd.war.db.dao;

import java.util.List;

import com.pwrd.war.core.orm.DBService;
import com.pwrd.war.db.model.UserArchiveEntity;

public class UserArchiveEntityDAO extends BaseDao<UserArchiveEntity> {
	private static final String GET_BY_HUMAN = "queryArchive";
	
	private static final String[] GET_BY_HUMAN_PARAMS = new String[] { "humanUUID" };
	
	public UserArchiveEntityDAO(DBService dbService) {
		super(dbService); 
	}
	@Override
	protected Class<UserArchiveEntity> getEntityClass() {
		return UserArchiveEntity.class;
	}
	
	@SuppressWarnings("unchecked")
	public List<UserArchiveEntity> getByHuman(String humanUUID){
		return this.dbService.findByNamedQueryAndNamedParam(GET_BY_HUMAN, GET_BY_HUMAN_PARAMS, new Object[]{humanUUID});
	}

}
