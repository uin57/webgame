package com.pwrd.war.tools.data;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;

/**
 * 导出Excel表格
 * 
 * 
 */
public class ExportWbHandler implements IExcelWorkbookHandler {
	private final File serverOutPath;
	
	private HSSFWorkbook workBook;
	
	private int configRowIndex ;
	
	private int dataRowIndex ;
	
	private boolean isShowHead;
	

	/**
	 * 
	 * @param serverOutPath
	 *            服务器导出文件的路径
	 * @param clientOutDir
	 *            客户端导出的目录
	 */
	public ExportWbHandler(File serverOutPath, boolean isShowHead, int configRowIndex, int dataRowIndex) {
		this.serverOutPath = serverOutPath;
		this.isShowHead = isShowHead;
		this.configRowIndex = configRowIndex;
		this.dataRowIndex = dataRowIndex;
	}

	@Override
	public IExcelSheetHandler getSheetHandler(int sheetIndex) {
		return new ExcetExportHandler();
	}

	@Override
	public boolean onStart(HSSFWorkbook sheet) {
		workBook = new HSSFWorkbook();
		return true;
	}

	@Override
	public boolean onFinish() {
		if (this.workBook == null) {
			return false;
		}
		if (this.workBook.getNumberOfSheets() == 0) {
			return false;
		}
		try {
			this.workBook.write(new FileOutputStream(this.serverOutPath));
			return true;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static int getIntValue(HSSFCell cell) {
		if (cell == null || cell.toString().length() == 0) {
			return 0;
		}
		return (int) Double.parseDouble(cell.toString());
	}

	private final class ExcetExportHandler implements IExcelSheetHandler {
		private HSSFSheet destSheet;
		/** 当前的行数 */
		private int rowNum = 0;
		/** 标题所在的行数 */
		private int headRowNum;
		
		private HSSFFormulaEvaluator evaluator;

		/**
		 * 解析一个Sheet的头部配置指令
		 */
		@Override
		public boolean onStart(HSSFSheet sheet) {
			int totalRowNum = sheet.getLastRowNum();
			if (totalRowNum < dataRowIndex) {
				System.out.println("Not found export config header in sheet [" + sheet.getSheetName() + "],skip it");
				return false;
			}
			if(!isShowHead)
			{
				//如果不显示列名,直接从数据行开始
				rowNum = 1;
			}
			evaluator = new HSSFFormulaEvaluator(sheet.getWorkbook());
			// 读取导出配置表
			HSSFRow configRow = sheet.getRow(configRowIndex);
			if (configRow == null) {
				System.out.println("Not found export config header in sheet [" + sheet.getSheetName() + "],skip it");
				return false;
			}
			boolean _export = getIntValue(configRow.getCell(0)) == 1;
			if (!_export) {
				System.out.println("Will not export sheet [" + sheet.getSheetName() + "],skip it");
				return false;
			}else{
				System.out.println("Will export sheet [" + sheet.getSheetName() + "]");
			}
			
			headRowNum = dataRowIndex;
			this.destSheet = workBook.createSheet(sheet.getSheetName());
			return true;
		}

		@Override
		public boolean handle(final HSSFRow row) {
			try {
				if(isShowHead)
				{
					if (row.getRowNum() < headRowNum) {
						return false;
					}					
				}
				else
				{
					//如果不显示数据,跳过列名行
					if (row.getRowNum() <= headRowNum) {
						return false;
					}
				}
				if (row.getPhysicalNumberOfCells() == 0) {
					return false;
				}
				HSSFCell firstCell = row.getCell(0);
				if (firstCell == null) {
					return false;
				}
				if (firstCell.toString().isEmpty()) {
					return false;
				}
				HSSFRow serverRow = this.destSheet.createRow(rowNum++);
				
				int cols = row.getLastCellNum();
				for (int i = 0; i <= cols; i++) {
					HSSFCell srcCell = row.getCell(i);
					exportServerData(row, serverRow, i, srcCell);					
				}

			} catch (Exception e) {
				throw new RuntimeException("row:" + row.getRowNum(), e);
			}
			return true;
		}

		@Override
		public boolean onFinish() {
			return true;
		}

		/**
		 * 导出服务器需要的数据格式
		 * 
		 * @param row
		 * @param destRow
		 * @param i
		 * @param srcCell
		 */
		private void exportServerData(final HSSFRow row, HSSFRow destRow, int i, HSSFCell srcCell) {
			try {
				HSSFCell destCell = destRow.createCell(i);
				destCell.getCellStyle().setAlignment(CellStyle.ALIGN_JUSTIFY);
				if (srcCell == null) {
					destCell.setCellType(HSSFCell.CELL_TYPE_STRING);
					destCell.setCellValue("");
					return;
				}
				switch (srcCell.getCellType()) {
				case HSSFCell.CELL_TYPE_FORMULA: {
					HSSFCell evaluateInCell = evaluator.evaluateInCell(srcCell);
					Util.setCellValue(row, evaluateInCell, destCell);
					break;
				}
				default: {
					Util.setCellValue(row, srcCell, destCell);
				}
				}
			} catch (Exception e) {
				System.err.println("Error:" + srcCell.getCellType() + " row:" + row.getRowNum() + " col:" + srcCell.getColumnIndex());
				throw new RuntimeException(e);
			}
			return;
		}

		

	}

}
