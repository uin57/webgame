package com.pwrd.war.logserver.common;

import com.pwrd.war.common.model.LogServerStatus;
import com.pwrd.war.core.schedule.ScheduleService;
import com.pwrd.war.core.schedule.ScheduleServiceImpl;
import com.pwrd.war.core.server.QueueMessageProcessor;
import com.pwrd.war.core.server.ShutdownHooker;
import com.pwrd.war.core.time.SystemTimeService;
import com.pwrd.war.core.time.TimeService;
import com.pwrd.war.logserver.LogServerConfig;

public class Globals {
	private static LogServerConfig config;
	private static ScheduleService scheduleService;
	private static TimeService timeService;
	/** 日志服务器状态 */
	private static LogServerStatus logServerStatus;
	private static ShutdownHooker shutdownHooker;

	public static void init(LogServerConfig cfg) {
		config = cfg;
		timeService = new SystemTimeService();
		scheduleService = new ScheduleServiceImpl(new QueueMessageProcessor(),
				timeService);
		shutdownHooker = new ShutdownHooker();
		logServerStatus = buildLogServerStatus(config);
	}

	public static LogServerConfig getServerConfig() {
		return config;
	}

	public static ScheduleService getScheduleService() {
		return scheduleService;
	}

	public static TimeService getTimeService() {
		return timeService;
	}
	
	public static LogServerStatus getLogServerStatus() {
		return logServerStatus;
	}
	
	public static ShutdownHooker getShutdownHooker() {
		return shutdownHooker;
	}

	/**
	 * 获取日志服务器状态
	 * @param logServerConfig
	 * @return
	 */
	private static LogServerStatus buildLogServerStatus(LogServerConfig logServerConfig)
	{
		LogServerStatus logServerStatus = new LogServerStatus();
		
		logServerStatus.setServerIndex(logServerConfig.getServerIndex());
		logServerStatus.setServerId(logServerConfig.getServerId());
		logServerStatus.setServerName(logServerConfig.getServerName());
		logServerStatus.setIp(logServerConfig.getServerHost());
		logServerStatus.setPort(String.valueOf(logServerConfig.getPort()));		
		logServerStatus.setVersion(logServerConfig.getVersion());
		
		return logServerStatus;
	}
}
