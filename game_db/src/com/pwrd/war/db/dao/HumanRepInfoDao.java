package com.pwrd.war.db.dao;

import java.util.List;

import com.pwrd.war.core.orm.DBService;
import com.pwrd.war.db.model.HumanRepInfoEntity;

public class HumanRepInfoDao extends BaseDao<HumanRepInfoEntity>{
	/** 按照charId获取宠物：HQL */
	private static final String GET_REPS_BY_CHARID = "queryPlayerReps";
	/** 按照charId获取宠物：参数 */
	private static final String[] GET_REPS_BY_CHARID_PARAMS = new String[] { "charId" };

	public HumanRepInfoDao(DBService dbService) {
		super(dbService);
	}

	@SuppressWarnings("unchecked")
	public List<HumanRepInfoEntity> getRepInfosByHumanId(String charId) {
		return this.dbService.findByNamedQueryAndNamedParam(GET_REPS_BY_CHARID,
				GET_REPS_BY_CHARID_PARAMS, new Object[] { charId });
	}

	@Override
	protected Class<HumanRepInfoEntity> getEntityClass() {
		return HumanRepInfoEntity.class;
	}
}
