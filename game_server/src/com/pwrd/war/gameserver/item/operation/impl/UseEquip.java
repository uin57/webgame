package com.pwrd.war.gameserver.item.operation.impl;

import com.pwrd.war.gameserver.common.container.Bag.BagType;
import com.pwrd.war.gameserver.human.Human;
import com.pwrd.war.gameserver.item.Item;
import com.pwrd.war.gameserver.item.ItemUseInfo;
import com.pwrd.war.gameserver.item.operation.AbstractUseItemOperation;
import com.pwrd.war.gameserver.item.operation.MoveItemServicePool;
import com.pwrd.war.gameserver.item.operation.UseEquipOperation;
import com.pwrd.war.gameserver.pet.Pet;
import com.pwrd.war.gameserver.role.Role;

/**
 * 玩家自己或者武将穿戴装备
 * @author xf
 */
public class UseEquip extends AbstractUseItemOperation {

	@Override
	public boolean consume(Human user, Item item, Role target) {
		// 不需要扣减
		return true;
	}

	@Override
	public Role getTarget(Human user, String targetUUID, Role target) { 
		if(target != null)
			return target;
		return user;
	}

	@Override
	public boolean isSuitable(Human user, Item item, Role target) {
		if (!item.isEquipment()) {
			return false;
		}
		if(!(target instanceof Human || target instanceof Pet)){
			return false;
		}
		if(item.getBagType() == BagType.HUMAN_EQUIP
				|| item.getBagType() == BagType.PET_EQUIP
				|| item.getBagType() == BagType.PRIM){
			return true;
		}
		return false;
	}

	@Override
	public ItemUseInfo collectItemUseInfo(Item item) {
		return collectItemOnly(item);
	}

	@Override
	protected boolean canUseImpl(Human user, Item item, Role target) {
//		if(user ==  target){
//			return true;
//		}
//		return false;
		return true;
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
				if(target instanceof Human){
					toBag = BagType.HUMAN_EQUIP;
				}else if(target instanceof Pet){
					toBag = BagType.PET_EQUIP;
				}
				break;
			case HUMAN_EQUIP: 
				toBag = BagType.PRIM;
				break;
			case PET_EQUIP: 
				toBag = BagType.PRIM;
				break;
			default:
				// 只允许使用身上或者主背包中的装备
				return false;
		}
		// 创建一个空Item,只是为了指定目标包
//		Item toItem = user.getInventory().getItemByIndex(toBag, target.getUUID(), 0); 
		UseEquipOperation service =  MoveItemServicePool.instance.getEquipOperation(fromBag, toBag);
		if(target instanceof Human){
			return service.move(user,  item);
		}else{
			return service.move(user, (Pet) target, item);
		}
	}
}
