package com.pwrd.war.tools.msg;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import org.apache.velocity.texen.util.FileUtil;

/**
 * 生成器工具类Java实现
 * 
 * 
 */
public class JavaGeneratorHelper {

	/**
	 * 根据消息对象生成Java文件
	 * @param msg
	 * @param outputFile
	 * @throws Exception
	 */
	public static void generateJavaFile(MessageObject msg, String outputFile) throws Exception {
		//先创建父目录
		FileUtil.mkdir(new File(outputFile).getParent());
		
		//创建文件写入流
		OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(outputFile, false), "UTF-8");
		StringBuilder builder = new StringBuilder();
		
		//写入文件包路径
		builder.append("package com.pwrd.war.gameserver.").append(msg.getModule()).append(".msg;\n\n");
		
		//写入import部分
		builder.append("import com.pwrd.war.core.msg.MessageType;\n");
		builder.append("import com.pwrd.war.gameserver.common.msg.GCMessage;\n\n");
		
		//写入类定义
		builder.append("/**\n");
		builder.append(" * ").append(msg.getComment()).append("\n");
		builder.append(" *\n");
		builder.append(" * @author JavaCodeGenerator, don't modify this file please.\n");
		builder.append(" */\n");
		builder.append("public class ").append(msg.getClassName()).append(" extends GCMessage {\n\n");
		
		//写入成员变量定义
		for (FieldObject field : msg.getFields()) {
			builder.append("	/** ").append(field.getComment()).append(" */\n");
			if (field.getList()) {
				builder.append("	private ").append(getSmallType(field.getType())).append("[] ").append(field.getSmallName()).append(";\n");
			} else {
				builder.append("	private ").append(getSmallType(field.getType())).append(" ").append(field.getSmallName()).append(";\n");
			}
		}
		
		//写入类默认构造方法
		builder.append("\n	public ").append(msg.getClassName()).append("() {\n");
		builder.append("	}\n\n");
		
		//写入带全部参数类构造方法
		if (msg.getFieldSize() > 0) {
			builder.append("\n	public ").append(msg.getClassName()).append("(");
			boolean start = true;
			for (FieldObject field : msg.getFields()) {
				if (!start) {
					builder.append(", ");
				}
				if (field.getList()) {
					builder.append(getSmallType(field.getType())).append("[] ").append(field.getSmallName());
				} else {
					builder.append(getSmallType(field.getType())).append(" ").append(field.getSmallName());
				}
				start = false;
			}
			builder.append(") {\n");
			for (FieldObject field : msg.getFields()) {
				builder.append("		this.").append(field.getSmallName()).append(" = ").append(field.getSmallName()).append(";\n");
			}
			builder.append("	}\n\n");
		}
		
		//写入read方法实现
		builder.append("	@Override\n");
		builder.append("	protected boolean readImpl() {\n");
		for (FieldObject field : msg.getFields()) {
			readField(builder, field, field.getSmallName(), false, 0);
		}
		builder.append("		return true;\n");
		builder.append("	}\n\n");
		
		//写入write方法实现
		builder.append("	@Override\n");
		builder.append("	protected boolean writeImpl() {\n");
		for (FieldObject field : msg.getFields()) {
			writeField(builder, field, "this", 0);
		}
		builder.append("		return true;\n");
		builder.append("	}\n\n");
		
		//写入getter和setter方法实现
		builder.append("	@Override\n");
		builder.append("	protected short getType() {\n");
		builder.append("		return MessageType.").append(msg.getType()).append(";\n");
		builder.append("	}\n\n");
		
		builder.append("	@Override\n");
		builder.append("	protected String getTypeName() {\n");
		builder.append("		return \"").append(msg.getType()).append("\";\n");
		builder.append("	}\n\n");
		
		for (FieldObject field : msg.getFields()) {
			if (field.getList()) {
				builder.append("	public ").append(getSmallType(field.getType())).append("[] get")
				.append(field.getBigName()).append("() {\n");
				builder.append("		return ").append(field.getSmallName()).append(";\n");
				builder.append("	}\n\n");
				
				builder.append("	public void set").append(field.getBigName()).append("(").append(getSmallType(field.getType()))
				.append("[] ").append(field.getSmallName()).append(") {\n");
				builder.append("		this.").append(field.getSmallName()).append(" = ").append(field.getSmallName()).append(";\n");
				builder.append("	}\n\n");
			} else {
				builder.append("	public ").append(getSmallType(field.getType())).append(" get").append(field.getBigName()).append("() {\n");
				builder.append("		return ").append(field.getSmallName()).append(";\n");
				builder.append("	}\n\n");
				
				builder.append("	public void set").append(field.getBigName()).append("(").append(getSmallType(field.getType()))
				.append(" ").append(field.getSmallName()).append(") {\n");
				builder.append("		this.").append(field.getSmallName()).append(" = ").append(field.getSmallName()).append(";\n");
				builder.append("	}\n\n");
			}
		}
		
		//写入类结束
		builder.append("}\n");
		
		//写入文件
		writer.write(builder.toString());
		
		//关闭写入流
		writer.flush();
		writer.close();
	}
	
	/**
	 * 解析变量read方法实现，带递归深度
	 * @param builder
	 * @param field
	 * @param parent
	 * @param parentIsSetter
	 * @param depth
	 */
	private static void readField(StringBuilder builder, FieldObject field, String parent, boolean parentIsSetter, int depth) {
		if (field.getIsNewType()) {		//复杂对象
			if (field.getList()) {		//复杂对象列表，先读取长度，再写入for循环，循环内遍历并读取对象每一个成员
				printTab(builder, depth);
				builder.append("{\n");
				printTab(builder, depth + 1);
				builder.append("short count_").append(depth + 1).append(" = readShort();\n");
				String param = printListConstructor(builder, parent, parentIsSetter, field, depth + 1);
				
				printTab(builder, depth + 1);
				builder.append("for (short itor_").append(depth + 1).append(" = 0; itor_").append(depth + 1).append(" < count_")
				.append(depth + 1).append("; itor_").append(depth + 1).append(" ++) {\n");
				
				String objParam = printObjectConstructor(builder, new StringBuilder().append(param)
						.append("[itor_").append(depth + 1).append("]").toString(), false, field, depth + 2);
				if (field.getSubFields() != null && field.getSubFields().size() > 0) {
					for (FieldObject subField : field.getSubFields()) {
						readField(builder, subField, objParam, true, depth + 2);
					}
				} else {
					System.out.println("NULL field=" + parent + "." + field.getSmallName() + ", " + field.getSubFields());
				}
				
				printTab(builder, depth + 1);
				builder.append("}\n");
				printTab(builder, depth);
				builder.append("}\n");
			} else {	//复杂对象，先写入new语句，再循环读取对象的每一个成员
				String param = printObjectConstructor(builder, parent, parentIsSetter, field, depth);
				for (FieldObject subField : field.getSubFields()) {
					readField(builder, subField, param, true, depth + 1);
				}
			}
		} else {	//常规对象
			if (field.getList()) {		//常规对象列表，先读取长度，再写入for循环读取
				printTab(builder, depth);
				builder.append("{\n");
				printTab(builder, depth + 1);
				builder.append("short count_").append(depth + 1).append(" = readShort();\n");
				String param = printListConstructor(builder, parent, parentIsSetter, field, depth + 1);
				
				printTab(builder, depth + 1);
				builder.append("for (short itor_").append(depth + 1).append(" = 0; itor_").append(depth + 1).append(" < count_")
				.append(depth + 1).append("; itor_").append(depth + 1).append(" ++) {\n");
				
				printTab(builder, depth + 2);
				printReadField(builder, new StringBuilder().append(param).append("[itor_").append(depth + 1).append("]").toString(),
						false, field);
				
				printTab(builder, depth + 1);
				builder.append("}\n");
				printTab(builder, depth);
				builder.append("}\n");
			} else {		//常规对象，直接读取类型
				printTab(builder, depth);
				printReadField(builder, parent, parentIsSetter, field);
			}
		}
	}
	
	/**
	 * 解析变量write方法实现，带递归深度
	 * @param builder
	 * @param field
	 * @param parent
	 * @param depth
	 */
	private static void writeField(StringBuilder builder, FieldObject field, String parent, int depth) {
		if (field.getList()) {	//列表对象，先输出长度
			printTab(builder, depth);
			builder.append(getSmallType(field.getType())).append("[] ").append(field.getSmallName()).append("_").append(depth)
			.append(" = ").append(parent).append(".get").append(field.getBigName()).append("();\n");
			
			printTab(builder, depth);
			builder.append("writeShort(").append(field.getSmallName()).append("_").append(depth).append(".length);\n");
			
			printTab(builder, depth);
			builder.append("for (int itor_").append(depth).append(" = 0; itor_").append(depth).append(" < ")
			.append(field.getSmallName()).append("_").append(depth).append(".length; itor_").append(depth).append(" ++) {\n");
			
			String param = new StringBuilder().append(field.getSmallName()).append("_").append(depth).append("[itor_")
					.append(depth).append("]").toString();
			if (field.getIsNewType()) {
				for (FieldObject subField : field.getSubFields()) {
					writeField(builder, subField, param, depth + 1);
				}
			} else {
				printTab(builder, depth + 1);
				printWriteField(builder, field, param);
			}
			
			printTab(builder, depth);
			builder.append("}\n");
		} else {	//普通对象，简单对象直接输出write方法
			if (field.getIsNewType()) {
				printTab(builder, depth);
				builder.append(getSmallType(field.getType())).append(" ").append(field.getSmallName()).append("_").append(depth)
				.append(" = ").append(parent).append(".get").append(field.getBigName()).append("();\n");
				String param = new StringBuilder().append(field.getSmallName()).append("_").append(depth).toString();
				
				for (FieldObject subField : field.getSubFields()) {
					writeField(builder, subField, param, depth);
				}
			} else {
				printTab(builder, depth);
				printWriteField(builder, field,
						new StringBuilder().append(parent).append(".get").append(field.getBigName()).append("()").toString());
			}
		}
	}
	
	/**
	 * 获取标准类型
	 * @param type
	 * @return
	 */
	private static String getSmallType(String type) {
		if ("Short".equals(type)) {
			return "short";
		} else if ("Integer".equals(type)) {
			return "int";
		} else if ("Boolean".equals(type)) {
			return "boolean";
		} else if ("Byte".equals(type)) {
			return "byte";
		} else if ("Long".equals(type)) {
			return "long";
		} else if ("Float".equals(type)) {
			return "float";
		} else if ("Double".equals(type)) {
			return "double";
		} else {
			return type;
		}
	}
	
	/**
	 * 写入缩进格式
	 * @param builder
	 * @param depth
	 */
	private static void printTab(StringBuilder builder, int depth) {
		for (int i = 0; i < depth + 2; i ++) {
			builder.append("	");
		}
	}
	
	/**
	 * 写入列表构造方法
	 * @param builder
	 * @param parent
	 * @param parentIsSetter
	 * @param field
	 * @param depth
	 * @return
	 */
	private static String printListConstructor(StringBuilder builder, String parent, boolean parentIsSetter, FieldObject field, int depth) {
		String param = new StringBuilder().append(field.getSmallName()).append("_").append(depth).toString();
		if (field.getType().indexOf("KeyValuePair") >= 0) {
			printTab(builder, depth);
			builder.append(field.getType()).append("[] ").append(param)
			.append(" = new com.pwrd.war.core.util.KeyValuePair.newKeyValuePairArray(count_").append(depth).append(");\n");
		} else {
			printTab(builder, depth);
			builder.append(getSmallType(field.getType())).append("[] ").append(param).append(" = new ")
			.append(getSmallType(field.getType())).append("[count_").append(depth).append("];\n");
		}
		printTab(builder, depth);
		printReadField(builder, parent, parentIsSetter, field, param);
		return param;
	}
	
	/**
	 * 写入对象构造方法
	 * @param builder
	 * @param parent
	 * @param parentIsSetter
	 * @param field
	 * @param depth
	 * @return
	 */
	private static String printObjectConstructor(StringBuilder builder, String parent, boolean parentIsSetter, FieldObject field, int depth) {
		String param = new StringBuilder().append(field.getSmallName()).append("_").append(depth).toString();
		printTab(builder, depth);
		builder.append(getSmallType(field.getType())).append(" ").append(param).append(" = new ")
		.append(getSmallType(field.getType())).append("();\n");
		printTab(builder, depth);
		printReadField(builder, parent, parentIsSetter, field, param);
		return param;
	}
	
	/**
	 * 写入变量read语句
	 * @param builder
	 * @param parent
	 * @param parentIsSetter
	 * @param field
	 */
	private static void printReadField(StringBuilder builder, String parent, boolean parentIsSetter, FieldObject field) {
		if (parentIsSetter) {
			builder.append(parent).append(".set").append(field.getBigName()).append("(read").append(field.getType()).append("());\n");
		} else {
			builder.append(parent).append(" = read").append(field.getType()).append("();\n");
		}
	}
	
	/**
	 * 写入变量read语句
	 * @param builder
	 * @param parent
	 * @param parentIsSetter
	 * @param field
	 * @param content
	 */
	private static void printReadField(StringBuilder builder, String parent, boolean parentIsSetter, FieldObject field, String content) {
		if (parentIsSetter) {
			builder.append(parent).append(".set").append(field.getBigName()).append("(").append(content).append(");\n");
		} else {
			builder.append(parent).append(" = ").append(content).append(";\n");
		}
	}
	
	/**
	 * 写入变量write语句
	 * @param builder
	 * @param field
	 * @param param
	 */
	private static void printWriteField(StringBuilder builder, FieldObject field, String param) {
		builder.append("write").append(field.getType()).append("(").append(param).append(");\n");
	}
}
