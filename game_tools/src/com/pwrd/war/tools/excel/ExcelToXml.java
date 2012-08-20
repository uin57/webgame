package com.pwrd.war.tools.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import com.pwrd.war.core.template.I18NField;

/**
 * 从excel配置文件生成前端用的xml文件,已多语言处理
 * excelToXML为MANIFEST配置文件
 * @author xf
 */
public class ExcelToXml {

	private static void readExcel(String strPath) throws IOException {
		// 构造 XSSFWorkbook 对象，strPath 传入文件路径
		Workbook wb = null;
		if (strPath.endsWith("xls")) {// 2003
			File f = new File(strPath);

			FileInputStream is = new FileInputStream(f);
			POIFSFileSystem fs = new POIFSFileSystem(is);
			wb = new HSSFWorkbook(fs);
			is.close();
		} else if (strPath.endsWith("xlsx")) {// 2007
			wb = new XSSFWorkbook(strPath);
		}
		/** 建立document对象 */
		Document document = DocumentHelper.createDocument();
		/** 建立config根节点 */
		Element configElement = document.addElement("items");
		String fileName = strPath.substring(strPath.lastIndexOf('\\')+1);
				
		for (int j = 0; j < wb.getNumberOfSheets(); j++) {
			// 读取第一章表格内容
			Sheet sheet = wb.getSheetAt(j);
			// 定义 row
//			Row[] rows = new Row[0];
			List<Row> list = new ArrayList<Row>();
			// 循环输出表格中的内容
			for (int i = 0; i < Short.MAX_VALUE; i++) {
				Row row = sheet.getRow(i);
				if(row == null){
					break;
				}
				Cell cell0 = row.getCell(0);
				if(cell0 == null ){
					break;
				}
				list.add(row);
			}
			if(list.size() == 0)continue;
//			String fileName = sheet.getSheetName() + ".xml";
			xmlWriteDemoByDocument(document, j, configElement,
					list, fileName);
		}
		fileName = fileName.replaceAll(".xls", ".xml");
		/** 保存Document */
		doc2XmlFile(document, fileName);
	}
	
	 
	
	public static void main(String[] args) {
		if (args.length < 1) {
			System.out.println("没有输入文件");
			return;
		}
		String strPath = args[0];
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss:SS");
		TimeZone t = sdf.getTimeZone();
		t.setRawOffset(0);
		sdf.setTimeZone(t);
		Long startTime = System.currentTimeMillis();
		// 检测代码
		try {
			readExcel(strPath);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		Long endTime = System.currentTimeMillis();
		System.out.println("用时：" + sdf.format(new Date(endTime - startTime)));
	}

	public static void xmlWriteDemoByDocument(Document document, int sheetIndex, 
			Element configElement,
			List<Row> rows, String fileName)
			throws UnsupportedEncodingException {
		
		Row row0 = rows.get(0);
		Map<Integer, I18NField> i18nField = new HashMap<Integer, I18NField>();
		if(row0 != null  ){
			String name = fileName.toLowerCase().replaceAll(".xls", "");
			name = name.toLowerCase().replaceAll(".xml", "");
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
		row0 = rows.get(0);
		for (int i = 1; i < rows.size(); i++) {
			Element ftpElement = configElement.addElement("item");
			for (int j = row0.getFirstCellNum(); j < row0
					.getPhysicalNumberOfCells(); j++) {
				String cell = row0.getCell(j).toString();
				if (cell.indexOf("(") > 0 && cell.indexOf(")") > 0
						&& cell.indexOf("(") < cell.indexOf(")")) {
					Row row = rows.get(i);
					Cell value = row.getCell(j);
					if(value == null) continue;
					value.setCellType(Cell.CELL_TYPE_STRING);
					if (value.toString() != null && value.toString() != "") {
						if(i18nField.containsKey(value.getColumnIndex())){
							ftpElement.addAttribute(cell.substring(cell.indexOf("(") + 1,cell.indexOf(")")),
									i18nField.get(value.getColumnIndex()).getIdNormal(i));
						}else{
							ftpElement.addAttribute(cell.substring(cell.indexOf("(") + 1,cell.indexOf(")")),
									value.toString());
						}
					}

				}
			}
			if(ftpElement.attributes().isEmpty()){
				configElement.remove(ftpElement);
			}
		}
		
	}

	/**
	 * doc2XmlFile 将Document对象保存为一个xml文件到本地
	 * 
	 * @return true:保存成功 flase:失败
	 * @param filename
	 *            保存的文件名
	 * @param document
	 *            需要保存的document对象
	 */
	public static void doc2XmlFile(Document document, String filename) {
		try {
			/* 将document中的内容写入文件中 */
			// 默认为UTF-8格式，指定为"GB2312"
			OutputFormat format = OutputFormat.createPrettyPrint();
			// format.setEncoding("UTF-8");
			FileOutputStream fos = new FileOutputStream(filename);
			// writer = new XMLWriter(new FileWriter(xmlFile), format);
			XMLWriter writer = new XMLWriter(fos, format);
			writer.write(document);
			writer.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
