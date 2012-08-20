/**
 * 
 */
package com.pwrd.war.db.dao;

import java.util.List;

import com.pwrd.war.core.orm.DBService;
import com.pwrd.war.db.model.PromptButtonEntity;

/**
 * 
 * @author dengdan
 *
 */
public class PromptButtonDao extends BaseDao<PromptButtonEntity> {

	private static final String QUERY_PROMPT_BUTTON_BY_CHARID = "queryPromptButtonByCharId";
	
	public PromptButtonDao(DBService dbService) {
		super(dbService);
	}
	
	/**
	 * 查询角色的提示按钮信息
	 * @param familyId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<PromptButtonEntity> queryButtonMessageByCharId(String charId){
		return dbService.findByNamedQueryAndNamedParam(
				QUERY_PROMPT_BUTTON_BY_CHARID,
				new String[] { "charId" }, new Object[] { charId }
			);
	}
	
	@Override
	protected Class<PromptButtonEntity> getEntityClass() {
		// TODO Auto-generated method stub
		return PromptButtonEntity.class;
	}

}
