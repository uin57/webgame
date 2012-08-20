package com.pwrd.war.gameserver.item.template;

import com.pwrd.war.core.annotation.ExcelRowBinding;
import com.pwrd.war.gameserver.common.container.Bag.BagType;
import com.pwrd.war.gameserver.item.Item;
import com.pwrd.war.gameserver.item.ItemDef.Position;
import com.pwrd.war.gameserver.item.ItemFeature;
import com.pwrd.war.gameserver.item.XinghunFeature;

/**
 * 星魂模版
 * @author xf
 */
@ExcelRowBinding
public class XinghunItemTemplate extends ItemTemplate {
	
	/** 道具将被放在哪个包中 */
	private BagType bagType;
	
	XinghunItemTemplate(){
		bagType = BagType.XINGHUN;
	}
	 
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
		XinghunFeature feature = new XinghunFeature(item); 
		return feature;
	}

	public BagType getBagType() {
		return bagType;
	}
}
