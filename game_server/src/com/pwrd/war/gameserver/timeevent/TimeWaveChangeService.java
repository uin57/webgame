package com.pwrd.war.gameserver.timeevent;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;
import com.pwrd.war.common.InitializeRequired;
import com.pwrd.war.common.constants.Loggers;
import com.pwrd.war.core.template.TemplateService;
import com.pwrd.war.core.util.MathUtils;
import com.pwrd.war.gameserver.timeevent.template.TimeWaveChangeTemplate;
import com.pwrd.war.gameserver.timeevent.template.WaveChangeLimit;

public class TimeWaveChangeService implements InitializeRequired{
	
	private TemplateService tmplService;
	
	private TimeEventService timeEventService;
	
	/**
	 * 得到各种类型的当前变化率
	 */
	private Map<TimeWaveChangeType, TimeWaveChange> currentChange = Maps.newConcurrentHashMap();
	
	public TimeWaveChangeService(TemplateService tmplService,TimeEventService teService)
	{
		this.tmplService = tmplService;
		this.timeEventService = teService;
	}

	@Override
	public void init() {
		Collection<TimeWaveChangeTemplate> allTemplates = this.tmplService.getAll(TimeWaveChangeTemplate.class).values();
		for(TimeWaveChangeTemplate timeWaveChangeTmpl : allTemplates)
		{
			TimeWaveChangeType timeWaveChangeType = TimeWaveChangeType.valueOf(timeWaveChangeTmpl.getId());
			if(timeWaveChangeType == null)
			{
				continue;
			}
			
			List<WaveChangeLimit> phases = timeWaveChangeTmpl.getPhases();
			float changes[] = getAverageChange(phases);
			float minValue = changes[0];
			float maxValue = changes[1];
			float initChange = changes[2];

			Loggers.timewaveLogger.info("init time wave type = " + timeWaveChangeType + ",init change =" + initChange );
			
			//都初始化中间值,上升状态
			currentChange.put(timeWaveChangeType, TimeWaveChange.createTimeWaveChange(TimeWaveChange.UP, initChange, minValue, maxValue));
			//增加定时事件
			for(int timeId : timeWaveChangeTmpl.getTimeIds())
			{
				timeEventService.addTask(timeId, this.new TimeWaveChangeTask(timeWaveChangeTmpl,timeWaveChangeType));
			}
		}		
	}
	
	private float[] getAverageChange(List<WaveChangeLimit> phases)
	{
		float minValue = phases.get(0).getLow();
		float maxValue = phases.get(phases.size() - 1).getHigh();
		return new float[]{minValue,maxValue,minValue + maxValue / 2};		
	}
	
	
	/**
	 * 得到阶段数
	 * @param change
	 * @param phases
	 * @return
	 */
	private int getFirstRightRangeIdx(float change,List<WaveChangeLimit> phases)
	{
		int result = 0;
		for(int i = 0; i < phases.size(); i++)
		{
			WaveChangeLimit limit = phases.get(i);			
			if(change >= limit.getLow() && change <= limit.getHigh())
			{
				result = i;
				break;
			}
		}
		return result;		
	}
	
	/**
	 * 根据类型得到变化对象
	 * @param changeType
	 * @return
	 */
	public TimeWaveChange getCurrentChange(TimeWaveChangeType changeType)
	{
		return currentChange.get(changeType);	
	}	
	

	private void doChangeWave(TimeWaveChangeTemplate bindTemplate, TimeWaveChangeType bindType) {		
		TimeWaveChange timeWaveChange = getCurrentChange(bindType);
		
		int currentPhaseIndex = getFirstRightRangeIdx(timeWaveChange.getChange(),bindTemplate.getPhases());
		
		//取得当前阶段模版定义
		WaveChangeLimit waveChangeLimit = bindTemplate.getPhases().get(currentPhaseIndex);		
		float randomChange = MathUtils.random(0.0f, waveChangeLimit.getChangeRange());
		
		if(timeWaveChange.isUp()) //上升趋势
		{
			float calculate = timeWaveChange.getChange() + randomChange;
			if(calculate >= waveChangeLimit.getHigh()) //超过上限
			{
				if(bindTemplate.isMaxPhaseIndex(currentPhaseIndex) || calculate >= timeWaveChange.getMaxValue()) //如果当前已经是最大阶段
				{
					timeWaveChange.down();//更改趋势为下降					
					float toSubtract = calculate - waveChangeLimit.getHigh();
					timeWaveChange.setChange(waveChangeLimit.getHigh() - toSubtract);	
				}
				else
				{
					timeWaveChange.setChange(calculate);
				}
			}
			else
			{
				timeWaveChange.setChange(calculate);
			}
		}
		else if(timeWaveChange.isDown()) //下降趋势
		{
			float calculate = timeWaveChange.getChange() - randomChange;
			if(calculate <= waveChangeLimit.getLow()) //小于最低值
			{
				if(bindTemplate.isMinPhaseIndex(currentPhaseIndex) || calculate <= timeWaveChange.getMinValue()) //处于最小阶段
				{
					timeWaveChange.up();//更改为上升趋势
					float toAdd = waveChangeLimit.getLow() - calculate;
					timeWaveChange.setChange(waveChangeLimit.getLow() + toAdd);	
				}
				else
				{
					timeWaveChange.setChange(calculate);
				}				
			}
			else
			{
				timeWaveChange.setChange(calculate);
			}
		}
//		Loggers.timewaveLogger.info("currentPhaseIndex === " + currentPhaseIndex + "timeWaveChange ==============" + timeWaveChange.getChange());
	}	
	
	
	private class TimeWaveChangeTask implements Runnable
	{
		private TimeWaveChangeTemplate bindTemplate = null;
		
		private TimeWaveChangeType bindType = null;
		
		public TimeWaveChangeTask(TimeWaveChangeTemplate bindTmpl,TimeWaveChangeType bindType)
		{
			this.bindTemplate = bindTmpl;
			this.bindType = bindType;
		}
		
		@Override
		public void run() {
			doChangeWave(bindTemplate,bindType);		
		}
			
	}
}
