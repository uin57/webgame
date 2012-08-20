package com.pwrd.war.gameserver.role.template;

import com.pwrd.war.core.annotation.ExcelRowBinding;
import com.pwrd.war.core.template.TemplateObject;
import com.pwrd.war.common.exception.TemplateConfigException;
import com.pwrd.war.core.annotation.ExcelCellBinding;

/**
 * 人物属性系数信息
 * 
 * @author CodeGenerator, don't modify this file please.
 */

@ExcelRowBinding
public abstract class RolePropertyFactorTemplateVO extends TemplateObject {

	/** 职业id */
	@ExcelCellBinding(offset = 1)
	protected int vocation;

	/** 职业特定速度值 */
	@ExcelCellBinding(offset = 2)
	protected int baseSpeed;

	/** 攻击系数 */
	@ExcelCellBinding(offset = 3)
	protected double atkVocationFactor;

	/** 防御系数 */
	@ExcelCellBinding(offset = 4)
	protected double defVocationFactor;

	/** 血量系数 */
	@ExcelCellBinding(offset = 5)
	protected double hpVocationFactor;

	/** 攻击属性A 1.1 */
	@ExcelCellBinding(offset = 6)
	protected double atkLevelFactorA;

	/** 攻击属性B 1.034 */
	@ExcelCellBinding(offset = 7)
	protected double atkLevelFactorB;

	/** 防御属性A 0.66 */
	@ExcelCellBinding(offset = 8)
	protected double defLevelFactorA;

	/** 防御属性B 1.034 */
	@ExcelCellBinding(offset = 9)
	protected double defLevelFactorB;

	/** 血量属性A 2.2 */
	@ExcelCellBinding(offset = 10)
	protected double hpLevelFactorA;

	/** 血量属性B 1.034 */
	@ExcelCellBinding(offset = 11)
	protected double hpLevelFactorB;


	public int getVocation() {
		return this.vocation;
	}

	public void setVocation(int vocation) {
		if (vocation == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[职业id]vocation不可以为0");
		}
		this.vocation = vocation;
	}
	
	public int getBaseSpeed() {
		return this.baseSpeed;
	}

	public void setBaseSpeed(int baseSpeed) {
		if (baseSpeed == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[职业特定速度值]baseSpeed不可以为0");
		}
		this.baseSpeed = baseSpeed;
	}
	
	public double getAtkVocationFactor() {
		return this.atkVocationFactor;
	}

	public void setAtkVocationFactor(double atkVocationFactor) {
		if (atkVocationFactor == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[攻击系数]atkVocationFactor不可以为0");
		}
		this.atkVocationFactor = atkVocationFactor;
	}
	
	public double getDefVocationFactor() {
		return this.defVocationFactor;
	}

	public void setDefVocationFactor(double defVocationFactor) {
		if (defVocationFactor == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[防御系数]defVocationFactor不可以为0");
		}
		this.defVocationFactor = defVocationFactor;
	}
	
	public double getHpVocationFactor() {
		return this.hpVocationFactor;
	}

	public void setHpVocationFactor(double hpVocationFactor) {
		if (hpVocationFactor == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					6, "[血量系数]hpVocationFactor不可以为0");
		}
		this.hpVocationFactor = hpVocationFactor;
	}
	
	public double getAtkLevelFactorA() {
		return this.atkLevelFactorA;
	}

	public void setAtkLevelFactorA(double atkLevelFactorA) {
		if (atkLevelFactorA == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					7, "[攻击属性A 1.1]atkLevelFactorA不可以为0");
		}
		this.atkLevelFactorA = atkLevelFactorA;
	}
	
	public double getAtkLevelFactorB() {
		return this.atkLevelFactorB;
	}

	public void setAtkLevelFactorB(double atkLevelFactorB) {
		if (atkLevelFactorB == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					8, "[攻击属性B 1.034]atkLevelFactorB不可以为0");
		}
		this.atkLevelFactorB = atkLevelFactorB;
	}
	
	public double getDefLevelFactorA() {
		return this.defLevelFactorA;
	}

	public void setDefLevelFactorA(double defLevelFactorA) {
		if (defLevelFactorA == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					9, "[防御属性A 0.66]defLevelFactorA不可以为0");
		}
		this.defLevelFactorA = defLevelFactorA;
	}
	
	public double getDefLevelFactorB() {
		return this.defLevelFactorB;
	}

	public void setDefLevelFactorB(double defLevelFactorB) {
		if (defLevelFactorB == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					10, "[防御属性B 1.034]defLevelFactorB不可以为0");
		}
		this.defLevelFactorB = defLevelFactorB;
	}
	
	public double getHpLevelFactorA() {
		return this.hpLevelFactorA;
	}

	public void setHpLevelFactorA(double hpLevelFactorA) {
		if (hpLevelFactorA == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					11, "[血量属性A 2.2]hpLevelFactorA不可以为0");
		}
		this.hpLevelFactorA = hpLevelFactorA;
	}
	
	public double getHpLevelFactorB() {
		return this.hpLevelFactorB;
	}

	public void setHpLevelFactorB(double hpLevelFactorB) {
		if (hpLevelFactorB == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					12, "[血量属性B 1.034]hpLevelFactorB不可以为0");
		}
		this.hpLevelFactorB = hpLevelFactorB;
	}
	

	@Override
	public String toString() {
		return "RolePropertyFactorTemplateVO[vocation=" + vocation + ",baseSpeed=" + baseSpeed + ",atkVocationFactor=" + atkVocationFactor + ",defVocationFactor=" + defVocationFactor + ",hpVocationFactor=" + hpVocationFactor + ",atkLevelFactorA=" + atkLevelFactorA + ",atkLevelFactorB=" + atkLevelFactorB + ",defLevelFactorA=" + defLevelFactorA + ",defLevelFactorB=" + defLevelFactorB + ",hpLevelFactorA=" + hpLevelFactorA + ",hpLevelFactorB=" + hpLevelFactorB + ",]";

	}
}