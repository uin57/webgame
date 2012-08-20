package com.pwrd.war.gameserver.form.template;

import com.pwrd.war.core.annotation.ExcelRowBinding;
import com.pwrd.war.core.template.TemplateObject;
import com.pwrd.war.common.exception.TemplateConfigException;
import com.pwrd.war.core.annotation.ExcelCellBinding;

/**
 * 阵型模板
 * 
 * @author CodeGenerator, don't modify this file please.
 */

@ExcelRowBinding
public abstract class FormSnTemplateVO extends TemplateObject {

	/** 位置序号 */
	@ExcelCellBinding(offset = 1)
	protected int position;

	/** 攻击变化 */
	@ExcelCellBinding(offset = 2)
	protected int atkRate;

	/** 防御变化 */
	@ExcelCellBinding(offset = 3)
	protected int defRate;

	/** 血量变化 */
	@ExcelCellBinding(offset = 4)
	protected int hpRate;

	/** 速度变化值 */
	@ExcelCellBinding(offset = 5)
	protected int spdValue;

	/** 暴击变化 */
	@ExcelCellBinding(offset = 6)
	protected int criRate;

	/** 闪避变化 */
	@ExcelCellBinding(offset = 7)
	protected int dodgeRate;

	/** 连击变化 */
	@ExcelCellBinding(offset = 8)
	protected int comboRate;

	/** 伤害加深率 */
	@ExcelCellBinding(offset = 9)
	protected int dmgRate;

	/** 出手回合 */
	@ExcelCellBinding(offset = 10)
	protected int order;


	public int getPosition() {
		return this.position;
	}

	public void setPosition(int position) {
		if (position == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[位置序号]position不可以为0");
		}
		this.position = position;
	}
	
	public int getAtkRate() {
		return this.atkRate;
	}

	public void setAtkRate(int atkRate) {
		this.atkRate = atkRate;
	}
	
	public int getDefRate() {
		return this.defRate;
	}

	public void setDefRate(int defRate) {
		this.defRate = defRate;
	}
	
	public int getHpRate() {
		return this.hpRate;
	}

	public void setHpRate(int hpRate) {
		this.hpRate = hpRate;
	}
	
	public int getSpdValue() {
		return this.spdValue;
	}

	public void setSpdValue(int spdValue) {
		this.spdValue = spdValue;
	}
	
	public int getCriRate() {
		return this.criRate;
	}

	public void setCriRate(int criRate) {
		this.criRate = criRate;
	}
	
	public int getDodgeRate() {
		return this.dodgeRate;
	}

	public void setDodgeRate(int dodgeRate) {
		this.dodgeRate = dodgeRate;
	}
	
	public int getComboRate() {
		return this.comboRate;
	}

	public void setComboRate(int comboRate) {
		this.comboRate = comboRate;
	}
	
	public int getDmgRate() {
		return this.dmgRate;
	}

	public void setDmgRate(int dmgRate) {
		this.dmgRate = dmgRate;
	}
	
	public int getOrder() {
		return this.order;
	}

	public void setOrder(int order) {
		this.order = order;
	}
	

	@Override
	public String toString() {
		return "FormSnTemplateVO[position=" + position + ",atkRate=" + atkRate + ",defRate=" + defRate + ",hpRate=" + hpRate + ",spdValue=" + spdValue + ",criRate=" + criRate + ",dodgeRate=" + dodgeRate + ",comboRate=" + comboRate + ",dmgRate=" + dmgRate + ",order=" + order + ",]";

	}
}