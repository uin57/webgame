package com.pwrd.war.gameserver.item.template;

import com.pwrd.war.core.annotation.ExcelRowBinding;
import com.pwrd.war.core.template.TemplateObject;
import com.pwrd.war.common.exception.TemplateConfigException;
import com.pwrd.war.core.annotation.ExcelCellBinding;

/**
 * 装备等级系数
 * 
 * @author CodeGenerator, don't modify this file please.
 */

@ExcelRowBinding
public abstract class EquipEnhanceLevelRateVO extends TemplateObject {

	/** 装备等级 */
	@ExcelCellBinding(offset = 1)
	protected int equipLevel;

	/** 等级系数 */
	@ExcelCellBinding(offset = 2)
	protected double levelRate;

	/** 价格系数 */
	@ExcelCellBinding(offset = 3)
	protected double currencyRate;


	public int getEquipLevel() {
		return this.equipLevel;
	}

	public void setEquipLevel(int equipLevel) {
		if (equipLevel == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[装备等级]equipLevel不可以为0");
		}
		this.equipLevel = equipLevel;
	}
	
	public double getLevelRate() {
		return this.levelRate;
	}

	public void setLevelRate(double levelRate) {
		if (levelRate == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[等级系数]levelRate不可以为0");
		}
		this.levelRate = levelRate;
	}
	
	public double getCurrencyRate() {
		return this.currencyRate;
	}

	public void setCurrencyRate(double currencyRate) {
		if (currencyRate == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[价格系数]currencyRate不可以为0");
		}
		this.currencyRate = currencyRate;
	}
	

	@Override
	public String toString() {
		return "EquipEnhanceLevelRateVO[equipLevel=" + equipLevel + ",levelRate=" + levelRate + ",currencyRate=" + currencyRate + ",]";

	}
}