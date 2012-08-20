package com.pwrd.war.db.dao.battle;

import com.pwrd.war.core.orm.DBService;
import com.pwrd.war.db.dao.BaseDao;
import com.pwrd.war.db.model.battle.BattleEndEntity;

public class BattleEndDao  extends BaseDao<BattleEndEntity>{

	public BattleEndDao(DBService dbService) {
		super(dbService);
	}

	@Override
	protected Class<BattleEndEntity> getEntityClass() {
		return BattleEndEntity.class;
	}

}
