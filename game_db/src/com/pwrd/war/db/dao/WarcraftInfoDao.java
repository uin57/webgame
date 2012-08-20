/**
 * 
 */
package com.pwrd.war.db.dao;

import java.util.List;

import com.pwrd.war.core.orm.DBService;
import com.pwrd.war.db.model.WarcraftInfoEntity;

/**
 * 
 * 兵法信息dao
 * @author dengdan
 *
 */
public class WarcraftInfoDao extends BaseDao<WarcraftInfoEntity> {

	public static final String GET_WARCRAFT_INFO_BY_CHARID = "getWarcraftInfoByCharId";
	
	public WarcraftInfoDao(DBService dbService) {
		super(dbService);
	}

	/**
	 * 查询角色id查询玩家兵法相关信息
	 * @param charId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public WarcraftInfoEntity queryWarcraftInfoByCharId(String charId){
		List<WarcraftInfoEntity> list = dbService.findByNamedQueryAndNamedParam(
				GET_WARCRAFT_INFO_BY_CHARID,
				new String[] { "charId" }, new Object[] { charId }
			);
		if(list != null && list.size() != 0){
			return list.get(0);
		}
		return null;
	}
	
	@Override
	protected Class<WarcraftInfoEntity> getEntityClass() {
		return WarcraftInfoEntity.class;
	}

}
