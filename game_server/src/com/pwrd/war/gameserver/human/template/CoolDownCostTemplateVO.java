package com.pwrd.war.gameserver.human.template;

import com.pwrd.war.core.annotation.ExcelRowBinding;
import com.pwrd.war.core.template.TemplateObject;
import com.pwrd.war.common.exception.TemplateConfigException;
import com.pwrd.war.core.annotation.ExcelCellBinding;

/**
 * 冷却队列消耗配置
 * 
 * @author CodeGenerator, don't modify this file please.
 */

@ExcelRowBinding
public abstract class CoolDownCostTemplateVO extends TemplateObject {

	/** 强化类型，1装备，2属性成长 */
	@ExcelCellBinding(offset = 1)
	protected int coolType;

	/** 子类型,同一子类型扣除元宝公式一样 */
	@ExcelCellBinding(offset = 2)
	protected int subType;

	/** 单位时间，总时间/单位时间*单位消耗元宝=总消耗 */
	@ExcelCellBinding(offset = 3)
	protected int timeUnit;

	/** 单位时间元宝 */
	@ExcelCellBinding(offset = 4)
	protected int goldUnit;

	/** 等级参数。冷却时间=等级参数*目标养成等级+消耗修正系数 */
	@ExcelCellBinding(offset = 5)
	protected int levelParam;

	/** 消耗修正系数 */
	@ExcelCellBinding(offset = 6)
	protected int fixParam;

	/** 后面的队列开启需要元宝 */
	@ExcelCellBinding(offset = 7)
	protected String otherOpenGold;

	/** 是否显示在队列里 */
	@ExcelCellBinding(offset = 8)
	protected int isShowInQueue;


	public int getCoolType() {
		return this.coolType;
	}

	public void setCoolType(int coolType) {
		if (coolType == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[强化类型，1装备，2属性成长]coolType不可以为0");
		}
		this.coolType = coolType;
	}
	
	public int getSubType() {
		return this.subType;
	}

	public void setSubType(int subType) {
		if (subType == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[子类型,同一子类型扣除元宝公式一样]subType不可以为0");
		}
		this.subType = subType;
	}
	
	public int getTimeUnit() {
		return this.timeUnit;
	}

	public void setTimeUnit(int timeUnit) {
		if (timeUnit == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[单位时间，总时间/单位时间*单位消耗元宝=总消耗]timeUnit不可以为0");
		}
		this.timeUnit = timeUnit;
	}
	
	public int getGoldUnit() {
		return this.goldUnit;
	}

	public void setGoldUnit(int goldUnit) {
		if (goldUnit == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[单位时间元宝]goldUnit不可以为0");
		}
		this.goldUnit = goldUnit;
	}
	
	public int getLevelParam() {
		return this.levelParam;
	}

	public void setLevelParam(int levelParam) {
		if (levelParam < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					6, "[等级参数。冷却时间=等级参数*目标养成等级+消耗修正系数]levelParam的值不得小于0");
		}
		this.levelParam = levelParam;
	}
	
	public int getFixParam() {
		return this.fixParam;
	}

	public void setFixParam(int fixParam) {
		if (fixParam < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					7, "[消耗修正系数]fixParam的值不得小于0");
		}
		this.fixParam = fixParam;
	}
	
	public String getOtherOpenGold() {
		return this.otherOpenGold;
	}

	public void setOtherOpenGold(String otherOpenGold) {
		this.otherOpenGold = otherOpenGold;
	}
	
	public int getIsShowInQueue() {
		return this.isShowInQueue;
	}

	public void setIsShowInQueue(int isShowInQueue) {
		if (isShowInQueue < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					9, "[是否显示在队列里]isShowInQueue的值不得小于0");
		}
		this.isShowInQueue = isShowInQueue;
	}
	

	@Override
	public String toString() {
		return "CoolDownCostTemplateVO[coolType=" + coolType + ",subType=" + subType + ",timeUnit=" + timeUnit + ",goldUnit=" + goldUnit + ",levelParam=" + levelParam + ",fixParam=" + fixParam + ",otherOpenGold=" + otherOpenGold + ",isShowInQueue=" + isShowInQueue + ",]";

	}
}