package com.pwrd.war.gameserver.item.template;

import com.pwrd.war.core.annotation.ExcelRowBinding;
import com.pwrd.war.core.template.TemplateObject;
import com.pwrd.war.core.util.StringUtils;
import com.pwrd.war.common.exception.TemplateConfigException;
import com.pwrd.war.core.annotation.ExcelCellBinding;

/**
 * 星魂模板
 * 
 * @author CodeGenerator, don't modify this file please.
 */

@ExcelRowBinding
public abstract class XinghunParaProTemplateVO extends TemplateObject {

	/** 品质，颜色 */
	@ExcelCellBinding(offset = 1)
	protected String quality;

	/** 品质代码 1红2橙3紫4蓝5绿 */
	@ExcelCellBinding(offset = 2)
	protected int qualityNum;

	/** 属性品质代码 1红2橙3紫4蓝5绿 */
	@ExcelCellBinding(offset = 3)
	protected int attributeNum;

	/** 属性品质 */
	@ExcelCellBinding(offset = 4)
	protected String attribute;

	/** 出现概率 */
	@ExcelCellBinding(offset = 5)
	protected double probability;

	/** 区间下限 */
	@ExcelCellBinding(offset = 6)
	protected double minVaule;

	/** 区间上限 */
	@ExcelCellBinding(offset = 7)
	protected double maxValue;


	public String getQuality() {
		return this.quality;
	}

	public void setQuality(String quality) {
		if (StringUtils.isEmpty(quality)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[品质，颜色]quality不可以为空");
		}
		this.quality = quality;
	}
	
	public int getQualityNum() {
		return this.qualityNum;
	}

	public void setQualityNum(int qualityNum) {
		if (qualityNum == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[品质代码 1红2橙3紫4蓝5绿]qualityNum不可以为0");
		}
		this.qualityNum = qualityNum;
	}
	
	public int getAttributeNum() {
		return this.attributeNum;
	}

	public void setAttributeNum(int attributeNum) {
		if (attributeNum == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[属性品质代码 1红2橙3紫4蓝5绿]attributeNum不可以为0");
		}
		this.attributeNum = attributeNum;
	}
	
	public String getAttribute() {
		return this.attribute;
	}

	public void setAttribute(String attribute) {
		if (StringUtils.isEmpty(attribute)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[属性品质]attribute不可以为空");
		}
		this.attribute = attribute;
	}
	
	public double getProbability() {
		return this.probability;
	}

	public void setProbability(double probability) {
		if (probability == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					6, "[出现概率]probability不可以为0");
		}
		this.probability = probability;
	}
	
	public double getMinVaule() {
		return this.minVaule;
	}

	public void setMinVaule(double minVaule) {
		if (minVaule == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					7, "[区间下限]minVaule不可以为0");
		}
		this.minVaule = minVaule;
	}
	
	public double getMaxValue() {
		return this.maxValue;
	}

	public void setMaxValue(double maxValue) {
		if (maxValue == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					8, "[区间上限]maxValue不可以为0");
		}
		this.maxValue = maxValue;
	}
	

	@Override
	public String toString() {
		return "XinghunParaProTemplateVO[quality=" + quality + ",qualityNum=" + qualityNum + ",attributeNum=" + attributeNum + ",attribute=" + attribute + ",probability=" + probability + ",minVaule=" + minVaule + ",maxValue=" + maxValue + ",]";

	}
}