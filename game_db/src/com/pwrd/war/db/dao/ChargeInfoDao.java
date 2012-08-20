package com.pwrd.war.db.dao;

import java.util.List;

import com.pwrd.war.core.orm.DBService;
import com.pwrd.war.db.model.ChargeInfo;

public class ChargeInfoDao extends BaseDao<ChargeInfo> {
	
	private static final String GET_CHARGE_INFO_BY_USERID = "queryChargeInfoListByUserId";
	
	private static final String GET_CHARGE_INFO_BY_ORDERID = "queryChargeInfoByOrderId";
	
	private static final String UPDATE_CHARGE_INFO_STATUS = "updateChargeInfoStatus";
	
	
	private static final String[] GET_CHARGE_INFO_BY_USERID_PARAM = new String[] { "userId" };
	
	private static final String[] GET_CHARGE_INFO_BY_ORDERID_PARAM = new String[] { "userId", "orderId" };
	
	private static final String[] UPDATE_CHARGE_INFO_STATUS_PARAM = new String[] { "orderId" };
	

	public ChargeInfoDao(DBService dbService) {
		super(dbService);
	}

	@Override
	protected Class<ChargeInfo> getEntityClass() {
		return ChargeInfo.class;
	}
	
	/**
	 * 根据账号ID从数据库获取所有直充记录
	 * 
	 * @param passportId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ChargeInfo> loadChargeInfos(String userId) {
		return dbService.findByNamedQueryAndNamedParam(
				GET_CHARGE_INFO_BY_USERID, GET_CHARGE_INFO_BY_USERID_PARAM,
				new Object[] { userId });
	}
	
	/**
	 * 根据订单id查询唯一的充值记录
	 * 
	 * @param passportId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ChargeInfo getChargeInfoByOrderId(String userId, long orderId) {
		List<ChargeInfo> _chargeInfos = this.dbService.findByNamedQueryAndNamedParam(
				GET_CHARGE_INFO_BY_ORDERID, GET_CHARGE_INFO_BY_ORDERID_PARAM,
				new Object[] { userId, orderId });
		if (_chargeInfos.size() > 0) {
			return (ChargeInfo) _chargeInfos.get(0);
		}
		return null;
	}
	
	/**
	 * 更新充值信息状态
	 * 
	 * @param prizeId
	 * @return
	 */
	public boolean updateChargeInfoStatus(long orderId) {
		return this.dbService.queryForUpdate(UPDATE_CHARGE_INFO_STATUS,
				UPDATE_CHARGE_INFO_STATUS_PARAM, new Object[] { orderId }) == 1;
	}

}
