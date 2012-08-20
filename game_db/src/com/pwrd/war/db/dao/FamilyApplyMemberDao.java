/**
 * 
 */
package com.pwrd.war.db.dao;

import java.util.List;

import com.pwrd.war.core.orm.DBService;
import com.pwrd.war.db.model.FamilyApplyMemberEntity;
import com.pwrd.war.db.model.FamilyMemberEntity;

/**
 * @author dengdan
 *
 */
public class FamilyApplyMemberDao extends BaseDao<FamilyApplyMemberEntity>{

	private static final String QUERY_FAMILY_APPLY_MEMBER_BY_FAMILYID = "queryFamilyApplyMemberByFamilyId";
	
	public FamilyApplyMemberDao(DBService dbService) {
		super(dbService);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Class<FamilyApplyMemberEntity> getEntityClass() {
		// TODO Auto-generated method stub
		return FamilyApplyMemberEntity.class;
	}
	
	/**
	 * 查询申请家族成员信息
	 * @param familyId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<FamilyApplyMemberEntity> queryFamilyApplyMemberByFamilyId(String familyId){
		return dbService.findByNamedQueryAndNamedParam(
				QUERY_FAMILY_APPLY_MEMBER_BY_FAMILYID,
				new String[] { "familyId" }, new Object[] { familyId }
			);
	}

}
