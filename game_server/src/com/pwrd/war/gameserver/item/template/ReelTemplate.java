package com.pwrd.war.gameserver.item.template;

import java.util.Map;

import com.pwrd.war.core.annotation.ExcelCellBinding;
import com.pwrd.war.core.annotation.ExcelRowBinding;
import com.pwrd.war.core.template.ExcelCollectionMapping;
import com.pwrd.war.gameserver.item.Item;
import com.pwrd.war.gameserver.item.ItemDef.Position;
import com.pwrd.war.gameserver.item.ItemFeature;

/**
 * 打造卷轴模版
 * @author xf
 */
@ExcelRowBinding
public class ReelTemplate extends ItemTemplate {
 
	
	/** 装备对应的位置 */
	@ExcelCellBinding(offset = 4)
	private int type;
	
	/** 合成和物品sn */
	@ExcelCellBinding(offset = (START_INDEX+1))
	private String getItemSn;
	/** 需求装备sn */
	@ExcelCellBinding(offset = (START_INDEX+2))
	private String needEquipSn;
	/** 需求装备数量 */
	@ExcelCellBinding(offset = (START_INDEX+3))
	private int needEquipNum;
	/** 需求材料sn和数目 */
	@ExcelCollectionMapping(clazz = Integer.class, 
			collectionNumber = ""+ 
						(START_INDEX+4)+","+(START_INDEX+5)+";"+
						(START_INDEX+6)+","+(START_INDEX+7)+";"+
						(START_INDEX+8)+","+(START_INDEX+9)+";"+
						(START_INDEX+10)+","+(START_INDEX+11)+";"+
						(START_INDEX+12)+","+(START_INDEX+13)
						)//"19,20;21,22;23,24;25,26"
	private Map<String, Integer> needItems;
	 
	
	
	
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

	public String getGetItemSn() {
		return getItemSn;
	}

	public void setGetItemSn(String getItemSn) {
		this.getItemSn = getItemSn;
	}

	public Map<String, Integer> getNeedItems() {
		return needItems;
	}

	public void setNeedItems(Map<String, Integer> needItems) {
		needItems.remove("");//空的
		this.needItems = needItems;
	}

	public String getNeedEquipSn() {
		return needEquipSn;
	}

	public void setNeedEquipSn(String needEquipSn) {
		this.needEquipSn = needEquipSn;
	}

	public int getNeedEquipNum() {
		return needEquipNum;
	}

	public void setNeedEquipNum(int needEquipNum) {
		this.needEquipNum = needEquipNum;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
		super.setTypeId(type);
	}


}
