package com.pwrd.war.logserver.createtable;

import java.util.TimerTask;

/**
 * 定义CreateMission,源自天书log_server的同名类
 * 
 * 
 */
public class CreateTabaleTask extends TimerTask {
	private ITableCreator iTableCreator;

	public CreateTabaleTask(ITableCreator creator) {
		this.iTableCreator = creator;
	}

	/**
	 * 建立日志数据表
	 */
	public void run() {
		this.iTableCreator.buildTable();
	}
}
