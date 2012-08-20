package com.pwrd.war.db.dao;

import java.util.List;

import com.pwrd.war.core.orm.DBService;
import com.pwrd.war.db.model.ItemEntity;

/**
 * 物品相关的Dao实现
 * 
 */
public class ItemDao extends BaseDao<ItemEntity> {
	private static final String GET_ITEMS_BY_CHARID = "queryPlayerItem";
	private static final String GET_ITEMS_IDS_BY_CHARID = "queryPlayerItemIds";
	private static final String[] GET_ITEMS_BY_CHARID_PARAMS = new String[] { "charId" };

	public ItemDao(DBService dbService) {
		super(dbService);
	}

	@SuppressWarnings("unchecked")
	public List<ItemEntity> getItemsByCharId(String characterId) {
		return this.dbService.findByNamedQueryAndNamedParam(GET_ITEMS_BY_CHARID, GET_ITEMS_BY_CHARID_PARAMS,
				new Object[] { characterId });
	}

	@SuppressWarnings("unchecked")
	public List<String> getItemIdsByCharId(String characterId) {
		return this.dbService.findByNamedQueryAndNamedParam(GET_ITEMS_IDS_BY_CHARID, GET_ITEMS_BY_CHARID_PARAMS,
				new Object[] { characterId });
	}

	@Override
	protected Class<ItemEntity> getEntityClass() {
		return ItemEntity.class;
	}
}
