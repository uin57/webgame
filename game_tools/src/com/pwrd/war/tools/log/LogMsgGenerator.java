package com.pwrd.war.tools.log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pwrd.war.core.config.ConfigUtil;

/**
 * 日志消息生成器，包括xml配置文件和java文件
 *
 *
 */
public class LogMsgGenerator {
	private static final Logger logger = LoggerFactory.getLogger(LogMsgGenerator.class);
	/** 日志文件信息 */
	private static final Pattern LOG_FILE_INFO = Pattern.compile("([^\\s]+)\\s+([^\\s]+)\\s+([^\\s]+)");
	
	/** 消息格式 message className( */
	private static final Pattern MESSAGE_HEADER = Pattern.compile("message\\s+(.+)\\s*\\(");

	/** 字段格式 type fieldName constraint */
	private static final Pattern MESSAGE_FIELD = Pattern.compile("([^\\s]+)\\s+([^\\s]+)\\s*(.*);\\s*[//]*(.*)");

	public static void main(String[] args) throws IOException {
		Properties _vp = new Properties();
		_vp.put("file.resource.loader.path", "config/log/template");
		try {
			Velocity.init(_vp);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		LogMsgGenConfig _config = buildLogMsgGenConfig();
		String _packageName = _config.getPackageName();//导出model文件对应logserver的包名
		String _logServerDir = _config.getLogServerDir();//导出MessageType.java对应logserver的包名
		String _logServiceDir = _config.getLogServiceDir(); //导出文件对应gameserver的包名
		String _logSrcGenDir = _config.getLogSrcGenDir();//导出logs文件的根目录
		String _logResGenDir = _config.getLogResGenDir();//导出logs ibatis配置文件片段的根目录
		String _gsGenDir = _config.getGsGenDir();//导出gameserver文件根目录
		String _logConfig = _config.getLogConfig(); //自动导出消息文件的配置文件
		String _msgDir = _config.getMsgDir();//消息模板目录
		
		BufferedReader _reader = new BufferedReader(new InputStreamReader(new FileInputStream(_logConfig)));
		String _line = null;

		/* 解析日志配置文件列表 */
		List<LogConfig> _logConfigs = new ArrayList<LogConfig>();
		while ((_line = _reader.readLine()) != null) {
			_line = _line.trim();
			if (_line.length() == 0) {
				continue;
			}
			Matcher _matcher = LOG_FILE_INFO.matcher(_line);
			if (_matcher.matches()) {
				LogConfig _log = new LogConfig(_matcher.group(1), _matcher.group(2), _matcher.group(3));
				_logConfigs.add(_log);
			}
		}
		_reader.close();
		
		/* 删除导出的旧文件 */ 
		deleteOutFile(new File("out"));
		if(logger.isInfoEnabled()) {
			logger.info("Delete dir : game_tools/out/");
		}
		
		/* 导出MessageType.java */
		generateMessageTypeConfig(_logConfigs, _logServerDir, _logSrcGenDir);
		/* 导出LogMessageRecognizer.java */
		generateLogMsgRecognizer(_logConfigs, _logServerDir, _logSrcGenDir);
		
		/* 加载日志消息的共有字段 */
		Map<String, Field> sharedFieldMap = new LinkedHashMap<String, Field>();
		importSharedField(_msgDir + "LogSharedField.txt", sharedFieldMap);
		
		/* 导出日志xml配置文件和日志消息.java */
		for(LogConfig _logType : _logConfigs) {
			generateMessageConfig(_msgDir + _logType.getLogFile(), _packageName, _logSrcGenDir, _logType, sharedFieldMap);
		}
		/* 导出GameServer下的日志接口文件LogService.java */
		generateLogService(_logConfigs, _logServiceDir, _gsGenDir,sharedFieldMap);
		
		/* 导出logserver的ibatis sqlMap配置 */
		generateLogsIbatisConfig(_logConfigs, _packageName, _logResGenDir);
		
	}

	private static LogMsgGenConfig buildLogMsgGenConfig() throws IOException {
		ClassLoader _classLoader = Thread.currentThread().getContextClassLoader();
		URL _url = _classLoader.getResource("log/log_msg_gen.cfg.js");
		return ConfigUtil.buildConfig(LogMsgGenConfig.class, _url.openStream());
	}
	
	/**
	 * 生成日志消息识别器文件
	 * 
	 * @param logFileName
	 * @param logServerDir
	 * @param genDir
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws UnsupportedEncodingException
	 */
	private static void generateLogMsgRecognizer( List<LogConfig> logTypes, String logServerDir, String genDir) throws FileNotFoundException, IOException, UnsupportedEncodingException {
		VelocityContext _context = new VelocityContext();
		_context.put("generator", LogMsgGenerator.class.getName());
		_context.put("date", new Date());
		_context.put("logTypes", logTypes);
		
		StringWriter _readWriter = new StringWriter();
		try {
			Velocity.mergeTemplate("LogMsgRecognizer.vm", "UTF-8", _context, _readWriter);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		File _srcDist = new File(genDir, logServerDir.replace('.', '/'));
		if (!_srcDist.exists()) {
			if (!_srcDist.mkdirs()) {
				throw new RuntimeException("Can't create dir " + _srcDist);
			}
		}
		Writer _fileWriter = new OutputStreamWriter(new FileOutputStream(new File(_srcDist, "LogMessageRecognizer.java")), "UTF-8");
		_fileWriter.write(_readWriter.toString());
		_fileWriter.close();
		
		if(LogMsgGenerator.logger.isInfoEnabled()) {
			LogMsgGenerator.logger.info("LogMessageRecognizer.java is generated at " + _srcDist.getAbsolutePath());
		}

	}
	
	/**
	 * 生成日志消息类型文件
	 * 
	 * @param logFileName
	 * @param logServerDir
	 * @param genDir
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws UnsupportedEncodingException
	 */
	private static void generateMessageTypeConfig( List<LogConfig> logConfigs, String logServerDir, String genDir) throws FileNotFoundException, IOException, UnsupportedEncodingException {
		VelocityContext _context = new VelocityContext();
		_context.put("generator", LogMsgGenerator.class.getName());
		_context.put("date", new Date());
		_context.put("logConfigs", logConfigs);
		
		StringWriter _readWriter = new StringWriter();
		try {
			Velocity.mergeTemplate("LogMsgType.vm", "UTF-8",	_context, _readWriter);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		File _srcDist = new File(genDir, logServerDir.replace('.', '/'));
		if (!_srcDist.exists()) {
			if (!_srcDist.mkdirs()) {
				throw new RuntimeException("Can't create dir " + _srcDist);
			}
		}
		Writer _fileWriter = new OutputStreamWriter(new FileOutputStream(new File(_srcDist, "MessageType.java")), "UTF-8");
		_fileWriter.write(_readWriter.toString());
		_fileWriter.close();
		
		if(LogMsgGenerator.logger.isInfoEnabled()) {
			LogMsgGenerator.logger.info("MessageType.java is generated at " + _srcDist.getAbsolutePath());
		}
	}
	
	/**
	 * 生成GameServer下的接口文件
	 * 
	 * @param logConfigs
	 * @param serviceDir
	 * @param genDir
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws UnsupportedEncodingException
	 */
	private static void generateLogService( List<LogConfig> logConfigs, String serviceDir, String genDir,Map<String, Field> sharedFieldMap) throws FileNotFoundException, IOException, UnsupportedEncodingException {
		VelocityContext _context = new VelocityContext();
		_context.put("generator", LogMsgGenerator.class.getName());
		_context.put("date", new Date());
		_context.put("logConfigs", logConfigs);
		_context.put("sharedFields", sharedFieldMap.values());
		
		StringWriter _readWriter = new StringWriter();
		try {
			Velocity.mergeTemplate("LogService.vm", "UTF-8",	_context, _readWriter);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		File _srcDist = new File(genDir, serviceDir.replace('.', '/'));
		if (!_srcDist.exists()) {
			if (!_srcDist.mkdirs()) {
				throw new RuntimeException("Can't create dir " + _srcDist);
			}
		}
		Writer _fileWriter = new OutputStreamWriter(new FileOutputStream(new File(_srcDist, "LogService.java")), "UTF-8");
		_fileWriter.write(_readWriter.toString());
		_fileWriter.close();
		
		if(LogMsgGenerator.logger.isInfoEnabled()) {
			LogMsgGenerator.logger.info("LogService.java is generated at " + _srcDist.getAbsolutePath());
		}
	}
	
	/**
	 * 生成xml日志配置文件
	 * 
	 * @param msgConfig
	 * @param packageName
	 * @param sourceDir
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws UnsupportedEncodingException
	 */
	private static void generateMessageConfig(String msgFile, String packageName, String sourceDir, LogConfig logConfig, Map<String, Field> sharedFieldMap) throws FileNotFoundException, IOException, UnsupportedEncodingException {
		BufferedReader _reader = new BufferedReader(new InputStreamReader(new FileInputStream(msgFile)));
		String _line = null;
		String _msgName = null;

		// 解析消息定义的名称和类型
		while ((_line = _reader.readLine()) != null) {
			_line = _line.trim();
			if (_line.length() == 0) {
				continue;
			}
			do {
				Matcher _matcher = MESSAGE_HEADER.matcher(_line);
				if (_matcher.matches()) {
					_msgName = _matcher.group(1);
					break;
				}
			} while (false);
			if (_msgName != null) {
				break;
			}
		}
		if (_msgName == null || _msgName.length() == 0) {
			throw new IllegalArgumentException("Bad message format,no Entity Class name:" + _line);
		}

		// 解析消息定义的字段
		Map<String, Field> _fieldMap = new LinkedHashMap<String, Field>();
		//_fieldMap.putAll(sharedFieldMap);
		
		while ((_line = _reader.readLine()) != null) {
			_line = _line.trim();
			if (_line.length() == 0) {
				continue;
			}
			Matcher _matcher = MESSAGE_FIELD.matcher(_line);
			if (_matcher.matches()) {
				Field _f = new Field(_matcher.group(2), _matcher.group(1), _matcher.group(3), _matcher.group(4));
				if(_fieldMap.containsKey(_matcher.group(2))) {
					throw new IllegalArgumentException("Duplicate variable name '" + _matcher.group(2) + "' in " + msgFile + " !");
				}
				_fieldMap.put(_matcher.group(2), _f);
			}
		}
		_reader.close();		
		logConfig.setFields(_fieldMap.values());
		
		String _ibatisFile = _msgName.substring(_msgName.lastIndexOf(".") + 1); 
		
		_ibatisFile = _ibatisFile.substring(0, _ibatisFile.lastIndexOf("Log")).toLowerCase();

		String _logName = _msgName.substring(_msgName.lastIndexOf(".") + 1);
		_logName = _logName.substring(0,1).toLowerCase()+_logName.substring(1);
		_logName = _logName.replaceAll("([A-Z]{1})","_$1").toLowerCase();
		
		List<Field> _allFields = new ArrayList<Field>();
		_allFields.addAll(sharedFieldMap.values());
		_allFields.addAll(logConfig.getFields());

		// 生成消息的xml配置文件
		VelocityContext _context = new VelocityContext();
		_context.put("logName", _logName);
		_context.put("msgName", _msgName);
		_context.put("fields", logConfig.getFields());
		_context.put("sharedFields",sharedFieldMap.values());
		_context.put("allFields", _allFields);
		
		StringWriter _readWriter = new StringWriter();
		try {
			Velocity.mergeTemplate("LogMsgConfigure.vm", "UTF-8",	_context, _readWriter);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		File _srcDist = new File(sourceDir, packageName.replace('.', '/'));
		if (!_srcDist.exists()) {
			if (!_srcDist.mkdirs()) {
				throw new RuntimeException("Can't create dir " + _srcDist);
			}
		}
		Writer _fileWriter = new OutputStreamWriter(new FileOutputStream(new File(_srcDist, _ibatisFile+ ".xml")), "UTF-8");
		_fileWriter.write(_readWriter.toString());
		_fileWriter.close();

		if(LogMsgGenerator.logger.isInfoEnabled()) {
			LogMsgGenerator.logger.info(_logName + ".xml is generated at " + _srcDist.getAbsolutePath());
		}
		
		generateMessageSource(_msgName, packageName, sourceDir, logConfig.getFields(),sharedFieldMap);
	}

	/**
	 * 生成日志消息源文件
	 * 
	 * @param msgName
	 * @param packageName
	 * @param sourceDir
	 * @param fields
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws UnsupportedEncodingException
	 */
	private static void generateMessageSource(String msgName, String packageName, String sourceDir, List<Field> fields,Map<String, Field> sharedFieldMap) throws FileNotFoundException, IOException, UnsupportedEncodingException {
		String _className = msgName.substring(msgName.lastIndexOf(".") + 1);

		List<Field> _allFields = new ArrayList<Field>();
		_allFields.addAll(sharedFieldMap.values());
		_allFields.addAll(fields);

		// 生成消息的Java文件
		VelocityContext _context = new VelocityContext();
		_context.put("generator", LogMsgGenerator.class.getName());
		_context.put("date", new Date());
		_context.put("className", _className);
		_context.put("packageName", packageName);
		_context.put("upperClassName", _className.substring(0, _className.lastIndexOf("Log")).toUpperCase());
		_context.put("fields", fields);
		_context.put("sharedFields",sharedFieldMap.values());
		_context.put("allFields", _allFields);
		
		StringWriter _readWriter = new StringWriter();
		try {
			Velocity.mergeTemplate("LogMsgClass.vm", "UTF-8", _context, _readWriter);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		File _srcDist = new File(sourceDir, packageName.replace('.', '/'));
		if (!_srcDist.exists()) {
			if (!_srcDist.mkdirs()) {
				throw new RuntimeException("Can't create dir " + _srcDist);
			}
		}
		Writer _fileWriter = new OutputStreamWriter(new FileOutputStream(new File(_srcDist, _className + ".java")), "UTF-8");
		_fileWriter.write(_readWriter.toString());
		_fileWriter.close();
		
		if(LogMsgGenerator.logger.isInfoEnabled()) {
			LogMsgGenerator.logger.info(_className + ".java is generated at " + _srcDist.getAbsolutePath());
		}
	}

	/**
	 * 加载日志消息的共有字段
	 * 
	 * @param sharedFieldFile
	 * 			存放共有字段的文件
	 * @param sharedFieldMap
	 * 			存放共有字段的表
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws UnsupportedEncodingException
	 */
	private static void importSharedField(String sharedFieldFile, Map<String, Field> sharedFieldMap) throws FileNotFoundException, IOException, UnsupportedEncodingException {
		BufferedReader _reader = new BufferedReader(new InputStreamReader(new FileInputStream(sharedFieldFile)));
		String _line = null;
		
		while ((_line = _reader.readLine()) != null) {
			_line = _line.trim();
			if (_line.length() == 0) {
				continue;
			}
			Matcher _matcher = MESSAGE_FIELD.matcher(_line);
			if (_matcher.matches()) {
				Field _f = new Field(_matcher.group(2), _matcher.group(1), _matcher.group(3), _matcher.group(4));
				if(sharedFieldMap.containsKey(_matcher.group(2))) {
					throw new IllegalArgumentException("Duplicate variable name '" + _matcher.group(2) + "' in " + sharedFieldFile + " !");
				}
				sharedFieldMap.put(_matcher.group(2), _f);
			}
		}
		_reader.close();	
	}
	
	/**
	 * 生成Log server的配置文件sqlMap信息
	 * 
	 * @param logConfigs
	 * @param packageName
	 * @param genDir
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws UnsupportedEncodingException
	 */
	private static void generateLogsIbatisConfig( List<LogConfig> logConfigs, String packageName, String genDir) throws FileNotFoundException, IOException, UnsupportedEncodingException {
		String _modelDir = packageName.replace('.', '/');
		String _basisInfo = "<sqlMap resource=\"" + _modelDir + "/";
		String _endInfo = ".xml\" />";
		String _tableName = "";
		String _configInfo = "";
		
		File _srcDist = new File(genDir);
		if (!_srcDist.exists()) {
			if (!_srcDist.mkdirs()) {
				throw new RuntimeException("Can't create dir " + _srcDist);
			}
		}
		Writer _fileWriter = new OutputStreamWriter(new FileOutputStream(new File(_srcDist, "log_ibatis_config_section.xml")), "UTF-8");
		for(LogConfig _logConfig : logConfigs) {
			_tableName = _logConfig.getUpperLogType().toLowerCase();
			_configInfo = _basisInfo + _tableName + _endInfo;
			_fileWriter.write(_configInfo + "\n");
			if(LogMsgGenerator.logger.isInfoEnabled()) {
				LogMsgGenerator.logger.info(_configInfo);
			}
		}
			
		_fileWriter.close();
		
		if(LogMsgGenerator.logger.isInfoEnabled()) {
			LogMsgGenerator.logger.info("log_ibatis_config_section.xml is generated at " + _srcDist.getAbsolutePath());
		}
	}
	
	/**
	 * 导出新文件前，先清空之前的
	 * 
	 * @param path
	 */
	private static void deleteOutFile(File path) {
		if(!path.exists()) {
			return;
		}
		
		if(path.isFile()) {
			path.delete();
			return;
		}
		
		File[] _files = path.listFiles();
		for(File file : _files) {
			deleteOutFile(file);
		}
		path.delete();
	}
	
	/**
	 * 日志文件信息
	 * 
	 * @author <a href="mailto:yong.fang@opi-corp.com">fang yong<a>
	 *
	 */
	public static class LogConfig {
		/** 日志配置文件名 */
		private final String logFile;
		/** 日志的类型值 */
		private final String logTypeValue;
		/** 日志的注释 */
		private final String logInfo;
		
		private List<Field> fields = new ArrayList<Field>();
		
		public LogConfig(String logFile, String logTypeValue, String logInfo) {
			this.logFile = logFile;
			this.logTypeValue = logTypeValue;
			this.logInfo = logInfo;
		}

		/**
		 * 获得大写日志类型名，不包括"Log"
		 * 
		 * @return
		 */
		public String getUpperLogType() {
			return logFile.substring(0, logFile.lastIndexOf("Log.txt")).toUpperCase();
		}
		
		public String getLogFile() {
			return logFile;
		}

		/**
		 * 获得日志文件名，不带扩展名
		 * 
		 * @return
		 */
		public String getLogFileName() {
			return logFile.substring(0, logFile.lastIndexOf(".txt"));
		}
		
		public String getLogTypeValue() {
			return logTypeValue;
		}

		public String getLogInfo() {
			return logInfo;
		}

		public void setFields(Collection<Field> fields) {
			this.fields.addAll(fields);
		}

		public List<Field> getFields() {
			return fields;
		}
	}
	/**
	 * 日志消息字段
	 * 
	 * @author <a href="mailto:yong.fang@opi-corp.com">fang yong<a>
	 *
	 */
	public static class Field {
		/** 字段名 */
		private final String name;
		/** 字段类型 */
		private final String type;
		/** 字段的sql额外约束 */
		private final String constraint;
		/** 是否为该字段建立索引 */
		private final boolean isKey;
		/** 字段注释 */
		private final String fieldInfo;
		/** 数据库字段名*/
		private final String columnName;
		
		
		private static final Map<String, String> _typeMap = new HashMap<String, String>();
		private static final Map<String, String> _readFuncMap = new HashMap<String,	String>();
		private static final Map<String, String> _writeFuncMap = new HashMap<String, String>();
		static {
			// int
			_typeMap.put("int", "int(11)");
			_readFuncMap.put("int", "readInt()");
			_writeFuncMap.put("int", "writeInt");
			// long
			_typeMap.put("long", "bigint");
			_readFuncMap.put("long", "readLong()");
			_writeFuncMap.put("long", "writeLong");
			// String
			_typeMap.put("String", "varchar");
			_readFuncMap.put("String", "readString()");
			_writeFuncMap.put("String", "writeString");
			// bytes
			_typeMap.put("byte[]", "blob");
			_readFuncMap.put("byte[]", "readByteArray()");
			_writeFuncMap.put("byte[]", "writeByteArray");
		}

		public Field(String name, String type, String constraint, String fieldInfo) {
			super();
			this.name = name.trim();
			this.columnName = this.name.replaceAll("([A-Z]{1})","_$1").toLowerCase();
			
			/* 类型转换为小写, 字符串类型对应String */
			String lowerCaseType = type.trim().toLowerCase();
			if(lowerCaseType.equals("string")){
				this.type = "String";
			}
			else{
				this.type = lowerCaseType;
			}	
			
			/* 类型有效性检测 */
			if(!(this.type.equals("int") || this.type.equals("long") || this.type.equals("String")  || this.type.equals("byte[]"))) {
				throw new IllegalArgumentException("Unsupported variable type '" + this.type + "'! Notice: only 'int', 'long' and 'String' supported!");
			}
			
			this.fieldInfo = fieldInfo.trim();
			
			if(constraint.contains("key")) {
				constraint = constraint.replaceAll("key", "");
				isKey = true;
			} else {
				isKey = false;
			}
			
			if(this.type.equals("String") && (constraint.trim()).equals("")) {
				this.constraint = "256";
			}
			else{
				this.constraint = constraint.trim();
			}
		}

		/**
		 * @return the columnName
		 */
		public String getColumnName() {
			return columnName;
		}

		public String getName() {
			return name;
		}

		/**
		 * 字段名转为首字母大写
		 * 
		 * @return
		 */
		public String getUpperName() {
			char firstChar = name.charAt(0);
			return Character.toUpperCase(firstChar) + name.substring(1);
		}

		public String getType() {
			return type;
		}
		
		public String getReadType() {
			return _typeMap.get(type);
		}

		public String getReadFunc() {
			return _readFuncMap.get(type);
		}

		public String getWriteFunc() {
			return _writeFuncMap.get(type);
		}

		public String getConstraint() {
			return constraint;
		}

		public String getFieldInfo() {
			return fieldInfo;
		}
		
		public boolean getIsKey() {
			return isKey;
		}
	}
}
