package com.pwrd.war.tools.msg;

/**
 * 常量定义
 * 
 * 
 * 
 */
public class ConstantObject {
	/** 常量类别 */
	private String name;
	/** 常量详细说明 */
	private String desc;

	public ConstantObject(String name, String desc) {
		super();
		this.name = name;
		this.desc = desc;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}
}
