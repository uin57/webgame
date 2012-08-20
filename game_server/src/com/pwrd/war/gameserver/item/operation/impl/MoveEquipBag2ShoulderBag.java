package com.pwrd.war.gameserver.item.operation.impl;

import com.pwrd.war.gameserver.common.container.Bag.BagType;
import com.pwrd.war.gameserver.human.Human;
import com.pwrd.war.gameserver.item.Item;
import com.pwrd.war.gameserver.item.operation.AbstractMoveItemOperation;
import com.pwrd.war.gameserver.pet.Pet;

public class MoveEquipBag2ShoulderBag extends AbstractMoveItemOperation {

	@Override
	protected boolean isSuiltable(BagType fromBag, BagType toBag) {
		if((fromBag == BagType.HUMAN_EQUIP || fromBag == BagType.PET_EQUIP )
				&& toBag == BagType.PRIM){
			return true;
		}
		return false;
	}

	@Override
	protected boolean moveImpl(Human user, Item fromItem, Item toItem) {
		//将人身上的装备移动到背包上
		boolean rs = user.getInventory().getEquipBag().unEquipItem(fromItem, user.getInventory().getPrimBag(), toItem);
		return rs;
	}

	@Override
	protected boolean moveImpl(Human user, Pet wearer, Item fromItem,
			Item toItem) {
		//将武将的背包移动到人身上
		boolean rs = user.getInventory().getBagByPet(wearer.getUUID()).unEquipItem(fromItem, 
				user.getInventory().getPrimBag(), toItem);
		return rs; 
	}

}
