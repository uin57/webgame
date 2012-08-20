package com.pwrd.war.gameserver.wallow;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.pwrd.war.common.constants.WallowConstants;

/**
 * 反沉迷管理器
 * 
 * @author wenpin.qian
 * 
 */
public class WallowLogicalProcessor {
	/** 所有的防沉迷用户 key=passportId */
	private Set<String> wallowUsers = new HashSet<String>();
	/** 等待同步时间的用户 key=passportId */
	private List<String> waitingUsers = new ArrayList<String>();
	/** 同步计数器 */
	private Counter counter = new Counter();
	/** 时间区间 */
	private static final int[] TIME_EXTENDS = { 0, 12, 24, 30, 33, 34, 35, 36, 60 }; // 0,1:00,2:00,2:30,2:45,2:50,2:55,3:00,5:00
	/**
	 * 将反沉迷用户全部导入到等待同步列表中
	 */
	public void pumpWaiters() {
		if (waitingUsers.size() > 0) {
			// 还没调完，继续
			return;
		}

		if (wallowUsers.size() == 0) {
			// 没得调，拉到
			return;
		}

		waitingUsers.addAll(wallowUsers);
		counter.total = waitingUsers.size();
		// >=50*(3-1) 分三批
		// >=50*(2-1) 分两批
		// >=50*(1-1) 一批
		for (int i = WallowConstants.BATCH_COUNTER_MAX - 1; i >= 0; i--) {
			if (counter.total > i * WallowConstants.BATCH_SIZE) {
				counter.batchSize = (counter.total + i) / (i + 1);
				counter.maxBatch = i + 1;
				break;
			}
		}
		counter.count = 0;
	}

	/**
	 * 取下一波玩家进行时间同步
	 * 
	 * @return
	 */
	public List<String> getNextWaiters() {
		int _fromIndex = counter.count * counter.batchSize;
		int _endIndex = _fromIndex + counter.batchSize;
		if (_fromIndex > counter.total) {
			_fromIndex = counter.total;
		}
		if (_endIndex > counter.total) {
			_endIndex = counter.total;
		}
		return waitingUsers.subList(_fromIndex, _endIndex);
	}

	/**
	 * 清理等待同步的玩家列表
	 */
	public void clearWaiters() {
		waitingUsers.clear();
		counter.total = 0;
		counter.batchSize = 0;
		counter.count = 0;
	}

	/**
	 * 本次所有等待同步的玩家都同步了么
	 * 
	 * @return
	 */
	public boolean isAllWaitersSync() {
		if (counter.batchSize == 0) {
			return true;
		}
		if (counter.count < counter.maxBatch) {
			return false;
		}
		return true;
	}

	/**
	 * 递增处理波数
	 */
	public void incCounter() {
		if (counter.batchSize == 0) {
			return;
		}
		if (counter.count < counter.maxBatch) {
			counter.count++;
		}
	}

	/**
	 * 添加翻沉迷用户
	 * 
	 * @param passportId
	 * @return
	 */
	public boolean addWallowUser(String passportId) {
		return this.wallowUsers.add(passportId);
//		return false;
	}

	/**
	 * 移除反沉迷用户
	 * 
	 * @param passportId
	 * @return
	 */
	public boolean removeWallowUser(String passportId) {
		return this.wallowUsers.remove(passportId);
	}

	/**
	 * 取所有的防沉迷用户
	 * 
	 * @return
	 */
	public Set<String> getAllWallowUsers() {
		return Collections.unmodifiableSet(wallowUsers);
	}

	/**
	 * 清理所有反沉迷用户
	 */
	public void clearWallowUsers() {
		this.wallowUsers.clear();
	}

	/**
	 * 取时间区间位置
	 * 
	 * @param unit
	 * @return
	 */
	public int getTimeExtend(int unit) {
		int i = 0;
		for (; i < TIME_EXTENDS.length; i++) {
			if (TIME_EXTENDS[i] > unit) {
				return i - 1;
			}
		}
		return unit - TIME_EXTENDS[TIME_EXTENDS.length - 1]
				+ TIME_EXTENDS.length - 1;
	}

	private final static class Counter {
		// 本次总共pump了多少只
		int total;
		// 每次处理多少只
		int batchSize;
		// 第几次处理
		int count;
		// 总共需要处理几次
		int maxBatch;
	}

}
