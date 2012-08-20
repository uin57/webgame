package com.pwrd.war.gameserver.human.template;

import com.pwrd.war.common.exception.TemplateConfigException;
import com.pwrd.war.core.annotation.ExcelCellBinding;
import com.pwrd.war.core.annotation.ExcelRowBinding;
import com.pwrd.war.core.template.TemplateObject;

/**
 * 游戏功能开放配置
 * 
 * @author CodeGenerator, don't modify this file please.
 */

@ExcelRowBinding
public abstract class GameFuncTemplateVO extends TemplateObject {

	/**  建筑 Id */
	@ExcelCellBinding(offset = 1)
	protected int buildingId;

	/**  等级 */
	@ExcelCellBinding(offset = 2)
	protected int level;

	/**  功能1 */
	@ExcelCellBinding(offset = 3)
	protected int funcTypeId1;

	/**  功能2 */
	@ExcelCellBinding(offset = 4)
	protected int funcTypeId2;

	/**  功能3 */
	@ExcelCellBinding(offset = 5)
	protected int funcTypeId3;

	/**  功能4 */
	@ExcelCellBinding(offset = 6)
	protected int funcTypeId4;

	/**  功能5 */
	@ExcelCellBinding(offset = 7)
	protected int funcTypeId5;

	/**  功能6 */
	@ExcelCellBinding(offset = 8)
	protected int funcTypeId6;

	/**  功能7 */
	@ExcelCellBinding(offset = 9)
	protected int funcTypeId7;

	/**  功能8 */
	@ExcelCellBinding(offset = 10)
	protected int funcTypeId8;

	/**  功能9 */
	@ExcelCellBinding(offset = 11)
	protected int funcTypeId9;

	/**  功能10 */
	@ExcelCellBinding(offset = 12)
	protected int funcTypeId10;

	/**  说明多语言 Id */
	@ExcelCellBinding(offset = 13)
	protected int descLangId;

	/**  说明 */
	@ExcelCellBinding(offset = 14)
	protected String desc;


	public int getBuildingId() {
		return this.buildingId;
	}

	public void setBuildingId(int buildingId) {
		if (buildingId < 1) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[ 建筑 Id]buildingId的值不得小于1");
		}
		this.buildingId = buildingId;
	}
	
	public int getLevel() {
		return this.level;
	}

	public void setLevel(int level) {
		if (level < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[ 等级]level的值不得小于0");
		}
		this.level = level;
	}
	
	public int getFuncTypeId1() {
		return this.funcTypeId1;
	}

	public void setFuncTypeId1(int funcTypeId1) {
		if (funcTypeId1 < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[ 功能1]funcTypeId1的值不得小于0");
		}
		this.funcTypeId1 = funcTypeId1;
	}
	
	public int getFuncTypeId2() {
		return this.funcTypeId2;
	}

	public void setFuncTypeId2(int funcTypeId2) {
		if (funcTypeId2 < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[ 功能2]funcTypeId2的值不得小于0");
		}
		this.funcTypeId2 = funcTypeId2;
	}
	
	public int getFuncTypeId3() {
		return this.funcTypeId3;
	}

	public void setFuncTypeId3(int funcTypeId3) {
		if (funcTypeId3 < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					6, "[ 功能3]funcTypeId3的值不得小于0");
		}
		this.funcTypeId3 = funcTypeId3;
	}
	
	public int getFuncTypeId4() {
		return this.funcTypeId4;
	}

	public void setFuncTypeId4(int funcTypeId4) {
		if (funcTypeId4 < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					7, "[ 功能4]funcTypeId4的值不得小于0");
		}
		this.funcTypeId4 = funcTypeId4;
	}
	
	public int getFuncTypeId5() {
		return this.funcTypeId5;
	}

	public void setFuncTypeId5(int funcTypeId5) {
		if (funcTypeId5 < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					8, "[ 功能5]funcTypeId5的值不得小于0");
		}
		this.funcTypeId5 = funcTypeId5;
	}
	
	public int getFuncTypeId6() {
		return this.funcTypeId6;
	}

	public void setFuncTypeId6(int funcTypeId6) {
		if (funcTypeId6 < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					9, "[ 功能6]funcTypeId6的值不得小于0");
		}
		this.funcTypeId6 = funcTypeId6;
	}
	
	public int getFuncTypeId7() {
		return this.funcTypeId7;
	}

	public void setFuncTypeId7(int funcTypeId7) {
		if (funcTypeId7 < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					10, "[ 功能7]funcTypeId7的值不得小于0");
		}
		this.funcTypeId7 = funcTypeId7;
	}
	
	public int getFuncTypeId8() {
		return this.funcTypeId8;
	}

	public void setFuncTypeId8(int funcTypeId8) {
		if (funcTypeId8 < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					11, "[ 功能8]funcTypeId8的值不得小于0");
		}
		this.funcTypeId8 = funcTypeId8;
	}
	
	public int getFuncTypeId9() {
		return this.funcTypeId9;
	}

	public void setFuncTypeId9(int funcTypeId9) {
		if (funcTypeId9 < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					12, "[ 功能9]funcTypeId9的值不得小于0");
		}
		this.funcTypeId9 = funcTypeId9;
	}
	
	public int getFuncTypeId10() {
		return this.funcTypeId10;
	}

	public void setFuncTypeId10(int funcTypeId10) {
		if (funcTypeId10 < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					13, "[ 功能10]funcTypeId10的值不得小于0");
		}
		this.funcTypeId10 = funcTypeId10;
	}
	
	public int getDescLangId() {
		return this.descLangId;
	}

	public void setDescLangId(int descLangId) {
		if (descLangId < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					14, "[ 说明多语言 Id]descLangId的值不得小于0");
		}
		this.descLangId = descLangId;
	}
	
	public String getDesc() {
		return this.desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	

	@Override
	public String toString() {
		return "GameFuncTemplateVO[buildingId=" + buildingId + ",level=" + level + ",funcTypeId1=" + funcTypeId1 + ",funcTypeId2=" + funcTypeId2 + ",funcTypeId3=" + funcTypeId3 + ",funcTypeId4=" + funcTypeId4 + ",funcTypeId5=" + funcTypeId5 + ",funcTypeId6=" + funcTypeId6 + ",funcTypeId7=" + funcTypeId7 + ",funcTypeId8=" + funcTypeId8 + ",funcTypeId9=" + funcTypeId9 + ",funcTypeId10=" + funcTypeId10 + ",descLangId=" + descLangId + ",desc=" + desc + ",]";

	}
}