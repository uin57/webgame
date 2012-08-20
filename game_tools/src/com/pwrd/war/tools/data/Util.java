package com.pwrd.war.tools.data;

import java.io.File;
import java.io.FileFilter;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.LinkedList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;

/**
 * 一些工具类
 * 
 * 
 */
public class Util {

	private static final NumberFormat FMT_NUMBER = new DecimalFormat("0.#########");

	/**
	 * 设置单元格的数据
	 * 
	 * @param row
	 * @param srcCell
	 * @param destCell
	 */
	public static void setCellValue(final HSSFRow row, HSSFCell srcCell,
			HSSFCell destCell) {
		switch (srcCell.getCellType()) {
		case HSSFCell.CELL_TYPE_NUMERIC: {
			destCell.setCellType(HSSFCell.CELL_TYPE_STRING);
			String str = FMT_NUMBER.format(srcCell.getNumericCellValue());
			if (str.endsWith(".0")) {
				destCell.setCellValue(str.substring(0, str.length() - 2));
			} else {
				destCell.setCellValue(str);
			}
			break;
		}
		case HSSFCell.CELL_TYPE_STRING: {
			destCell.setCellType(HSSFCell.CELL_TYPE_STRING);
			destCell.setCellValue(srcCell.getStringCellValue());
			break;
		}
		case HSSFCell.CELL_TYPE_BLANK: {
			destCell.setCellType(HSSFCell.CELL_TYPE_STRING);
			destCell.setCellValue("");
			break;
		}
		case HSSFCell.CELL_TYPE_FORMULA: {
			destCell.setCellType(HSSFCell.CELL_TYPE_FORMULA);
			destCell.setCellFormula(srcCell.getCellFormula());
			break;
		}
		default: {
			throw new IllegalStateException("Unknown supported cell type:"
					+ srcCell.getCellType() + " row:" + row.getRowNum()
					+ " col:" + srcCell.getColumnIndex());
		}
		}
	}

	/**
	 * 简单的拷贝两个Sheet,从src sheet拷贝到dest sheet
	 * 
	 * @param src
	 *            源Sheet
	 * @param dest
	 *            目标Sheet
	 */
	public static void copySheet(HSSFSheet src, HSSFSheet dest) {
		int num = src.getLastRowNum();
		for (int i = 0; i < num; i++) {
			HSSFRow row = src.getRow(i);
			HSSFRow destRow = dest.createRow(i);
			if (row == null) {
				continue;
			}
			int cols = row.getLastCellNum();
			for (int j = 0; j < cols; j++) {
				HSSFCell srcCell = row.getCell(j);
				HSSFCell destCell = destRow.createCell(j);
				if (srcCell != null) {
					setCellValue(row, srcCell, destCell);
				}
			}
		}
	}

	public static void printSheet(HSSFSheet src) {
		int num = src.getLastRowNum();
		for (int i = 0; i < num; i++) {
			HSSFRow row = src.getRow(i);
			if (row == null) {
				continue;
			}
			int cols = row.getLastCellNum();
			for (int j = 0; j < cols; j++) {
				HSSFCell srcCell = row.getCell(j);
				if (srcCell != null) {
					System.out.print(srcCell.toString() + "\t");
				}
			}
			System.out.println();
		}
	}

	/**
	 * 删除指定的文件或者目录
	 * 
	 * @param file
	 * @exception RuntimeException
	 *                删除失败时会抛出此异常
	 */
	public static void deleteFile(File file) {
		if (file.isDirectory()) {
			File[] listFiles = file.listFiles();
			for (File f : listFiles) {
				deleteFile(f);
			}
		}
		if (!file.delete()) {
			throw new RuntimeException("Can't delete the file " + file);
		}
	}

	/**
	 * 列出指定目录下的所有符合过滤条件的文件
	 * 
	 * @param dir
	 * @param filter
	 *            文件过滤器
	 * @return
	 */
	public static List<File> listFiles(File dir, FileFilter filter) {
		List<File> result = new LinkedList<File>();
		if (!dir.isDirectory()) {
			throw new IllegalArgumentException("The file " + dir
					+ " is not a dir.");
		}
		File[] listFiles = dir.listFiles();
		for (File f : listFiles) {
			if (f.isFile()) {
				if (filter.accept(f)) {
					result.add(f);
				}
			} else if (f.isDirectory()) {
				result.addAll(listFiles(f, filter));
			}
		}
		return result;
	}
}
