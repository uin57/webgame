package com.pwrd.war.gameserver.item.generate;

import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.common.container.Bag.BagType;
import com.pwrd.war.gameserver.human.Human;
import com.pwrd.war.gameserver.item.Item;
import com.pwrd.war.gameserver.item.template.EquipItemTemplate;

public class EquipGenerator {
	
	public EquipGenerator() {
	}
	
	/**
	 * 生成已经激活的装备
	 * 
	 * @return 如果道具不是装备则返回null
	 */
	public Item generateActivedEquip(Human owner, EquipItemTemplate template,
			BagType bagType, int bagIndex) {
		if (!template.isEquipment()) {
			return null;
		}
		// 生成激活的实例
		Item item = Item.newActivatedInstance(owner, template, bagType, bagIndex);
		//初始化一般属性
		Globals.getItemService().buildPropsByEnhanceLevel(item, 1);
		return genByEquipType(owner, item, bagType, bagIndex);
	}
	
	/**
	 * 生成未激活的装备 
	 * 
	 * @return 如果道具不是装备则返回null
	 */
	public Item generateDeactivedEquip(Human owner, EquipItemTemplate template,
			BagType bagType, int bagIndex) {
		if (!template.isEquipment()) {
			return null;
		}
		// 生成未激活的实例
		Item item = Item.newDeactivedInstance(owner, template, bagType,	bagIndex);
		//初始化一般属性
		Globals.getItemService().buildPropsByEnhanceLevel(item, 1);
		return genByEquipType(owner, item, bagType, bagIndex);
	}
	
	private Item genByEquipType(Human owner, Item item, BagType bagType, int bagIndex) {
		return item;
	}

}
