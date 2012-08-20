package com.pwrd.war.gameserver.tree.template;

import com.pwrd.war.core.annotation.ExcelRowBinding;
import com.pwrd.war.core.template.TemplateObject;
import com.pwrd.war.common.exception.TemplateConfigException;
import com.pwrd.war.core.annotation.ExcelCellBinding;

/**
 * 摇钱树模版类
 * 
 * @author CodeGenerator, don't modify this file please.
 */

@ExcelRowBinding
public abstract class TreeTemplateVO extends TemplateObject {

	/** 玩家等级 */
	@ExcelCellBinding(offset = 1)
	protected int level;

	/** 获得铜钱 */
	@ExcelCellBinding(offset = 2)
	protected int getCoins;


	public int getLevel() {
		return this.level;
	}

	public void setLevel(int level) {
		if (level < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[玩家等级]level的值不得小于0");
		}
		this.level = level;
	}
	
	public int getGetCoins() {
		return this.getCoins;
	}

	public void setGetCoins(int getCoins) {
		if (getCoins < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[获得铜钱]getCoins的值不得小于0");
		}
		this.getCoins = getCoins;
	}
	

	@Override
	public String toString() {
		return "TreeTemplateVO[level=" + level + ",getCoins=" + getCoins + ",]";

	}
}