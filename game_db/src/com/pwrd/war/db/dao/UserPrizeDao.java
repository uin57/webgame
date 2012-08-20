package com.pwrd.war.db.dao;

import java.util.ArrayList;
import java.util.List;

import com.pwrd.war.core.orm.DBService;
import com.pwrd.war.db.model.UserPrize;

/**
 * GM补偿本地数据访问类
 * 
 */
public class UserPrizeDao extends BaseDao<UserPrize> {

	private static final String GET_USER_PRIZE_NAMELIST_BY_PASSPORTID = "queryUserPrizeNameListByPassportId";
	
	private static final String GET_USER_PRIZE_BY_PRIZEID = "queryUserPrizeByPrizeId";
	
	private static final String[] GET_USER_PRIZE_NAMELIST_BY_PASSPORTID_PARAM = new String[] { "passportId" };
	
	private static final String[] GET_USER_PRIZE_BY_PRIZEID_PARAM = new String[] { "passportId", "prizeId" };
	
	private static final String UPDATE_USER_PRIZE_STATUS = "updateUserPrizeStatus";
	
	private static final String[] UPDATE_USER_PRIZE_STATUS_PARAM = new String[] { "prizeId" };

	public UserPrizeDao(DBService dbServcie) {
		super(dbServcie);
	}

	/**
	 * 查询玩家的补偿奖励列表，只取prizeId,名称，奖励类型
	 * 
	 * @param passportId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<UserPrize> getUserPrizeNameListByPassportId(String passportId) {
		List<Object[]> _ids = this.dbService.findByNamedQueryAndNamedParam(
				GET_USER_PRIZE_NAMELIST_BY_PASSPORTID,
				GET_USER_PRIZE_NAMELIST_BY_PASSPORTID_PARAM,
				new Object[] { passportId });
		if (_ids == null || _ids.size() == 0) {
			return null;
		}

		List<UserPrize> _prizeList = new ArrayList<UserPrize>(_ids.size());
		for (Object[] _prizeObject : _ids) {
			UserPrize _userPrize = new UserPrize();
			_userPrize.setId((Integer) _prizeObject[0]);
			_userPrize.setUserPrizeName((String) _prizeObject[1]);
			_userPrize.setType((Integer) _prizeObject[2]);
			_userPrize.setPassportId(passportId);
			_prizeList.add(_userPrize);
		}

		return _prizeList;
	}

	/**
	 * 查询玩家的补偿奖励列表
	 * 
	 * @param passportId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public UserPrize getUserPrizeByPrizeId(String passportId, int prizeId) {
		List<Object[]> _ids = this.dbService.findByNamedQueryAndNamedParam(
				GET_USER_PRIZE_BY_PRIZEID, GET_USER_PRIZE_BY_PRIZEID_PARAM,
				new Object[] { passportId, prizeId });
		if (_ids == null || _ids.size() == 0) {
			return null;
		}
		UserPrize _userPrize = null;
		for (Object[] _prizeObject : _ids) {
			if (_prizeObject != null) {
				_userPrize = new UserPrize();
				_userPrize.setId((Integer) _prizeObject[0]);
				_userPrize.setUserPrizeName((String) _prizeObject[1]);
				_userPrize.setPassportId(""+ _prizeObject[2]);
				_userPrize.setCoin((String) _prizeObject[3]);
				_userPrize.setItem((String) _prizeObject[4]);
				_userPrize.setType((Integer) _prizeObject[5]);
				_userPrize.setStatus((Integer) _prizeObject[6]);
				break;
			}
		}
		return _userPrize;
	}

	/**
	 * 
	 * 
	 * @param prizeId
	 * @return
	 */
	public boolean updateUserPrizeStatus(int prizeId) {
		return this.dbService.queryForUpdate(UPDATE_USER_PRIZE_STATUS,
				UPDATE_USER_PRIZE_STATUS_PARAM, new Object[] { prizeId }) == 1;
	}

	@Override
	protected Class<UserPrize> getEntityClass() {
		return UserPrize.class;
	}

}