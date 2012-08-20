package com.pwrd.war.gameserver.buff.template;

import com.pwrd.war.core.annotation.ExcelRowBinding;
import com.pwrd.war.core.template.TemplateObject;
import com.pwrd.war.core.util.StringUtils;
import com.pwrd.war.common.exception.TemplateConfigException;
import com.pwrd.war.core.annotation.ExcelCellBinding;

/**
 * 状态模板
 * 
 * @author CodeGenerator, don't modify this file please.
 */

@ExcelRowBinding
public abstract class StatusTemplateVO extends TemplateObject {

	/**  状态id */
	@ExcelCellBinding(offset = 1)
	protected String statusSn;

	/** 等级 */
	@ExcelCellBinding(offset = 2)
	protected Integer statusLevel;

	/**  状态名称 */
	@ExcelCellBinding(offset = 3)
	protected String statusName;

	/**  状态类别 */
	@ExcelCellBinding(offset = 4)
	protected Integer statusType;

	/**  状态说明 */
	@ExcelCellBinding(offset = 5)
	protected String statusDescription;

	/**  状态特效 */
	@ExcelCellBinding(offset = 6)
	protected String statusSpecialEffect;

	/**  状态权重 */
	@ExcelCellBinding(offset = 7)
	protected Double statusWeight;

	/**  状态回合数 */
	@ExcelCellBinding(offset = 8)
	protected Integer statusRound;

	/**  状态效果 */
	@ExcelCellBinding(offset = 9)
	protected String statusEffect;

	/**  参数1 */
	@ExcelCellBinding(offset = 10)
	protected String arg1;

	/**  参数2 */
	@ExcelCellBinding(offset = 11)
	protected String arg2;


	public String getStatusSn() {
		return this.statusSn;
	}

	public void setStatusSn(String statusSn) {
		if (StringUtils.isEmpty(statusSn)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[ 状态id]statusSn不可以为空");
		}
		this.statusSn = statusSn;
	}
	
	public Integer getStatusLevel() {
		return this.statusLevel;
	}

	public void setStatusLevel(Integer statusLevel) {
		this.statusLevel = statusLevel;
	}
	
	public String getStatusName() {
		return this.statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	
	public Integer getStatusType() {
		return this.statusType;
	}

	public void setStatusType(Integer statusType) {
		this.statusType = statusType;
	}
	
	public String getStatusDescription() {
		return this.statusDescription;
	}

	public void setStatusDescription(String statusDescription) {
		this.statusDescription = statusDescription;
	}
	
	public String getStatusSpecialEffect() {
		return this.statusSpecialEffect;
	}

	public void setStatusSpecialEffect(String statusSpecialEffect) {
		this.statusSpecialEffect = statusSpecialEffect;
	}
	
	public Double getStatusWeight() {
		return this.statusWeight;
	}

	public void setStatusWeight(Double statusWeight) {
		this.statusWeight = statusWeight;
	}
	
	public Integer getStatusRound() {
		return this.statusRound;
	}

	public void setStatusRound(Integer statusRound) {
		this.statusRound = statusRound;
	}
	
	public String getStatusEffect() {
		return this.statusEffect;
	}

	public void setStatusEffect(String statusEffect) {
		this.statusEffect = statusEffect;
	}
	
	public String getArg1() {
		return this.arg1;
	}

	public void setArg1(String arg1) {
		this.arg1 = arg1;
	}
	
	public String getArg2() {
		return this.arg2;
	}

	public void setArg2(String arg2) {
		this.arg2 = arg2;
	}
	

	@Override
	public String toString() {
		return "StatusTemplateVO[statusSn=" + statusSn + ",statusLevel=" + statusLevel + ",statusName=" + statusName + ",statusType=" + statusType + ",statusDescription=" + statusDescription + ",statusSpecialEffect=" + statusSpecialEffect + ",statusWeight=" + statusWeight + ",statusRound=" + statusRound + ",statusEffect=" + statusEffect + ",arg1=" + arg1 + ",arg2=" + arg2 + ",]";

	}
}