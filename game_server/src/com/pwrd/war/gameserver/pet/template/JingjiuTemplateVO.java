package com.pwrd.war.gameserver.pet.template;

import com.pwrd.war.core.annotation.ExcelRowBinding;
import com.pwrd.war.core.template.TemplateObject;
import com.pwrd.war.core.util.StringUtils;
import com.pwrd.war.common.exception.TemplateConfigException;
import com.pwrd.war.core.annotation.ExcelCellBinding;

/**
 * 敬酒模板
 * 
 * @author CodeGenerator, don't modify this file please.
 */

@ExcelRowBinding
public abstract class JingjiuTemplateVO extends TemplateObject {

	/** 酒名 */
	@ExcelCellBinding(offset = 1)
	protected String name;

	/** 所需vip等级 */
	@ExcelCellBinding(offset = 2)
	protected int vip;

	/** 所需铜钱 */
	@ExcelCellBinding(offset = 3)
	protected int coin;

	/** 所需元宝 */
	@ExcelCellBinding(offset = 4)
	protected int gold;

	/** 所需声望 */
	@ExcelCellBinding(offset = 5)
	protected int popularity;


	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		if (StringUtils.isEmpty(name)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[酒名]name不可以为空");
		}
		this.name = name;
	}
	
	public int getVip() {
		return this.vip;
	}

	public void setVip(int vip) {
		this.vip = vip;
	}
	
	public int getCoin() {
		return this.coin;
	}

	public void setCoin(int coin) {
		this.coin = coin;
	}
	
	public int getGold() {
		return this.gold;
	}

	public void setGold(int gold) {
		this.gold = gold;
	}
	
	public int getPopularity() {
		return this.popularity;
	}

	public void setPopularity(int popularity) {
		this.popularity = popularity;
	}
	

	@Override
	public String toString() {
		return "JingjiuTemplateVO[name=" + name + ",vip=" + vip + ",coin=" + coin + ",gold=" + gold + ",popularity=" + popularity + ",]";

	}
}