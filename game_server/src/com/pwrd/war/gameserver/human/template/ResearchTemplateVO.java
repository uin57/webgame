package com.pwrd.war.gameserver.human.template;

import com.pwrd.war.core.annotation.ExcelRowBinding;
import com.pwrd.war.core.template.TemplateObject;
import com.pwrd.war.common.exception.TemplateConfigException;
import com.pwrd.war.core.annotation.ExcelCellBinding;

/**
 * 属性研究模版
 * 
 * @author CodeGenerator, don't modify this file please.
 */

@ExcelRowBinding
public abstract class ResearchTemplateVO extends TemplateObject {

	/** 等级 */
	@ExcelCellBinding(offset = 1)
	protected int level;

	/** 生命上限（强体术） */
	@ExcelCellBinding(offset = 2)
	protected int addMaxHp;

	/** 描述 */
	@ExcelCellBinding(offset = 3)
	protected String addMaxHpDesc;

	/** $field.comment */
	@ExcelCellBinding(offset = 4)
	protected int addMaxHpNeed;

	/** 	攻击（兵器冶炼） */
	@ExcelCellBinding(offset = 5)
	protected int addAtk;

	/** 描述 */
	@ExcelCellBinding(offset = 6)
	protected String addAtkDesc;

	/** $field.comment */
	@ExcelCellBinding(offset = 7)
	protected int addAtkNeed;

	/** 	防御（护甲术） */
	@ExcelCellBinding(offset = 8)
	protected int addDef;

	/** 描述 */
	@ExcelCellBinding(offset = 9)
	protected String addDefDesc;

	/** $field.comment */
	@ExcelCellBinding(offset = 10)
	protected int addDefNeed;

	/** 	近战攻击（近战学） */
	@ExcelCellBinding(offset = 11)
	protected int addShortAtk;

	/** 描述 */
	@ExcelCellBinding(offset = 12)
	protected String addShortAtkDesc;

	/** $field.comment */
	@ExcelCellBinding(offset = 13)
	protected int addShortAtkNeed;

	/** 	近战防御（重甲冶炼） */
	@ExcelCellBinding(offset = 14)
	protected int addShortDef;

	/** 描述 */
	@ExcelCellBinding(offset = 15)
	protected String addShortDefDesc;

	/** $field.comment */
	@ExcelCellBinding(offset = 16)
	protected int addShortDefNeed;

	/** 	远程攻击（弹药学） */
	@ExcelCellBinding(offset = 17)
	protected int addRemoteAtk;

	/** 描述 */
	@ExcelCellBinding(offset = 18)
	protected String addRemoteAtkDesc;

	/** $field.comment */
	@ExcelCellBinding(offset = 19)
	protected int addRemoteAtkNeed;

	/** 	远程防御（轻甲冶炼） */
	@ExcelCellBinding(offset = 20)
	protected int addRemoteDef;

	/** 描述 */
	@ExcelCellBinding(offset = 21)
	protected String addRemoteDefDesc;

	/** $field.comment */
	@ExcelCellBinding(offset = 22)
	protected int addRemoteDefNeed;


	public int getLevel() {
		return this.level;
	}

	public void setLevel(int level) {
		if (level < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[等级]level的值不得小于0");
		}
		this.level = level;
	}
	
	public int getAddMaxHp() {
		return this.addMaxHp;
	}

	public void setAddMaxHp(int addMaxHp) {
		if (addMaxHp < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[生命上限（强体术）]addMaxHp的值不得小于0");
		}
		this.addMaxHp = addMaxHp;
	}
	
	public String getAddMaxHpDesc() {
		return this.addMaxHpDesc;
	}

	public void setAddMaxHpDesc(String addMaxHpDesc) {
		this.addMaxHpDesc = addMaxHpDesc;
	}
	
	public int getAddMaxHpNeed() {
		return this.addMaxHpNeed;
	}

	public void setAddMaxHpNeed(int addMaxHpNeed) {
		if (addMaxHpNeed < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[$field.comment]addMaxHpNeed的值不得小于0");
		}
		this.addMaxHpNeed = addMaxHpNeed;
	}
	
	public int getAddAtk() {
		return this.addAtk;
	}

	public void setAddAtk(int addAtk) {
		if (addAtk < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					6, "[	攻击（兵器冶炼）]addAtk的值不得小于0");
		}
		this.addAtk = addAtk;
	}
	
	public String getAddAtkDesc() {
		return this.addAtkDesc;
	}

	public void setAddAtkDesc(String addAtkDesc) {
		this.addAtkDesc = addAtkDesc;
	}
	
	public int getAddAtkNeed() {
		return this.addAtkNeed;
	}

	public void setAddAtkNeed(int addAtkNeed) {
		if (addAtkNeed < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					8, "[$field.comment]addAtkNeed的值不得小于0");
		}
		this.addAtkNeed = addAtkNeed;
	}
	
	public int getAddDef() {
		return this.addDef;
	}

	public void setAddDef(int addDef) {
		if (addDef < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					9, "[	防御（护甲术）]addDef的值不得小于0");
		}
		this.addDef = addDef;
	}
	
	public String getAddDefDesc() {
		return this.addDefDesc;
	}

	public void setAddDefDesc(String addDefDesc) {
		this.addDefDesc = addDefDesc;
	}
	
	public int getAddDefNeed() {
		return this.addDefNeed;
	}

	public void setAddDefNeed(int addDefNeed) {
		if (addDefNeed < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					11, "[$field.comment]addDefNeed的值不得小于0");
		}
		this.addDefNeed = addDefNeed;
	}
	
	public int getAddShortAtk() {
		return this.addShortAtk;
	}

	public void setAddShortAtk(int addShortAtk) {
		if (addShortAtk < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					12, "[	近战攻击（近战学）]addShortAtk的值不得小于0");
		}
		this.addShortAtk = addShortAtk;
	}
	
	public String getAddShortAtkDesc() {
		return this.addShortAtkDesc;
	}

	public void setAddShortAtkDesc(String addShortAtkDesc) {
		this.addShortAtkDesc = addShortAtkDesc;
	}
	
	public int getAddShortAtkNeed() {
		return this.addShortAtkNeed;
	}

	public void setAddShortAtkNeed(int addShortAtkNeed) {
		if (addShortAtkNeed < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					14, "[$field.comment]addShortAtkNeed的值不得小于0");
		}
		this.addShortAtkNeed = addShortAtkNeed;
	}
	
	public int getAddShortDef() {
		return this.addShortDef;
	}

	public void setAddShortDef(int addShortDef) {
		if (addShortDef < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					15, "[	近战防御（重甲冶炼）]addShortDef的值不得小于0");
		}
		this.addShortDef = addShortDef;
	}
	
	public String getAddShortDefDesc() {
		return this.addShortDefDesc;
	}

	public void setAddShortDefDesc(String addShortDefDesc) {
		this.addShortDefDesc = addShortDefDesc;
	}
	
	public int getAddShortDefNeed() {
		return this.addShortDefNeed;
	}

	public void setAddShortDefNeed(int addShortDefNeed) {
		if (addShortDefNeed < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					17, "[$field.comment]addShortDefNeed的值不得小于0");
		}
		this.addShortDefNeed = addShortDefNeed;
	}
	
	public int getAddRemoteAtk() {
		return this.addRemoteAtk;
	}

	public void setAddRemoteAtk(int addRemoteAtk) {
		if (addRemoteAtk < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					18, "[	远程攻击（弹药学）]addRemoteAtk的值不得小于0");
		}
		this.addRemoteAtk = addRemoteAtk;
	}
	
	public String getAddRemoteAtkDesc() {
		return this.addRemoteAtkDesc;
	}

	public void setAddRemoteAtkDesc(String addRemoteAtkDesc) {
		this.addRemoteAtkDesc = addRemoteAtkDesc;
	}
	
	public int getAddRemoteAtkNeed() {
		return this.addRemoteAtkNeed;
	}

	public void setAddRemoteAtkNeed(int addRemoteAtkNeed) {
		if (addRemoteAtkNeed < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					20, "[$field.comment]addRemoteAtkNeed的值不得小于0");
		}
		this.addRemoteAtkNeed = addRemoteAtkNeed;
	}
	
	public int getAddRemoteDef() {
		return this.addRemoteDef;
	}

	public void setAddRemoteDef(int addRemoteDef) {
		if (addRemoteDef < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					21, "[	远程防御（轻甲冶炼）]addRemoteDef的值不得小于0");
		}
		this.addRemoteDef = addRemoteDef;
	}
	
	public String getAddRemoteDefDesc() {
		return this.addRemoteDefDesc;
	}

	public void setAddRemoteDefDesc(String addRemoteDefDesc) {
		this.addRemoteDefDesc = addRemoteDefDesc;
	}
	
	public int getAddRemoteDefNeed() {
		return this.addRemoteDefNeed;
	}

	public void setAddRemoteDefNeed(int addRemoteDefNeed) {
		if (addRemoteDefNeed < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					23, "[$field.comment]addRemoteDefNeed的值不得小于0");
		}
		this.addRemoteDefNeed = addRemoteDefNeed;
	}
	

	@Override
	public String toString() {
		return "ResearchTemplateVO[level=" + level + ",addMaxHp=" + addMaxHp + ",addMaxHpDesc=" + addMaxHpDesc + ",addMaxHpNeed=" + addMaxHpNeed + ",addAtk=" + addAtk + ",addAtkDesc=" + addAtkDesc + ",addAtkNeed=" + addAtkNeed + ",addDef=" + addDef + ",addDefDesc=" + addDefDesc + ",addDefNeed=" + addDefNeed + ",addShortAtk=" + addShortAtk + ",addShortAtkDesc=" + addShortAtkDesc + ",addShortAtkNeed=" + addShortAtkNeed + ",addShortDef=" + addShortDef + ",addShortDefDesc=" + addShortDefDesc + ",addShortDefNeed=" + addShortDefNeed + ",addRemoteAtk=" + addRemoteAtk + ",addRemoteAtkDesc=" + addRemoteAtkDesc + ",addRemoteAtkNeed=" + addRemoteAtkNeed + ",addRemoteDef=" + addRemoteDef + ",addRemoteDefDesc=" + addRemoteDefDesc + ",addRemoteDefNeed=" + addRemoteDefNeed + ",]";

	}
}