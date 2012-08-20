package com.pwrd.war.gameserver.item.operation.impl;

import java.util.Collection;
import java.util.List;

import com.pwrd.war.common.LogReasons.ItemGenLogReason;
import com.pwrd.war.gameserver.human.Human;
import com.pwrd.war.gameserver.item.Item;
import com.pwrd.war.gameserver.item.ItemParam;
import com.pwrd.war.gameserver.item.ItemUseInfo;
import com.pwrd.war.gameserver.item.operation.AbstractUseItemOperation;
import com.pwrd.war.gameserver.item.template.ItemBagTemplate;
import com.pwrd.war.gameserver.role.Role;

/**
 * 使用物品包
 * @author xf
 */
public class UseItemBag extends AbstractUseItemOperation {

	@Override
	protected boolean canUseImpl(Human user, Item item, Role target) {
		// 检查一般条件
		if (!checkCommonConditions(user, item)) {
			return false;
		}

		return true;
	}

	@Override
	public ItemUseInfo collectItemUseInfo(Item item) {
		return collectItemAndTemplate(item);
	}

	@Override
	public boolean isSuitable(Human user, Item item, Role target) {
		if(item.getTemplate() instanceof ItemBagTemplate){
			return true;
		}
		return false;
	}

	@Override
	protected boolean useImpl(Human user, ItemUseInfo itemInfo, Role target) {	
//		System.out.println("使用物品包");
		ItemBagTemplate template = (ItemBagTemplate) itemInfo.getTemplate();
		List<ItemParam> listItem = template.getBagItems();
		if(user.getInventory().checkSpace(listItem, true)){
			Collection<Item> rs = user.getInventory().addAllItems(listItem, ItemGenLogReason.QUEST_BONUS,
					"", true);
			if(rs.size() > 0){
				return this.consume(user, itemInfo.getItemToUse(), target);
			}
		}
		return false;
	}

	@Override
	public boolean consume(Human user, Item item, Role target) {
		return consumeOne(user, item);
	}
	
	@Override
	public Role getTarget(Human user, String targetUUID, Role target) {
		return target;
	}


}
