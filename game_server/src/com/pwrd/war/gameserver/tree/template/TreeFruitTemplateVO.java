package com.pwrd.war.gameserver.tree.template;

import com.pwrd.war.core.annotation.ExcelRowBinding;
import com.pwrd.war.core.template.TemplateObject;
import com.pwrd.war.core.util.StringUtils;
import com.pwrd.war.common.exception.TemplateConfigException;
import com.pwrd.war.core.annotation.ExcelCellBinding;

/**
 * 摇钱树模版类
 * 
 * @author CodeGenerator, don't modify this file please.
 */

@ExcelRowBinding
public abstract class TreeFruitTemplateVO extends TemplateObject {

	/** 果实等级 */
	@ExcelCellBinding(offset = 1)
	protected int level;

	/** 获得铜钱 */
	@ExcelCellBinding(offset = 2)
	protected int coins;

	/** vip获得铜钱 */
	@ExcelCellBinding(offset = 3)
	protected int vipCoins;

	/** 获得元宝 */
	@ExcelCellBinding(offset = 4)
	protected int glod;

	/** vip获得元宝 */
	@ExcelCellBinding(offset = 5)
	protected int vipGlod;

	/** 获得道具 */
	@ExcelCellBinding(offset = 6)
	protected String item;

	/** vip获得道具 */
	@ExcelCellBinding(offset = 7)
	protected String vipItem;


	public int getLevel() {
		return this.level;
	}

	public void setLevel(int level) {
		if (level < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[果实等级]level的值不得小于0");
		}
		this.level = level;
	}
	
	public int getCoins() {
		return this.coins;
	}

	public void setCoins(int coins) {
		if (coins < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[获得铜钱]coins的值不得小于0");
		}
		this.coins = coins;
	}
	
	public int getVipCoins() {
		return this.vipCoins;
	}

	public void setVipCoins(int vipCoins) {
		if (vipCoins < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[vip获得铜钱]vipCoins的值不得小于0");
		}
		this.vipCoins = vipCoins;
	}
	
	public int getGlod() {
		return this.glod;
	}

	public void setGlod(int glod) {
		if (glod < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[获得元宝]glod的值不得小于0");
		}
		this.glod = glod;
	}
	
	public int getVipGlod() {
		return this.vipGlod;
	}

	public void setVipGlod(int vipGlod) {
		if (vipGlod < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					6, "[vip获得元宝]vipGlod的值不得小于0");
		}
		this.vipGlod = vipGlod;
	}
	
	public String getItem() {
		return this.item;
	}

	public void setItem(String item) {
		if (StringUtils.isEmpty(item)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					7, "[获得道具]item不可以为空");
		}
		this.item = item;
	}
	
	public String getVipItem() {
		return this.vipItem;
	}

	public void setVipItem(String vipItem) {
		if (StringUtils.isEmpty(vipItem)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					8, "[vip获得道具]vipItem不可以为空");
		}
		this.vipItem = vipItem;
	}
	

	@Override
	public String toString() {
		return "TreeFruitTemplateVO[level=" + level + ",coins=" + coins + ",vipCoins=" + vipCoins + ",glod=" + glod + ",vipGlod=" + vipGlod + ",item=" + item + ",vipItem=" + vipItem + ",]";

	}
}