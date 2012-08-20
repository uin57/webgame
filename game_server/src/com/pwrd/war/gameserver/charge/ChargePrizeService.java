package com.pwrd.war.gameserver.charge;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.pwrd.war.common.InitializeRequired;
import com.pwrd.war.core.template.TemplateService;
import com.pwrd.war.core.util.Range;
import com.pwrd.war.gameserver.charge.template.ChargePrizeTemplate;

public class ChargePrizeService implements InitializeRequired{
	
	/** 配置模版服务 */
	private TemplateService templateService;
	
	/** key - 充值额度范围  value - 充值额外赠送 */
	private LinkedHashMap<Range<Integer>,Integer> transferPrizeDef = Maps.newLinkedHashMap();
	
	private int minTransferPrize = 0;
	
	public ChargePrizeService(TemplateService tmplService)
	{
		this.templateService = tmplService;
	}

	@Override
	public void init() {		
		int min = Integer.MAX_VALUE;
		for(ChargePrizeTemplate  prizeTemplate:this.templateService.getAll(ChargePrizeTemplate.class).values())
		{
			Range<Integer> transferPrize = new Range<Integer>(prizeTemplate.getLowlimit(), prizeTemplate.getHighlimit());
			transferPrizeDef.put(transferPrize, prizeTemplate.getExtraPrize());
			
			if(prizeTemplate.getLowlimit() < min)
			{
				min = prizeTemplate.getLowlimit();
			}
		}
		minTransferPrize = min;
	}
	
	
	
	/**
	 * 根据当前充入得到所在范围
	 * @param todayTransferCount
	 * @return
	 */
	public Range<Integer> getTransferRange(int todayTransferCount)
	{
		Range<Integer> result = null;
		Iterator<Map.Entry<Range<Integer>,Integer>> it = transferPrizeDef.entrySet().iterator();
		while(it.hasNext())
		{
			Map.Entry<Range<Integer>, Integer> entry = it.next();
			Range<Integer> range = entry.getKey();
			if(range.contains(todayTransferCount))
			{
				result = range;
				break;
			}			
		}
		return result;
	}
	
	/**
	 * 得到下一阶段的范围对象
	 * @param range
	 * @return
	 */
	public Range<Integer> getNextTransferRange(Range<Integer> range)
	{
		return getTransferRange(range.getMax());
	}
	
	/**
	 * 得到某范围的充值奖励
	 * 
	 * @param range
	 * @return
	 */
	public int getTransferExtraPrize(Range<Integer> range)
	{
		Integer extraPrize = transferPrizeDef.get(range);
		if(extraPrize != null)
		{
			return extraPrize.intValue();
		}		
		else
		{
			return 0;
		}
	}

	public int getMinTransferPrize() {
		return minTransferPrize;
	}

	public List<Range<Integer>> getRangesByCharge(int todayCharge, int chargeGold) {
		
		List<Range<Integer>> ranges = Lists.newArrayList();
		
		Iterator<Map.Entry<Range<Integer>,Integer>> it = transferPrizeDef.entrySet().iterator();
		while(it.hasNext())
		{
			Map.Entry<Range<Integer>, Integer> entry = it.next();
			Range<Integer> range = entry.getKey();
			if(range.getMin() > todayCharge && (range.getMin() <= todayCharge + chargeGold))
			{
				ranges.add(range);
			}
		}
		
		return ranges;
	}
	
	

}
