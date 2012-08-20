package com.pwrd.war.gameserver.quest.template;

import com.pwrd.war.common.exception.TemplateConfigException;
import com.pwrd.war.core.annotation.ExcelRowBinding;
import com.pwrd.war.core.util.MathUtils;

@ExcelRowBinding
public class DailyQuestStarTemplate extends DailyQuestStarTemplateVO{

	@Override
	public void check() throws TemplateConfigException {
		int totalRate = 0;
		for(int rate :this.rateArray)
		{
			totalRate += rate;
		}
		if(totalRate != 100)
		{
			throw new TemplateConfigException("日常任务星数变化配置表", id, "概率加起来不为100");
		}		
	}
	
	/**
	 * 随机一种变化值
	 * @return
	 */
	public int randomChangeStar()
	{
		int index = MathUtils.random(rateArray);
		if(index == 0)
		{
			return 1; //升1星
		}
		else if(index == 1)
		{
			return 0; //不变
		}
		else
		{
			return -1; //降1星
		}				
	}

}
