package com.pwrd.war.core.schedule;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pwrd.war.core.msg.sys.CronScheduledMessage;
import com.pwrd.war.core.msg.sys.ScheduledMessage;
import com.pwrd.war.core.server.IMessageProcessor;
import com.pwrd.war.core.server.NamedThreadFactory;
import com.pwrd.war.core.time.TimeService;

/**
 * 调度各种消息，这些消息由系统各个部分指定时间戳发送到这里 
 * 可以被取消 如果在触发前没有被取消，则统一发送到MessageProcessor来处理
 * 
 * 
 */
public class ScheduleServiceImpl implements ScheduleService {
	private static final Logger logger = LoggerFactory.getLogger("schedule");
	private final ScheduledExecutorService scheduler;
	private final IMessageProcessor messageProcessor;
	private final TimeService timeService;
	
	private final SchedulerFactory sf ;
	private  Scheduler sched ;
	
	/** 是否已经停止 */
	private volatile boolean shutdown = false;

	public ScheduleServiceImpl(IMessageProcessor messageProcessor,
			TimeService timeService){
		scheduler = Executors.newScheduledThreadPool(1,new NamedThreadFactory(
				getClass()));
		this.messageProcessor = messageProcessor;
		this.timeService = timeService;
		sf = new StdSchedulerFactory();
		 
		try {
			sched = sf.getScheduler();
			sched.start();
		} catch (SchedulerException e) { 
			e.printStackTrace();
		}
		
		 
		
	}

	public void shutdown() {
		this.shutdown = true;
		this.scheduler.shutdownNow();
		try {
			this.sched.shutdown();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}


	@Override
	public void scheduleOnce(ScheduledMessage msg, long delay) {
		if (logger.isDebugEnabled()) {
			logger.debug("scheduleOnce delay:" + delay + " msg:" + msg);
		}
		msg.setState(ScheduledMessage.MESSAGE_STATE_WAITING);
		MessageCarrier carrier = new MessageCarrier(msg);
		ScheduledFuture<?> _scheduledFuture = scheduler.schedule(carrier,
				delay, TimeUnit.MILLISECONDS);
		msg.setFuture(_scheduledFuture);
	}


	@Override
	public void scheduleOnce(ScheduledMessage msg, Date d) {
		if (logger.isDebugEnabled()) {
			logger.debug("scheduleOnce date:" + d + " msg:" + msg);
		}
		msg.setState(ScheduledMessage.MESSAGE_STATE_WAITING);
		MessageCarrier carrier = new MessageCarrier(msg);
		ScheduledFuture<?> _scheduledFuture = scheduler
				.schedule(carrier, (d.getTime() - System.currentTimeMillis()),
						TimeUnit.MILLISECONDS);
		msg.setFuture(_scheduledFuture);
	}

	
	@Override
	public void scheduleWithFixedDelay(ScheduledMessage msg, long delay, long period) {
		if (logger.isDebugEnabled()) {
			logger.debug("scheduleWithFixedDelay delay:" + delay + " period:"
					+ period + " msg:" + msg);
		}
		msg.setState(ScheduledMessage.MESSAGE_STATE_WAITING);
		MessageCarrier carrier = new MessageCarrier(msg);
		ScheduledFuture<?> _scheduledFuture = scheduler.scheduleAtFixedRate(
				carrier, delay, period, TimeUnit.MILLISECONDS);
		msg.setFuture(_scheduledFuture);
	}
	@Override
	public  void schedule(CronScheduledMessage msg){
		if (logger.isDebugEnabled()) {
			logger.debug("schedule cron:" + msg.getCronStr()  + " msg:" + msg);
		}
		msg.setState(ScheduledMessage.MESSAGE_STATE_WAITING);
		MessageCarrier carrier = new MessageCarrier(msg);
		
		try {
			JobDetail job = newJob(TaskJob.class)
			// .withIdentity("job1", "group1")
					.build();
			msg.setJobKey(job.getKey());
			msg.setSched(sched);
			
			job.getJobDataMap().put("carrier", carrier); 
			CronTrigger trigger = newTrigger()
			// .withIdentity("trigger1", "group1")
					.withSchedule(cronSchedule(msg.getCronStr())).build(); //0/5 * * * * ?
			Date ft = sched.scheduleJob(job, trigger);
			logger.info(job.getKey() + " has been scheduled to run at: "
					+ ft + " and repeat based on expression: "
					+ trigger.getCronExpression());
			msg.setTrigger(trigger);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("error create schedule cron:" + msg.getCronStr()  + " msg:" + msg);
		}
		 
	}

	/**
	 * 执行消息调度任务,向{@link ScheduleServiceImpl.messageProcessor}的消息队列中增加消息
	 * 
	 * 
	 */
	private final class MessageCarrier implements Runnable {
		private final ScheduledMessage msg;

		public MessageCarrier(ScheduledMessage msg) {
			this.msg = msg;
		}

		public void run() {
			if (shutdown) {
				return;
			}
			if (msg.isCanceled()) {
				return;
			}
			msg.setState(ScheduledMessage.MESSAGE_STATE_INQUEUE);
			msg.setTrigerTimestamp(timeService.now());
			messageProcessor.put(msg);
		}
	}
	
	public static final class TaskJob implements Job {
		private MessageCarrier carrier;
		
		@Override
		public void execute(JobExecutionContext arg0)
				throws JobExecutionException { 
			carrier.run(); 
		}

		public MessageCarrier getCarrier() {
			return carrier;
		}

		public void setCarrier(MessageCarrier carrier) {
			this.carrier = carrier;
		}

	}
}
