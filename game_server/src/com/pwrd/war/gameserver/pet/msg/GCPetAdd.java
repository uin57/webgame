package com.pwrd.war.gameserver.pet.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.GCMessage;

/**
 * 增加武将消息
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCPetAdd extends GCMessage{
	
	/** 武将信息 */
	private com.pwrd.war.common.model.pet.PetInfo pets;

	public GCPetAdd (){
	}
	
	public GCPetAdd (
			com.pwrd.war.common.model.pet.PetInfo pets ){
			this.pets = pets;
	}

	@Override
	protected boolean readImpl() {
		pets = new com.pwrd.war.common.model.pet.PetInfo();
					pets.setUuid(readString());
							pets.setPetSn(readString());
				return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeString(pets.getUuid());
		writeString(pets.getPetSn());
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_PET_ADD;
	}
	
	@Override
	public String getTypeName() {
		return "GC_PET_ADD";
	}

	public com.pwrd.war.common.model.pet.PetInfo getPets(){
		return pets;
	}
		
	public void setPets(com.pwrd.war.common.model.pet.PetInfo pets){
		this.pets = pets;
	}
}