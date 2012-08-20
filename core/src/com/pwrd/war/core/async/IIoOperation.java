package com.pwrd.war.core.async;

/**
 * 与IO相关的较为费时的操作，分为三个阶段:doStart,doIo,doStop
 * 
 */
public interface IIoOperation {
	/** 初始化 */
	public static final int STAGE_INITAILZED = 0;
	/** doStart完成 */
	public static final int STAGE_START_DONE = 1;
	/** doIo完成 */
	public static final int STAGE_IO_DONE = 2;
	/** doStop完成,也可用于中止执行 */
	public static final int STAGE_STOP_DONE = 3;

	/**
	 * 操作开始时的操作,在主线程中执行
	 * 
	 * @return
	 */
	public int doStart();

	/**
	 * 运行在另一个线程，如果要访问主线程共享对象，注意只读操作 或者先Copy一个对象给数据库线程操作
	 * 
	 * 注意防止我们在另一个线程操作，但是失败后
	 * 
	 * @return
	 */
	public int doIo();

	/**
	 * 异步操作结束后执行的操作,在主线程中执行
	 * 
	 * @return
	 */
	public int doStop();

}