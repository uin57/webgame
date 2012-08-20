package com.pwrd.war.gameserver.item.operation.impl;

import com.pwrd.war.gameserver.common.container.Bag.BagType;
import com.pwrd.war.gameserver.human.Human;
import com.pwrd.war.gameserver.item.Item;
import com.pwrd.war.gameserver.item.ItemUseInfo;
import com.pwrd.war.gameserver.item.operation.AbstractUseItemOperation;
import com.pwrd.war.gameserver.item.operation.MoveItemOperation;
import com.pwrd.war.gameserver.item.operation.MoveItemServicePool;
import com.pwrd.war.gameserver.pet.Pet;
import com.pwrd.war.gameserver.role.Role;

/**
 * 
 * 使用即移动的情况，使用武器、防具、行囊、格箱等都在这处理，转交给移动服务实现功能
 * 
 * 
 */
public class UseAsMove extends AbstractUseItemOperation {

	@Override
	protected boolean canUseImpl(Human user, Item item, Role target) {

		// 检查一般条件
		if (!checkCommonConditions((Pet)target, item)) {
			return false;
		}
		
		return true;
	}

	@Override
	public boolean isSuitable(Human user, Item item, Role target) {
		// 只适用于使用装备（武器、防具）
		if (item.isEquipment() && target instanceof Pet) {
			return true;
		}
		return false;
	}

	@Override
	protected boolean useImpl(Human user, ItemUseInfo itemInfo, Role target) {
		Item item = itemInfo.getItemToUse();
		if (item.isEquipment()) {
			return useEquip(user, item, target);
		} else {
			return false;
		}
	}

	@Override
	public ItemUseInfo collectItemUseInfo(Item item) {
		return collectItemOnly(item);
	}

	@Override
	public boolean consume(Human user, Item item, Role target) {
		// 不需要扣减
		return true;
	}

	private boolean useEquip(Human user, Item item, Role target) {
		if(target == null)
		{
			return false;
		}		
		BagType fromBag = item.getBagType();
		BagType toBag = BagType.NULL;
		// 根据fromBag决定toBag
		switch (fromBag) {
			case PRIM:
				// 从包里使用装备，就是移动到武将身上
				toBag = BagType.PET_EQUIP;
				break;
			case PET_EQUIP:
				// 从武将身上装备栏使用装备，就是移动到主背包
				toBag = BagType.PRIM;
				break;
			default:
				// 只允许使用身上或者主背包中的装备
				return false;
		}
		// 创建一个空Item,只是为了指定目标包
		Item toItem = user.getInventory().getItemByIndex(toBag, target.getUUID(), 0);
		
		MoveItemOperation service =  MoveItemServicePool.instance.get(fromBag, toBag);
		return service.move(user,(Pet)target, item, toItem);
	}


	@Override
	public Role getTarget(Human user, String targetUUID, Role target) {	
		Pet currPet = user.getPetManager().getPetByUuid(targetUUID);		
		return currPet;
	}
}
