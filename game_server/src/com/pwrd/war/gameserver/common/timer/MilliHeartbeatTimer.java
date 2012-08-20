package com.pwrd.war.gameserver.common.timer;

import com.pwrd.war.core.time.TimeService;

/**
 * 精确到毫秒的心跳计时器
 * 
 * 
 */
public class MilliHeartbeatTimer implements HeartbeatTimer {

	/** 计时时间 */
	private final long milliTimeSpan;
	/** 到时时间 */
	private long timeUp;
	private static TimeService timeService;

	public static void setTimeService(TimeService timeService) {
		MilliHeartbeatTimer.timeService = timeService;
	}

	public MilliHeartbeatTimer(long milliTimeSpan) {
		this.milliTimeSpan = milliTimeSpan;
		this.timeUp = timeService.now();
	}

	@Override
	public boolean isTimeUp() {
		return timeService.timeUp(timeUp);
	}

	@Override
	public void nextRound() {
		this.timeUp = timeService.now() + milliTimeSpan;
	}
}
