package com.pwrd.war.db.dao;

import java.util.List;

import com.pwrd.war.core.orm.DBService;
import com.pwrd.war.db.model.SecretShopInfoEntity;

public class SecretShopInfoDao extends BaseDao<SecretShopInfoEntity>{
	
	private static final String GET_SECRET_SHOP_BY_CHARID = "queryPlayerSecretShop";
	private static final String UPDATE_SECRET_SHOP_BY_CHARID = "updatePlayerSecretShop";
	private static final String UPDATE_SECRET_SHOP_BY_BUY_ITEMS = "updatePlayerSecretShopBuy";
	
	
	private static final String[] GET_SECRET_SHOP_BY_CHARID_PARAMS = new String[] { "charId" };
	private static final String[] UPDATE_SECRET_SHOP_BUY_PARAMS = new String[] { "charId" , "itemSn", "number", "buyFlag", "position"};
	
	
	public SecretShopInfoDao(DBService dbService) {
		super(dbService);
	}
	
	/**
	 * 获取商品列表
	 * @param characterId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<SecretShopInfoEntity> getSecretShopByCharId(String characterId) {
		return this.dbService.findByNamedQueryAndNamedParam(GET_SECRET_SHOP_BY_CHARID, GET_SECRET_SHOP_BY_CHARID_PARAMS,
				new Object[] { characterId });
	}
	
	/**
	 * 将商品变为历史数据
	 * @param characterId
	 * @return
	 */
	public int updateSecretShopByCharId(String characterId) {
		return this.dbService.queryForUpdate(UPDATE_SECRET_SHOP_BY_CHARID, GET_SECRET_SHOP_BY_CHARID_PARAMS,
				new Object[] { characterId });
	}
	
	/**
	 * 更新商品 
	 * @param characterId
	 * @param itemSn
	 * @param number
	 * @return
	 */
	public int updateSecretShopByCharIdAndItemSn(String characterId, String itemSn, int number, boolean buyFlag, int position) {
		return this.dbService.queryForUpdate(UPDATE_SECRET_SHOP_BY_BUY_ITEMS, UPDATE_SECRET_SHOP_BUY_PARAMS,
				new Object[] { characterId, itemSn, number, buyFlag, position});
	}
	
	@Override
	protected Class<SecretShopInfoEntity> getEntityClass() {
		return SecretShopInfoEntity.class;
	}

}
