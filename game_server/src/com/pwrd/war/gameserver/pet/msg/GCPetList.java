package com.pwrd.war.gameserver.pet.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.GCMessage;

/**
 * 武将列表消息
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCPetList extends GCMessage{
	
	/** 武将列表总的大小 */
	private short maxSize;
	/** 目前武将的数量 */
	private short currentSize;
	/** 武将信息列表 */
	private com.pwrd.war.common.model.pet.PetInfo[] pets;

	public GCPetList (){
	}
	
	public GCPetList (
			short maxSize,
			short currentSize,
			com.pwrd.war.common.model.pet.PetInfo[] pets ){
			this.maxSize = maxSize;
			this.currentSize = currentSize;
			this.pets = pets;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		maxSize = readShort();
		currentSize = readShort();
		count = readShort();
		count = count < 0 ? 0 : count;
		pets = new com.pwrd.war.common.model.pet.PetInfo[count];
		for(int i=0; i<count; i++){
			com.pwrd.war.common.model.pet.PetInfo obj = new com.pwrd.war.common.model.pet.PetInfo();
			obj.setUuid(readString());
			obj.setPetSn(readString());
			pets[i] = obj;
		}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeShort(maxSize);
		writeShort(currentSize);
		writeShort(pets.length);
		for(int i=0; i<pets.length; i++){
			writeString(pets[i].getUuid());
			writeString(pets[i].getPetSn());
		}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_PET_LIST;
	}
	
	@Override
	public String getTypeName() {
		return "GC_PET_LIST";
	}

	public short getMaxSize(){
		return maxSize;
	}
		
	public void setMaxSize(short maxSize){
		this.maxSize = maxSize;
	}

	public short getCurrentSize(){
		return currentSize;
	}
		
	public void setCurrentSize(short currentSize){
		this.currentSize = currentSize;
	}

	public com.pwrd.war.common.model.pet.PetInfo[] getPets(){
		return pets;
	}

	public void setPets(com.pwrd.war.common.model.pet.PetInfo[] pets){
		this.pets = pets;
	}	
}