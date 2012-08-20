package com.pwrd.war.core.template;

import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.slf4j.Logger;

import com.pwrd.war.common.constants.Loggers;
import com.pwrd.war.common.exception.ConfigException;
import com.pwrd.war.core.annotation.ExcelRowBinding;
import com.pwrd.war.core.annotation.FixUpByCellRange;
import com.pwrd.war.core.util.PoiUtils;

/**
 * 模板文件解析器
 * 
 * 
 */
public class TemplateFileParser {

	Logger logger = Loggers.scriptLogger;
	private TemplateObjectAssembler objectAssembler;

	public TemplateFileParser() {
		objectAssembler = new TemplateObjectAssembler();
	}

	/**
	 * 
	 * 解析一个Excel文件，加载该文件表示的所有TemplateObject对象到templateObjects；
	 * 
	 * @param classes
	 * @param templateObjects
	 * @param inputStream
	 *            TODO
	 */
	public void parseXlsFile(Class<?>[] classes,
			Map<Class<?>, Map<Integer, TemplateObject>> templateObjects,
			InputStream is, String excelFileName) throws Exception {
		int i = 0;
		HSSFWorkbook wb = new HSSFWorkbook(new POIFSFileSystem(is));
		for (; i < classes.length; i++) {
			HSSFSheet sheet = wb.getSheetAt(i);
			Class<?> curClazz = classes[i];
			if (curClazz == null)
				continue;
			Map<Integer, TemplateObject> curSheetObjects = parseXlsSheet(sheet,
					curClazz, i, excelFileName);
			Map<Integer, TemplateObject> existCurClazzMap = templateObjects
					.get(curClazz);
			if (existCurClazzMap != null) {
				// 如果当前类型的对象已存在了，则合并
				existCurClazzMap.putAll(curSheetObjects);
			} else {
				templateObjects.put(curClazz, curSheetObjects);
			}
		}
	}

	/**
	 * 解析Excel文件中的一个Sheet，返回以<id,数据对象>为键值对的Map
	 * 
	 * @param sheet
	 * @param clazz
	 * @return
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	protected Map<Integer, TemplateObject> parseXlsSheet(HSSFSheet sheet,
			Class<?> clazz, int sheetIndex, String excelFileName) throws InstantiationException,
			IllegalAccessException {
		
		Map<Integer, I18NField> i18nField = new HashMap<Integer, I18NField>();
		HSSFRow row0 = sheet.getRow(0);
		if(row0 != null  ){
			String name = excelFileName.toLowerCase().replaceAll(".xls", "");
			Iterator<Cell> cellIt = row0.cellIterator();
			while(cellIt.hasNext()){
				Cell cell = cellIt.next();
				if(cell == null){
					break;
				}
				String str = cell.getStringCellValue();
				Pattern p = Pattern.compile(".+\\[(.+)\\]");
				Matcher m = p.matcher(str);
				if(m.find()){
//					System.out.println(m.group(1));
					I18NField f = new I18NField();
					f.setOffset(cell.getColumnIndex());
					f.setShortTip(m.group(1));
					f.setSheetIndex(sheetIndex);
					f.setFileName(name);
					i18nField.put(cell.getColumnIndex(), f);
				} 
			}
		}
		// int rowLength = sheet.getPhysicalNumberOfRows();
		Map<Integer, TemplateObject> map = new HashMap<Integer, TemplateObject>(
				200, 0.8f);
		// 第一行(原来的标题行)肯定有空,忽略不计
		for (int i = 1; i <= Short.MAX_VALUE; i++) {
			TemplateObject obj;
			obj = (TemplateObject) clazz.newInstance();
			obj.setSheetName(sheet.getSheetName());
			HSSFRow row = sheet.getRow(i);
			if (isEmpty(row)) {
				// 遇到空行就结束
				break;
			}
			this.parseXlsRow(obj, row, i18nField);
			map.put(obj.getId(), obj);
		}
		return map;
	}

	protected boolean isEmpty(HSSFRow row) {
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

	/**
	 * 根据Excel表格，自行组装对象
	 * 
	 * @param obj
	 * @param row
	 */
	public void parseXlsRow(Object obj, HSSFRow row, Map<Integer, I18NField> i18nField) {
		Class<?> clazz = obj.getClass();
		if (!clazz.isAnnotationPresent(ExcelRowBinding.class)) {
			throw new ConfigException(clazz
					+ " is not a excel row binding object");
		}
		try {
			objectAssembler.doAssemble(obj, row, clazz, i18nField);
		} catch (Exception e1) {
			HSSFSheet sheet = row.getSheet();
			int rowNum = row.getRowNum();

			// 异常信息
			String errMsg = String.format("sheet = %s, row = %d", 
				sheet.getSheetName(), rowNum);
			
			throw new ConfigException(errMsg, e1);
		}
		try {
			Method[] methods = null;
			if (TemplateObjectAssembler.classMethods.containsKey(clazz)) {
				methods = TemplateObjectAssembler.classMethods.get(clazz);
			} else {
				methods = clazz.getDeclaredMethods();
				Method.setAccessible(methods, true);
				TemplateObjectAssembler.classMethods.put(clazz, methods);
			}
			for (int i = 0; i < methods.length; i++) {
				if ((methods[i].getModifiers() & Modifier.STATIC) != 0) {
					continue;
				}
				if (methods[i].isAnnotationPresent(FixUpByCellRange.class)) {
					FixUpByCellRange fixupByCellRange = methods[i]
							.getAnnotation(FixUpByCellRange.class);
					int startOff = fixupByCellRange.start();
					int len = fixupByCellRange.len();
					String[] params = new String[len];
					for (int k = 0; k < params.length; k++) {
						params[k] = PoiUtils.getStringValue(row
								.getCell(startOff + k));
					}
					methods[i].invoke(obj, new Object[] { params });
				}
			}
		} catch (Exception e) {
			throw new ConfigException("Unknown exception", e);
		}
	}

	/**
	 * 解析一个Excel文件，加载该文件表示的所有TemplateObject对象到templateObjects；
	 * 
	 * @param xlsPath
	 * @param index
	 * @param clazz
	 * @param templateObjects
	 */
	public void parseXlsFile(String xlsPath, int index, Class<?> clazz,
			Map<Class<?>, Map<Integer, TemplateObject>> templateObjects, String excelFileName) {
		InputStream is = null;
		try {
			is = new FileInputStream(xlsPath);
			HSSFWorkbook wb = new HSSFWorkbook(new POIFSFileSystem(is));
			HSSFSheet sheet = wb.getSheetAt(index);
			Map<Integer, TemplateObject> curSheetObjects = parseXlsSheet(sheet,
					clazz, index, excelFileName);
			Map<Integer, TemplateObject> existCurClazzMap = templateObjects
					.get(clazz);
			if (existCurClazzMap != null) {
				// 如果当前类型的对象已存在了，则合并
				existCurClazzMap.putAll(curSheetObjects);
			} else {
				templateObjects.put(clazz, curSheetObjects);
			}
		} catch (Exception e) {
			throw new ConfigException("Errors occurs while parsing xls file:"
					+ xlsPath + ",sheet:" + index, e);
		}
	}

	/**
	 * 获取限定类
	 * 
	 * @return 限定类数组, 如果返回值为 null, 则说明解析器可以解析任何类
	 */
	public Class<?>[] getLimitClazzes() {
		return null;
	}
}
