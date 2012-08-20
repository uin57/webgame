package com.pwrd.war.gameserver.timeevent;

import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Maps;
import com.pwrd.war.common.InitializeRequired;
import com.pwrd.war.common.constants.Loggers;
import com.pwrd.war.common.model.template.TimeEventTemplate;
import com.pwrd.war.core.schedule.ScheduleService;
import com.pwrd.war.core.template.TemplateService;
import com.pwrd.war.core.time.TimeService;
import com.pwrd.war.core.util.TimeUtils;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.common.log.OnlinePlayerStatiMessage;
import com.pwrd.war.gameserver.player.OnlinePlayerService;
import com.pwrd.war.gameserver.timeevent.msg.SysTimeEventServiceStart;
import com.pwrd.war.gameserver.timeevent.msg.TimeEventTaskMsg;

/**
 * 定时服务
 * 			外部只需要关心已经定义的时间id,增加自己要运行的{@link Runnable}即可
 * 
 * @author jiliang.lu
 *
 */
public class TimeEventService implements InitializeRequired{
	
	private TemplateService tmplService;
	
	/** 时间服务 */
	private TimeService timeService;

	/** 调度任务服务 */
	private ScheduleService schService;
	
	/** 在线玩家服务 */
	private OnlinePlayerService onlinePlayerService;
	
	/** key - 定时时间点的id, value 时间点 */
	private Map<Integer,Long> timePoints = Maps.newConcurrentHashMap();
	
	/** key - 距离{{@link #getZeroTime()}的时间点, value - 要运行的Runnable组*/
	private ArrayListMultimap<Long,Runnable> eventMap = new ArrayListMultimap<Long,Runnable>();
	
	public TimeEventService(TemplateService tmplSrv,TimeService timeSrv, ScheduleService schService,OnlinePlayerService onlineService)
	{
		this.tmplService = tmplSrv;
		this.timeService = timeSrv;
		this.schService = schService;	
		this.onlinePlayerService = onlineService;
	}

	@Override
	public void init() {
		Collection<TimeEventTemplate> allTimePoint = this.tmplService.getAll(TimeEventTemplate.class).values();
		for(TimeEventTemplate timeEventTmpl : allTimePoint)
		{						
			long zeroTime = TimeUtils.getTodayBegin(this.timeService);
			
			Calendar nowCal = Calendar.getInstance();
			nowCal.setTimeInMillis(this.timeService.now());
			Calendar date = TimeUtils.mergeDateAndTime(nowCal, timeEventTmpl.getTriggerTimePoint());
			
			long diff = date.getTimeInMillis() - zeroTime;
			
			timePoints.put(timeEventTmpl.getId(),diff);
		}		
	}
	
	
	
	public long getZeroTime()
	{
		long zeroTime = TimeUtils.getTodayBegin(timeService);
		return zeroTime;
	}
	
	/**
	 * 提交一个调度任务
	 * 
	 * @param task
	 * @param delay
	 */
	public void addTask(int timeId,Runnable task) {
		if(!timePoints.containsKey(timeId))
		{
			Loggers.gameLogger.warn(String.format("提交了一个未知的定时时间点任务,timeId:%s",timeId));
			return;
		}	
		eventMap.put(timePoints.get(timeId),task);		
	}

	/**
	 * 开始方法
	 */
	public void start()
	{
		Globals.getMessageProcessor().put(new SysTimeEventServiceStart());		
	}
	
	/**
	 * 开始服务
	 */
	public void startService() {
		activeZeroTask();
		activeStaffTask();		
		
		Globals.getItemService().startEnhanceService();
		Globals.getSceneService().startCheckLine();
		Globals.getVitService().addSysVit();
		
		//定时统计人数
		OnlinePlayerStatiMessage onlinePlayerStatiMessage = new OnlinePlayerStatiMessage();
		Globals.getScheduleService().scheduleWithFixedDelay(onlinePlayerStatiMessage, 0, 600 * 1000);
		//每分钟执行一次
		Globals.getScheduleService().scheduleWithFixedDelay(new TimeNoticeMessage(), 20 * 1000, 60 * 1000);
	}
	
	/**
	 * 开始0点定时任务
	 */
	private void activeZeroTask() {
		// 零点检查
		long zeroTime = TimeUtils.getTodayBegin(timeService) + TimeUtils.DAY;
		submitTask(new ZeroTask(), zeroTime - timeService.now());
	}
	
	private void activeStaffTask() {
		Iterator<Long> it = eventMap.asMap().keySet().iterator();
		while(it.hasNext())
		{
			long diff = it.next();
			List<Runnable> tasks = eventMap.get(diff);
			long taskTime = TimeUtils.getTodayBegin(timeService) + diff;
			for(Runnable task : tasks)
			{
				submitTask(task, taskTime - timeService.now());
			}
		}
	}
	
	/**
	 * 提交一个调度任务
	 * 
	 * @param task
	 * @param delay
	 */
	private void submitTask(Runnable task, long delay) {
		if(!Globals.getServerConfig().getIsDebug())
		{
			if(delay < 0)
			{
				return;
			}
		}
		TimeEventTaskMsg msg = new TimeEventTaskMsg(this.timeService.now(), task);
		this.schService.scheduleOnce(msg, delay);
	}

	/**
	 * 得到某时间点对应当天的实际毫秒数 
	 * 
	 * @param timeId
	 * @return
	 */
	public long getLastRealTime(int timeId)
	{
		if(timePoints.containsKey(timeId))
		{
			long zeroTime = TimeUtils.getTodayBegin(this.timeService);
			return zeroTime + timePoints.get(timeId);
		}
		return -1l;
	}
	
	
	/**
	 * 
	 * 零点时触发
	 */
	private void onZero() {
		activeZeroTask();
		activeStaffTask();
	}
	
	/**
	 * 零点任务,主要用于生成当天的新定时任务
	 * 
	 */
	private class ZeroTask implements Runnable {

		@Override
		public void run() {
			onZero();
		}

	}
	

}
