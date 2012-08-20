package com.pwrd.war.tools.log;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.pwrd.war.common.LogReasons.LogDesc;
import com.pwrd.war.common.LogReasons.ReasonDesc;

/**
 * 日志数据字典生成器
 * 
 *
 */
public class LogDataDictGenerator {
	/** 日志类 */
	private static final String LOG_REASON_CLAZZ = "com.pwrd.war.common.LogReasons";
	/** 日志数据字典 */
	private static final String XLS_FILENAME = "/D:/logDataDict.xls";

	/**
	 * 类参数构造器
	 * 
	 */
	public LogDataDictGenerator() {
	}

	/**
	 * 生成
	 * 
	 */
	public void generate() {
		System.out.println("createGenLogTypeList");
		// 获取日志类型列表
		List<GenLogType> genLogTypeList = this.createGenLogTypeList();
		
		System.out.println("writeOutXls");
		// 输出文本文件
		this.writeOutXls(genLogTypeList);

		System.out.println("Ok!");
		System.exit(0);
	}

	/**
	 * 输出文本文件
	 * 
	 * @param genLogTypeList 
	 * 
	 */
	private void writeOutXls(List<GenLogType> genLogTypeList) {
		if (genLogTypeList == null) {
			return;
		}

		// 创建工作薄
		HSSFWorkbook wb = new HSSFWorkbook();
		// 创建工作单
		HSSFSheet sheet = wb.createSheet("logDataDict");

		RowNumber rownum = new RowNumber();

		// 创建表头
		this.createTitle(sheet, rownum);

		for (GenLogType genLogType : genLogTypeList) {
			// 创建数据行
			HSSFRow row = sheet.createRow(rownum.incAndGet());
			// 输出日志类型
			this.writeOutGenLogType(genLogType, row, rownum);
		}

		try {
			// 创建输出流
			OutputStream os = new FileOutputStream(XLS_FILENAME);
			// 写出 xls
			wb.write(os);
	
			// 刷新并关闭输出流
			os.flush();
			os.close();
		} catch (Exception ex) {
			// 抛出异常
			throw new RuntimeException(ex);
		}
	}

	/**
	 * 创建表头
	 * 
	 * @param sheet
	 * @param rownum
	 */
	private void createTitle(HSSFSheet sheet, RowNumber rownum) {
		// 创建表头
		HSSFRow row = sheet.createRow(rownum.curr());

		int i = 0;
		HSSFCell cell = row.createCell(i++);
		cell.setCellValue("日志原因");
		cell = row.createCell(i++);
		cell.setCellValue("编号");
		cell = row.createCell(i++);
		cell.setCellValue("原因说明");
		cell = row.createCell(i++);
		cell.setCellValue("日志文本格式");
	}

	/**
	 * 输出日志类型
	 * 
	 * @param genLogType
	 * @param row
	 * @param rownum 
	 * 
	 */
	private void writeOutGenLogType(GenLogType genLogType, HSSFRow row, RowNumber rownum) {
		if (genLogType == null || 
			row == null || 
			rownum == null) {
			return;
		}

		HSSFSheet sheet = row.getSheet();
		
		HSSFCell cell = row.createCell(0);
		cell.setCellValue(genLogType._name);

		// 获取日志原因列表并排序
		Collection<GenLogReason> values = this.sortLogReasonList(
				genLogType._logReasonMap.values());

		for (GenLogReason genLogReason : values) {
			// 创建数据行
			row = sheet.createRow(rownum.incAndGet());
			// 输出日志原因
			this.writeOutGenLogReason(genLogReason, row);
		}

		// 创建空白行
		sheet.createRow(rownum.incAndGet());
	}

	/**
	 * 排序日志原因
	 * 
	 * @param values
	 * @return 
	 * 
	 */
	private Collection<GenLogReason> sortLogReasonList(
		Collection<GenLogReason> logReasonList) {
		// 创建临时数组列表
		List<GenLogReason> tempList = new ArrayList<GenLogReason>(logReasonList);

		// 排序
		Collections.sort(tempList, new Comparator<GenLogReason>(){
			@Override
			public int compare(GenLogReason a, GenLogReason b) {
				return a._id - b._id;
			}
		});

		return tempList;
	}

	/**
	 * 输出日志原因
	 * 
	 * @param genLogReason
	 * @param row 
	 * 
	 */
	private void writeOutGenLogReason(GenLogReason genLogReason, HSSFRow row) {
		if (genLogReason == null || 
			row == null) {
			return;
		}

		int i = 0;

		HSSFCell cell = row.createCell(i++);
		cell.setCellValue(genLogReason._name);
		cell = row.createCell(i++);
		cell.setCellValue(String.valueOf(genLogReason._id));
		cell = row.createCell(i++);
		cell.setCellValue(genLogReason._reasonDesc);
		cell = row.createCell(i++);
		cell.setCellValue(genLogReason._reasonText);
	}

	/**
	 * 创建日志类型列表
	 * 
	 * @return
	 * 
	 */
	private List<GenLogType> createGenLogTypeList() {
		// 获取内置类数组
		Class<?>[] innerClazzes = this
			.getLogReasonsClazz().getDeclaredClasses();

		List<GenLogType> genLogTypeList = new LinkedList<GenLogType>();
		
		for (Class<?> innerClazz : innerClazzes) {
			if (innerClazz.isEnum()) {
				// 创建日志类型
				GenLogType genLogType = this.createGenLogType(innerClazz);

				if (genLogType == null) {
					continue;
				}

				// 添加日志类型到列表
				genLogTypeList.add(genLogType);
			}
		}

		return genLogTypeList;
	}

	/**
	 * 创建日志类型
	 * 
	 * @param enumClazz
	 * 
	 */
	private GenLogType createGenLogType(Class<?> enumClazz) {
		if (enumClazz == null) {
			return null;
		}

		GenLogType genLogType = new GenLogType();

		// 日志说明
		LogDesc logDesc = enumClazz.getAnnotation(LogDesc.class);
		String clazzSimpleName = enumClazz.getSimpleName();

		genLogType._name = logDesc.desc() + " " + clazzSimpleName;
		genLogType._logReasonMap = new HashMap<Integer, GenLogReason>();

		try {
			// Enum.values
			Method valuesMethod = enumClazz.getDeclaredMethod("values");
			// 获取枚举值数组
			Object[] values = (Object[])valuesMethod.invoke(enumClazz);

			for (Object value : values) {
				// 创建日志原因
				GenLogReason genLogReason = this.createGenLogReason(value);

				if (genLogReason == null) {
					continue;
				}

				// 添加日志原因到日志类型
				genLogType._logReasonMap.put(
					genLogReason._id, genLogReason);
			}
		} catch (Exception ex) {
			// 抛出异常
			throw new RuntimeException(ex);
		}

		return genLogType;
	}

	/**
	 * 收集枚举值
	 * 
	 * @param enumValue
	 * 
	 */
	private GenLogReason createGenLogReason(Object enumValue) {
		if (enumValue == null) {
			return null;
		}

		GenLogReason genLogReason = new GenLogReason();

		genLogReason._id = 0;
		genLogReason._name = null;
		genLogReason._reasonText = null;

		try {
			Class<?> clazz = enumValue.getClass();
			
			// 获取原有说明
			ReasonDesc reasonDesc = clazz.getField(enumValue.toString()).getAnnotation(ReasonDesc.class);
			// 获取日志 Id 函数
			Method getReasonMethod = clazz.getMethod("getReason");
			// 获取日志文本函数
			Method getReasonTextMethod = enumValue.getClass().getMethod("getReasonText");

			// 获取日志 Id
			Integer reason = (Integer)getReasonMethod.invoke(enumValue);
			// 获取日志文本值
			String reasonText = (String)getReasonTextMethod.invoke(enumValue);

			genLogReason._name = enumValue.toString();
			genLogReason._id = reason;
			genLogReason._reasonDesc = reasonDesc.value();
			genLogReason._reasonText = reasonText;
		} catch (Exception ex) {
			// 抛出异常
			throw new RuntimeException(ex);
		}

		return genLogReason;
	}

	/**
	 * 获取日志原因类
	 * 
	 * @return
	 */
	private Class<?> getLogReasonsClazz() {
		try {
			return Class.forName(LOG_REASON_CLAZZ);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	/**
	 * 日志类型
	 * 
	 * @author haijiang.jin
	 *
	 */
	private static class GenLogType {
		/** 日志类型名称 */
		private String _name;
		/** 日志原因字典 */
		private Map<Integer, GenLogReason> _logReasonMap;
	}

	/**
	 * 生成日志原因
	 * 
	 * @author haijiang.jin
	 *
	 */
	private static class GenLogReason {
		/** 日志名称 */
		private String _name;
		/** 日志 Id */
		private int _id;
		/** 日志原因说明 */
		private String _reasonDesc;
		/** 日志原因文本 */
		private String _reasonText;
	}

	/**
	 * 行号
	 * 
	 * @author haijiang.jin
	 *
	 */
	private static class RowNumber {
		/** 行号 */
		private int _number = 0;

		/**
		 * 加 1 并返回
		 * 
		 * @return
		 */
		public int incAndGet() {
			return ++this._number;
		}

		/**
		 * 获取当前值
		 * 
		 * @return
		 */
		public int curr() {
			return this._number;
		}
	}

	/**
	 * 应用程序主函数
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// 创建生成器并生成日志数据字典
		LogDataDictGenerator generator;

		generator = new LogDataDictGenerator();
		generator.generate();
	}
}
