package com.pwrd.war.gameserver.item.template;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.pwrd.war.common.exception.TemplateConfigException;
import com.pwrd.war.core.annotation.ExcelCellBinding;
import com.pwrd.war.core.annotation.ExcelRowBinding;
import com.pwrd.war.core.template.ExcelCollectionMapping;
import com.pwrd.war.gameserver.human.wealth.Bindable.BindStatus;
import com.pwrd.war.gameserver.human.wealth.Bindable.Oper;
import com.pwrd.war.gameserver.item.Item;
import com.pwrd.war.gameserver.item.ItemDef.Position;
import com.pwrd.war.gameserver.item.ItemFeature;
import com.pwrd.war.gameserver.item.ItemParam;

/**
 * 物品包模板
 * @author xf
 */
@ExcelRowBinding
public class ItemBagTemplate extends ItemTemplate {
 
	 
	@ExcelCellBinding(offset = 4)
	private int subType;
	 
	/** 包里的物品和数量 */
	@ExcelCollectionMapping(clazz = String.class, 
			collectionNumber = ""+ 
						(START_INDEX+1)+";"+(START_INDEX+2)+";"+
						(START_INDEX+3)+";"+(START_INDEX+4)+";"+
						(START_INDEX+5)+";"+(START_INDEX+6)+";"+
						(START_INDEX+7)+";"+(START_INDEX+8)+";"+
						(START_INDEX+9)+";"+(START_INDEX+10)
						)//"19,20;21,22;23,24;25,26"
	private List<String> items;
	 
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

	 

	public List<String> getItems() {
		return items;
	}

	public void setItems(List<String> items) {
		this.items = items;
	}

	public int getSubType() {
		return subType;
	}

	public void setSubType(int subType) {
		this.subType = subType;
	}

	@Override
	public void check() throws TemplateConfigException {		
		super.check();
		try {
			this.getBagItems();
		} catch (Exception e) {
			e.printStackTrace();
			throw new TemplateConfigException(this.getSheetName(), this.getId(), "加载物品包出现错误");
		}
	}

	public List<ItemParam> getBagItems(){
		List<String> itemss = this.getItems();
		List<ItemParam> listItem = new ArrayList<ItemParam>();
		for(String s : itemss){
			if(StringUtils.isEmpty(s))continue;
			String[] t = s.split("[*]");
			BindStatus bind = this.getBindStatusAfterOper(BindStatus.NOT_BIND, Oper.GET);
			ItemParam p = new ItemParam(t[0], Integer.valueOf(t[1]), bind);			
			listItem.add(p);
		}
		return listItem;
	}

	 

}
