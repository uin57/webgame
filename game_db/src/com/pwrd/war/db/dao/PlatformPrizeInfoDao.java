package com.pwrd.war.db.dao;

import java.util.List;

import com.pwrd.war.core.orm.DBService;
import com.pwrd.war.db.model.PrizeInfo;

/**
 * 
 * 平台奖励信息查询Dao
 * 
 */
public class PlatformPrizeInfoDao extends BaseDao<PrizeInfo> {
	
	private static final String GET_PRIZE_BY_PRIZEID = "queryPrizeInfo";
	
	private static final String[] GET_PRIZE_BY_PRIZEID_PARAM = new String[] { "prizeId" };

	public PlatformPrizeInfoDao(DBService dbServcie) {
		super(dbServcie);
	}

	@SuppressWarnings("unchecked")
	public PrizeInfo getPrizesByPrizeId(int prizeId) {
		List<Object[]> _ids = this.dbService.findByNamedQueryAndNamedParam(
				GET_PRIZE_BY_PRIZEID, GET_PRIZE_BY_PRIZEID_PARAM,
				new Object[] { prizeId });
		if (_ids == null || _ids.size() == 0) {
			return null;
		}
		Object[] _prizeObject = _ids.get(0);
		PrizeInfo _prizeInfo = null;
		if (_prizeObject != null) {
			_prizeInfo = new PrizeInfo();
			_prizeInfo.setId((Integer) _prizeObject[0]);
			_prizeInfo.setPrizeId((Integer) _prizeObject[1]);
			_prizeInfo.setPrizeName((String) _prizeObject[2]);
			_prizeInfo.setCoin((String) _prizeObject[3]);
			_prizeInfo.setItem((String) _prizeObject[4]);
			_prizeInfo.setPet((String) _prizeObject[5]);
		}
		return _prizeInfo;
	}

	@Override
	protected Class<PrizeInfo> getEntityClass() {
		return PrizeInfo.class;
	}

}
