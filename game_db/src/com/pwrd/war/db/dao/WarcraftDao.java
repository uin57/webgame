/**
 * 
 */
package com.pwrd.war.db.dao;

import java.util.List;

import com.pwrd.war.core.orm.DBService;
import com.pwrd.war.db.model.WarcraftEntity;

/**
 * 兵法dao
 * 
 * @author dengdan
 *
 */
public class WarcraftDao extends BaseDao<WarcraftEntity> {
	
	public static final String GET_ALL_WARCRAFT_BY_CHARID = "getAllWarcraftByCharId";

	public WarcraftDao(DBService dbService) {
		super(dbService);
	}

	/**
	 * 查询玩家兵法列表
	 * @param familyId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<WarcraftEntity> queryAllWarcrafByCharId(String charId){
		return dbService.findByNamedQueryAndNamedParam(
				GET_ALL_WARCRAFT_BY_CHARID,
				new String[] { "charId" }, new Object[] { charId }
			);
	}
	
	@Override
	protected Class<WarcraftEntity> getEntityClass() {
		return WarcraftEntity.class;
	}

}
