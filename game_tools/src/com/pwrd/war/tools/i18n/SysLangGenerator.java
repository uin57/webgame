package com.pwrd.war.tools.i18n;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/** 
 * 多语言文件sys_lang.xls生成器
 * 
 * 
 */
public class SysLangGenerator {

	/** 日志 */
	private static final Logger logger = Logger
			.getLogger(SysLangGenerator.class);

	/** 类型 */
	private static final String TYPE_INT = "Integer";

	/** 相对路径 */
	private static final String SYS_LANG_PATN = "..\\resources\\i18n\\zh_CN\\sys_lang.xls";

	/**
	 * 读取LangConstants类中的常量
	 * 
	 * @return 返回多语言数据
	 */
	private static List<Map<String, String>> getSysLangData() {
		List<Map<String, String>> dataList = new ArrayList<Map<String, String>>();
//		try {
//			Class<LangConstants> clazz = LangConstants.class;
//			Field[] fields = clazz.getFields();// 读取public成员
//			for (Field field : fields) {
//				if (Modifier.isStatic(field.getModifiers())
//						&& Modifier.isFinal(field.getModifiers())) {// 判断是否为static和final
//					if (TYPE_INT.equals(field.getType().getSimpleName())) {// 判断整形变量
//						Object obj = field.get(null);
//						SysI18nString annotation = field
//								.getAnnotation(SysI18nString.class);
//						if (annotation != null) {
//							Map<String, String> map = new HashMap<String, String>();
//							map.put("id", obj.toString());
//							map.put("content", annotation.content());
//							map.put("comment", annotation.comment());
//							dataList.add(map);
//						}
//					}
//				}
//			}
//		} catch (Exception e) {
//			logger.error("Unknown Exception", e);
//		}
		return dataList;
	}

	/**
	 * 生成Excel
	 * 
	 * @param dataList
	 *            多语言数据
	 * @param path
	 *            路径
	 */
	private static void createExcel(List<Map<String, String>> dataList,
			String path) {
		OutputStream fout = null;
		try {
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet("sys_lang_sheet");
			// 设置格式
			HSSFCellStyle cellStyle = wb.createCellStyle();
			cellStyle.setAlignment((short) 2);
			HSSFFont cellFont = wb.createFont();
			cellFont.setFontName("宋体");
			cellFont.setFontHeightInPoints((short) 12);
			cellStyle.setFont(cellFont);
			cellStyle.setAlignment((short) 0);
			sheet.setColumnWidth(1, 10000);
			sheet.setColumnWidth(2, 10000);

			// 生成数据区
			if (dataList != null && dataList.size() > 0) {
				for (int row = 0; row < dataList.size(); row++) {
					HSSFRow rowValue = sheet.createRow(row);
					Map<String, String> dataMap = dataList.get(row);

					HSSFCell id = rowValue.createCell(0);
					id.setCellStyle(cellStyle);
					id.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
					id.setCellValue(Integer.valueOf(dataMap.get("id")));

					HSSFCell content = rowValue.createCell(1);
					content.setCellStyle(cellStyle);
					content.setCellType(HSSFCell.CELL_TYPE_STRING);
					content.setCellValue(dataMap.get("content"));

					String comment = dataMap.get("comment");
					if (comment != null && !"".equals(comment.trim())) {
						HSSFCell cell = rowValue.createCell(2);
						cell.setCellStyle(cellStyle);
						cell.setCellType(HSSFCell.CELL_TYPE_STRING);
						cell.setCellValue(comment);
					}
				}
			}
			// 写入到文件
			fout = new FileOutputStream(path);
			wb.write(fout);
			fout.flush();
		} catch (Exception e) {
			logger.error("Unknown Exception", e);
		} finally {
			if (fout != null) {
				try {
					fout.close();
				} catch (IOException e) {
					logger.error("IOException", e);
				}
			}
		}
	}

	public static void main(String[] args) throws IllegalArgumentException,
			IllegalAccessException {
		List<Map<String, String>> dataList = getSysLangData();

		if (args.length == 1) {
			createExcel(dataList, args[0]);
		} else {
			createExcel(dataList, SYS_LANG_PATN);
		}

		System.out.println("sys_lang.xls重新生成完毕。");
	}
}
