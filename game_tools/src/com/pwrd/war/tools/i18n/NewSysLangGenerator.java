package com.pwrd.war.tools.i18n;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;

import com.pwrd.war.core.annotation.SysI18nString;
import com.pwrd.war.core.template.I18NField;
import com.pwrd.war.core.util.PoiUtils;

/** 
 * 多语言文件sys_lang.xls生成器
 * 
 * 
 */
public class NewSysLangGenerator {

	/** 日志 */
	private static final Logger logger = Logger
			.getLogger(NewSysLangGenerator.class);

	/** 类型 */
	private static final String TYPE_INT = "Integer";

	/** 相对路径 */
//	private static final String SYS_LANG_PATN = "..\\resources\\i18n\\zh_CN\\sys_lang.xls";
	private static final String SYS_LANG_PATN = "..\\resources\\i18n\\zh_CN\\lang.properties";

	/**
	 * 读取LangConstants类中的常量
	 * 
	 * @return 返回多语言数据
	 */
	private static List<Map<String, String>> getSysLangData() {
		List<Map<String, String>> dataList = new ArrayList<Map<String, String>>();
		try {
			List<Class> clazzes  =  new ArrayList<Class>();
			String p = "../game_server/src/com/pwrd/war/gameserver/common/i18n/constants/";
			File f = new File(p);
			for(File f1 : f.listFiles()){
				if(f1.isFile()){
					String name = f1.getName();
					name = name.replace(".java", "");
					name = "com.pwrd.war.gameserver.common.i18n.constants."+name;
					Class c = Class.forName(name);
					System.out.println(c);
					clazzes.add(c);
				}
			} 
			for(Class clazz : clazzes){ 
				Field[] fields = clazz.getFields();// 读取public成员
				for (Field field : fields) {
					if (Modifier.isStatic(field.getModifiers())
							&& Modifier.isFinal(field.getModifiers())) {// 判断是否为static和final
						if (TYPE_INT.equals(field.getType().getSimpleName())) {// 判断整形变量
							Object obj = field.get(null);
							SysI18nString annotation = field
									.getAnnotation(SysI18nString.class);
							if (annotation != null) {
								Map<String, String> map = new HashMap<String, String>();
								map.put("id",   "sys_lang_"+obj.toString());
								map.put("content", annotation.content());
								map.put("comment", annotation.comment());
								dataList.add(map);
							}
						}
					}
				}
			}

			
		} catch (Exception e) {
			logger.error("Unknown Exception", e);
		}
		return dataList;
	}
	
	/**
	 * 取得配置文件的多语言
	 * @author xf
	 */
	private static List<Map<String, String>> getConfigLangData() {
		List<Map<String, String>> dataList = new ArrayList<Map<String, String>>();
		try {
			String path = "..\\resources\\scripts";
			File pathf = new File(path);
			for (File f : pathf.listFiles()) {
				if(f.isDirectory())continue;
				if(!f.getName().endsWith(".xls"))continue;
				System.out.println("start..."+f.getName());
				//每个excel
				HSSFWorkbook wb = new HSSFWorkbook(new POIFSFileSystem(
						new FileInputStream(f)));
				String name = f.getName().toLowerCase().replace(".xls", "");
				for (int sheetIndex = 0; sheetIndex < wb.getNumberOfSheets(); sheetIndex++) {
					//每个sheet
					HSSFSheet sheet = wb.getSheetAt(sheetIndex);
					//确定第一行哪些是需要国际化的
					Map<Integer, I18NField> i18nField = new HashMap<Integer, I18NField>();
					HSSFRow row0 = sheet.getRow(0);
					if(row0 != null  ){
						Iterator<Cell> cellIt = row0.cellIterator();
						while(cellIt.hasNext()){
							Cell cell = cellIt.next();
							if(cell == null){
								break;
							}
//							String str = cell.getStringCellValue();
							String str = getStringValue(cell);
							Pattern p = Pattern.compile(".+\\[(.+)\\]");
							Matcher m = p.matcher(str);
							if(m.find()){
//								System.out.println(m.group(1));
								I18NField field = new I18NField();
								field.setOffset(cell.getColumnIndex());
								field.setShortTip(m.group(1));
								field.setSheetIndex(sheetIndex);
								field.setFileName(name);
								i18nField.put(cell.getColumnIndex(), field);
							} 
						}
					}
					//遍历每一行
					// 第一行(原来的标题行)肯定有空,忽略不计
					for (int i = 1; i <= Short.MAX_VALUE; i++) { 
						HSSFRow row = sheet.getRow(i);
						if (isEmpty(row)) {
							// 遇到空行就结束
							break;
						}
						for(Map.Entry<Integer, I18NField> e : i18nField.entrySet()){
							int cellIndex = e.getKey();
							Cell cell = row.getCell(cellIndex);
							if(cell == null)continue;
//							String id = cell.getStringCellValue();
							String id = getStringValue(cell);
							String content = i18nField.get(cellIndex).getIdNoFormat(row.getRowNum());
							Map<String, String> map = new HashMap<String, String>();
							map.put("id",   content);
							map.put("content", id); 
							dataList.add(map);
						}
					}
				}
				System.out.println("end..."+f.getName());				
				//end
			}
		} catch (Exception e) { 
			e.printStackTrace();
		}
		return dataList;
	}
	
	public static String getStringValue(Cell cell) {
		if (cell == null) {
			return "";
		}
		switch (cell.getCellType()) {
		case HSSFCell.CELL_TYPE_STRING: {
			return cell.toString();
		}
		case HSSFCell.CELL_TYPE_NUMERIC: {
			String str =  cell.getNumericCellValue()+"";
			if (str.endsWith(".0")) {
				return str.substring(0, str.length() - 2);
			} else {
				return str;
			}
		}
		default: {
			return cell.toString();
		}
		}
	}
	protected static boolean isEmpty(HSSFRow row) {
		// 检测此行的第一个单元格是否为空
		if (row == null) {
			return true;
		}
		HSSFCell cell0 = row.getCell(0);
		String value = PoiUtils.getStringValue(cell0);
		if (value == null || value.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}
	private static void createProperties(List<Map<String, String>> dataList,
			String path) {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(path));
			// 生成数据区
			if (dataList != null && dataList.size() > 0) {
				for (int row = 0; row < dataList.size(); row++) {
				 
					Map<String, String> dataMap = dataList.get(row);
					String t = dataMap.get("id") +"="+dataMap.get("content")+"\r\n"; 

					String comment = dataMap.get("comment"); 
					bw.write(t);
				}
			}
			
			bw.flush();
			bw.close();
		} catch (Exception e) { 
			e.printStackTrace();
		}
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
		dataList.addAll(getConfigLangData());
		
		if (args.length == 1) {
			createProperties(dataList, args[0]);
		} else {
			createProperties(dataList, SYS_LANG_PATN);
		}

		System.out.println("sys_lang.properties重新生成完毕。");
	}
}
