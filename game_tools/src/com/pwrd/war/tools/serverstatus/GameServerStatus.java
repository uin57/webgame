package com.pwrd.war.tools.serverstatus;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;

/**
 * GameServer服务器状态信息
 * 
 * @author <a href="mailto:yong.fang@opi-corp.com">fang yong<a>
 *
 */
public class GameServerStatus  extends StatusInfo{
	/* 
	* [INFO ] 2009-12-13 00:00:32 server_status -
	 * {"id":"1015","name":"??","type"
	 * :1,"ip":"192.168.0.230","port":"8085","freeMemory"
	 * :462,"usedMemory":27,"totalMemory"
	 * :490,"cpuUsageRate":0,"lastUpdateTime":1260633632539
	 * ,"state":1,"onlines":2,"battleTotal":0}
	 */
	/** 在线人数 */
	private String onlines;
	
	/**
	 * 创建状态信息
	 */
	public void createXlsRow(HSSFRow row) {
		super.createXlsRow(row);
		int cellIndex = row.getLastCellNum();
		HSSFCell cell = row.createCell(cellIndex);
		cell.setCellValue(Integer.parseInt(onlines));
		super.setCellIndex(cellIndex);
	}


	public String getOnlines() {
		return onlines;
	}

	public void setOnlines(String onlines) {
		this.onlines = onlines;
	}
}
