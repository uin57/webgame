package com.pwrd.war.gameserver.dayTask.template;

import com.pwrd.war.core.annotation.ExcelRowBinding;
import com.pwrd.war.core.template.TemplateObject;
import com.pwrd.war.core.util.StringUtils;
import com.pwrd.war.common.exception.TemplateConfigException;
import com.pwrd.war.core.annotation.ExcelCellBinding;

/**
 * 每日任务的任务库模板
 * 
 * @author CodeGenerator, don't modify this file please.
 */

@ExcelRowBinding
public abstract class DayTaskTemplateVO extends TemplateObject {

	/** 任务id */
	@ExcelCellBinding(offset = 1)
	protected String taskId;

	/** 任务名称 */
	@ExcelCellBinding(offset = 2)
	protected String taskName;

	/** 完成次数 */
	@ExcelCellBinding(offset = 3)
	protected int times;

	/** 权重 */
	@ExcelCellBinding(offset = 4)
	protected int weight;

	/** 出现等级 */
	@ExcelCellBinding(offset = 5)
	protected int level;

	/** 任务描述 */
	@ExcelCellBinding(offset = 6)
	protected String taskDesc;


	public String getTaskId() {
		return this.taskId;
	}

	public void setTaskId(String taskId) {
		if (StringUtils.isEmpty(taskId)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[任务id]taskId不可以为空");
		}
		this.taskId = taskId;
	}
	
	public String getTaskName() {
		return this.taskName;
	}

	public void setTaskName(String taskName) {
		if (StringUtils.isEmpty(taskName)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[任务名称]taskName不可以为空");
		}
		this.taskName = taskName;
	}
	
	public int getTimes() {
		return this.times;
	}

	public void setTimes(int times) {
		this.times = times;
	}
	
	public int getWeight() {
		return this.weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}
	
	public int getLevel() {
		return this.level;
	}

	public void setLevel(int level) {
		if (level == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					6, "[出现等级]level不可以为0");
		}
		this.level = level;
	}
	
	public String getTaskDesc() {
		return this.taskDesc;
	}

	public void setTaskDesc(String taskDesc) {
		if (StringUtils.isEmpty(taskDesc)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					7, "[任务描述]taskDesc不可以为空");
		}
		this.taskDesc = taskDesc;
	}
	

	@Override
	public String toString() {
		return "DayTaskTemplateVO[taskId=" + taskId + ",taskName=" + taskName + ",times=" + times + ",weight=" + weight + ",level=" + level + ",taskDesc=" + taskDesc + ",]";

	}
}