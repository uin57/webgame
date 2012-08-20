package com.pwrd.war.gameserver.currency;

import com.pwrd.war.common.LogReasons.CurrencyLogReason;
import com.pwrd.war.common.LogReasons.MoneyLogReason;
import com.pwrd.war.common.constants.Loggers;
import com.pwrd.war.core.util.LogUtils;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.common.i18n.constants.CommonLangConstants_10000;
import com.pwrd.war.gameserver.human.Human;

/**
 * 金钱处理器,单实例
 * 
 * 
 */
public enum CurrencyProcessor {

	instance;


	/**
	 * 扣钱，如果扣钱的数量为0则抛出{@link IllegalArgumentException}
	 * 
	 * @param human
	 *            玩家角色
	 * @param amount
	 *            扣得数量，>0才有效
	 * @param mainCurrency
	 *            主货币类型，不为NULL/null才有效
	 * @param altCurrency
	 *            替补货币类型，可以为NULL/null
	 * @param needNotify
	 *            是否在该函数内通知玩家货币改变，为true时信息格式为"您花费了xx（货币单位）用于xx"， false时不在本函数内提示
	 * @param usageLangId
	 *            用途多语言Id,如果needNotify为true，这里提供的用途会被加载提示信息“用于”后面
	 * @param reason
	 *            扣钱的原因
	 * @param detailReason
	 *            详细原因，通常为null，扩展使用
	 * @param reportItemId
	 *            向平台汇报贵重物品消耗时的itemTemplateId，非物品的消耗时使用-1
	 * @return 扣钱成功返回true,否则返回false,失败可能是钱已经超出了最大限额,参数不合法等
	 */
	public boolean costMoney(Human human, final int amount,
			final Currency mainCurrency, final Currency altCurrency,
			boolean needNotify, Integer usageLangId, CurrencyLogReason currencyLogReason,
			String detailReason, int reportItemId) {
		// 无效参数
		if (amount <= 0 || mainCurrency == null
				|| mainCurrency == Currency.NULL) {
			throw new IllegalArgumentException(
					String
							.format(
									"扣钱参数有误：amount=%d mainCurrency=%s altCurrency=%s reason=%s detailReason=%s reportItemId=%d",
									amount, mainCurrency, altCurrency, currencyLogReason,
									detailReason, reportItemId));
		}
		int mainCost = 0, altCost = 0;
		// 取玩家身上的钱数
		int mainCurrAmount = getMoney(human, mainCurrency);
		int altCurrAmount = 0;
		if (mainCurrAmount >= amount) {
			// 只扣主货币就够了
			mainCost = amount;
			altCost = 0;
		} else {
			// 需要扣辅助货币，看看够不够
			if (altCurrency != null && altCurrency != Currency.NULL) {
				// 还需要扣这么多辅助货币
				int mainLack = amount - mainCurrAmount;
				altCurrAmount = getMoney(human, altCurrency);
				if (altCurrAmount >= mainLack) {
					// 住货币全部扣掉
					mainCost = mainCurrAmount;
					// 辅助货币扣掉剩下的
					altCost = mainLack;
				} else {
					// 辅助货币不够
					return false;
				}
			} else {
				// 没有辅助货币，不够扣
				return false;
			}
		}
		// 扣钱成功
		int mainLeft = mainCurrAmount, altLeft = altCurrAmount;
		if (mainCost > 0) {
			mainLeft = substractAndFixMoney(human, mainCost, mainCurrency,
					needNotify);
		}
		if (altCost > 0) {
			altLeft = substractAndFixMoney(human, altCost, altCurrency,
					needNotify);
		}
		
		// 实时更新钱
		human.setModified();
		// 更新客户端任务属性
		human.snapChangedProperty(true);
		
		// 通知
		if (needNotify) {
			//TODO 为true时信息格式为"您花费了xx（货币单位）用于xx"
		}
		
		try{
			if(mainCurrency == Currency.GOLD)
			{
//				human.getQuestDiary().getListener().onCostGold(human);
			}
		}catch (Exception e) {
			Loggers.charLogger.error(LogUtils.buildLogInfoStr(human.getUUID(),
				"记录日常任务次数"), e);
		}

		// 汇报贵重物品消耗
		reportTransrecord(human, mainCurrency, amount, reportItemId, currencyLogReason);

		int altCurrencyIndex = (altCurrency == null ? 0 : altCurrency.index);
		
//		// 发送金钱日志
//		try {
//			Globals.getLogService().sendMoneyLog(human, reason, detailReason,
//					mainCurrency.index, -mainCost, mainLeft, altCurrencyIndex,
//					-altCost, altLeft);
//		} catch (Exception e) {
//			Loggers.charLogger.error(LogUtils.buildLogInfoStr(human.getUUID(),
//					"记录扣钱日志时出错"), e);
//		}
		return true;
	}

	private int addAndFixMoney(Human human, final int addValue, final Currency currency, boolean needNotify) {
		int orgMoney = getMoney(human, currency);
		int regularNewValue = orgMoney + addValue;
//		int propIndex = currency.getRoleBaseIntPropIndex();
		human.getPropertyManager().setCurrency(currency, regularNewValue);
		if (needNotify && addValue != 0) {
			//TODO 消息通知玩家获得了多少钱		
//			human.getPlayer().sendHeadMessage("+"+addValue, 0xfff600);//#fff600
			String content = Globals.getLangService().read(CommonLangConstants_10000.ADDPROP, addValue,
					Globals.getLangService().read(currency.getNameKey()));
			human.getPlayer().sendHeadMessage(content, 0xfff600);//
		}
		return regularNewValue;
	}

	private int substractAndFixMoney(Human human, final int subValue,
			final Currency currency, boolean needNotify) {
		int orgMoney = getMoney(human, currency);
		int maxCanSub = orgMoney - 0; // 金钱下限统一定为0
		int regularNewValue = orgMoney;
		if (subValue > maxCanSub) {
			Loggers.gameLogger.error(LogUtils.buildLogInfoStr(human.getUUID(),
					String.format("money not enough orgMoney=%d subValue=%d",
							orgMoney, subValue)));
			regularNewValue = 0;
		} else {
			regularNewValue = orgMoney - subValue;
		}
//		int propIndex = currency.getRoleBaseIntPropIndex();
////		human.getBaseIntProperties().set(propIndex,
////				regularNewValue);
		human.getPropertyManager().setCurrency(currency, regularNewValue);
		return regularNewValue;
	}

	/**
	 * 给钱
	 * 
	 * @param human
	 *            玩家角色
	 * @param amount
	 *            改变数量，>0才有效
	 * @param currency
	 *            货币类型
	 * @param needNotify
	 *            是否在该函数内通知玩家货币改变，为true时信息格式为"您获得xx（货币单位）"， false时不在本函数内提示
	 * @param reason
	 *            给钱原因
	 * @param detailReason
	 *            详细原因，通常为null，扩展使用
	 * @param consumeDescs
	 *            返回给调用者的货币改变信息
	 * @return 给钱成功返回true,否则返回false,失败可能是钱已经超出了最大限额,参数不合法等
	 */
	public boolean giveMoney(Human human, final int amount,
			final Currency currency, boolean needNotify, CurrencyLogReason currencyReason,
			String detailReason) {
		// 无效参数
		if (amount <= 0 || currency == null || currency == Currency.NULL) {
			throw new IllegalArgumentException(String.format(
					"给钱参数有误：amount=%d currency=%s reason=%s detailReason=%s",
					amount, currency, currencyReason, detailReason));
		}
		
		
		//TODO 根据货币产生原因,再判断是否超过该类货币上限,进行操作限制
		int realamount = amount;
		if(currency == Currency.COINS){
			int max = Integer.MAX_VALUE;
			if(human.getLevel() <= 90)max = human.getLevel() * human.getLevel() *5000 + 1073741824;
			if((human.getPropertyManager().getCurrency(Currency.COINS) <= max 
					&& human.getPropertyManager().getCurrency(Currency.COINS) + amount <= 0)
					|| human.getPropertyManager().getCurrency(Currency.COINS) + amount >= max){				
//				human.getPlayer().sendErrorPromptMessage(CommonLangConstants_10000.MAX_COINS, max);
				human.getPlayer().sendButtonMessage(CommonLangConstants_10000.MAX_COINS, max);
				realamount = max - human.getPropertyManager().getCurrency(Currency.COINS);
			}
		}else if(currency == Currency.COUPON || currency == Currency.GOLD){
			int max = 999999999; 
			int all = human.getPropertyManager().getCurrency(Currency.COUPON) + human.getPropertyManager().getCurrency(Currency.GOLD); 
			if(all + amount >= max || (all <= max && all + amount <= 0)){				
//				human.getPlayer().sendErrorPromptMessage(CommonLangConstants_10000.MAX_GOLD, max);
				human.getPlayer().sendButtonMessage(CommonLangConstants_10000.MAX_GOLD, max);
				realamount = max - all ;
			}
		}
		
//		int newAmount = addAndFixMoney(human, realamount, currency, needNotify);
		this.addAndFixMoney(human, realamount, currency, needNotify);
		
		// 实时更新钱
		human.setModified();
		// 更新客户端任务属性
		human.snapChangedProperty(true);
		
		
//		// 发送金钱日志
//		try {
//			Globals.getLogService().sendMoneyLog(human, reason, detailReason, 
//					currency.index,	amount, newAmount, 0, 0, 0);
//		} catch (Exception e) {
//			Loggers.charLogger.error(LogUtils.buildLogInfoStr(human.getUUID(),
//					"记录给钱日志时出错"), e);
//		}
		return true;
	}

	/**
	 * 检查玩家是否足够指定货币，如果替补货币为NULL/null则只检查主货币，主货币不可以为NULL/null
	 * 
	 * @param human
	 *            玩家角色
	 * @param amount
	 *            数量，>=0才有效，==0时永远返回true
	 * @param mainCurrency
	 *            主货币类型
	 * @param altCurrency
	 *            替补货币类型，为NULL/null时只检测主货币
	 * @param needNotify
	 *            是否需要通知，如果为true则当返回false时会提示，您的xxx（主货币名称）不足
	 * @return 
	 *         如果主货币够amount返回true，主货币不够看替补货币够不够除现有主货币外剩下的，够也返回true，加起来都不够返回false，
	 *         参数无效也会返回false
	 */
	public boolean hasEnoughMoney(Human human, final int amount,
			final Currency mainCurrency, final Currency altCurrency,
			boolean needNotify) {
		if (amount < 0 || mainCurrency == null || mainCurrency == Currency.NULL) {
			return false;
		}
		int mainCurrAmount = getMoney(human, mainCurrency);
		if (mainCurrAmount >= amount) {
			// 主货币就足够了
			return true;
		} else if (altCurrency == null || altCurrency == Currency.NULL) {
			// 主货币不足，没有替补货币，就不够了
			if (needNotify) {
				human.getPlayer().sendErrorPromptMessage(
						CommonLangConstants_10000.COMMON_NOT_ENOUGH,
						Globals.getLangService().read(mainCurrency.getNameKey()));
			}
			return false;
		} else {
			// 还缺多少钱
			int lack = amount - mainCurrAmount;
			int altCurrAmount = getMoney(human, altCurrency);
			if (altCurrAmount >= lack) {
				// 辅助货币够
				return true;
			} else {
				// 辅助货币不够
				if (needNotify) {
					//TODO
					human.getPlayer().sendErrorPromptMessage(
							CommonLangConstants_10000.COMMON_NOT_ENOUGH, 
							Globals.getLangService().read(mainCurrency.getNameKey()));
				}
				return false;
			}
		}
	}

	/**
	 * 查询玩家身上有多少钱
	 * 
	 * @param human
	 *            玩家角色
	 * @param currency
	 *            货币类型
	 * @return 钱的数量
	 */
	public int getMoney(Human human, final Currency currency) {
//		int propIndex = currency.getRoleBaseIntPropIndex();
		return human.getPropertyManager().getCurrency(currency);
	}

	/**
	 * 报告贵重物品消耗（只记录了钻石的消耗）
	 * 
	 * @param human
	 *            角色
	 * @param currency
	 *            货币类型
	 * @param amount
	 *            消耗货币数量
	 * @param reportItemId
	 *            temTemplateId，非物品的消耗时使用-1
	 * @param reason
	 *            扣钱的原因
	 */
	private void reportTransrecord(Human human, Currency currency, int amount,
			int reportItemId, CurrencyLogReason reason) {
		if (currency == Currency.GOLD) {
			if (reportItemId < 0) {
//				reportItemId = reason.reason;
			}
			
//			String orderId = UUID.randomUUID().toString();
			
			if (Globals.getServerConfig().isTurnOnLocalInterface()) {
				try {
//					Globals.getAsyncLocalService().reportTransRecord(orderId,
//							human.getPassportId(), String.valueOf(human.getUUID()),
//							amount, String.valueOf(reportItemId),
//							human.getPlayer().getClientIp());
				}catch (Exception e) {
					Loggers.gameLogger.error(LogUtils.buildLogInfoStr(
							human.getUUID(), "汇报贵重物品时异常"), e);
				}
			}
		}
	}
}
