package com.pwrd.war.gameserver.pet.msg;

import java.util.List;

import com.pwrd.war.common.constants.RoleConstants;
import com.pwrd.war.common.model.pet.PetInfo;
import com.pwrd.war.core.util.Assert;
import com.pwrd.war.gameserver.human.Human;
import com.pwrd.war.gameserver.pet.Pet;
import com.pwrd.war.gameserver.pet.PetContainer;

public class PetMsgBuilder {
	
	
	/**
	 * 获取宠物信息列表
	 * 
	 * @return 宠物信息列表{link com.imop.webzt.gameserver.pet.msg.GCPetList}
	 */
	public static GCPetList getPetListMsg(Human owner, PetContainer petContainer) {
		Assert.notNull(owner);
		Assert.notNull(petContainer);
		GCPetList gcPetList = new GCPetList();
		gcPetList.setMaxSize(RoleConstants.PET_MAX_SIZE);
		gcPetList.setCurrentSize((short) petContainer.getPetCount());
		List<Pet> petList = petContainer.getPets();
		PetInfo[] pets = new PetInfo[petList.size()];
		for (int i = 0; i < petList.size(); i++) {
			PetInfo gcPetInfo = getPetInfo(petList.get(i));
			pets[i] = gcPetInfo;
		}
		gcPetList.setPets(pets);
		return gcPetList;
	}

	/**
	 * 获取宠物信息
	 * 
	 * @param pet
	 * @return
	 */
	public static PetInfo getPetInfo(Pet pet) {
		PetInfo petInfo = new PetInfo();
		
		petInfo.setUuid(pet.getUUID());
		petInfo.setPetSn(pet.getPetSn());
//		petInfo.setSkeletonId(pet.getSkeletonId());
//		petInfo.setName(pet.getName());
//		petInfo.setType(pet.getPetType());
//		petInfo.setLevel((short)pet.getLevel());
//		petInfo.setExp(pet.getCurExp()); 
		
		// TODO 增加属性的设置 
//		petInfo.setLeadership(pet.getPropertyManager().getLeaderShip());
//		petInfo.setMight(pet.getPropertyManager().getMight());
//		petInfo.setIntellect(pet.getPropertyManager().getIntellect());
		
		return petInfo;
	}
	
	
	
}
