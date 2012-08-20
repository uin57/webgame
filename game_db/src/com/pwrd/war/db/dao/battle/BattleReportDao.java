package com.pwrd.war.db.dao.battle;

import com.pwrd.war.core.orm.DBService;
import com.pwrd.war.db.dao.BaseDao;
import com.pwrd.war.db.model.battle.BattleReportEntity;

public class BattleReportDao  extends BaseDao<BattleReportEntity> {

	public BattleReportDao(DBService dbService) {
		super(dbService);
	}

	@Override
	protected Class<BattleReportEntity> getEntityClass() {
		return BattleReportEntity.class;
	}

}
