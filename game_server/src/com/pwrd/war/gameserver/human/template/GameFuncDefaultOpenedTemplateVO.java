package com.pwrd.war.gameserver.human.template;

import com.pwrd.war.common.exception.TemplateConfigException;
import com.pwrd.war.core.annotation.ExcelCellBinding;
import com.pwrd.war.core.annotation.ExcelRowBinding;
import com.pwrd.war.core.template.TemplateObject;

/**
 * 游戏功能默认开放配置
 * 
 * @author CodeGenerator, don't modify this file please.
 */

@ExcelRowBinding
public abstract class GameFuncDefaultOpenedTemplateVO extends TemplateObject {

	/**  是否开启 */
	@ExcelCellBinding(offset = 1)
	protected int opened;


	public int getOpened() {
		return this.opened;
	}

	public void setOpened(int opened) {
		if (opened < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[ 是否开启]opened的值不得小于0");
		}
		this.opened = opened;
	}
	

	@Override
	public String toString() {
		return "GameFuncDefaultOpenedTemplateVO[opened=" + opened + ",]";

	}
}