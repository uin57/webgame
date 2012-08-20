package com.pwrd.war.tools.data;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;

public interface IExcelSheetHandler {
	/**
	 * 一个Sheet处理开始时的回调方法
	 * 
	 * @return
	 */
	public boolean onStart(HSSFSheet sheet);

	/**
	 * 处理一行数据
	 * 
	 * @param row
	 * @return
	 */
	public boolean handle(HSSFRow row);

	/**
	 * 一个Sheet处理结束时的回调方法
	 * 
	 * @return
	 */
	public boolean onFinish();
}