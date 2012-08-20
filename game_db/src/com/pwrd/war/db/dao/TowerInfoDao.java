package com.pwrd.war.db.dao;

import java.util.List;

import com.pwrd.war.core.orm.DBService;
import com.pwrd.war.db.model.TowerInfoEntity;

public class TowerInfoDao extends BaseDao<TowerInfoEntity>{
	
	private static final String GET_TOWER_INFO_BY_CHARID = "queryPlayerTowerInfo";
	private static final String UPDATE_TOWER_INFO_BY_CHARID = "updatePlayerTowerInfo";
	private static final String UPDATE_REFRESH_TIMES_BY_CHARID = "updatePlayerRefreshTimes";
	private static final String UPDATE_SECRET_FLAG_BY_CHARID = "updatePlayerSecretFlag";
	
	private static final String[] GET_TOWER_INFO_BY_CHARID_PARAMS = new String[] { "charId" };
	private static final String[] UPDATE_TOWER_INFO_BY_CHARID_PARAMS = new String[] { "charId", "constellationId", "curNum", "maxNum" };
	private static final String[] UPDATE_REFRESH_TIMES_BY_CHARID_PARAMS = new String[] { "charId", "constellationId", "curNum", "refreshNum", "lastRefreshTime" };
	private static final String[] UPDATE_SECRET_FLAG_BY_CHARID_PARAMS = new String[] { "charId", "constellationId", "secretFlag" };
	
	
	public TowerInfoDao(DBService dbService) {
		super(dbService);
	}
	
	/**
	 * 获取商品列表
	 * @param characterId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<TowerInfoEntity> getTowerInfoByCharId(String characterId) {
		return this.dbService.findByNamedQueryAndNamedParam(GET_TOWER_INFO_BY_CHARID, GET_TOWER_INFO_BY_CHARID_PARAMS,
				new Object[] { characterId });
	}
	
	/**
	 * 战斗成功一次
	 * @param characterId
	 * @return
	 */

	public void updateTowInfoByCharId(String characterId, int towerNum, int curNum, int maxNum) {
		this.dbService.queryForUpdate(UPDATE_TOWER_INFO_BY_CHARID, UPDATE_TOWER_INFO_BY_CHARID_PARAMS,
				new Object[] { characterId, towerNum, curNum, maxNum });
	}
	
	@Override
	protected Class<TowerInfoEntity> getEntityClass() {
		return TowerInfoEntity.class;
	}
	
	/**
	 * 刷新星座
	 * @param charId
	 * @param curNum
	 * @param times
	 * @param refreshTime
	 */
	public void updateRefreshTimesByCharId(String charId, int towerNum, int curNum,
			int times, long refreshTime) {
		this.dbService.queryForUpdate(UPDATE_REFRESH_TIMES_BY_CHARID, UPDATE_REFRESH_TIMES_BY_CHARID_PARAMS,
				new Object[] { charId, towerNum, curNum, times, refreshTime });
	}

	public void updateRefreshTimesByCharId(String charId, int towerNum,
			boolean secretFlag) {
		this.dbService.queryForUpdate(UPDATE_SECRET_FLAG_BY_CHARID, UPDATE_SECRET_FLAG_BY_CHARID_PARAMS,
				new Object[] { charId, towerNum, secretFlag });
	}

}
