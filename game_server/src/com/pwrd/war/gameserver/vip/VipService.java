package com.pwrd.war.gameserver.vip;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.pwrd.op.LogOp;
import com.pwrd.op.LogOpChannel;
import com.pwrd.war.common.LogReasons.ChargeLogReason;
import com.pwrd.war.common.LogReasons.CurrencyLogReason;
import com.pwrd.war.common.LogReasons.MoneyLogReason;
import com.pwrd.war.common.LogReasons.VipLogReason;
import com.pwrd.war.core.util.TimeUtils;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.currency.Currency;
import com.pwrd.war.gameserver.currency.CurrencyCostType;
import com.pwrd.war.gameserver.human.Human;
import com.pwrd.war.gameserver.vip.template.VipFunctionTemplate;
import com.pwrd.war.gameserver.vip.template.VipTemplate;
import com.pwrd.war.gameserver.vip.template.VipTowerTemplate;

public class VipService {
	private Map<Integer, Integer> vipTemplateMap = new HashMap<Integer, Integer>();
	private Map<Integer, VipFunctionTemplate> vipFunctionTemplateMap = new HashMap<Integer, VipFunctionTemplate>();
	private Map<Integer, Integer> vipTowerMap = new HashMap<Integer, Integer>();
	
	public VipService(){
		Map<Integer, VipTemplate>  map = Globals.getTemplateService().getAll(VipTemplate.class);
		for(Map.Entry<Integer, VipTemplate> e :map.entrySet()){
			vipTemplateMap.put(e.getValue().getVipLevel(), e.getValue().getCharge());
		}
		
		vipFunctionTemplateMap = Globals.getTemplateService().getAll(VipFunctionTemplate.class);
		
		Map<Integer, VipTowerTemplate> tower = Globals.getTemplateService().getAll(VipTowerTemplate.class);
		for(Map.Entry<Integer, VipTowerTemplate> t : tower.entrySet()){
			vipTowerMap.put(t.getValue().getVipLevel(), t.getValue().getTimes());
		}
	}
	
	/**
	 * 根据充值金额返回vip等级
	 * @param charge
	 * @return
	 */
	public int getVipLevel(int charge){
		int level = 0;
		for(Map.Entry<Integer, Integer> e : vipTemplateMap.entrySet()){
			if(charge >= e.getValue()){
				level++;
			}
		}
		return level;
	}
	
	/**
	 * 用户充值所引起的状态变化
	 * @param human
	 * @param gold
	 */
	public void charge(Human human, int gold){
		int curLevel = human.getVipLevel();
		int amount = human.getPropertyManager().getChargeAmount() + gold;
		int level = this.getVipLevel(amount);
		
		human.getPropertyManager().setChargeAmount(amount);
		human.getPropertyManager().setVip(level);
		human.snapChangedProperty(true);
		human.giveMoney(gold, Currency.GOLD, true, 
				CurrencyLogReason.VIP, CurrencyLogReason.VIP.getReasonText());
//		human.snapChangedProperty(true);
		
		//GM平台
		long now = Globals.getTimeService().now();
		LogOp.log(LogOpChannel.RECHARGE, human.getUUID(), now, TimeUtils.formatYMDTime(now), gold, 0);
		
		//LOG平台
		Globals.getLogService().sendChargeLog(human, ChargeLogReason.CHARGE_DIAMOND_SUCCESS, null, gold, now);
		
		
		//先在此写一个 以后要做通用的推送功能
		//TODO
		if(level >= 4){
			Globals.getTreeService().getTreeInfo(human.getPlayer());
		}
		
		//LOG
		if(curLevel < level){
			Globals.getLogService().sendVipLog(human, VipLogReason.CHANGE, null, level, Globals.getTimeService().now());
		}
	}
	
	/**
	 * 根据功能id返回所需的vip等级
	 * @param functionId
	 * @return
	 */
	public int getLevelFromFunction(String functionId){
		int level = -1;
		if(StringUtils.isBlank(functionId)){
			return level;
		}
		for(Map.Entry<Integer, VipFunctionTemplate> e : vipFunctionTemplateMap.entrySet()){
			if(functionId.equals(e.getValue().getFunctionId())){
				level = e.getValue().getVipLevel();
			}
		}
		return level;
	}

	public Map<Integer, Integer> getVipTowerMap() {
		return vipTowerMap;
	}
}
