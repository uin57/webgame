package com.pwrd.war.tools.properties;

/**
 * 属性生成对象
 * 
 * 
 */
public class RolePropertiesObject {
	/** 名称前缀 */
	private String prefix;
	/** 名称 */
	private String key;
	/** 值 */
	private int value;
	/** 注释 */
	private String comment;

	public String getPrefix() {
		return this.prefix;
	}

	public void setPrefix(String value) {
		this.prefix = value;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
}
