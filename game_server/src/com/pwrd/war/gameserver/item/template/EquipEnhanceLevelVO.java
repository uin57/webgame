package com.pwrd.war.gameserver.item.template;

import com.pwrd.war.core.annotation.ExcelRowBinding;
import com.pwrd.war.core.template.TemplateObject;
import com.pwrd.war.common.exception.TemplateConfigException;
import com.pwrd.war.core.annotation.ExcelCellBinding;

/**
 * 强化等级属性模版
 * 
 * @author CodeGenerator, don't modify this file please.
 */

@ExcelRowBinding
public abstract class EquipEnhanceLevelVO extends TemplateObject {

	/** 强化等级 */
	@ExcelCellBinding(offset = 1)
	protected int level;

	/** 名称 */
	@ExcelCellBinding(offset = 2)
	protected String name;

	/** 消耗游戏币类型 */
	@ExcelCellBinding(offset = 3)
	protected int currencyId;

	/** 增加可装备等级 */
	@ExcelCellBinding(offset = 4)
	protected int addLevel;

	/** 消耗游戏币数量（白） */
	@ExcelCellBinding(offset = 5)
	protected int whiteCurrencyNum;

	/** 失败返还游戏币数量 */
	@ExcelCellBinding(offset = 6)
	protected int whiteFailReturnCurrencyNum;

	/** 攻击增值 */
	@ExcelCellBinding(offset = 7)
	protected double addAtk;

	/** 防御增值 */
	@ExcelCellBinding(offset = 8)
	protected double addDef;

	/** 血量增值 */
	@ExcelCellBinding(offset = 9)
	protected double addHp;

	/** 绿色 */
	@ExcelCellBinding(offset = 10)
	protected int greenCurrencyNum;

	/** 失败返还游戏币数量 */
	@ExcelCellBinding(offset = 11)
	protected int greenFailReturnCurrencyNum;

	/** 绿属性品质系数 */
	@ExcelCellBinding(offset = 12)
	protected double greenRate;

	/** 蓝色 */
	@ExcelCellBinding(offset = 13)
	protected int blueCurrencyNum;

	/** 失败返还游戏币数量 */
	@ExcelCellBinding(offset = 14)
	protected int blueFailReturnCurrencyNum;

	/** 蓝属性品质系数 */
	@ExcelCellBinding(offset = 15)
	protected double blueRate;

	/** 紫色 */
	@ExcelCellBinding(offset = 16)
	protected int purpleCurrencyNum;

	/**  */
	@ExcelCellBinding(offset = 17)
	protected int purpleFailReturnCurrencyNum;

	/** 紫属性品质系数 */
	@ExcelCellBinding(offset = 18)
	protected double purpleRate;

	/** 黄色 */
	@ExcelCellBinding(offset = 19)
	protected int yellowCurrencyNum;

	/**  */
	@ExcelCellBinding(offset = 20)
	protected int yellowFailReturnCurrencyNum;

	/** 黄属性品质系数 */
	@ExcelCellBinding(offset = 21)
	protected double yellowRate;


	public int getLevel() {
		return this.level;
	}

	public void setLevel(int level) {
		if (level < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[强化等级]level的值不得小于0");
		}
		this.level = level;
	}
	
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public int getCurrencyId() {
		return this.currencyId;
	}

	public void setCurrencyId(int currencyId) {
		if (currencyId < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[消耗游戏币类型]currencyId的值不得小于0");
		}
		this.currencyId = currencyId;
	}
	
	public int getAddLevel() {
		return this.addLevel;
	}

	public void setAddLevel(int addLevel) {
		if (addLevel < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[增加可装备等级]addLevel的值不得小于0");
		}
		this.addLevel = addLevel;
	}
	
	public int getWhiteCurrencyNum() {
		return this.whiteCurrencyNum;
	}

	public void setWhiteCurrencyNum(int whiteCurrencyNum) {
		if (whiteCurrencyNum < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					6, "[消耗游戏币数量（白）]whiteCurrencyNum的值不得小于0");
		}
		this.whiteCurrencyNum = whiteCurrencyNum;
	}
	
	public int getWhiteFailReturnCurrencyNum() {
		return this.whiteFailReturnCurrencyNum;
	}

	public void setWhiteFailReturnCurrencyNum(int whiteFailReturnCurrencyNum) {
		if (whiteFailReturnCurrencyNum < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					7, "[失败返还游戏币数量]whiteFailReturnCurrencyNum的值不得小于0");
		}
		this.whiteFailReturnCurrencyNum = whiteFailReturnCurrencyNum;
	}
	
	public double getAddAtk() {
		return this.addAtk;
	}

	public void setAddAtk(double addAtk) {
		if (addAtk < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					8, "[攻击增值]addAtk的值不得小于0");
		}
		this.addAtk = addAtk;
	}
	
	public double getAddDef() {
		return this.addDef;
	}

	public void setAddDef(double addDef) {
		if (addDef < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					9, "[防御增值]addDef的值不得小于0");
		}
		this.addDef = addDef;
	}
	
	public double getAddHp() {
		return this.addHp;
	}

	public void setAddHp(double addHp) {
		if (addHp < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					10, "[血量增值]addHp的值不得小于0");
		}
		this.addHp = addHp;
	}
	
	public int getGreenCurrencyNum() {
		return this.greenCurrencyNum;
	}

	public void setGreenCurrencyNum(int greenCurrencyNum) {
		if (greenCurrencyNum < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					11, "[绿色]greenCurrencyNum的值不得小于0");
		}
		this.greenCurrencyNum = greenCurrencyNum;
	}
	
	public int getGreenFailReturnCurrencyNum() {
		return this.greenFailReturnCurrencyNum;
	}

	public void setGreenFailReturnCurrencyNum(int greenFailReturnCurrencyNum) {
		if (greenFailReturnCurrencyNum < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					12, "[失败返还游戏币数量]greenFailReturnCurrencyNum的值不得小于0");
		}
		this.greenFailReturnCurrencyNum = greenFailReturnCurrencyNum;
	}
	
	public double getGreenRate() {
		return this.greenRate;
	}

	public void setGreenRate(double greenRate) {
		if (greenRate < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					13, "[绿属性品质系数]greenRate的值不得小于0");
		}
		this.greenRate = greenRate;
	}
	
	public int getBlueCurrencyNum() {
		return this.blueCurrencyNum;
	}

	public void setBlueCurrencyNum(int blueCurrencyNum) {
		if (blueCurrencyNum < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					14, "[蓝色]blueCurrencyNum的值不得小于0");
		}
		this.blueCurrencyNum = blueCurrencyNum;
	}
	
	public int getBlueFailReturnCurrencyNum() {
		return this.blueFailReturnCurrencyNum;
	}

	public void setBlueFailReturnCurrencyNum(int blueFailReturnCurrencyNum) {
		if (blueFailReturnCurrencyNum < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					15, "[失败返还游戏币数量]blueFailReturnCurrencyNum的值不得小于0");
		}
		this.blueFailReturnCurrencyNum = blueFailReturnCurrencyNum;
	}
	
	public double getBlueRate() {
		return this.blueRate;
	}

	public void setBlueRate(double blueRate) {
		if (blueRate < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					16, "[蓝属性品质系数]blueRate的值不得小于0");
		}
		this.blueRate = blueRate;
	}
	
	public int getPurpleCurrencyNum() {
		return this.purpleCurrencyNum;
	}

	public void setPurpleCurrencyNum(int purpleCurrencyNum) {
		if (purpleCurrencyNum < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					17, "[紫色]purpleCurrencyNum的值不得小于0");
		}
		this.purpleCurrencyNum = purpleCurrencyNum;
	}
	
	public int getPurpleFailReturnCurrencyNum() {
		return this.purpleFailReturnCurrencyNum;
	}

	public void setPurpleFailReturnCurrencyNum(int purpleFailReturnCurrencyNum) {
		if (purpleFailReturnCurrencyNum < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					18, "[]purpleFailReturnCurrencyNum的值不得小于0");
		}
		this.purpleFailReturnCurrencyNum = purpleFailReturnCurrencyNum;
	}
	
	public double getPurpleRate() {
		return this.purpleRate;
	}

	public void setPurpleRate(double purpleRate) {
		if (purpleRate < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					19, "[紫属性品质系数]purpleRate的值不得小于0");
		}
		this.purpleRate = purpleRate;
	}
	
	public int getYellowCurrencyNum() {
		return this.yellowCurrencyNum;
	}

	public void setYellowCurrencyNum(int yellowCurrencyNum) {
		if (yellowCurrencyNum < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					20, "[黄色]yellowCurrencyNum的值不得小于0");
		}
		this.yellowCurrencyNum = yellowCurrencyNum;
	}
	
	public int getYellowFailReturnCurrencyNum() {
		return this.yellowFailReturnCurrencyNum;
	}

	public void setYellowFailReturnCurrencyNum(int yellowFailReturnCurrencyNum) {
		if (yellowFailReturnCurrencyNum < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					21, "[]yellowFailReturnCurrencyNum的值不得小于0");
		}
		this.yellowFailReturnCurrencyNum = yellowFailReturnCurrencyNum;
	}
	
	public double getYellowRate() {
		return this.yellowRate;
	}

	public void setYellowRate(double yellowRate) {
		if (yellowRate < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					22, "[黄属性品质系数]yellowRate的值不得小于0");
		}
		this.yellowRate = yellowRate;
	}
	

	@Override
	public String toString() {
		return "EquipEnhanceLevelVO[level=" + level + ",name=" + name + ",currencyId=" + currencyId + ",addLevel=" + addLevel + ",whiteCurrencyNum=" + whiteCurrencyNum + ",whiteFailReturnCurrencyNum=" + whiteFailReturnCurrencyNum + ",addAtk=" + addAtk + ",addDef=" + addDef + ",addHp=" + addHp + ",greenCurrencyNum=" + greenCurrencyNum + ",greenFailReturnCurrencyNum=" + greenFailReturnCurrencyNum + ",greenRate=" + greenRate + ",blueCurrencyNum=" + blueCurrencyNum + ",blueFailReturnCurrencyNum=" + blueFailReturnCurrencyNum + ",blueRate=" + blueRate + ",purpleCurrencyNum=" + purpleCurrencyNum + ",purpleFailReturnCurrencyNum=" + purpleFailReturnCurrencyNum + ",purpleRate=" + purpleRate + ",yellowCurrencyNum=" + yellowCurrencyNum + ",yellowFailReturnCurrencyNum=" + yellowFailReturnCurrencyNum + ",yellowRate=" + yellowRate + ",]";

	}
}