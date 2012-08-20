package com.pwrd.war.gameserver.human.template;

import com.pwrd.war.core.annotation.ExcelRowBinding;
import com.pwrd.war.core.template.TemplateObject;
import com.pwrd.war.common.exception.TemplateConfigException;
import com.pwrd.war.core.annotation.ExcelCellBinding;

/**
 * 修炼等级配置模版
 * 
 * @author CodeGenerator, don't modify this file please.
 */

@ExcelRowBinding
public abstract class XiulianLevelTemplateVO extends TemplateObject {

	/** 修炼境界等级 */
	@ExcelCellBinding(offset = 1)
	protected int xiulianLevel;

	/** 修炼境界名称 */
	@ExcelCellBinding(offset = 2)
	protected String xiulianName;

	/** 需要元宝 */
	@ExcelCellBinding(offset = 3)
	protected int needGold;

	/** 需要玩家等级 */
	@ExcelCellBinding(offset = 4)
	protected int needPlayerLevel;

	/** 持续时间,h */
	@ExcelCellBinding(offset = 5)
	protected int durTime;

	/** 修炼标志一 */
	@ExcelCellBinding(offset = 6)
	protected int xiulianSymbol1;

	/** 开始区间 */
	@ExcelCellBinding(offset = 7)
	protected int xiulianRangeStart1;

	/** 结束区间 */
	@ExcelCellBinding(offset = 8)
	protected int xiulianRangeEnd1;

	/** 修炼标志二 */
	@ExcelCellBinding(offset = 9)
	protected int xiulianSymbol2;

	/** 开始区间 */
	@ExcelCellBinding(offset = 10)
	protected int xiulianRangeStart2;

	/** 结束区间 */
	@ExcelCellBinding(offset = 11)
	protected int xiulianRangeEnd2;

	/** 修炼标志三 */
	@ExcelCellBinding(offset = 12)
	protected int xiulianSymbol3;

	/** 开始区间 */
	@ExcelCellBinding(offset = 13)
	protected int xiulianRangeStart3;

	/** 结束区间 */
	@ExcelCellBinding(offset = 14)
	protected int xiulianRangeEnd3;

	/** 修炼标志四 */
	@ExcelCellBinding(offset = 15)
	protected int xiulianSymbol4;

	/** 开始区间 */
	@ExcelCellBinding(offset = 16)
	protected int xiulianRangeStart4;

	/** 结束区间 */
	@ExcelCellBinding(offset = 17)
	protected int xiulianRangeEnd4;

	/** 修炼标志五 */
	@ExcelCellBinding(offset = 18)
	protected int xiulianSymbol5;

	/** 开始区间 */
	@ExcelCellBinding(offset = 19)
	protected int xiulianRangeStart5;

	/** 结束区间 */
	@ExcelCellBinding(offset = 20)
	protected int xiulianRangeEnd5;


	public int getXiulianLevel() {
		return this.xiulianLevel;
	}

	public void setXiulianLevel(int xiulianLevel) {
		if (xiulianLevel < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[修炼境界等级]xiulianLevel的值不得小于0");
		}
		this.xiulianLevel = xiulianLevel;
	}
	
	public String getXiulianName() {
		return this.xiulianName;
	}

	public void setXiulianName(String xiulianName) {
		this.xiulianName = xiulianName;
	}
	
	public int getNeedGold() {
		return this.needGold;
	}

	public void setNeedGold(int needGold) {
		if (needGold < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[需要元宝]needGold的值不得小于0");
		}
		this.needGold = needGold;
	}
	
	public int getNeedPlayerLevel() {
		return this.needPlayerLevel;
	}

	public void setNeedPlayerLevel(int needPlayerLevel) {
		if (needPlayerLevel < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[需要玩家等级]needPlayerLevel的值不得小于0");
		}
		this.needPlayerLevel = needPlayerLevel;
	}
	
	public int getDurTime() {
		return this.durTime;
	}

	public void setDurTime(int durTime) {
		if (durTime < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					6, "[持续时间,h]durTime的值不得小于0");
		}
		this.durTime = durTime;
	}
	
	public int getXiulianSymbol1() {
		return this.xiulianSymbol1;
	}

	public void setXiulianSymbol1(int xiulianSymbol1) {
		this.xiulianSymbol1 = xiulianSymbol1;
	}
	
	public int getXiulianRangeStart1() {
		return this.xiulianRangeStart1;
	}

	public void setXiulianRangeStart1(int xiulianRangeStart1) {
		if (xiulianRangeStart1 < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					8, "[开始区间]xiulianRangeStart1的值不得小于0");
		}
		this.xiulianRangeStart1 = xiulianRangeStart1;
	}
	
	public int getXiulianRangeEnd1() {
		return this.xiulianRangeEnd1;
	}

	public void setXiulianRangeEnd1(int xiulianRangeEnd1) {
		if (xiulianRangeEnd1 < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					9, "[结束区间]xiulianRangeEnd1的值不得小于0");
		}
		this.xiulianRangeEnd1 = xiulianRangeEnd1;
	}
	
	public int getXiulianSymbol2() {
		return this.xiulianSymbol2;
	}

	public void setXiulianSymbol2(int xiulianSymbol2) {
		this.xiulianSymbol2 = xiulianSymbol2;
	}
	
	public int getXiulianRangeStart2() {
		return this.xiulianRangeStart2;
	}

	public void setXiulianRangeStart2(int xiulianRangeStart2) {
		if (xiulianRangeStart2 < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					11, "[开始区间]xiulianRangeStart2的值不得小于0");
		}
		this.xiulianRangeStart2 = xiulianRangeStart2;
	}
	
	public int getXiulianRangeEnd2() {
		return this.xiulianRangeEnd2;
	}

	public void setXiulianRangeEnd2(int xiulianRangeEnd2) {
		if (xiulianRangeEnd2 < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					12, "[结束区间]xiulianRangeEnd2的值不得小于0");
		}
		this.xiulianRangeEnd2 = xiulianRangeEnd2;
	}
	
	public int getXiulianSymbol3() {
		return this.xiulianSymbol3;
	}

	public void setXiulianSymbol3(int xiulianSymbol3) {
		this.xiulianSymbol3 = xiulianSymbol3;
	}
	
	public int getXiulianRangeStart3() {
		return this.xiulianRangeStart3;
	}

	public void setXiulianRangeStart3(int xiulianRangeStart3) {
		if (xiulianRangeStart3 < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					14, "[开始区间]xiulianRangeStart3的值不得小于0");
		}
		this.xiulianRangeStart3 = xiulianRangeStart3;
	}
	
	public int getXiulianRangeEnd3() {
		return this.xiulianRangeEnd3;
	}

	public void setXiulianRangeEnd3(int xiulianRangeEnd3) {
		if (xiulianRangeEnd3 < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					15, "[结束区间]xiulianRangeEnd3的值不得小于0");
		}
		this.xiulianRangeEnd3 = xiulianRangeEnd3;
	}
	
	public int getXiulianSymbol4() {
		return this.xiulianSymbol4;
	}

	public void setXiulianSymbol4(int xiulianSymbol4) {
		this.xiulianSymbol4 = xiulianSymbol4;
	}
	
	public int getXiulianRangeStart4() {
		return this.xiulianRangeStart4;
	}

	public void setXiulianRangeStart4(int xiulianRangeStart4) {
		if (xiulianRangeStart4 < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					17, "[开始区间]xiulianRangeStart4的值不得小于0");
		}
		this.xiulianRangeStart4 = xiulianRangeStart4;
	}
	
	public int getXiulianRangeEnd4() {
		return this.xiulianRangeEnd4;
	}

	public void setXiulianRangeEnd4(int xiulianRangeEnd4) {
		if (xiulianRangeEnd4 < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					18, "[结束区间]xiulianRangeEnd4的值不得小于0");
		}
		this.xiulianRangeEnd4 = xiulianRangeEnd4;
	}
	
	public int getXiulianSymbol5() {
		return this.xiulianSymbol5;
	}

	public void setXiulianSymbol5(int xiulianSymbol5) {
		this.xiulianSymbol5 = xiulianSymbol5;
	}
	
	public int getXiulianRangeStart5() {
		return this.xiulianRangeStart5;
	}

	public void setXiulianRangeStart5(int xiulianRangeStart5) {
		if (xiulianRangeStart5 < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					20, "[开始区间]xiulianRangeStart5的值不得小于0");
		}
		this.xiulianRangeStart5 = xiulianRangeStart5;
	}
	
	public int getXiulianRangeEnd5() {
		return this.xiulianRangeEnd5;
	}

	public void setXiulianRangeEnd5(int xiulianRangeEnd5) {
		if (xiulianRangeEnd5 < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					21, "[结束区间]xiulianRangeEnd5的值不得小于0");
		}
		this.xiulianRangeEnd5 = xiulianRangeEnd5;
	}
	

	@Override
	public String toString() {
		return "XiulianLevelTemplateVO[xiulianLevel=" + xiulianLevel + ",xiulianName=" + xiulianName + ",needGold=" + needGold + ",needPlayerLevel=" + needPlayerLevel + ",durTime=" + durTime + ",xiulianSymbol1=" + xiulianSymbol1 + ",xiulianRangeStart1=" + xiulianRangeStart1 + ",xiulianRangeEnd1=" + xiulianRangeEnd1 + ",xiulianSymbol2=" + xiulianSymbol2 + ",xiulianRangeStart2=" + xiulianRangeStart2 + ",xiulianRangeEnd2=" + xiulianRangeEnd2 + ",xiulianSymbol3=" + xiulianSymbol3 + ",xiulianRangeStart3=" + xiulianRangeStart3 + ",xiulianRangeEnd3=" + xiulianRangeEnd3 + ",xiulianSymbol4=" + xiulianSymbol4 + ",xiulianRangeStart4=" + xiulianRangeStart4 + ",xiulianRangeEnd4=" + xiulianRangeEnd4 + ",xiulianSymbol5=" + xiulianSymbol5 + ",xiulianRangeStart5=" + xiulianRangeStart5 + ",xiulianRangeEnd5=" + xiulianRangeEnd5 + ",]";

	}
}