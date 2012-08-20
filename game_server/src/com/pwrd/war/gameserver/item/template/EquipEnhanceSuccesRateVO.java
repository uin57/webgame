package com.pwrd.war.gameserver.item.template;

import com.pwrd.war.core.annotation.ExcelRowBinding;
import com.pwrd.war.core.template.TemplateObject;
import com.pwrd.war.common.exception.TemplateConfigException;
import com.pwrd.war.core.annotation.ExcelCellBinding;

/**
 * 装备强化成功率模版
 * 
 * @author CodeGenerator, don't modify this file please.
 */

@ExcelRowBinding
public abstract class EquipEnhanceSuccesRateVO extends TemplateObject {

	/** 成功率分布1 */
	@ExcelCellBinding(offset = 1)
	protected double succesRate1;

	/** 成功率出现权重1 */
	@ExcelCellBinding(offset = 2)
	protected int weaight1;

	/** $field.comment */
	@ExcelCellBinding(offset = 3)
	protected double succesRate2;

	/** $field.comment */
	@ExcelCellBinding(offset = 4)
	protected int weaight2;

	/** $field.comment */
	@ExcelCellBinding(offset = 5)
	protected double succesRate3;

	/** $field.comment */
	@ExcelCellBinding(offset = 6)
	protected int weaight3;

	/** $field.comment */
	@ExcelCellBinding(offset = 7)
	protected double succesRate4;

	/** $field.comment */
	@ExcelCellBinding(offset = 8)
	protected int weaight4;


	public double getSuccesRate1() {
		return this.succesRate1;
	}

	public void setSuccesRate1(double succesRate1) {
		if (succesRate1 < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[成功率分布1]succesRate1的值不得小于0");
		}
		this.succesRate1 = succesRate1;
	}
	
	public int getWeaight1() {
		return this.weaight1;
	}

	public void setWeaight1(int weaight1) {
		if (weaight1 < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[成功率出现权重1]weaight1的值不得小于0");
		}
		this.weaight1 = weaight1;
	}
	
	public double getSuccesRate2() {
		return this.succesRate2;
	}

	public void setSuccesRate2(double succesRate2) {
		if (succesRate2 < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[$field.comment]succesRate2的值不得小于0");
		}
		this.succesRate2 = succesRate2;
	}
	
	public int getWeaight2() {
		return this.weaight2;
	}

	public void setWeaight2(int weaight2) {
		if (weaight2 < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[$field.comment]weaight2的值不得小于0");
		}
		this.weaight2 = weaight2;
	}
	
	public double getSuccesRate3() {
		return this.succesRate3;
	}

	public void setSuccesRate3(double succesRate3) {
		if (succesRate3 < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					6, "[$field.comment]succesRate3的值不得小于0");
		}
		this.succesRate3 = succesRate3;
	}
	
	public int getWeaight3() {
		return this.weaight3;
	}

	public void setWeaight3(int weaight3) {
		if (weaight3 < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					7, "[$field.comment]weaight3的值不得小于0");
		}
		this.weaight3 = weaight3;
	}
	
	public double getSuccesRate4() {
		return this.succesRate4;
	}

	public void setSuccesRate4(double succesRate4) {
		if (succesRate4 < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					8, "[$field.comment]succesRate4的值不得小于0");
		}
		this.succesRate4 = succesRate4;
	}
	
	public int getWeaight4() {
		return this.weaight4;
	}

	public void setWeaight4(int weaight4) {
		if (weaight4 < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					9, "[$field.comment]weaight4的值不得小于0");
		}
		this.weaight4 = weaight4;
	}
	

	@Override
	public String toString() {
		return "EquipEnhanceSuccesRateVO[succesRate1=" + succesRate1 + ",weaight1=" + weaight1 + ",succesRate2=" + succesRate2 + ",weaight2=" + weaight2 + ",succesRate3=" + succesRate3 + ",weaight3=" + weaight3 + ",succesRate4=" + succesRate4 + ",weaight4=" + weaight4 + ",]";

	}
}