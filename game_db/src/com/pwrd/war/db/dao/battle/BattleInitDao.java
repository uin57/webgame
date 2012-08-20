package com.pwrd.war.db.dao.battle;

import com.pwrd.war.core.orm.DBService;
import com.pwrd.war.db.dao.BaseDao;
import com.pwrd.war.db.model.battle.BattleInitEntity;

public class BattleInitDao extends BaseDao<BattleInitEntity>{

	public BattleInitDao(DBService dbService) {
		super(dbService);
	}

	@Override
	protected Class<BattleInitEntity> getEntityClass() {
		return BattleInitEntity.class;
	}

}
