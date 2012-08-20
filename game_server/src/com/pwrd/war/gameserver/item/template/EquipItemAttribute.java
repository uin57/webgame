package com.pwrd.war.gameserver.item.template;

import com.pwrd.war.core.annotation.ExcelRowBinding;
import com.pwrd.war.core.template.BeanFieldNumber;

/**
 * 
 * 装备的一个属性
 * 
 */
@ExcelRowBinding
public class EquipItemAttribute {

	/** 属性key */
	@BeanFieldNumber(number = 1)
	private String propKey;
	/** 属性值 */
	@BeanFieldNumber(number = 2)
	private int propValue;
	/** 每级增加 */
	@BeanFieldNumber(number = 3)
	private int enhancePerLevel;
	
	public EquipItemAttribute() {
		super();
	}

	public EquipItemAttribute(String propKey, int propValue) {
		super();
		this.propKey = propKey;
		this.propValue = propValue;
	}

	public String getPropKey() {
		return propKey;
	}

	public void setPropKey(String propKey) {
		this.propKey = propKey;
	}

	public int getPropValue() {
		return propValue;
	}

	public void setPropValue(int propValue) {
		this.propValue = propValue;
	}
	
	public int getEnhancePerLevel() {
		return enhancePerLevel;
	}

	public void setEnhancePerLevel(int enhancePerLevel) {
		this.enhancePerLevel = enhancePerLevel;
	}

	@Override
	public String toString() {
		return "EquipItemAttribute [propKey=" + propKey + ", propValue=" + propValue + ", enhancePerLevel=" + enhancePerLevel + "]";
	}

}
