package com.pwrd.war.db.dao;

import java.util.List;

import com.pwrd.war.core.orm.DBService;
import com.pwrd.war.db.model.ServerInfoEntity;

public class ServerInfoDAO extends BaseDao<ServerInfoEntity> {
	private static final String GET_BY_KEY = "queryServerInfo";
	
	private static final String[] GET_BY_KEY_PARAMS = new String[] { "key" };
	
	public ServerInfoDAO(DBService dbService) {
		super(dbService); 
	}

	@Override
	protected Class<ServerInfoEntity> getEntityClass() { 
		return ServerInfoEntity.class;
	}

	public ServerInfoEntity getByKey(String key){
		@SuppressWarnings("unchecked")
		List<ServerInfoEntity> list = 
				this.dbService.findByNamedQueryAndNamedParam(GET_BY_KEY, GET_BY_KEY_PARAMS, new Object[]{key});
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}
}
