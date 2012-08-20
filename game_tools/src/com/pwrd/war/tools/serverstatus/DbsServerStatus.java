package com.pwrd.war.tools.serverstatus;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;

/**
 * DBS服务器状态
 * 
 * 
 */
public class DbsServerStatus extends StatusInfo {
	/*
	 * [INFO ] 2009-12-13 00:00:00 server_status -
	 * {"id":"1012","name":"DBSServer"
	 * ,"type":2,"ip":"192.168.0.230","port":"8082"
	 * ,"freeMemory":465,"usedMemory"
	 * :25,"totalMemory":490,"cpuUsageRate":0,"lastUpdateTime" :1260633600514
	 * ,"agentStatus":"CharacterAgent[cache:3/2000,evicted:0] "}
	 */
	/*
	 * [INFO ] 2010-04-07 00:00:32 lzr.server_status -
	 * {"id":"10111","name":"DBSServer"
	 * ,"type":2,"ip":"10.30.37.194","port":"7771"
	 * ,"freeMemory":4039,"usedMemory"
	 * :39,"totalMemory":4079,"cpuUsageRate":0.06250104308128357
	 * ,"lastUpdateTime":1270569632737,"version":"1.3.1.4","agentStatus":
	 * "CharacterAgent[LRU status:cache size:5/5000,hit:419,miss:4,hitRate:99%] "
	 * }
	 */
	/** 使用中的cache */
	private String cacheUsed;

	@Override
	public void createXlsRow(HSSFRow row) {
		super.createXlsRow(row);
		int cellIndex = row.getLastCellNum();
		HSSFCell cell = row.createCell(cellIndex);
		cell.setCellValue(Integer.parseInt(cacheUsed));
		super.setCellIndex(cellIndex);
	}

	public String getCacheUsed() {
		return cacheUsed;
	}

	public void setCacheUsed(String cacheUsed) {
		this.cacheUsed = cacheUsed;
	}
}
