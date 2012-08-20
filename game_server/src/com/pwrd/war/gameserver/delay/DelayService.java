package com.pwrd.war.gameserver.delay;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

import com.pwrd.war.core.async.IIoOperation;
import com.pwrd.war.gameserver.common.Globals;

public class DelayService extends Thread{

	private Map<String, DelayTask> taskMap = new HashMap<String, DelayTask>();

	private DelayQueue<DelayTask> delayTasks = new DelayQueue<DelayTask>();

	public DelayService() {
	}
	
	public void run(){
		while (true) {
			try {
				Globals.getAsyncService().createOperationAndExecuteAtOnce(delayTasks.take());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	

	public void add(long delayTime, String sn, DelayType delayType) {
		DelayTask task = new DelayTask();
		task.setDelayTime(delayTime);
		task.setSn(sn);
		task.setDelayType(delayType);
		String key = sn + delayType;
		taskMap.put(key, task);
		delayTasks.add(task);
	}

	public void remove(String sn, DelayType delayType) {
		String key = sn + delayType;
		DelayTask task = taskMap.remove(key);
		if (task != null) {
			delayTasks.remove(task);
		}
	}

	public boolean inCooldown(String sn, DelayType delayType) {
		String key = sn + delayType;
		if (taskMap.get(key) != null) {
			return true;
		}
		return false;
	}

	public class DelayTask implements Delayed, IIoOperation {

		private long delayTime;

		private String sn;

		private DelayType delayType;

		public DelayTask() {
		}

		@Override
		public int compareTo(Delayed o) {
			DelayTask delayTask = (DelayTask) o;
			return delayTime > delayTask.getDelayTime() ? 1
					: delayTime < delayTask.getDelayTime() ? -1 : 0;
		}

		@Override
		public long getDelay(TimeUnit unit) {
			return this.delayTime - System.nanoTime();
		}

		public long getDelayTime() {
			return delayTime;
		}

		public void setDelayTime(long delayTime) {
			this.delayTime = delayTime + System.nanoTime();
		}

		public String getSn() {
			return sn;
		}

		public void setSn(String sn) {
			this.sn = sn;
		}

		public DelayType getDelayType() {
			return delayType;
		}

		public void setDelayType(DelayType delayType) {
			this.delayType = delayType;
		}

		@Override
		public int doStart() {
			return IIoOperation.STAGE_START_DONE;
		}

		@Override
		public int doIo() {
			String key = sn + delayType;
			taskMap.remove(key);
			System.out.println("sn:" + sn);
			return IIoOperation.STAGE_IO_DONE;
		}

		@Override
		public int doStop() {
			return IIoOperation.STAGE_STOP_DONE;
		}
	}

	public static void main(String[] args) {
		for (int i = 0; i < 500; i++) {
			DelayService serive = new DelayService();
			serive.start();
			for (int j = 0; j < 50; j++) {
				serive.add(1500, "" + j, DelayType.FORM_UPDATE);
			}

		}
	}
}
