package com.pwrd.war.gameserver.common.heartbeat;

import java.util.ArrayList;
import java.util.List;

import com.pwrd.war.gameserver.common.timer.HeartbeatTimer;
import com.pwrd.war.gameserver.common.timer.MilliHeartbeatTimer;

/**
 * HeartbeatTaskExecutor默认实现
 * 
 * @author li.liu
 * @since 2010-8-11
 */
public class HeartbeatTaskExecutorImpl implements HeartbeatTaskExecutor {

	private List<TaskTimerPair> taskPairs;

	public HeartbeatTaskExecutorImpl() {
		taskPairs = new ArrayList<TaskTimerPair>();
	}

	@Override
	public void onHeartBeat() {
		for (TaskTimerPair taskTimerPair : taskPairs) {
			if (taskTimerPair.timer.isTimeUp()) {
				taskTimerPair.task.run();
				taskTimerPair.timer.nextRound();
			}
		}
	}

	@Override
	public void submit(HeartbeatTask task) {
		TaskTimerPair taskTimerPair = new TaskTimerPair(task,
				new MilliHeartbeatTimer(task.getRunTimeSpan()));
		taskPairs.add(taskTimerPair);
	}

	@Override
	public void clear() {
		taskPairs = new ArrayList<TaskTimerPair>();
	}

	private static class TaskTimerPair {
		public HeartbeatTask task;
		public HeartbeatTimer timer;

		private TaskTimerPair(HeartbeatTask task, HeartbeatTimer timer) {
			super();
			this.task = task;
			this.timer = timer;
		}
	}
}
