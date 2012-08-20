package com.pwrd.war.tools.serverstatus.datagen;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.pwrd.war.tools.serverstatus.AgentServerStatus;
import com.pwrd.war.tools.serverstatus.DbsServerStatus;
import com.pwrd.war.tools.serverstatus.GameServerStatus;
import com.pwrd.war.tools.serverstatus.StatusInfo;

/**
 * 把服务器状态信息导出到excel文件
 * 
 * 
 */
public class ExportStatusToExcel {
	/** 公共表头 */
	private static final String[] tableHeader = { "LastUpdateTime",
			"FreeMemory", "UsedMemeory", "TotalMemory", "CpuUsgRate" };
	/** gs特殊表头 */
	private static final String[] gsTableHeader = { "Onlines" };
	/** dbs特殊表头 */
	private static final String[] dbsTableHeader = { "CacheUsed" };
	/** as特殊表头 */
	private static final String[] asTableHeader = { "Onlines" };

	/** excel文件目录 */
	private String expXlsDir;

	/** 日期格式 */
	private static final SimpleDateFormat sdf = new SimpleDateFormat(
			"yyyy-MM-dd");

	/** 生成Excel文件的前缀名 */
	private String xlsName = "server_status";

	/** 生成Excel文件的扩展名 */
	private static final String xlsExpName = ".xls";

	public ExportStatusToExcel(String exportDir) {
		this.expXlsDir = exportDir;
	}

	/**
	 * 创建Excel表
	 * 
	 * @param statusInfos
	 */
	public void createExcelSheeet(Map<String, List<StatusInfo>> statusInfos) {
		long _endTime = 0;
		long _startTime = 6860460830970l;
		HSSFWorkbook _workBook = new HSSFWorkbook();// 创建工作本
		Iterator<Map.Entry<String, List<StatusInfo>>> _statusSet = statusInfos
				.entrySet().iterator();
		while (_statusSet.hasNext()) {
			Map.Entry<String, List<StatusInfo>> _tempEntry = _statusSet.next();
			String _serverId = _tempEntry.getKey();
			List<StatusInfo> _statusInfoList = _tempEntry.getValue();
			if (_statusInfoList == null || _statusInfoList.isEmpty()) {
				return;
			}
			StatusInfo _tempInfo = _statusInfoList.get(0);
			String _expSheetName = _tempInfo.getServerName() + _serverId;
			HSSFSheet _sheet = _workBook.createSheet(_expSheetName); // 创建表
			createTableHeader(_sheet, _tempInfo);// 创建表头

			if (_startTime > _tempInfo.getLastUpdateTime()) { // 获得最早时间
				_startTime = _tempInfo.getLastUpdateTime();
			}

			StatusInfo _lastInfo = _statusInfoList
					.get(_statusInfoList.size() - 1);
			if (_endTime < _lastInfo.getLastUpdateTime()) { // 获得最晚时间
				_endTime = _lastInfo.getLastUpdateTime();
			}

			int rowIndex = 1;
			for (StatusInfo _statusInfo : _statusInfoList) {
				createTableRow(_sheet, (short) rowIndex, _statusInfo);// 创建行
				rowIndex++;
			}
			_sheet.setGridsPrinted(true);
		}

		FileOutputStream fos = null;
		try {
			Date date = new Date(_startTime);
			String _startDayStr = sdf.format(date);
			date = new Date(_endTime);
			String _endDayStr = sdf.format(date);
			String _fileName = expXlsDir + "/" + xlsName + "_" + _startDayStr
					+ " - " + _endDayStr + xlsExpName;
			fos = new FileOutputStream(_fileName);
			_workBook.write(fos);
			fos.close();
			fos = null;

			System.out.println("Status Data export to : " + _fileName);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (fos != null) {
					fos.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 创建行
	 * 
	 * @param cells
	 * @param rowIndex
	 * @param statusInfos
	 */
	private void createTableRow(HSSFSheet sheet, short rowIndex,
			StatusInfo statusInfo) {
		HSSFRow row = sheet.createRow((short) rowIndex);// 创建第rowIndex行
		statusInfo.createXlsRow(row);
	}

	/**
	 * 创建表头
	 * 
	 * @param sheet
	 * @param statusInfo
	 * @return
	 */
	private void createTableHeader(HSSFSheet sheet, StatusInfo statusInfo) {
		HSSFRow headerRow = sheet.createRow((short) 0);
		int i = 0;

		for (int j = 0; j < tableHeader.length; j++) {
			HSSFCell headerCell = headerRow.createCell(i++);
			headerCell.setCellValue(tableHeader[j]);
		}

		if (statusInfo instanceof GameServerStatus) {
			for (int j = 0; j < gsTableHeader.length; j++) {
				HSSFCell headerCell = headerRow.createCell(i++);
				headerCell.setCellValue(gsTableHeader[j]);
			}

		} else if (statusInfo instanceof DbsServerStatus) {
			for (int j = 0; j < dbsTableHeader.length; j++) {
				HSSFCell headerCell = headerRow.createCell(i++);
				headerCell.setCellValue(dbsTableHeader[j]);
			}
		} else if (statusInfo instanceof AgentServerStatus) {
			for (int j = 0; j < asTableHeader.length; j++) {
				HSSFCell headerCell = headerRow.createCell(i++);
				headerCell.setCellValue(asTableHeader[j]);
			}
		}

	}
}
