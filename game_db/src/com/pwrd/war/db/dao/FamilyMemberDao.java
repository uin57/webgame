/**
 * 
 */
package com.pwrd.war.db.dao;

import java.util.List;

import com.pwrd.war.core.orm.DBService;
import com.pwrd.war.db.model.FamilyMemberEntity;

/**
 * @author dendgan
 * 家族成员dao
 *
 */
public class FamilyMemberDao extends BaseDao<FamilyMemberEntity> {

	private static final String QUERY_FAMILY_MEMBER_BY_FAMILYID = "queryFamilyMemberByFamilyId";
	
	private static final String QUERY_FAMILY_MEMBER_BY_CHARID = "queryFamilyMemberByCharId";

	public FamilyMemberDao(DBService dbService) {
		super(dbService);
	}

	/**
	 * 查询家族成员信息
	 * @param familyId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<FamilyMemberEntity> queryFamilyMemberByFamilyId(String familyId){
		return dbService.findByNamedQueryAndNamedParam(
				QUERY_FAMILY_MEMBER_BY_FAMILYID,
				new String[] { "familyId" }, new Object[] { familyId }
			);
	}
	
	/**
	 * 查询玩家id查询家族成员信息
	 * @param familyId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public FamilyMemberEntity queryFamilyMemberByCharId(String charId){
		List<FamilyMemberEntity> list = dbService.findByNamedQueryAndNamedParam(
				QUERY_FAMILY_MEMBER_BY_CHARID,
				new String[] { "charId" }, new Object[] { charId }
			);
		if(list != null && list.size() != 0){
			return list.get(0);
		}
		return null;
	}
	@Override
	protected Class<FamilyMemberEntity> getEntityClass() {
		return FamilyMemberEntity.class;
	}
}
