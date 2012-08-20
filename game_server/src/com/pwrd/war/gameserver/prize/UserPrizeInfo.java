package com.pwrd.war.gameserver.prize;

import java.util.ArrayList;
import java.util.List;

import com.pwrd.war.db.model.UserPrize;

/**
 * 玩家奖励的基本信息，奖励ID和奖励名
 * 
 * 
 * 
 */
public class UserPrizeInfo {
	/** 奖励Id */
	private int prizeId;
	/** 奖励名 */
	private String prizeName;

	public UserPrizeInfo()
	{
		
	}
	
	private UserPrizeInfo(int prizeId, String prizeName) {
		this.prizeId = prizeId;
		this.prizeName = prizeName;
	}

	public static List<UserPrizeInfo> getFromUserPrizes(
			List<UserPrize> userPrizes) {
		List<UserPrizeInfo> _basicInfos = new ArrayList<UserPrizeInfo>();
		if (userPrizes == null) {
			return _basicInfos;
		}

		for (UserPrize _userPrize : userPrizes) {
			UserPrizeInfo _info = new UserPrizeInfo(_userPrize.getId(),
					_userPrize.getUserPrizeName());
			_basicInfos.add(_info);
		}

		return _basicInfos;
	}

	public int getPrizeId() {
		return prizeId;
	}

	public void setPrizeId(int prizeId) {
		this.prizeId = prizeId;
	}

	public String getPrizeName() {
		return prizeName;
	}

	public void setPrizeName(String prizeName) {
		this.prizeName = prizeName;
	}
}
