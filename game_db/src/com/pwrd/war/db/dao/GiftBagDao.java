/**
 * 
 */
package com.pwrd.war.db.dao;

import java.util.List;

import com.pwrd.war.core.orm.DBService;
import com.pwrd.war.db.model.GiftBagEntity;

/**
 * 
 * 礼包dao
 * @author dengdan
 *
 */
public class GiftBagDao extends BaseDao<GiftBagEntity> {

	/** 获取玩家所有礼包 **/
	private static final String GET_ALL_GIFT_BAG_BY_CHARID = "getAllgiftBagByCharId";
	
	public GiftBagDao(DBService dbService) {
		super(dbService);
	}

	@SuppressWarnings("unchecked")
	public List<GiftBagEntity> getAllGiftBagEntityByCharId(String charId){
		return dbService.findByNamedQueryAndNamedParam(
				GET_ALL_GIFT_BAG_BY_CHARID,
				new String[] { "charId" }, new Object[] { charId }
			);
	}
	
	@Override
	protected Class<GiftBagEntity> getEntityClass() {
		return GiftBagEntity.class;
	}

}
