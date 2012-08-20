package com.pwrd.war.gameserver.item.template;

import com.pwrd.war.core.annotation.ExcelRowBinding;
import com.pwrd.war.core.template.TemplateObject;
import com.pwrd.war.core.util.StringUtils;
import com.pwrd.war.common.exception.TemplateConfigException;
import com.pwrd.war.core.annotation.ExcelCellBinding;

/**
 * 星魂模板
 * 
 * @author CodeGenerator, don't modify this file please.
 */

@ExcelRowBinding
public abstract class XinghunXilianTemplateVO extends TemplateObject {

	/** SN */
	@ExcelCellBinding(offset = 1)
	protected String quality;

	/** 品质代码 1红2橙3紫4蓝5绿 */
	@ExcelCellBinding(offset = 2)
	protected int qualtityNum;

	/** 道具名称 */
	@ExcelCellBinding(offset = 3)
	protected int lock;

	/** 品质，颜色 */
	@ExcelCellBinding(offset = 4)
	protected int refresh;

	/** 消耗魂石 */
	@ExcelCellBinding(offset = 5)
	protected int hunshi;

	/** 消耗铜钱 */
	@ExcelCellBinding(offset = 6)
	protected int price;


	public String getQuality() {
		return this.quality;
	}

	public void setQuality(String quality) {
		if (StringUtils.isEmpty(quality)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[SN]quality不可以为空");
		}
		this.quality = quality;
	}
	
	public int getQualtityNum() {
		return this.qualtityNum;
	}

	public void setQualtityNum(int qualtityNum) {
		if (qualtityNum == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[品质代码 1红2橙3紫4蓝5绿]qualtityNum不可以为0");
		}
		this.qualtityNum = qualtityNum;
	}
	
	public int getLock() {
		return this.lock;
	}

	public void setLock(int lock) {
		if (lock < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[道具名称]lock的值不得小于0");
		}
		this.lock = lock;
	}
	
	public int getRefresh() {
		return this.refresh;
	}

	public void setRefresh(int refresh) {
		if (refresh < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[品质，颜色]refresh的值不得小于0");
		}
		this.refresh = refresh;
	}
	
	public int getHunshi() {
		return this.hunshi;
	}

	public void setHunshi(int hunshi) {
		if (hunshi < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					6, "[消耗魂石]hunshi的值不得小于0");
		}
		this.hunshi = hunshi;
	}
	
	public int getPrice() {
		return this.price;
	}

	public void setPrice(int price) {
		if (price < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					7, "[消耗铜钱]price的值不得小于0");
		}
		this.price = price;
	}
	

	@Override
	public String toString() {
		return "XinghunXilianTemplateVO[quality=" + quality + ",qualtityNum=" + qualtityNum + ",lock=" + lock + ",refresh=" + refresh + ",hunshi=" + hunshi + ",price=" + price + ",]";

	}
}