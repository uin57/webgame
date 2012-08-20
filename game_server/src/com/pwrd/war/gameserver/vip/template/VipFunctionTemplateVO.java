package com.pwrd.war.gameserver.vip.template;

import com.pwrd.war.core.annotation.ExcelRowBinding;
import com.pwrd.war.core.template.TemplateObject;
import com.pwrd.war.common.exception.TemplateConfigException;
import com.pwrd.war.core.annotation.ExcelCellBinding;

/**
 * VIP功能描述模板=======
 * 
 * @author CodeGenerator, don't modify this file please.
 */

@ExcelRowBinding
public abstract class VipFunctionTemplateVO extends TemplateObject {

	/** vip等级 */
	@ExcelCellBinding(offset = 1)
	protected int vipLevel;

	/** 充值金额 */
	@ExcelCellBinding(offset = 2)
	protected int charge;

	/** 功能id */
	@ExcelCellBinding(offset = 3)
	protected String functionId;

	/** 功能描述 */
	@ExcelCellBinding(offset = 4)
	protected String functionDesc;


	public int getVipLevel() {
		return this.vipLevel;
	}

	public void setVipLevel(int vipLevel) {
		this.vipLevel = vipLevel;
	}
	
	public int getCharge() {
		return this.charge;
	}

	public void setCharge(int charge) {
		this.charge = charge;
	}
	
	public String getFunctionId() {
		return this.functionId;
	}

	public void setFunctionId(String functionId) {
		this.functionId = functionId;
	}
	
	public String getFunctionDesc() {
		return this.functionDesc;
	}

	public void setFunctionDesc(String functionDesc) {
		this.functionDesc = functionDesc;
	}
	

	@Override
	public String toString() {
		return "VipFunctionTemplateVO[vipLevel=" + vipLevel + ",charge=" + charge + ",functionId=" + functionId + ",functionDesc=" + functionDesc + ",]";

	}
}