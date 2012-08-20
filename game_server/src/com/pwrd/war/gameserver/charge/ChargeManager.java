package com.pwrd.war.gameserver.charge;

import java.text.MessageFormat;
import java.util.List;

import com.pwrd.war.common.LogReasons;
import com.pwrd.war.common.LogReasons.CurrencyLogReason;
import com.pwrd.war.core.time.TimeService;
import com.pwrd.war.core.util.Range;
import com.pwrd.war.core.util.TimeUtils;
import com.pwrd.war.gameserver.currency.Currency;
import com.pwrd.war.gameserver.human.Human;

public class ChargeManager {
	
	/** 主人 */
	private Human owner;
	
	private ChargePrizeService prizeService;
	
	private TimeService timeService;
	
	
	public ChargeManager(Human owner,ChargePrizeService prizeService,TimeService timeService) {
		this.owner = owner;		
		this.prizeService = prizeService;
		this.timeService = timeService;
	}


	public Human getOwner() {
		return owner;
	}
	
	public void checkAndResetTodayCharge(long lastChargeTime)
	{
		if(!TimeUtils.isSameDay(timeService.now(), lastChargeTime)) 
		{
			this.getOwner().setTodayCharge(0);
		}
	}
	
	/**
	 * 根据本次充值金额进行充值奖励给予
	 * @param chargeDiamond
	 */
	public void onPlusTodayCharge(int chargeDiamond)
	{
		int todayCharge = this.getOwner().getTodayCharge();	
		
		//增加当日充值总金额
		this.getOwner().setTodayCharge(todayCharge + chargeDiamond);

		List<Range<Integer>> ranges = prizeService.getRangesByCharge(todayCharge,chargeDiamond);
		
		int chargePrize = 0;//充值奖励
		for(Range<Integer> range : ranges)
		{
			chargePrize += prizeService.getTransferExtraPrize(range);
		}
		
		if(chargePrize > 0 )
		{
			this.owner.giveMoney(chargePrize, Currency.GOLD, true, 
					CurrencyLogReason.CHARGE, 
					CurrencyLogReason.CHARGE.getReasonText());
		}
	}
	
	public void refreshChargePanelInfo(int vipLevel,int vipNextLevel,int totalCharge,int nextVipDiffCharge,int nextVipTotalCharge)
	{
		
//		GCShowChargePanel gcShowChargePanel = new GCShowChargePanel();
//		gcShowChargePanel.setVipLevel(vipLevel);
//		gcShowChargePanel.setTotalCharge(totalCharge);
//		if(vipLevel == VipLevel.VIP10.getIndex())
//		{
//			gcShowChargePanel.setNextVipLevel(0);
//			gcShowChargePanel.setNextVipDesc(new String[0]);
//			gcShowChargePanel.setDiffCharge(0);			
//			gcShowChargePanel.setNextVipTotalCharge(0);
//		}
//		else
//		{
//			List<VipFunctionInfo> descs = Globals.getVipService().getDesc(vipNextLevel);
//			List<String> descList = Lists.newArrayList();
//			for(VipFunctionInfo desc :descs)
//			{
//				if(!StringUtils.isEmpty(desc.getDesc()))
//				{
//					descList.add(desc.getDesc());
//				}
//			}	
//			gcShowChargePanel.setNextVipLevel(vipNextLevel);
//			gcShowChargePanel.setNextVipDesc(descList.toArray(new String[0]));
//			gcShowChargePanel.setDiffCharge(nextVipDiffCharge);
//			gcShowChargePanel.setNextVipTotalCharge(nextVipTotalCharge);
//		}
//		
//		gcShowChargePanel.setChargePrizeInfo(buildChargePrizeInfo());	
//		this.owner.sendMessage(gcShowChargePanel);
	}
	
	/**
	 * 
	 * 您还需要充值多少钱就可以获得当日充值奖励
	 * 
	 * @return
	 */
	public String buildChargePrizeInfo()
	{
		String result = "";
		//当前充值距离下一级的差值
		int transferDiff = 0;
		//下一级充值奖励值
		int nextPrize = 0;
		
		long lastChargeTime = this.getOwner().getLastChargeTime();
		if(lastChargeTime != 0)
		{
			//如果不是同一天,每日充值清零
			this.getOwner().getChargeManager().checkAndResetTodayCharge(lastChargeTime);
		}
		
		//取得当天的充值额度
		int todayTransfer = this.getOwner().getTodayCharge();		
		Range<Integer> range = prizeService.getTransferRange(todayTransfer);
		if(range == null)
		{
			if(todayTransfer < prizeService.getMinTransferPrize()) //充值低于100
			{
				transferDiff = prizeService.getMinTransferPrize() - todayTransfer;				
				Range<Integer> minPrizeRange = prizeService.getTransferRange(prizeService.getMinTransferPrize());
				nextPrize = prizeService.getTransferExtraPrize(minPrizeRange);				
			}
		}
		else
		{
			Range<Integer> nextRange = prizeService.getNextTransferRange(range);
			if(nextRange != null)
			{
				transferDiff = nextRange.getMin() - todayTransfer;
				nextPrize = prizeService.getTransferExtraPrize(nextRange);		
			}	
		}
		if(transferDiff != 0 && nextPrize != 0)
		{			
//			result = Globals.getLangService().read(CommonLangConstants_10000.TODAY_TRANSFER_PRIZE_INFO,transferDiff,nextPrize);
		}
		return result;
	}

}
