package com.pwrd.war.gameserver.item.template;

import com.pwrd.war.core.annotation.ExcelRowBinding;
import com.pwrd.war.core.template.TemplateObject;
import java.util.List;
import com.pwrd.war.core.template.ExcelCollectionMapping;
import com.pwrd.war.common.exception.TemplateConfigException;
import com.pwrd.war.core.annotation.ExcelCellBinding;

/**
 * 装备强化成功率模版
 * 
 * @author CodeGenerator, don't modify this file please.
 */

@ExcelRowBinding
public abstract class EquipEnhancePosPriceVO extends TemplateObject {

	/** 装备等级 */
	@ExcelCellBinding(offset = 1)
	protected int lelve;

	/** 部位对应的价格武器	头盔	衣服	鞋子	戒指	配饰 */
	@ExcelCollectionMapping(clazz = Integer.class, collectionNumber = "2;3;4;5;6;7")
	protected List<Integer> posPrice;


	public int getLelve() {
		return this.lelve;
	}

	public void setLelve(int lelve) {
		if (lelve == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[装备等级]lelve不可以为0");
		}
		this.lelve = lelve;
	}
	
	public List<Integer> getPosPrice() {
		return this.posPrice;
	}

	public void setPosPrice(List<Integer> posPrice) {
		if (posPrice == null) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[部位对应的价格武器	头盔	衣服	鞋子	戒指	配饰]posPrice不可以为空");
		}	
		this.posPrice = posPrice;
	}
	

	@Override
	public String toString() {
		return "EquipEnhancePosPriceVO[lelve=" + lelve + ",posPrice=" + posPrice + ",]";

	}
}