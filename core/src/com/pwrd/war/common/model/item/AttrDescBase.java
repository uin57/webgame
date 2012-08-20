package com.pwrd.war.common.model.item;

/**
 * 
 * 装备主属性信息
 * 
 * 
 */
public class AttrDescBase {
	/** 属性key */
	short key;
	/** 属性主value描述 */
	String mainValue;
	/** 强化描述装备强化可能增强基础属性 */
	String enhanceDesc;

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

	public String getEnhanceDesc() {
		return enhanceDesc;
	}

	public void setEnhanceDesc(String enhanceDesc) {
		this.enhanceDesc = enhanceDesc;
	}

	@Override
	public String toString() {
		return "AttrDescBase [key=" + key + ", mainValue=" + mainValue
				+ ", enhanceDesc=" + enhanceDesc + "]";
	}
}
