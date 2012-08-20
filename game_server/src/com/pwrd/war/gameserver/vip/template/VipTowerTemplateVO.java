package com.pwrd.war.gameserver.vip.template;

import com.pwrd.war.core.annotation.ExcelRowBinding;
import com.pwrd.war.core.template.TemplateObject;
import com.pwrd.war.common.exception.TemplateConfigException;
import com.pwrd.war.core.annotation.ExcelCellBinding;

/**
 * vip将星刷新
 * 
 * @author CodeGenerator, don't modify this file please.
 */

@ExcelRowBinding
public abstract class VipTowerTemplateVO extends TemplateObject {

	/** vip等级 */
	@ExcelCellBinding(offset = 1)
	protected int vipLevel;

	/** 刷新次数 */
	@ExcelCellBinding(offset = 2)
	protected int times;


	public int getVipLevel() {
		return this.vipLevel;
	}

	public void setVipLevel(int vipLevel) {
		this.vipLevel = vipLevel;
	}
	
	public int getTimes() {
		return this.times;
	}

	public void setTimes(int times) {
		this.times = times;
	}
	

	@Override
	public String toString() {
		return "VipTowerTemplateVO[vipLevel=" + vipLevel + ",times=" + times + ",]";

	}
}