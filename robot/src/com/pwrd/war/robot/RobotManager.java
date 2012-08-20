package com.pwrd.war.robot;

import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.apache.mina.common.IoSession;

import com.google.common.collect.Maps;
import com.pwrd.war.core.session.MinaSession;

public class RobotManager {
	
	private static RobotManager _instance = null;
	
	static{
		_instance = new RobotManager();
	}
		
	private final Map<String, Robot> passport2RobotMap;
	
	private final Map<IoSession, Robot> ioSession2RobotMap;
	
	private final Map<MinaSession, Robot> session2RobotMap;	
	
	private final ScheduledExecutorService scheduler;	
	
	private RobotManager()
	{
		passport2RobotMap = Maps.newConcurrentHashMap();
		ioSession2RobotMap = Maps.newConcurrentHashMap();
		session2RobotMap = Maps.newConcurrentHashMap();
		scheduler = Executors.newScheduledThreadPool(10);
	}
	
	public static RobotManager getInstance()
	{
		return _instance;
	}
	
	public void shutdown() {
		this.scheduler.shutdownNow();
	}
	
	public void addRobot(IoSession ioSession, Robot robot)
	{
		ioSession2RobotMap.put(ioSession, robot);
	}
	
	public Robot removeRobot(IoSession ioSession)
	{
		return ioSession2RobotMap.remove(ioSession);
	}
	
	public Robot getRobot(IoSession ioSession)
	{
		return ioSession2RobotMap.get(ioSession);
	}
		
	public void addRobot(Robot robot)
	{
		passport2RobotMap.put(robot.getPassportId(), robot);
	}
	
	public Robot getRobot(String passportId)
	{
		return passport2RobotMap.get(passportId);
	}
	
	public void addRobot(MinaSession clientSession,Robot robot)
	{
		session2RobotMap.put(clientSession, robot);
	}
	
	public Robot getRobot(MinaSession clientSession)
	{
		return session2RobotMap.get(clientSession);
	}
	
	public Robot removeRobot(MinaSession ioSession)
	{
		return ioSession2RobotMap.remove(ioSession);
	}
	
	

	public void scheduleOnce(RobotAction action, long delay) {
		ScheduledFuture<?> future = scheduler.schedule(action, delay, TimeUnit.MILLISECONDS);
		action.setFuture(future);
	}

	
	public void scheduleWithFixedDelay(RobotAction action, long delay, long period) {
		ScheduledFuture<?> _scheduledFuture = scheduler.scheduleAtFixedRate(action, delay, period, TimeUnit.MILLISECONDS);
		action.setFuture(_scheduledFuture);
	}

}
