package com.pwrd.war.gameserver.behavior;

import net.sf.json.JSONArray;

/**
 * 行为记录
 * 
 * @author haijiang.jin
 *
 */
class BehaviorRecord {
	/** 操作次数 */
	private int opCount;
	/** 操作次数上限 */
	private int opCountMax;
	/** 上次操作时间 */
	private long lastOpTime;
	/** 重置时间 */
	private long resetTime;

	/**
	 * 类默认构造器
	 * 
	 */
	public BehaviorRecord() {
		this.opCount = 0;
		this.opCountMax = 0;
		this.lastOpTime = 0;
	}

	/**
	 * 获取操作次数
	 * 
	 * @return
	 */
	public int getOpCount() {
		return this.opCount;
	}

	/**
	 * 设置操作次数
	 * 
	 * @param value
	 */
	public void setOpCount(int value) {
		this.opCount = value;
	}

	/**
	 * 获取操作次数上限
	 * 
	 * @return
	 */
	public int getOpCountMax() {
		return this.opCountMax;
	}

	/**
	 * 设置操作次数上限
	 * 
	 * @param value
	 */
	public void setOpCountMax(int value) {
		this.opCountMax = value;
	}

	/**
	 * 获取上次操作时间
	 * 
	 * @return
	 */
	public long getLastOpTime() {
		return this.lastOpTime;
	}

	/**
	 * 设置上次操作时间
	 * 
	 * @param value
	 */
	public void setLastOpTime(long value) {
		this.lastOpTime = value;
	}

	/**
	 * 获取重置时间
	 * 
	 * @return
	 */
	public long getResetTime() {
		return this.resetTime;
	}

	/**
	 * 设置重置时间
	 * 
	 * @param value
	 */
	public void setResetTime(long value) {
		this.resetTime = value;
	}

	/**
	 * 序列化为 JSON 字符串
	 * 
	 * @return
	 */
	public String toJson() {
		// 创建 JSON 数组对象
		JSONArray jsonArray = new JSONArray();

		jsonArray.add(this.getOpCount());
		jsonArray.add(this.getLastOpTime());

		return jsonArray.toString();
	}

	/**
	 * 从 JSON 字符串中反序列化对象
	 * 
	 * @param json
	 */
	public void loadJson(String json) {
		if (json == null) {
			return;
		}

		// 创建 JSON 数组对象
		JSONArray jsonArray = JSONArray.fromObject(json);
		int i = 0;

		if (jsonArray.size() > i) {
			this.setOpCount(jsonArray.getInt(i++));
		}

		if (jsonArray.size() > i) {
			this.setLastOpTime(jsonArray.getLong(i++));
		}
	}
}