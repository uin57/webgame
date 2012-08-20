package com.pwrd.war.gameserver.item.template;

import com.pwrd.war.core.annotation.ExcelCellBinding;
import com.pwrd.war.core.annotation.ExcelRowBinding;
import com.pwrd.war.gameserver.item.Item;
import com.pwrd.war.gameserver.item.ItemDef.Position;
import com.pwrd.war.gameserver.item.ItemFeature;

/**
 * 材料模版
 * @author xf
 */
@ExcelRowBinding
public class MaterialTemplate extends ItemTemplate {
 
	
	/** 位置 */
	@ExcelCellBinding(offset = 4)
	private int type;
	
	/** 出处sn */
	@ExcelCellBinding(offset = (START_INDEX+1))
	private String outPlace;
	
	/** 材料不够需要的元宝  */
	@ExcelCellBinding(offset = (START_INDEX+3))
	private int gold;
	
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
		return false;
	}

	@Override
	public ItemFeature initItemFeature(Item item) { 
		return null;
	}

	 
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
		super.setTypeId(type);
	}

	public String getOutPlace() {
		return outPlace;
	}

	public void setOutPlace(String outPlace) {
		this.outPlace = outPlace;
	}

	public int getGold() {
		return gold;
	}

	public void setGold(int gold) {
		this.gold = gold;
	}

	 

}
