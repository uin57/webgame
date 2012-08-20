package com.pwrd.war.tools.data;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public interface IExcelWorkbookHandler {
	/**
	 * 取得指定sheet索引的处理器
	 * 
	 * @param sheetIndex
	 * @return
	 */
	public IExcelSheetHandler getSheetHandler(int sheetIndex);

	/**
	 * 处理开始时的回调方法
	 * 
	 * @return
	 */
	public boolean onStart(HSSFWorkbook sheet);

	/**
	 * 处理结束时的回调方法
	 * 
	 * @return
	 */
	public boolean onFinish();
}