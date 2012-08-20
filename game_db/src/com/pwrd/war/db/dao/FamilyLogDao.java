/**
 * 
 */
package com.pwrd.war.db.dao;

import java.util.List;

import com.pwrd.war.core.orm.DBService;
import com.pwrd.war.db.model.FamilyLogEntity;

/**
 * @author dengdan
 * 
 *
 */
public class FamilyLogDao extends BaseDao<FamilyLogEntity> {
	
	private static final String QUERY_LOGS_BY_FAMILYID = "queryLogsByFamilyId";

	public FamilyLogDao(DBService dbService) {
		super(dbService);
	}

	/**
	 * 查询家族日志列表,只取20条记录
	 * @param familyId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<FamilyLogEntity> queryLogsByFamilyId(String familyId){
		return dbService.findByNamedQueryAndNamedParam(
				QUERY_LOGS_BY_FAMILYID,
				new String[] { "familyId" }, new Object[] { familyId },20,0
			);
	}
	@Override
	protected Class<FamilyLogEntity> getEntityClass() {
		return FamilyLogEntity.class;
	}

}
