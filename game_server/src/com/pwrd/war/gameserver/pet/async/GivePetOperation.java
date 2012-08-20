package com.pwrd.war.gameserver.pet.async;

import com.pwrd.war.db.model.PetEntity;
import com.pwrd.war.gameserver.common.Globals;
import com.pwrd.war.gameserver.common.db.operation.BindUUIDIoOperation;
import com.pwrd.war.gameserver.common.event.AddPetEvent;
import com.pwrd.war.gameserver.human.Human;
import com.pwrd.war.gameserver.pet.Pet;

/**
 * 创建新武将的异步操作
 * @author yue.yan
 *
 */
public class GivePetOperation implements BindUUIDIoOperation {

	/** 创建新武将的玩家角色 */
	private Human human;
	/** 创建的新武将 */
	private Pet pet;
	
	public GivePetOperation(Human human, Pet pet) {
		this.human = human;
		this.pet = pet;
	}
	
	@Override
	public String getBindUUID() {
		return human.getUUID();
	}


	@Override
	public int doStart() {
		return STAGE_START_DONE;
	}
	
	@Override
	public int doIo() {
		pet.setOwner(human);
		pet.init();
		PetEntity ent = pet.toEntity();
		Globals.getDaoService().getPetDao().save(ent);
		pet.setDbId(ent.getId());
		pet.setInDb(true);
		return STAGE_IO_DONE;
	}

	@Override
	public int doStop() {
		//如果创建成功，则进行初始化，并将其添加至PetManager中
		if(pet != null) {
			
			human.getPetManager().addPet(pet);
			
			//增加装备包
			human.getInventory().addPetBag(pet);
			
			//增加兵法装备位
			human.getWarcraftInventory().addWarcraftEquipBag(pet.getUUID());
			
			//激活此武将
			pet.onInit();
			
			pet.setModified();
			
			
			//发消息刷新武将列表
//			human.sendMessage(human.getPetManager().getPetListMsg());
			human.sendMessage(human.getPetManager().getPetAddMsg(pet));
			pet.calcProps();
			//刷新武将背包
			human.sendMessage(human.getInventory().getPetEquipBagInfoMsg(pet.getUUID()));
			
			//发送武将数据信息
			human.getPetManager().sendPetPropsMsg(pet.getUUID());
			
			AddPetEvent event = new AddPetEvent(human, pet);
			Globals.getEventService().fireEvent(event);
		}
		return STAGE_STOP_DONE;
	}

}
