package com.pwrd.war.tools.serverstatus;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;

public class AgentServerStatus extends StatusInfo{
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
	}


	public String getOnlines() {
		return onlines;
	}

	public void setOnlines(String onlines) {
		this.onlines = onlines;
	}
}
