package com.pwrd.war.gameserver.timeevent.template;

import java.util.List;

import com.pwrd.war.common.exception.TemplateConfigException;
import com.pwrd.war.common.model.template.TimeEventTemplate;
import com.pwrd.war.core.annotation.ExcelRowBinding;
import com.pwrd.war.core.util.StringUtils;
import com.pwrd.war.gameserver.common.Globals;

@ExcelRowBinding
public class TimeWaveChangeTemplate extends TimeWaveChangeTemplateVO {
		
	private int[] timeIds;	
	
	
	@Override
	public void setWaveChangeTimeIds(String waveChangeTimeIds) {
		super.setWaveChangeTimeIds(waveChangeTimeIds);
		timeIds = StringUtils.getIntArray(waveChangeTimeIds, ";");
	}

	@Override
	public void setPhases(List<WaveChangeLimit> phases) {
		super.setPhases(phases);
		for(int i = 0; i < phases.size(); i++)
		{
			WaveChangeLimit waveChange = phases.get(i);
		
			if(waveChange.getLow() >= waveChange.getHigh())
			{
				throw new TemplateConfigException(this.getSheetName(), this.getId(),
						"第" + i + "阶段的低值大于第" + i + "阶段的高值,没有变化区间");
			}
			
			if(i + 1 < phases.size())
			{
				WaveChangeLimit nextWaveChange = phases.get(i + 1);
				if(nextWaveChange.getLow() > waveChange.getHigh())
				{
					throw new TemplateConfigException(this.getSheetName(), this.getId(),
							"第" + (i + 1) + "阶段的低值大于第" + i + "阶段的高值,没有完全覆盖");
				}
			}			
		}
	}


	@Override
	public void check() throws TemplateConfigException {
		for(int timeEventId : timeIds)
		{
			if (!Globals.getTemplateService().isTemplateExist(timeEventId,
					TimeEventTemplate.class)) {
				throw new TemplateConfigException(this.getSheetName(), this.getId(),
						"timeEventId=" + timeEventId + "不存在于timeEvent模板中");
			}	
		}
	}

	public int[] getTimeIds() {
		return timeIds;
	}
	
	public boolean isMinPhaseIndex(int index)
	{
		if(index == 0)
		{
			return true;
		}
		return false;
	}
	
	public boolean isMaxPhaseIndex(int index)
	{
		if(index == this.getPhases().size() - 1)
		{
			return true;
		}
		return false;
	}
	
}
