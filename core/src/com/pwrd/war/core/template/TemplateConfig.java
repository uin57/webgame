package com.pwrd.war.core.template;

import java.util.Arrays;

/**
 * 
 *
 */
public class TemplateConfig {
	/** 模板的文件路径 */
	private String fileName;
	/** 该模板自定义的解析器 */
	private String parserClassName;
	/** 模板类型 */
	private Class<?>[] classes;

	public TemplateConfig(String fileName, Class<?>[] classes) {
		this.fileName = fileName;
		this.classes = classes;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileName() {
		return fileName;
	}

	public String getParserClassName() {
		return parserClassName;
	}

	public void setParserClassName(String parserClassName) {
		this.parserClassName = parserClassName;
	}

	public void setClasses(Class<?>[] classes) {
		this.classes = classes;
	}

	public Class<?>[] getClasses() {
		return classes;
	}

	@Override
	public String toString() {
		return "TemplateConfig [classes=" + Arrays.toString(classes) + ", fileName=" + fileName + "]";
	}

}
