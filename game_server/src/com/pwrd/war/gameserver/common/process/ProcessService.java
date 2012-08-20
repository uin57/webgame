package com.pwrd.war.gameserver.common.process;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.pwrd.war.common.InitializeRequired;
import com.pwrd.war.common.constants.Loggers;
import com.pwrd.war.core.time.TimeService;
import com.pwrd.war.db.model.ProcessEventEntity;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.common.db.GameDaoService;
import com.pwrd.war.gameserver.common.msg.SysProcessEventExecute;
import com.pwrd.war.gameserver.common.msg.SysProcessServiceStart;
import com.pwrd.war.gameserver.common.process.ProcessDef.ProcessEventType;
import com.pwrd.war.gameserver.startup.GameExecutorService;

public class ProcessService implements InitializeRequired {
	
	private Logger logger = Loggers.processLogger;
	
	private TimeService timeService;
	
	private GameExecutorService executorService;
	
	private GameDaoService gameDaoServie;
	
	
	/** 最后一个被捞取的eventUUID */
	private AtomicLong lastEventUid = new AtomicLong(0);
	
	/**
	 * <进程类型，处理器>
	 */
	private Map<ProcessEventType, IProcessEventProcessor> processorMap = Maps.newEnumMap(ProcessEventType.class);
	
	/**
	 * <进程类型，进程类>
	 */
	private Map<ProcessEventType, Class<? extends ProcessEvent>> eventClassMap = Maps.newEnumMap(ProcessEventType.class);
	
	public ProcessService(TimeService timeService,GameExecutorService executorService,GameDaoService gameDaoService)
	{
		this.timeService = timeService;
		this.executorService = executorService;
		this.gameDaoServie = gameDaoService;
	}
	
	@Override
	public void init() {
		
	}
	

	/**
	 * 
	 * 注册进程
	 * 
	 * @param eventType
	 * 		 进程类型
	 * @param processor
	 * 		 进程处理器
	 * @param eventClass
	 * 		 进程BO类型
	 *
	 */
	public void registEvent(ProcessEventType eventType,IProcessEventProcessor processor, Class<? extends ProcessEvent> eventClass)
	{
		if (!processorMap.containsKey(eventType)) {
			processorMap.put(eventType, processor);
		}
		
		if (!eventClassMap.containsKey(eventType)) {
			eventClassMap.put(eventType, eventClass);
		}
	}
	
	/**
	 * 开始方法
	 */
	public void start()
	{
		Globals.getMessageProcessor().put(new SysProcessServiceStart());		
	}
	
	/**
	 * 开始服务
	 */
	public void startService() {
		this.executorService.getProcessExcecutor().scheduleAtFixedRate(createLoadProcessAndExecuteTask(), 0, ProcessDef.SCHEDULE_INTERVAL,
				TimeUnit.MILLISECONDS);
	}

	/**
	 * 创建一个用于[取出需要执行进程列表,并执行]的任务
	 * 
	 * @return
	 */
	private Runnable createLoadProcessAndExecuteTask() {
		return new Runnable() {
			
			private Set<ProcessEventEntity> inValidEvents = Sets.newHashSet();
			
			
			@Override
			public void run() {			
				inValidEvents.clear();
				
				List<ProcessEventEntity> eventList = gameDaoServie.getProcessEventDao().getRunnableEvents(timeService.now() + ProcessDef.TIME_EXPAND, ProcessDef.MAX_PICK_NUMS, lastEventUid.get());				
				for (ProcessEventEntity event : eventList) {
					lastEventUid.set(event.getId());
					
					ProcessEventType eventType = ProcessEventType.valueOf(event.getType()); 
					if(eventType == null)
					{
						inValidEvents.add(event);
						continue;
					}					
					IProcessEventProcessor processor = processorMap.get(eventType);
					if(processor == null)
					{
						inValidEvents.add(event);
						continue;
					}					
					
					SysProcessEventExecute sysProcessEventExecute = new SysProcessEventExecute();
					sysProcessEventExecute.setEvent(event);
					sysProcessEventExecute.setProcessor(processor);
					
					Globals.getMessageProcessor().put(sysProcessEventExecute);		
				}			
				
				for(ProcessEventEntity toDelete : inValidEvents)
				{
					logger.error("invalid event found !!! event type: " + toDelete.getType() + " event props:" + toDelete.getProps());
					gameDaoServie.getProcessEventDao().delete(toDelete);
				}
				
			}
		};
	}
	
	
}
