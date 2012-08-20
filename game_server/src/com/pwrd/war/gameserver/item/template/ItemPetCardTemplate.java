package com.pwrd.war.gameserver.item.template;

import com.pwrd.war.common.exception.TemplateConfigException;
import com.pwrd.war.core.annotation.ExcelCellBinding;
import com.pwrd.war.core.annotation.ExcelRowBinding;
import com.pwrd.war.gameserver.item.Item;
import com.pwrd.war.gameserver.item.ItemDef.Position;
import com.pwrd.war.gameserver.item.ItemFeature;

/**
 * 武将卡物品模板，使用模板文件中最后一列作为武将id进行配置
 * @author zy
 */
@ExcelRowBinding
public class ItemPetCardTemplate extends ItemTemplate {
 
	 
	@ExcelCellBinding(offset = 21)
	private String petSn;
	 
	@Override
	public Position getPosition() { 
		return Position.NULL;
	}

	@Override
	public boolean isEquipment() { 
		return false;
	}

	@Override
	public boolean getCanBeUsed() { 
		return true;
	}

	@Override
	public ItemFeature initItemFeature(Item item) { 
		return null;
	}

	public String getPetSn() {
		return petSn;
	}

	public void setPetSn(String petSn) {
		this.petSn = petSn;
	}

	@Override
	public void check() throws TemplateConfigException {		
		super.check();
	}
	 

}
