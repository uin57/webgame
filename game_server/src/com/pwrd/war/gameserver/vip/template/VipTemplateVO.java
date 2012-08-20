package com.pwrd.war.gameserver.vip.template;

import com.pwrd.war.core.annotation.ExcelRowBinding;
import com.pwrd.war.core.template.TemplateObject;
import com.pwrd.war.common.exception.TemplateConfigException;
import com.pwrd.war.core.annotation.ExcelCellBinding;

/**
 * vip充值模板
 * 
 * @author CodeGenerator, don't modify this file please.
 */

@ExcelRowBinding
public abstract class VipTemplateVO extends TemplateObject {

	/** vip等级 */
	@ExcelCellBinding(offset = 1)
	protected int vipLevel;

	/** 货币类型 */
	@ExcelCellBinding(offset = 2)
	protected int charge;


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
	

	@Override
	public String toString() {
		return "VipTemplateVO[vipLevel=" + vipLevel + ",charge=" + charge + ",]";

	}
}