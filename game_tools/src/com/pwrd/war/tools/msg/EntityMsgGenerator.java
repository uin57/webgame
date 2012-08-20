package com.pwrd.war.tools.msg;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

/**
 * Model内容解析
 * 
 * 
 */
public class EntityMsgGenerator {

	/** 消息格式 message className{ */
	private static final Pattern MESSAGE_HEADER = Pattern.compile("message\\s+(.+)\\s*\\{");
	/** 消息类型 */
	private static final Pattern TYPE = Pattern.compile("type\\s*=\\s*(\\d+)\\s*;");
	/** 字段格式 type fieldName = sequence */
	private static final Pattern MESSAGE_FIELD = Pattern.compile("([^\\s]+)\\s([^\\s]+)\\s*=\\s*(\\d+);");

	public static void main(String[] args) throws IOException {
		Properties _vp = new Properties();
		_vp.put("resource.loader", "file");
		_vp.put("file.resource.loader.path", "config/entity/codetemplate");
		try {
			Velocity.init(_vp);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		String _packageName = "com.pwrd.war.db.model.msg";
		String _source = "../game_db/src";
		File _msgDir = new File("config/entity/msg");
		File[] _files = _msgDir.listFiles(new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				if (pathname.isFile() && pathname.getName().endsWith(".txt")) {
					return true;
				}
				return false;
			}
		});

		List<Type> _types = new ArrayList<Type>();
		for (File _config : _files) {
			generateMessageSource(_config, _packageName, _source, _types);
		}
		generateMessageTypeSource(_packageName, _source, _types);
		System.out.println("生成完毕");
	}

	private static void generateMessageSource(File msgConfig, String packageName, String sourceDir, List<Type> types)
			throws FileNotFoundException, IOException, UnsupportedEncodingException {
		BufferedReader _reader = new BufferedReader(new InputStreamReader(new FileInputStream(msgConfig)));
		String _line = null;
		String _entityClassName = null;
		String _type = null;

		//解析消息定义的名称和类型
		while ((_line = _reader.readLine()) != null) {
			_line = _line.trim();
			if (_line.length() == 0) {
				continue;
			}
			do {
				Matcher _matcher = MESSAGE_HEADER.matcher(_line);
				if (_matcher.matches()) {
					_entityClassName = _matcher.group(1);
					break;
				}
				_matcher = TYPE.matcher(_line);
				if (_matcher.matches()) {
					_type = _matcher.group(1);
					break;
				}
			} while (false);
			if (_type != null && _entityClassName != null) {
				break;
			}
		}
		if (_entityClassName == null || _entityClassName.length() == 0) {
			throw new IllegalArgumentException("Bad message format,no Entity Class name");
		}
		if (_type == null || _type.length() == 0) {
			throw new IllegalArgumentException("Bad message format,no message type");
		}

		//解析消息定义的字段
		List<Field> _fields = new ArrayList<Field>();
		while ((_line = _reader.readLine()) != null) {
			_line = _line.trim();
			if (_line.length() == 0) {
				continue;
			}
			Matcher _matcher = MESSAGE_FIELD.matcher(_line);
			if (_matcher.matches()) {
				Field _f = new Field(_matcher.group(2), _matcher.group(1), Short.parseShort(_matcher.group(3)));
				_fields.add(_f);
			}
		}
		_reader.close();

		String _className = _entityClassName.substring(_entityClassName.lastIndexOf(".") + 1) + "Msg";

		Type _typeObj = new Type(_className, Short.parseShort(_type));
		types.add(_typeObj);

		//生成消息的Java源代码
		VelocityContext _context = new VelocityContext();
		_context.put("packageName", packageName);
		_context.put("className", _className);
		_context.put("typeName", _typeObj.name);
		_context.put("entityType", _entityClassName);
		_context.put("generator", EntityMsgGenerator.class.getName());
		_context.put("date", new Date());

		VelocityContext _readcontext = new VelocityContext();
		_readcontext.put("fields", _fields);
		StringWriter _readWriter = new StringWriter();
		try {
			Velocity.mergeTemplate("EntityMessageRead.template", "UTF-8", _readcontext, _readWriter);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		VelocityContext _writecontext = new VelocityContext();
		_writecontext.put("fields", _fields);
		StringWriter _writeWriter = new StringWriter();
		try {
			Velocity.mergeTemplate("EntityMessageWrite.template", "UTF-8", _writecontext, _writeWriter);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		_context.put("readCaseCode", _readWriter.toString());
		_context.put("writeCode", _writeWriter.toString());

		StringWriter _fieldWriter = new StringWriter();
		try {
			Velocity.mergeTemplate("EntityMessageClassTemplate.template", "UTF-8", _context, _fieldWriter);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		File _srcDist = new File(sourceDir, packageName.replace('.', '/'));
		if (!_srcDist.exists()) {
			if (!_srcDist.mkdirs()) {
				throw new RuntimeException("Can't create dir " + _srcDist);
			}
		}
		Writer _fileWriter = new OutputStreamWriter(new FileOutputStream(new File(_srcDist, _className + ".java")),
				"UTF-8");
		_fileWriter.write(_fieldWriter.toString());
		_fileWriter.close();
	}

	private static void generateMessageTypeSource(String packageName, String sourceDir, List<Type> types)
			throws FileNotFoundException, IOException, UnsupportedEncodingException {

		//生成消息的Java源代码
		VelocityContext _context = new VelocityContext();
		_context.put("packageName", packageName);
		_context.put("generator", EntityMsgGenerator.class.getName());
		_context.put("date", new Date());
		_context.put("types", types);
		StringWriter _readWriter = new StringWriter();
		try {
			Velocity.mergeTemplate("EntityMessageType.template", "UTF-8", _context, _readWriter);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		File _srcDist = new File(sourceDir, packageName.replace('.', '/'));
		if (!_srcDist.exists()) {
			if (!_srcDist.mkdirs()) {
				throw new RuntimeException("Can't create dir " + _srcDist);
			}
		}
		Writer _fileWriter = new OutputStreamWriter(new FileOutputStream(new File(_srcDist, "EntityMessageType.java")),
				"UTF-8");
		_fileWriter.write(_readWriter.toString());
		_fileWriter.close();
	}

	public static class Field {
		public final String name;
		public final String type;
		public final int sequence;

		private static final Map<String, String> _typeMap = new HashMap<String, String>();

		static {
			//int
			_typeMap.put("int", "Int");
			_typeMap.put("java.lang.Integer", "Int");

			//byte
			_typeMap.put("byte", "Byte");
			_typeMap.put("java.lang.Byte", "Byte");

			//short
			_typeMap.put("short", "Short");
			_typeMap.put("java.lang.Short", "Short");

			//long
			_typeMap.put("long", "Long");
			_typeMap.put("java.lang.Long", "Long");

			//float
			_typeMap.put("float", "Float");
			_typeMap.put("java.lang.Float", "Float");

			//double
			_typeMap.put("double", "Double");
			_typeMap.put("java.lang.Double", "Double");

			//String
			_typeMap.put("String", "String");
			_typeMap.put("java.lang.String", "String");

			//byte[]
			_typeMap.put("byte[]", "ByteArray");
			
		}

		public String getName() {
			return name;
		}

		public String getType() {
			return type;
		}

		public int getSequence() {
			return sequence;
		}

		public Field(String name, String type, int sequence) {
			super();
			this.name = name;
			this.type = type;
			this.sequence = sequence;
		}

		public String getReadType() {
			return _typeMap.get(type);
		}
	}

	public static class Type {
		public final String name;
		public final short value;

		public Type(String name, short value) {
			this.name = name;
			this.value = value;
		}

		public String getName() {
			return name;
		}

		public short getValue() {
			return value;
		}
	}
}
