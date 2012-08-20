package com.pwrd.war.gameserver.common.event;

import com.pwrd.war.core.event.IEvent;
import com.pwrd.war.gameserver.human.Human;
import com.pwrd.war.gameserver.pet.Pet;

/**
 * 增加武将事件
 * @author xf
 */
public class AddPetEvent implements IEvent<Pet> {
	private Human human;
	private Pet pet;
	public AddPetEvent(Human human, Pet pet){
		this.human = human;
		this.pet = pet;
	}
	
	@Override
	public Pet getInfo() { 
		return pet;
	}

	public Human getHuman() {
		return human;
	}

}
