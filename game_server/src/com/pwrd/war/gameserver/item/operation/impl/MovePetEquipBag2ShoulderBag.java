package com.pwrd.war.gameserver.item.operation.impl;

import com.pwrd.war.gameserver.common.container.Bag.BagType;
import com.pwrd.war.gameserver.human.Human;
import com.pwrd.war.gameserver.item.Item;
import com.pwrd.war.gameserver.item.container.PetBodyEquipBag;
import com.pwrd.war.gameserver.item.operation.UseEquipOperation;
import com.pwrd.war.gameserver.pet.Pet;

public class MovePetEquipBag2ShoulderBag implements UseEquipOperation {

	@Override
	public boolean isSuiltable(BagType fromBag, BagType toBag) {
		// 从主背包到身上
		if (fromBag == BagType.PET_EQUIP && toBag == BagType.PRIM) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean move(Human user, Item fromItem) { 
		return false;
	}

	@Override
	public boolean move(Human user, Pet wearer, Item fromItem) {
		boolean rs = false;
		PetBodyEquipBag bag = user.getInventory().getBagByPet(wearer.getUUID());
		rs = bag.unEquipItem(fromItem, user.getInventory().getPrimBag(), null);
		return rs;
	}
}
