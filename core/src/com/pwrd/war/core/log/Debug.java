package com.pwrd.war.core.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pwrd.war.common.constants.Loggers;

/**
 * Debug状态管理
 * 
 */
public class Debug {
	private static boolean debugMode = false;
	private static Logger logger = LoggerFactory.getLogger("DebugMode");

	/**
	 * 设置
	 * @param mode
	 */
	public static void setDebugMode(boolean mode) {
		debugMode = mode;
	}

	/**
	 * 确保处于Debug模式
	 * 
	 * @throws IllegalStateException
	 *             如果不在debug模式,会抛出此异常
	 */
	public static void assertDebug() {
		if (!debugMode) {
			throw new IllegalStateException("Not in debug mode.");
		}
	}

	/**
	 * 在Debug模式下抛出异常,用于在测试环境中发现bug;在生产环境中不会抛出此异常,尽量保持生产环境正常
	 */
	public static void assertLog(String msg) {
		if(Loggers.ASSERT_LOGGER.isWarnEnabled()){
			Loggers.ASSERT_LOGGER.warn(msg);
		}
	}

	public static void warnInDebug(String msg) {
		if (debugMode) {
			logger.warn("If you catch this,mabye some bug existed,msg [" + msg + "]");
		}
	}

}
