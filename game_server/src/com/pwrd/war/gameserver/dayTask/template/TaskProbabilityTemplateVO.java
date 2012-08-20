package com.pwrd.war.gameserver.dayTask.template;

import com.pwrd.war.core.annotation.ExcelRowBinding;
import com.pwrd.war.core.template.TemplateObject;
import com.pwrd.war.core.util.StringUtils;
import com.pwrd.war.common.exception.TemplateConfigException;
import com.pwrd.war.core.annotation.ExcelCellBinding;

/**
 * 每日任务的任务概率模板=======
 * 
 * @author CodeGenerator, don't modify this file please.
 */

@ExcelRowBinding
public abstract class TaskProbabilityTemplateVO extends TemplateObject {

	/** 角色最低等级 */
	@ExcelCellBinding(offset = 1)
	protected int lowLevel;

	/** 角色最高等级 */
	@ExcelCellBinding(offset = 2)
	protected int highLevel;

	/** 当日奖励次数 */
	@ExcelCellBinding(offset = 3)
	protected int times;

	/** 铜钱 */
	@ExcelCellBinding(offset = 4)
	protected int copper;

	/** 阅历 */
	@ExcelCellBinding(offset = 5)
	protected int see;

	/** 道具奖励 */
	@ExcelCellBinding(offset = 6)
	protected String itemPrize;

	/** 道具奖励概率 */
	@ExcelCellBinding(offset = 7)
	protected double itemProbability;

	/** 体力奖励 */
	@ExcelCellBinding(offset = 8)
	protected int vitPrize;

	/** 体力奖励概率 */
	@ExcelCellBinding(offset = 9)
	protected double vitProbability;

	/** 声望 */
	@ExcelCellBinding(offset = 10)
	protected int popularityPrize;

	/** 声望奖励概率 */
	@ExcelCellBinding(offset = 11)
	protected double popularityProbability;

	/** 任务1 */
	@ExcelCellBinding(offset = 12)
	protected int task1;

	/** 任务2 */
	@ExcelCellBinding(offset = 13)
	protected int task2;

	/** 任务3 */
	@ExcelCellBinding(offset = 14)
	protected int task3;

	/** 任务4 */
	@ExcelCellBinding(offset = 15)
	protected int task4;

	/** 任务5 */
	@ExcelCellBinding(offset = 16)
	protected int task5;

	/** 任务6 */
	@ExcelCellBinding(offset = 17)
	protected int task6;


	public int getLowLevel() {
		return this.lowLevel;
	}

	public void setLowLevel(int lowLevel) {
		if (lowLevel == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[角色最低等级]lowLevel不可以为0");
		}
		this.lowLevel = lowLevel;
	}
	
	public int getHighLevel() {
		return this.highLevel;
	}

	public void setHighLevel(int highLevel) {
		if (highLevel == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[角色最高等级]highLevel不可以为0");
		}
		this.highLevel = highLevel;
	}
	
	public int getTimes() {
		return this.times;
	}

	public void setTimes(int times) {
		if (times == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[当日奖励次数]times不可以为0");
		}
		this.times = times;
	}
	
	public int getCopper() {
		return this.copper;
	}

	public void setCopper(int copper) {
		if (copper == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[铜钱]copper不可以为0");
		}
		this.copper = copper;
	}
	
	public int getSee() {
		return this.see;
	}

	public void setSee(int see) {
		if (see == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					6, "[阅历]see不可以为0");
		}
		this.see = see;
	}
	
	public String getItemPrize() {
		return this.itemPrize;
	}

	public void setItemPrize(String itemPrize) {
		if (StringUtils.isEmpty(itemPrize)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					7, "[道具奖励]itemPrize不可以为空");
		}
		this.itemPrize = itemPrize;
	}
	
	public double getItemProbability() {
		return this.itemProbability;
	}

	public void setItemProbability(double itemProbability) {
		if (itemProbability == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					8, "[道具奖励概率]itemProbability不可以为0");
		}
		this.itemProbability = itemProbability;
	}
	
	public int getVitPrize() {
		return this.vitPrize;
	}

	public void setVitPrize(int vitPrize) {
		this.vitPrize = vitPrize;
	}
	
	public double getVitProbability() {
		return this.vitProbability;
	}

	public void setVitProbability(double vitProbability) {
		if (vitProbability == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					10, "[体力奖励概率]vitProbability不可以为0");
		}
		this.vitProbability = vitProbability;
	}
	
	public int getPopularityPrize() {
		return this.popularityPrize;
	}

	public void setPopularityPrize(int popularityPrize) {
		this.popularityPrize = popularityPrize;
	}
	
	public double getPopularityProbability() {
		return this.popularityProbability;
	}

	public void setPopularityProbability(double popularityProbability) {
		if (popularityProbability == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					12, "[声望奖励概率]popularityProbability不可以为0");
		}
		this.popularityProbability = popularityProbability;
	}
	
	public int getTask1() {
		return this.task1;
	}

	public void setTask1(int task1) {
		this.task1 = task1;
	}
	
	public int getTask2() {
		return this.task2;
	}

	public void setTask2(int task2) {
		this.task2 = task2;
	}
	
	public int getTask3() {
		return this.task3;
	}

	public void setTask3(int task3) {
		this.task3 = task3;
	}
	
	public int getTask4() {
		return this.task4;
	}

	public void setTask4(int task4) {
		this.task4 = task4;
	}
	
	public int getTask5() {
		return this.task5;
	}

	public void setTask5(int task5) {
		this.task5 = task5;
	}
	
	public int getTask6() {
		return this.task6;
	}

	public void setTask6(int task6) {
		this.task6 = task6;
	}
	

	@Override
	public String toString() {
		return "TaskProbabilityTemplateVO[lowLevel=" + lowLevel + ",highLevel=" + highLevel + ",times=" + times + ",copper=" + copper + ",see=" + see + ",itemPrize=" + itemPrize + ",itemProbability=" + itemProbability + ",vitPrize=" + vitPrize + ",vitProbability=" + vitProbability + ",popularityPrize=" + popularityPrize + ",popularityProbability=" + popularityProbability + ",task1=" + task1 + ",task2=" + task2 + ",task3=" + task3 + ",task4=" + task4 + ",task5=" + task5 + ",task6=" + task6 + ",]";

	}
}