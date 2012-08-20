package com.pwrd.war.gameserver.item.msg;

import java.util.Collection;

import com.pwrd.war.common.model.item.CommonItem;
import com.pwrd.war.gameserver.item.Item;
import com.pwrd.war.gameserver.item.container.AbstractItemBag;

public class ItemMessageBuilder {
	
	public static GCItemUpdate buildGCItemInfo(Item item) {
		CommonItemBuilder builder = new CommonItemBuilder();
		GCItemUpdate msg = new GCItemUpdate();
		builder.bindItem(item);
		msg.setItem(new CommonItem[]{builder.buildCommonItem()});
		return msg;
	}
	
	public static GCBagUpdate buildGCBagUpdate(AbstractItemBag bag) {
		CommonItem[] commonItems = getCommonItem(bag);
		GCBagUpdate msg = new GCBagUpdate(bag.getOwner().getUUID(), bag.getOwner().getUUID(),(short) bag.getBagType().index,
				(short) bag.getCapacity(), commonItems);
		return msg;
	}
	
	public static CommonItem[] getCommonItem(AbstractItemBag bag) {
		CommonItemBuilder builder = new CommonItemBuilder();
		Collection<Item> items = bag.getAll();
		CommonItem[] commonItems = new CommonItem[0];
		if (!items.isEmpty()) {
			commonItems = new CommonItem[items.size()];
			int i = 0;
			for (Item item : items) {
				builder.bindItem(item);
				commonItems[i] = builder.buildCommonItem();
				i++;
			}
		}
		return commonItems;
	}

}
