package com.pwrd.war.db.dao;

import java.util.List;

import com.pwrd.war.core.orm.DBService;
import com.pwrd.war.db.model.PlayerGuideEntity;

public class PlayerGuideDAO extends BaseDao<PlayerGuideEntity> {

	public PlayerGuideDAO(DBService dbService) {
		super(dbService); 
	}

	@Override
	protected Class<PlayerGuideEntity> getEntityClass() { 
		return PlayerGuideEntity.class;
	}

	public PlayerGuideEntity getByPlayerUUID(String playerUUID){
		@SuppressWarnings("rawtypes")
		List list = this.dbService.findByNamedQueryAndNamedParam("getPlayerGuide",
				new String[]{"playerUUID"}, new Object[]{playerUUID});
		if(list.size() == 0)return null;
		return (PlayerGuideEntity) list.get(0);
	}
}
