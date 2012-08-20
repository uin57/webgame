package com.pwrd.war.gameserver.item.operation;

import com.pwrd.war.gameserver.common.container.Bag.BagType;
import com.pwrd.war.gameserver.human.Human;
import com.pwrd.war.gameserver.item.Item;
import com.pwrd.war.gameserver.pet.Pet;

public interface UseEquipOperation {
	
	public boolean isSuiltable(BagType fromBag, BagType toBag);
	
	public boolean move(Human user, Item item);	
	
	public boolean move(Human user,Pet wearer, Item from);
}
