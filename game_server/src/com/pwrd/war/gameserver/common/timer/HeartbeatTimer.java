package com.pwrd.war.gameserver.common.timer;

/**
 * 用于控制心跳频率的计时器
 * 
 * 
 */
public interface HeartbeatTimer {

	/**
	 * 是否已经到时
	 * 
	 * @return
	 */
	boolean isTimeUp();

	/**
	 * 重置，开始下一轮计时
	 */
	void nextRound();
}
