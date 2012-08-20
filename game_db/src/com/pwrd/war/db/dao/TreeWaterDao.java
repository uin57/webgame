package com.pwrd.war.db.dao;

import java.util.List;

import com.pwrd.war.core.orm.DBService;
import com.pwrd.war.db.model.TreeWaterEntity;

public class TreeWaterDao extends BaseDao<TreeWaterEntity>{
	/** 按照charId获取摇钱树信息：HQL */
	private static final String GET_TREE_WATER_BY_CHARID = "queryPlayerTreeWater";
	/** 按照charId获取摇钱树信息：参数 */
	private static final String[] GET_TREE_WATER_BY_CHARID_PARAMS = new String[] { "toCharId", "waterTime" };

	public TreeWaterDao(DBService dbService) {
		super(dbService);
	}

	@SuppressWarnings("unchecked")
	public List<TreeWaterEntity> getTreeWaterInfosByHumanId(String toCharId, long waterTime) {
		return this.dbService.findByNamedQueryAndNamedParam(GET_TREE_WATER_BY_CHARID,
				GET_TREE_WATER_BY_CHARID_PARAMS, new Object[] { toCharId, waterTime });
	}

	@Override
	protected Class<TreeWaterEntity> getEntityClass() {
		return TreeWaterEntity.class;
	}
}
