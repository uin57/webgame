package com.pwrd.war.tools.xls2txt;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import com.pwrd.war.core.util.StringUtils;

/**
 * excel文件转为txt文本文件
 * 
 * 
 */
public class ExcelConverter {
	private String rootDir;
	private Map<String, String[]> templateFiles;
	private Map<String, int[]> templateFileFirstRows;
	private static final String EXCEL_LINE_DELIMITER = "|";
	private static String editorPath;// 编辑器路径
	private static int DEFAULT_FIRSTROW_NO = 6;
	private static final String configPath = "config.xml";
	private static final String debugConfigPath = "xls2txt/config.xml";

	private HSSFFormulaEvaluator evaluator;

	public void convert() {
		ClassLoader classLoader = Thread.currentThread()
				.getContextClassLoader();
		String configFile = null;
		if (classLoader.getResource(configPath) == null) {
			configFile = classLoader.getResource(debugConfigPath).getPath();
		} else {
			configFile = classLoader.getResource(configPath).getPath();
		}
		this.loadConfig(configFile);
		Set<String> fileNames = templateFiles.keySet();
		for (String fileName : fileNames) {
			String[] sheetIndexes = templateFiles.get(fileName);
			int[] fristRows = templateFileFirstRows.get(fileName);
			parseXlsFile(fileName, sheetIndexes, fristRows);
		}
	}

	/**
	 * @param xlsPath
	 * @param sheetIndexes
	 */
	private void parseXlsFile(String fileName, String[] sheetIndexes,
			int[] fristRows) {
		String xlsPath = getResourceFilePath(fileName);
		InputStream is = null;
		OutputStream os = null;
		try {
			is = new FileInputStream(xlsPath);
			HSSFWorkbook wb = new HSSFWorkbook(new POIFSFileSystem(is));
			for (int i = 0; i < sheetIndexes.length; i++) {
				HSSFSheet sheet = wb.getSheetAt(Integer
						.valueOf(sheetIndexes[i]));
				evaluator = new HSSFFormulaEvaluator(sheet.getWorkbook());
				os = new FileOutputStream(getSheetTxtFilePath(fileName, i));
				int rows = sheet.getPhysicalNumberOfRows();
				if (rows < 1) {
					continue;
				}
				int cols = sheet.getRow(fristRows[i]).getLastCellNum() - 1;
				for (int j = fristRows[i]; j < rows; j++) {
					String txtRow = parseXlsRow(sheet.getRow(j), cols);
					os.write(txtRow.getBytes("UTF-8"));
					if (j < rows - 1) {
						os.write("\n".getBytes("UTF-8"));
					}
				}
				os.close();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private String getSheetTxtFilePath(String xlsPath, int i) {
		int lastIndexOf = xlsPath.lastIndexOf(".");
		StringBuffer txtFilePath = new StringBuffer(xlsPath.substring(0,
				lastIndexOf));
		txtFilePath.append(i);
		txtFilePath.append(".txt");
		return txtFilePath.toString();
	}

	/**
	 * @param rowline
	 * @return
	 */
	@SuppressWarnings("deprecation")
	private String parseXlsRow(HSSFRow rowline, int filledColumns) {
		StringBuffer buffer = new StringBuffer();
		HSSFCell cell = null;
		for (int i = 0; i < filledColumns; i++) {
			cell = rowline.getCell((short) i);
			String cellValue = getCellValue(cell);
			 cellValue = cellValue.trim();
			if (i < filledColumns - 1)
				buffer.append(cellValue).append(EXCEL_LINE_DELIMITER);
			else
				buffer.append(cellValue);
		}
		return buffer.toString();
	}

	public String getCellValue(HSSFCell cell) {
		if (cell == null) {
			return "";
		}
		String cellValue = null;
		switch (cell.getCellType()) {
		case HSSFCell.CELL_TYPE_NUMERIC: {
			if (HSSFDateUtil.isCellDateFormatted(cell)) {
				SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
				cellValue = date.format(cell.getDateCellValue());
			} else {
				Integer num = new Integer((int) cell.getNumericCellValue());
				cellValue = String.valueOf(num);
			}
			break;
		}
		case HSSFCell.CELL_TYPE_FORMULA: {
			HSSFCell evaluateCell = evaluator.evaluateInCell(cell);
			cellValue = this.getCellValue(evaluateCell);
			break;
		}
		case HSSFCell.CELL_TYPE_STRING:
			cellValue = cell.getRichStringCellValue().toString().replaceAll(
					"'", "''");
			break;
		default:
			cellValue = "";
		}
		return cellValue;
	}

	private String getResourceFilePath(String fileName) {
		return rootDir + "//" + fileName;
	}

	@SuppressWarnings("unchecked")
	private void loadConfig(String cfgPath) {
		Element root = getRootElemet(cfgPath);
		rootDir = root.getAttributeValue("dir");
		editorPath = root.getAttributeValue("editor");
		templateFiles = new HashMap<String, String[]>();
		templateFileFirstRows = new HashMap<String, int[]>();
		List<Element> fileElements = root.getChildren();
		for (Element fileElement : fileElements) {
			String fileName = fileElement.getAttributeValue("name");
			String sheetIndexAttri = fileElement.getAttributeValue("sheet");
			String fristRow = fileElement.getAttributeValue("fristRow");
			String[] sheetIndexes = sheetIndexAttri.split(",");
			int[] fristRows = null;
			if (StringUtils.isEmpty(fristRow)) {
				fristRows = new int[sheetIndexes.length];
				for (int i = 0; i < fristRows.length; i++) {
					fristRows[i] = DEFAULT_FIRSTROW_NO;
				}
			} else {
				fristRows = StringUtils.getIntArray(fristRow, ",");
			}
			templateFiles.put(fileName, sheetIndexes);
			templateFileFirstRows.put(fileName, fristRows);
		}
	}

	public static Element getRootElemet(String xmlPath) {
		SAXBuilder builder = new SAXBuilder();
		Document doc = null;
		try {
			doc = builder.build(xmlPath);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return doc.getRootElement();
	}

	public static void main(String[] args) throws IOException {
		ExcelConverter converter = new ExcelConverter();
		converter.convert();
		if (editorPath != null && !editorPath.trim().equals("")) {
			Runtime.getRuntime().exec(editorPath);
		}
	}
}
