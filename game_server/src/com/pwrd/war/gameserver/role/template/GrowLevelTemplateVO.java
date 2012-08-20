package com.pwrd.war.gameserver.role.template;

import com.pwrd.war.core.annotation.ExcelRowBinding;
import com.pwrd.war.core.template.TemplateObject;
import com.pwrd.war.common.exception.TemplateConfigException;
import com.pwrd.war.core.annotation.ExcelCellBinding;

/**
 * 等级对应属性定值
 * 
 * @author CodeGenerator, don't modify this file please.
 */

@ExcelRowBinding
public abstract class GrowLevelTemplateVO extends TemplateObject {

	/** �ȼ� */
	@ExcelCellBinding(offset = 1)
	protected Integer level;

	/** ���� */
	@ExcelCellBinding(offset = 2)
	protected Double atk;

	/** ���� */
	@ExcelCellBinding(offset = 3)
	protected Double def;

	/** Ѫ�� */
	@ExcelCellBinding(offset = 4)
	protected Double maxHp;


	public Integer getLevel() {
		return this.level;
	}

	public void setLevel(Integer level) {
		if (level == null) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[�ȼ�]level不可以为空");
		}	
		this.level = level;
	}
	
	public Double getAtk() {
		return this.atk;
	}

	public void setAtk(Double atk) {
		if (atk == null) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[����]atk不可以为空");
		}	
		this.atk = atk;
	}
	
	public Double getDef() {
		return this.def;
	}

	public void setDef(Double def) {
		if (def == null) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[����]def不可以为空");
		}	
		this.def = def;
	}
	
	public Double getMaxHp() {
		return this.maxHp;
	}

	public void setMaxHp(Double maxHp) {
		if (maxHp == null) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[Ѫ��]maxHp不可以为空");
		}	
		this.maxHp = maxHp;
	}
	

	@Override
	public String toString() {
		return "GrowLevelTemplateVO[level=" + level + ",atk=" + atk + ",def=" + def + ",maxHp=" + maxHp + ",]";

	}
}