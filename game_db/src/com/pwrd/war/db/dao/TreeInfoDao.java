package com.pwrd.war.db.dao;

import java.util.List;

import com.pwrd.war.core.orm.DBService;
import com.pwrd.war.db.model.TreeInfoEntity;

public class TreeInfoDao extends BaseDao<TreeInfoEntity>{
	/** 按照charId获取摇钱树信息：HQL */
	private static final String GET_TREE_INFO_BY_CHARID = "queryPlayerTreeInfo";
	/** 按照charId获取摇钱树信息：HQL */
	private static final String UPDATE_TREE_INFO_BY_CHARID_OFFLINE = "updateOffLinePlayerTreeInfo";
	/** 按照charId获取摇钱树信息：参数 */
	private static final String[] GET_TREE_INFO_BY_CHARID_PARAMS = new String[] { "charId" };
	/** 按照charId获取摇钱树信息：参数 */
	private static final String[] UPDATE_TREE_INFO_BY_CHARID_OFFLINE_PARAMS = new String[] { "charId", "curTreeExp", "maxTreeExp", "treeLevel" };

	public TreeInfoDao(DBService dbService) {
		super(dbService);
	}

	@SuppressWarnings("unchecked")
	public List<TreeInfoEntity> getTreeInfosByHumanId(String charId) {
		return this.dbService.findByNamedQueryAndNamedParam(GET_TREE_INFO_BY_CHARID,
				GET_TREE_INFO_BY_CHARID_PARAMS, new Object[] { charId });
	}
	
	public void updateOffLineHumanTreeInfo(String charId, int curExp, int maxExp, int level) {
		this.dbService.queryForUpdate(UPDATE_TREE_INFO_BY_CHARID_OFFLINE, UPDATE_TREE_INFO_BY_CHARID_OFFLINE_PARAMS,
				new Object[] { charId, curExp, maxExp, level});
	}

	@Override
	protected Class<TreeInfoEntity> getEntityClass() {
		return TreeInfoEntity.class;
	}
}
