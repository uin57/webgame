package com.pwrd.war.gameserver.common.timer;

import java.util.Comparator;
import java.util.concurrent.TimeUnit;

/**
 * 游戏计时器
 * 
 */
public class GameTimer {
	
	/** 无效的timerID */
	public static final int INVALID_ID = 0;
	
	private static int TIMER_ID = 1;
	
	public static final Comparator<GameTimer> comparator = new Comparator<GameTimer>(){

		@Override
		public int compare(GameTimer o1, GameTimer o2) {
			if( o1.getDeadline() > o2.getDeadline() ){
				return 1;
			}
			if( o1.getDeadline() < o2.getDeadline() ){
				return -1;
			}
			return 0;
		}}; 

	private int id;
	private long deadline;
	private long seconds;
	private boolean cycle;

	public GameTimer(long now, long seconds,boolean cycle) {
		this.id = TIMER_ID++;
		this.seconds = seconds;
		this.resetDeadline(now);
		this.cycle = cycle;
	}
	
	public int getId() {
		return id;
	}
	
	public void resetDeadline(long now){
		this.deadline = now + TimeUnit.SECONDS.toMillis(seconds);
	}
	
	public long getDeadline(){
		return deadline;
	}
	
	public boolean isCycle(){
		return cycle;
	}
	
	public void setCycle(boolean cycle) {
		this.cycle = cycle;
	}

}
