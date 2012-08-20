package com.pwrd.war.gameserver.prize;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.pwrd.war.common.model.quest.MoneyBonus;
import com.pwrd.war.core.util.StringUtils;
import com.pwrd.war.db.model.PrizeInfo;
import com.pwrd.war.db.model.UserPrize;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.currency.Currency;
import com.pwrd.war.gameserver.human.wealth.Bindable.BindStatus;
import com.pwrd.war.gameserver.human.wealth.Bindable.Oper;
import com.pwrd.war.gameserver.item.ItemParam;
import com.pwrd.war.gameserver.item.template.ItemTemplate;

public class PrizeService {
	
	/** 奖励缓存 */
	private ConcurrentMap<Integer, PrizeInfo> prizeCache;
	
	/** 缓存大小 */
	private final static int CACHE_SIZE = 100;
	
	public PrizeService() {
		prizeCache = new ConcurrentHashMap<Integer, PrizeInfo>();
	}
	

	
	/**
	 * 领取平台奖励
	 * 
	 * @param prizeId
	 * @return
	 */
	public PlatformPrizeHolder fetchPlatformPrizeByPrizeId(int uniqueId,
			int prizeId) {

		PrizeInfo _prizeInfo = prizeCache.get(prizeId);
		if (_prizeInfo == null) {
			_prizeInfo = Globals.getDaoService().getPlatformPrizeDao()
					.getPrizesByPrizeId(prizeId);

			if (_prizeInfo != null) {
				if (prizeCache.size() == CACHE_SIZE) {
					// 缓存满了，全部清空从头再来
					clearCache();
				}
				PrizeInfo _tmp = prizeCache.putIfAbsent(
						_prizeInfo.getPrizeId(), _prizeInfo);
				if (_tmp != null) {
					_prizeInfo = _tmp;
				}
			}
		}
		if (_prizeInfo != null) {
			PlatformPrizeHolder _prize = toPlatformPrizeHolder(_prizeInfo);
			_prize.setUniqueId(uniqueId);
			return _prize;
		}

		return null;
	}
	
	
	/**
	 * 转换平台奖励
	 * 
	 * @param prizeInfo
	 * @return
	 */
	private PlatformPrizeHolder toPlatformPrizeHolder(PrizeInfo prizeInfo) {
		PlatformPrizeHolder _holder = new PlatformPrizeHolder();
		_holder.setPrizeId(prizeInfo.getPrizeId());
		_holder.setPrizeName(prizeInfo.getPrizeName());
		_holder.setItemList(getPrizeItem(prizeInfo.getItem()));
		_holder.setCoinList(getPrizeCoin(prizeInfo.getCoin()));
		return _holder;
	}
	
	/**
	 * 清空缓存
	 */
	public void clearCache() {
		prizeCache.clear();
	}
	
	
	/**
	 * 更新用户奖励状态
	 * 
	 * @param prizeId
	 * @return
	 */
	public boolean updateUserPrizeStatus(int prizeId) {
		return Globals.getDaoService().getUserPrizeDao().updateUserPrizeStatus(
				prizeId);
	}
	
	/**
	 * 获取玩家奖励
	 * 
	 * @param passportId
	 * @param prizeId
	 * @return
	 */
	public UserPrizeHolder fetchUserPrizeByPassportId(String passportId,
			int prizeId) {

		UserPrize prize = Globals.getDaoService().getUserPrizeDao()
				.getUserPrizeByPrizeId(passportId, prizeId);
		if (prize != null) {
			UserPrizeHolder _prizeHolder = toUserPrizeHolder(prize);
			checkPrize(_prizeHolder);
			return _prizeHolder;
		}
		return null;
	}
	
	/**
	 * 获取玩家奖励列表
	 * 
	 * @param passportId
	 * @return
	 */
	public List<UserPrize> fetchUserPrizeNameListByPassportId(String passportId) {
		return Globals.getDaoService().getUserPrizeDao()
				.getUserPrizeNameListByPassportId(passportId);
	}
	
	/**
	 * 转换用户奖励
	 * 
	 * @param userPrize
	 * @return
	 */
	private UserPrizeHolder toUserPrizeHolder(UserPrize userPrize) {
		UserPrizeHolder _holder = new UserPrizeHolder();
		_holder.setId(userPrize.getId());
		_holder.setPassportId(userPrize.getPassportId());
		_holder.setType(userPrize.getType());
		_holder.setStatus(userPrize.getStatus());
		_holder.setItemList(getPrizeItem(userPrize.getItem()));
		_holder.setCoinList(getPrizeCoin(userPrize.getCoin()));
		return _holder;
	}
	
	/**
	 * 校验数据
	 * 
	 * @param prize
	 */
	private void checkPrize(BasePrizeHolder prize) {

		// 物品ID存在,数量合法
		if (prize.getItemList() != null) {
			for (ItemParam _item : prize.getItemList()) {
//				int _itemId = _item.getTemplateId();
//				int _count = _item.getCount();
//
//				ItemTemplate tmpl = Globals.getItemService()
//						.getItemTempByTempId(_itemId);
//
//				if (tmpl == null) {
//					throw new IllegalArgumentException(prize
//							+ " Item template cannot be found!");
//				}
//				if (_count <= 0) {// Constants
//					throw new IllegalArgumentException(prize
//							+ " Item count illegal!");
//				}
			}
		}

		// 金钱类型合法，数量合法
		if (prize.getCoinList() != null) {
			for (MoneyBonus _money : prize.getCoinList()) {
				Currency currency = Currency.valueOf(_money.getType());
				int _count = _money.getMoney();

				if (currency == null || currency == Currency.NULL) {
					throw new IllegalArgumentException(prize
							+ " CurrencyType cannot be found!");
				}

				if (_count <= 0) {
					throw new IllegalArgumentException(prize
							+ " Currency count illegal!");
				}
			}
		}
	}
	
	
	
	
	/**
	 * 转换物品
	 * 
	 * @param itemStr
	 * @param itemMap
	 */
	private ItemParam[] getPrizeItem(String itemStr) {
		if (itemStr == null || itemStr.trim().length() == 0) {
			return null;
		}
		String[] _items = itemStr.split(";");
		if (_items.length == 0) {
			return null;
		}
		Map<Integer, Integer> _itemMap = new HashMap<Integer, Integer>(
				_items.length);

		for (int i = 0; i < _items.length; i++) {
			String[] _itemEQ = _items[i].split("=");
			int _itemId = Integer.parseInt(_itemEQ[0].trim());
			int _itemCount = Integer.parseInt(_itemEQ[1].trim());
			// 同物品ID,叠加
			int _oldCount = 0;
			if (_itemMap.get(_itemId) != null) {
				_oldCount = _itemMap.get(_itemId);
			}
			_itemMap.put(_itemId, _itemCount + _oldCount);
		}

		ItemParam[] _holder = new ItemParam[_itemMap.size()];
		int i = 0;
//		for (Entry<Integer, Integer> _entry : _itemMap.entrySet()) {
//			int tmplId = _entry.getKey();
//			int count = _entry.getValue();
//			ItemTemplate tmpl = Globals.getItemService().getItemTempByTempId(
//					tmplId);
//			BindStatus bind = null;
//			if (tmpl != null) {
//				bind = tmpl.getBindStatusAfterOper(BindStatus.NOT_BIND,
//						Oper.GET);
//			}
////			_holder[i] = new ItemParam(tmplId, count, bind);
//			i++;
//		}
		return _holder;
	}

	
	/**
	 * 转换金钱
	 * 
	 * @param coinStr
	 * @param coinMap
	 */
	private MoneyBonus[] getPrizeCoin(String coinStr) {
		if (StringUtils.isEmpty(coinStr)) {
			return null;
		}
		String[] _coins = coinStr.split(";");
		if (_coins.length == 0) {
			return null;
		}
		Map<Integer, Integer> _coinMap = new HashMap<Integer, Integer>(coinStr
				.length());
		for (int i = 0; i < _coins.length; i++) {
			String[] _coinEQ = _coins[i].split("=");
			int _coinId = Integer.parseInt(_coinEQ[0].trim());
			int _coinCount = Integer.parseInt(_coinEQ[1].trim());
			int _oldCount = 0;
			// 同货币类型，叠加
			if (_coinMap.get(_coinId) != null) {
				_oldCount = _coinMap.get(_coinId);
			}
			_coinMap.put(_coinId, _coinCount + _oldCount);
		}

		MoneyBonus[] _holder = new MoneyBonus[_coins.length];
		int i = 0;
		for (Entry<Integer, Integer> _entry : _coinMap.entrySet()) {
			_holder[i++] = new MoneyBonus(_entry.getValue(), _entry.getKey());
		}
		return _holder;
	}


}
