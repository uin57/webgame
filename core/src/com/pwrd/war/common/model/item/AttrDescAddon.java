package com.pwrd.war.common.model.item;

/**
 * 
 * 附加属性描述数据结构<br/>
 * 例：敏捷 +3
 * 
 * 
 */
public class AttrDescAddon {
	/** 属性key */
	short key;
	/** 主数值 */
	String mainValue;

	public short getKey() {
		return key;
	}

	public void setKey(short key) {
		this.key = key;
	}

	public String getMainValue() {
		return mainValue;
	}

	public void setMainValue(String mainValue) {
		this.mainValue = mainValue;
	}

	@Override
	public String toString() {
		return "AddonAttrDesc [key=" + key + ", mainValue=" + mainValue + "]";
	}

}
