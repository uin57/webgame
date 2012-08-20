package com.pwrd.war.gameserver.pet.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.GCMessage;

/**
 * 获取可见招募武将列表
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCPetHireList extends GCMessage{
	
	/** 招募宠物信息 */
	private com.pwrd.war.common.model.pet.PetHireInfo[] petInfo;

	public GCPetHireList (){
	}
	
	public GCPetHireList (
			com.pwrd.war.common.model.pet.PetHireInfo[] petInfo ){
			this.petInfo = petInfo;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		petInfo = new com.pwrd.war.common.model.pet.PetHireInfo[count];
		for(int i=0; i<count; i++){
			com.pwrd.war.common.model.pet.PetHireInfo obj = new com.pwrd.war.common.model.pet.PetHireInfo();
			obj.setPetSn(readString());
			obj.setCanHire(readBoolean());
			obj.setHireLevel(readInteger());
			obj.setHirePopularity(readInteger());
			obj.setHireCoin(readInteger());
			obj.setHireGold(readInteger());
			obj.setHireItem1Sn(readString());
			obj.setHireItem1Num(readInteger());
			obj.setHireItem2Sn(readString());
			obj.setHireItem2Num(readInteger());
			petInfo[i] = obj;
		}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeShort(petInfo.length);
		for(int i=0; i<petInfo.length; i++){
			writeString(petInfo[i].getPetSn());
			writeBoolean(petInfo[i].getCanHire());
			writeInteger(petInfo[i].getHireLevel());
			writeInteger(petInfo[i].getHirePopularity());
			writeInteger(petInfo[i].getHireCoin());
			writeInteger(petInfo[i].getHireGold());
			writeString(petInfo[i].getHireItem1Sn());
			writeInteger(petInfo[i].getHireItem1Num());
			writeString(petInfo[i].getHireItem2Sn());
			writeInteger(petInfo[i].getHireItem2Num());
		}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_PET_HIRE_LIST;
	}
	
	@Override
	public String getTypeName() {
		return "GC_PET_HIRE_LIST";
	}

	public com.pwrd.war.common.model.pet.PetHireInfo[] getPetInfo(){
		return petInfo;
	}

	public void setPetInfo(com.pwrd.war.common.model.pet.PetHireInfo[] petInfo){
		this.petInfo = petInfo;
	}	
}