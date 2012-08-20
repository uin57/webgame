package com.pwrd.war.tools.msg;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.List;
import java.util.Properties;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.texen.util.FileUtil;

import com.pwrd.war.common.exception.ConfigException;
import com.pwrd.war.core.util.StringUtils;

/**
 * 生成器工具类
 * 
 * 
 */
public class GeneratorHelper {
	/**
	 * 根据消息类型生成消息类名称
	 * 
	 * @param msgType
	 * @return
	 */
	public static String generateServerClassName(String msgType) {
		StringBuilder className = new StringBuilder();
		className.append(msgType.substring(0, 2));
		String msgBody = msgType.substring(3);
		String[] subMsgBodys = msgBody.split("_");
		for (String subMsgBody : subMsgBodys) {
			className.append(subMsgBody.substring(0, 1).toUpperCase());
			className.append(subMsgBody.substring(1).toLowerCase());
		}
		// className.append("Msg");
		return className.toString();
	}

	public static String getClientClassName(String classFullName) {
		if (classFullName.contains("<")) {
			int beginIndex = classFullName.indexOf("<");
			int endIndex = classFullName.indexOf(">") + 1;
			classFullName = classFullName.substring(0, beginIndex)
					+ classFullName.substring(endIndex, classFullName.length());
		}
		if (classFullName.indexOf(".") > 0) {
			List<String> list = StringUtils
					.getListBySplit(classFullName, "\\.");
			return list.get(list.size() - 1);
		} else {
			return classFullName;
		}
	}

	/**
	 * 获得文件所属的目录名
	 * 
	 * @param fileName
	 * @return
	 */
	public static String getDirectoryName(String fileName) {
		int lastIndex = fileName.lastIndexOf("\\");
		if (lastIndex == -1) {
			return null;
		}
		String dir = fileName.substring(0, lastIndex) + "\\";
		return dir;
	}

	/**
	 * 生成消息处理方法名称
	 * 
	 * @param msgType
	 * @return
	 */
	public static String generateHandleMethodName(String msgType) {
		StringBuilder methodName = new StringBuilder("handle");
		String msgTypeBody = msgType.substring(3);
		String[] subMsgBodys = msgTypeBody.split("_");
		for (int i = 0; i < subMsgBodys.length; i++) {
			String subMsgBody = subMsgBodys[i];
			methodName.append(StringUtils.upperCaseFirstCharOnly(subMsgBody));
		}
		return methodName.toString();
	}

	public static String getBuildPath(String fileName) {
		ClassLoader classLoader = Thread.currentThread()
				.getContextClassLoader();
		URL url = classLoader.getResource(fileName);
		if (url == null) {
			throw new ConfigException("file:" + fileName + " does not exists");
		}
		return url.getPath();
	}

	public static void generate(VelocityContext context,
			String templateFileName, String outputFile) throws Exception {
		//先创建父目录
		FileUtil.mkdir(new File(outputFile).getParent());
		VelocityEngine velocityEngine = new VelocityEngine();
		Properties p = new Properties();
		p.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH, GeneratorHelper
				.getBuildPath(MessageGenerator.TEMPLATE_DIC));
		p.setProperty(Velocity.RUNTIME_LOG, "logs/velocity.log");
		p.setProperty(Velocity.ENCODING_DEFAULT, "utf-8");
		p.setProperty(Velocity.INPUT_ENCODING, "utf-8");
		p.setProperty(Velocity.OUTPUT_ENCODING, "utf-8");
		try {
			velocityEngine.init(p);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Template template = velocityEngine.getTemplate(templateFileName);
		String directoryName = getDirectoryName(outputFile);
		if (directoryName != null) {
			File file = new File(directoryName);
			if (!file.exists()) {
				file.mkdirs();
			}
		}
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(outputFile), "utf-8"));
		template.merge(context, writer);
		writer.flush();
		writer.close();
	}
}
