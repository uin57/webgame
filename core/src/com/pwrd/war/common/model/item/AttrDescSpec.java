package com.pwrd.war.common.model.item;

/**
 * 
 * 特殊属性信息
 * 
 */
public class AttrDescSpec {
	
	private short key;
	
	private String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public short getKey() {
		return key;
	}

	public void setKey(short key) {
		this.key = key;
	}

}
