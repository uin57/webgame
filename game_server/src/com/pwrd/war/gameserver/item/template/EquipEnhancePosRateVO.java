package com.pwrd.war.gameserver.item.template;

import com.pwrd.war.core.annotation.ExcelRowBinding;
import com.pwrd.war.core.template.TemplateObject;
import com.pwrd.war.core.util.StringUtils;
import com.pwrd.war.common.exception.TemplateConfigException;
import com.pwrd.war.core.annotation.ExcelCellBinding;

/**
 * 强化装备部位参数
 * 
 * @author CodeGenerator, don't modify this file please.
 */

@ExcelRowBinding
public abstract class EquipEnhancePosRateVO extends TemplateObject {

	/** 装备位置ID */
	@ExcelCellBinding(offset = 1)
	protected int positionId;

	/** 装备等级 */
	@ExcelCellBinding(offset = 2)
	protected int lelve;

	/** 装备类型名称 */
	@ExcelCellBinding(offset = 3)
	protected String positionName;

	/** 攻击系数 */
	@ExcelCellBinding(offset = 4)
	protected double atkRate;

	/** 防御系数 */
	@ExcelCellBinding(offset = 5)
	protected double defRate;

	/** 生命系数 */
	@ExcelCellBinding(offset = 6)
	protected double hpRate;


	public int getPositionId() {
		return this.positionId;
	}

	public void setPositionId(int positionId) {
		if (positionId == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[装备位置ID]positionId不可以为0");
		}
		this.positionId = positionId;
	}
	
	public int getLelve() {
		return this.lelve;
	}

	public void setLelve(int lelve) {
		if (lelve == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[装备等级]lelve不可以为0");
		}
		this.lelve = lelve;
	}
	
	public String getPositionName() {
		return this.positionName;
	}

	public void setPositionName(String positionName) {
		if (StringUtils.isEmpty(positionName)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[装备类型名称]positionName不可以为空");
		}
		this.positionName = positionName;
	}
	
	public double getAtkRate() {
		return this.atkRate;
	}

	public void setAtkRate(double atkRate) {
		this.atkRate = atkRate;
	}
	
	public double getDefRate() {
		return this.defRate;
	}

	public void setDefRate(double defRate) {
		this.defRate = defRate;
	}
	
	public double getHpRate() {
		return this.hpRate;
	}

	public void setHpRate(double hpRate) {
		this.hpRate = hpRate;
	}
	

	@Override
	public String toString() {
		return "EquipEnhancePosRateVO[positionId=" + positionId + ",lelve=" + lelve + ",positionName=" + positionName + ",atkRate=" + atkRate + ",defRate=" + defRate + ",hpRate=" + hpRate + ",]";

	}
}