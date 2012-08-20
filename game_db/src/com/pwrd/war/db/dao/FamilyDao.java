/**
 * 
 */
package com.pwrd.war.db.dao;

import java.util.List;

import com.pwrd.war.core.orm.DBService;
import com.pwrd.war.db.model.FamilyEntity;

/**
 * @author dengdan
 *
 */
public class FamilyDao extends BaseDao<FamilyEntity> {

	/** 根据家族名称和族长名称查询家族 ,参数都为空时为加载所有家族 */
	//private static final String GET_FAMILY_BY_CONDITION = "queryFamilyByCondition";
	
	/** 获取所有家族列表 **/
	private static final String GET_ALL_FAMILY = "getAllFamily";
	
	//private static final String[] GET_FAMILY_BY_CONDITION_PARAMS = new String[] { "name","leaderName" };
	
	public FamilyDao(DBService dbService) {
		super(dbService);
	}
	
	@SuppressWarnings("unchecked")
	public List<FamilyEntity> getAllFamilyEntity(){
		return this.dbService.findByNamedQuery(GET_ALL_FAMILY);
	}
	
	@Override
	protected Class<FamilyEntity> getEntityClass() {
		// TODO Auto-generated method stub
		return FamilyEntity.class;
	}

	
}
