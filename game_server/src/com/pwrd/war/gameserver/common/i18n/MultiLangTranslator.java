package com.pwrd.war.gameserver.common.i18n;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.pwrd.war.common.constants.Loggers;
import com.pwrd.war.core.annotation.SyncIoOper;
import com.pwrd.war.core.template.TemplateConfig;
import com.pwrd.war.core.template.TemplateFileParser;
import com.pwrd.war.core.template.TemplateObject;
import com.pwrd.war.core.template.TemplateService;
import com.pwrd.war.core.util.PoiUtils;

/**
 * 多语言翻译
 * 
 * @author haijiang.jin
 *
 */
public class MultiLangTranslator {
	/** 多语言 Id 字段命名规则 */
	private static final String LANG_ID_FIELD_NAME = "\\w+?LangId";
	/** 多语言 Id 列 */
	private static final int LANG_ID_COL = 0;
	/** 多语言文本列 */
	private static final int LANG_TEXT_COL = 2;
	
	/** 资源目录 */
	public static final String RESOURCE_DIR = "resource_dir";
	/** 多语言目录 */
	public static final String I18N_DIR = "i18n_dir";
	/** 使用语言 */
	public static final String USE_LANGUAGE = "use_lang";

	/** 
	 * 多语言字典: 
	 * 
	 * Map<多语言 Excel 文件名称, Map<多语言Id, 多语言字符串>> 
	 */
	private Map<String, Map<Integer, String>> _langXlsMap;

	/** 
	 * 配置类字典: 
	 * 
	 * Map<Template 类名称, 配置 Excel 文件名称>
	 */
	private Map<String, String> _classXlsMap;
	
	/** 环境变量 */
	private Properties _props;
	/** 模版服务 */
	private TemplateService _tmplServ;

	/**
	 * 类参数构造器
	 * 
	 * @param tmplServ
	 * @param props 环境变量
	 * <ul>
	 * <li>{@link #RESOURCE_DIR}: Excel 文件资源目录</li>
	 * <li>{@link #I18N_DIR}: 多语言目录</li>
	 * <li>{@link #USE_LANGUAGE}: 使用语言</li>
	 * </ul>
	 * 
	 */
	public MultiLangTranslator(TemplateService tmplServ, Properties props) {
		this._props = props;
		this._tmplServ = tmplServ;

		this._langXlsMap = Maps.newHashMap();
		this._classXlsMap = Maps.newHashMap();
	}

	/**
	 * 获取多语言资源目录
	 * 
	 * @return
	 */
	private String getLangResDir() {
		String resourceDir = this._props.getProperty(RESOURCE_DIR);
		String i18nDir = this._props.getProperty(I18N_DIR);
		String useLang = this._props.getProperty(USE_LANGUAGE);

		return String.format(
			"%s/%s/%s", resourceDir, i18nDir, useLang);
	}

	/**
	 * 开始翻译
	 * 
	 */
	public void startTranslate() {
		// 初始化
		this.init();
		// 翻译
		this.translate();
	}

	/**
	 * 初始化多语言翻译器
	 * 
	 */
	@SyncIoOper
	private void init() {
		logInfo("multiLangTranslator.init");
		
		// 将模版配置映射为字典
		this.mappingTemplateCfgList(
			this._tmplServ.getTemplateCfgs(), this._classXlsMap);
		
		// 加载多语言资源
		this.loadLangXls(
			this.getLangResDir(), this._langXlsMap);
	}

	/**
	 * 遍历 TemplateConfig 列表, 将模版配置映射为字典!
	 * 字典结构如下: 
	 * 
	 * <ul>
	 * <li>Key: 模版类名称</li>
	 * <li>value: 所属 Excel 文档名称</li>
	 * </ul>
	 * 
	 * @param cfgList
	 * @param resultMap
	 * 
	 */
	private void mappingTemplateCfgList(
		List<TemplateConfig> cfgList, Map<String, String> resultMap) {

		for (TemplateConfig cfg : cfgList) {
			this.mappingTemplateCfg(cfg, resultMap);
		}
	}

	/**
	 * 映射单个配置
	 * 
	 * @param cfg
	 * @param resultMap
	 */
	private void mappingTemplateCfg(
		TemplateConfig cfg, Map<String, String> resultMap) {
		// 获取包含类数组
		Class<?>[] clazzArray = cfg.getClasses();

		this.mappingTemplateClazzesAndFileName(
			clazzArray, cfg.getFileName(), resultMap);

		// 获取解析器名称
		String parserClazzName = cfg.getParserClassName();

		if (parserClazzName != null) {
			// 模版文件解析器
			TemplateFileParser parser;
			
			try {
				// 创建解析器
				parser = (TemplateFileParser)Class
					.forName(parserClazzName).newInstance();
			} catch (Exception ex) {
				throw new RuntimeException(ex);
			}

			// 映射类数组和文件名
			this.mappingTemplateClazzesAndFileName(
				parser.getLimitClazzes(), cfg.getFileName(), resultMap);
		}
	}

	/**
	 * 映射类数组和文件名, 
	 * 将 TemplateObject 及其派生类与所对应得 .xls 文件名作映射
	 * 
	 * @param clazzes
	 * @param fileName
	 * @param resultMap
	 */
	private void mappingTemplateClazzesAndFileName(
		Class<?>[] clazzes, String fileName, Map<String, String> resultMap) {

		for (Class<?> clazz : clazzes) {
			// 设置类名称所对应的 Excel 文件名
			resultMap.put(clazz.getName(), fileName);
		}
	}

	/**
	 * 加载多语言配置文件
	 * 
	 * @param resDir
	 * @param resultMap 
	 * 
	 */
	private void loadLangXls(
		String resDir, 
		Map<String, Map<Integer, String>> resultMap) {

		// 创建文件对象
		File resPath = new File(resDir);

		// 获取所有 .xls 文件
		File[] fl = resPath.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File f, String fname) {
				return fname.endsWith(".xls");
			}
		});

		for (File f : fl) {
			try {
				// 创建多语言字典
				Map<Integer, String> langMap = this.createLangMap(f.getAbsolutePath());
				resultMap.put(f.getName(), langMap);
			} catch (Exception ex) {
				// 抛出运行时异常
				throw new RuntimeException(f.getAbsolutePath() + " error!", ex);
			}
		}
	}

	/**
	 * 创建多语言字典
	 * 
	 * @param xls
	 * @return
	 * @throws Exception
	 */
	private Map<Integer, String> createLangMap(String xls) throws Exception {
		// 创建文件输入流
		InputStream is = new FileInputStream(xls);
		// 创建工作簿
		HSSFWorkbook wb = new HSSFWorkbook(is);
		// 工作工作单
		HSSFSheet sheet = wb.getSheetAt(0);

		// 获取表格行数
		int rowCount = sheet.getLastRowNum();

		Map<Integer, String> resultMap = Maps.newHashMap();

		for (int r = 0; r < rowCount; r++) {
			// 获取数据行
			HSSFRow row = sheet.getRow(r);

			// 多语言 Id
			int langId = PoiUtils.getIntValue(row.getCell(LANG_ID_COL));
			// 多语言值
			String langStr = PoiUtils.getStringValue(row.getCell(LANG_TEXT_COL));

			resultMap.put(langId, langStr);
		}

		return resultMap;
	}

	/**
	 * 翻译, 工作流程如下: 
	 * 
	 * <ol>
	 * <li>获取一个 TemplateObject 对象;</li>
	 * <li>获取该 TemplateObject 对象所对应的多语言字典;</li>
	 * <li>获取该 TemplateObject 对象所有 Field;</li>
	 * <li>遍历每一个 Field, 判断是否为多语言 Id 字段? <font color='#909090'>(字段是否有 LangId 后缀)</font>;</li>
	 * 		<ul>
	 * 		<li>是: 替换多语言文本字段;</li>
	 * 		<li>否: 跳过当前 Field;</li>
	 * 		</ul>
	 * <li>回到第 1 步, 继续执行</li>
	 * </ol>
	 * 
	 */
	private void translate() {
		// 获取配置字典
		Map<Class<?>, Map<Integer, TemplateObject>> 
			clazzTmplMap = this._tmplServ.getAllClassTemplateMaps();

		for (Class<?> key : clazzTmplMap.keySet()) {
			// 获取类名称
			String clazzName = key.getName();
			// 获取配置字典
			Map<Integer, TemplateObject> tmplMap = clazzTmplMap.get(key);
			// 获取多语言字典
			Map<Integer, String> 
				langMap = this.getLangMap(clazzName);

			if (langMap == null) {
				logError("langMap is null, class name = " + clazzName);
				continue;
			}

			logInfo("translate: " + clazzName);
			// 回写多语言文本
			this.rewriteLangText(tmplMap, langMap);
		}
	}

	/**
	 * 根据类名称找到多语言字典
	 * 
	 * @param clazzName 
	 * @return
	 */
	private Map<Integer, String> getLangMap(String clazzName) {
		// 首先根据类名称找到所对应的 Excel 文档名称
		String xlsName = this._classXlsMap.get(clazzName);
		// 之后根据 Excel 文档名称取得多语言字典
		return this._langXlsMap.get("lang_" + xlsName);
	}

	/**
	 * 回写多个 Template 对象中的多语言文本
	 * 
	 * @param tmplMap
	 * @param langMap
	 */
	private void rewriteLangText(
		Map<Integer, TemplateObject> tmplMap, 
		Map<Integer, String> langMap) {

		for (TemplateObject tmpl : tmplMap.values()) {
			// 回写多语言文本
			this.rewriteLangText(tmpl, langMap);
		}
	}

	/**
	 * 回写一个 Template 对象中的多语言文本
	 * 
	 * @param tmpl
	 * @param langMap
	 * 
	 */
	private void rewriteLangText(TemplateObject tmpl, Map<Integer, String> langMap) {
		// 字段列表
		List<Field> fieldList = Lists.newArrayList();
		// 是否为 TemplateObject 派生类
		boolean isTmplObjDriveClazz = this.isTemplateObjectDriveClass(
			tmpl, fieldList);
		
		if (!isTmplObjDriveClazz) {
			// 错误信息
			String err = "%s not extends from TemplateObject";
			// 抛出异常
			throw new RuntimeException(
				String.format(err, tmpl.getClass().getName()));
		} 

		// 回写多语言文本字段
		this.rewriteObjectLangText(tmpl, fieldList, langMap);
	}

	/**
	 * 回写集合字段多语言文本
	 * 
	 * @param tmpl
	 * @param listField
	 * @param langMap
	 */
	private void rewriteListFieldLangText(
		Object obj, 
		Field listField, 
		Map<Integer, String> langMap) {

		try {
			// 获取列表值
			List<?> listValue = (List<?>)listField.get(obj);

			if (listValue != null) {
				for (Object value : listValue) {
					// 获取字段列表
					List<Field> fieldList = Arrays.asList(
						value.getClass().getDeclaredFields());
					
					// 回写多语言文本字段
					this.rewriteObjectLangText(value, fieldList, langMap);
				}
			}
		} catch (Exception ex) {
			// 抛出异常
			throw new RuntimeException(ex);
		}
	}

	/**
	 * 回写多语言文本字段
	 * 
	 * @param obj
	 * @param objFieldList
	 * @param langMap
	 */
	private void rewriteObjectLangText(
		Object obj, 
		List<Field> objFieldList, 
		Map<Integer, String> langMap) {

		Field currField;
		Field langTextField;

		for (int i = 0; i < objFieldList.size(); i++) {
			// 获取当前字段
			currField = objFieldList.get(i);
	
			if (currField.getType().equals(List.class)) {
				// 设置字段为可访问
				currField.setAccessible(true);
				// 回写集合字段
				this.rewriteListFieldLangText(
					obj, 
					currField, 
					langMap);
			} else if (isLangIdField(currField)) {
				// 多语言 Id 字段与多语言文本字段一定是先后出现的, 
				// 即, 如果 Fields[i] 是多语言 Id 字段, 
				// 那么 Fields[i + 1] 一定是多语言文本字段!
				// 所以, 必须保证配置文件符合这一要求
				// 
				langTextField = objFieldList.get(i + 1);
	
				// 验证多语言字段!
				// 验证有误, 会抛出异常并终止程序!
				this.validateLangFields(currField, langTextField);
				// 设置字段为可访问
				currField.setAccessible(true);
				langTextField.setAccessible(true);
				
				try {
					// 回写多语言文本
					this.rewriteLangText(obj, currField, langTextField, langMap);
				} catch (Exception ex) {
					// 抛出运行时异常
					throw new RuntimeException(ex);
				}
			}
		}
	}

	/**
	 * 判断是 TemplateObject 类的派生类 ?
	 * <p>
	 * 如果父类不是 TemplateObject, 那么遍历并判断其父类! 
	 * 在遍历的过程中收集类的字段列表
	 * </p>
	 * 
	 * @param tmpl
	 * @param returnFieldList 
	 * @return 
	 * 
	 */
	private boolean isTemplateObjectDriveClass(
		TemplateObject tmpl, 
		List<Field> returnFieldList) {

		// 获取类对象
		Class<?> currClazz = tmpl.getClass();

		while (currClazz != null) {
			// 收集类字段列表
			returnFieldList.addAll(
				Arrays.asList(currClazz.getDeclaredFields()));

			if (currClazz.equals(TemplateObject.class)) {
				// 如果父类是 TemplateObject, 
				// 则返回 true, 
				// 同时保留 returnFieldList!
				return true;
			} else {
				// 转到父类
				currClazz = currClazz.getSuperclass();
			}
		}

		// 如果不是从 TemplateObject 类派生而来, 
		// 则返回 false, 
		// 同时清除  returnFieldList!
		returnFieldList.clear();
		return false;
	}

	/**
	 * 回写多语言文本
	 * 
	 * <ol>
	 * <li>首先通过 langIdField 读取 tmpl 中的多语言 Id;</li> 
	 * <li>之后再根据多语言 Id 从 langMap 中读取多语言文本;</li> 
	 * <li>最后通过 langTextField 回写 tmpl 中的多语言文本;</li>
	 * </ol>
	 * 
	 * @param obj
	 * @param langIdField
	 * @param langTextField
	 * @param langMap
	 * @throws  
	 * @throws Exception 
	 */
	private void rewriteLangText(
		Object obj, 
		Field langIdField, 
		Field langTextField, 
		Map<Integer, String> langMap) throws Exception {
		// 
		// 获取多语言 Id
		int langId = (Integer)langIdField.get(obj);
		// 获取多语言文本
		String langText = langMap.get(langId);

		if (langText != null && !langText.equals("")) {
			// 将多语言文本设置到字段
			langTextField.set(obj, langText);
		}
	}

	/**
	 * 是否为多语言 Id 字段
	 * 
	 * @param field
	 * @return
	 */
	private static boolean isLangIdField(Field field) {
		 return field.getName().matches(LANG_ID_FIELD_NAME);
	}

	/**
	 * 验证多语言字段
	 * 
	 * <ol>
	 * <li>多语言 Id 字段与多语言文本字段必须成对出现;</li>
	 * <li>多语言 Id 字段先出现, 多语言文本字段紧随其后;</li>
	 * <li>多语言文本字段必为 String 类型;</li>
	 * </ol>
	 * 
	 * @param langIdField
	 * @param langTextField
	 * @throws RuntimeException 
	 * 
	 */
	private void validateLangFields(Field langIdField, Field langTextField) {
		// 获取字段名称
		String langIdFieldName = langIdField.getName();
		String langTextFieldName = langTextField.getName();

		if (!langIdFieldName.startsWith(langTextFieldName)) {
			// 多语言 Id 字段与多语言文本字段必须成对出现!
			// 例如: 多语言 Id 字段名称为 "armDescLangId", 
			// 那么多语言文本名称必为 "armDesc"!
			// 
			// 定义错误文本
			String err = "%s and %s not paired!";
			// 抛出运行时异常
			throw new RuntimeException(
				String.format(err, langIdFieldName, langTextFieldName));
		}

		if (langTextField.getType() != String.class) {
			// 多语言文本字段必为 String 类型
			// 
			// 定义错误文本
			String err = "%s not String type!";
			// 抛出运行时异常
			throw new RuntimeException(
				String.format(err, langTextFieldName));
		}
	}

	/**
	 * 记录日志信息
	 * 
	 * @param msg
	 */
	private static void logInfo(String msg) {
		if (Loggers.gameLogger.isInfoEnabled()) {
			Loggers.gameLogger.info(msg);
		}
	}

	/**
	 * 记录异常信息
	 * 
	 * @param msg
	 */
	private static void logError(String msg) {
		if (Loggers.gameLogger.isErrorEnabled()) {
			Loggers.gameLogger.error(msg);
		}
	}
}
