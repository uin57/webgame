package com.pwrd.war.gameserver.pet.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.GCMessage;

/**
 * 宠物信息
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCPetInfo extends GCMessage{
	
	/** 单个宠物信息 */
	private com.pwrd.war.common.model.pet.PetInfo petInfo;

	public GCPetInfo (){
	}
	
	public GCPetInfo (
			com.pwrd.war.common.model.pet.PetInfo petInfo ){
			this.petInfo = petInfo;
	}

	@Override
	protected boolean readImpl() {
		petInfo = new com.pwrd.war.common.model.pet.PetInfo();
					petInfo.setUuid(readString());
							petInfo.setPetSn(readString());
				return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeString(petInfo.getUuid());
		writeString(petInfo.getPetSn());
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_PET_INFO;
	}
	
	@Override
	public String getTypeName() {
		return "GC_PET_INFO";
	}

	public com.pwrd.war.common.model.pet.PetInfo getPetInfo(){
		return petInfo;
	}
		
	public void setPetInfo(com.pwrd.war.common.model.pet.PetInfo petInfo){
		this.petInfo = petInfo;
	}
}