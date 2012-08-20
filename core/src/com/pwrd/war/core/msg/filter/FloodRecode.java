package com.pwrd.war.core.msg.filter;

class FloodRecode {
	
	/**
	 * 每秒最大包数量 
	 */
	public static final int MAX_SECEND_PACKS = 64;

	/**
	 * 每分钟最大包数量
	 */
	public static final int MAX_MINUTE_PACKS = 1024;
	
	/**
	 * 每秒最大数据量
	 */
	public static final int MAX_SECEND_SIZES = 4096;
	
	/**
	 * 每分钟最大数据量
	 */
	public static final int MAX_MINUTE_SIZES = 131072;

	/**
	 * 检测到多少次洪水包后, 禁止链接
	 */
	public static final int BLOCK_DETECT_COUNT = 5;

	/**
	 * 检测到洪水包后IP禁用时间分钟
	 */
	public static final int BLOCK_IP_MINUS = 30;
	
	/**
	 * 最大连接极限
	 */
	public static final int MAX_CLIENTS_LIMIT = 5000;
	
	/**
	 * 最大连接数
	 */
	public static final int MAX_CLIENTS_ACTIVE = 3200;
	
	
	// ------------------------------------------------------
	
	/**
	 * 上次检查数据大小的时间戳
	 */
	long lastSizeTime = 0;
	/**
	 * 上次检查命令次数的时间戳
	 */
	long lastPackTime = 0;
	/**
	 * 秒累计数据包数量
	 */
	int lastSecendPacks = 0;
	/**
	 * 秒累计数据包大小
	 */
	int lastSecendSizes = 0;
	/**
	 * 分累计数据包数量
	 */
	int lastMinutePacks = 0;
	/**
	 * 分累计数据包大小
	 */
	int lastMinuteSizes = 0;
}