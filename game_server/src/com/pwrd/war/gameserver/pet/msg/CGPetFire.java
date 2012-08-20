package com.pwrd.war.gameserver.pet.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.CGMessage;
import com.pwrd.war.gameserver.pet.handler.PetHandlerFactory;

/**
 * 解雇武将
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGPetFire extends CGMessage{
	
	/** 武将uuid */
	private String petUUID;
	
	public CGPetFire (){
	}
	
	public CGPetFire (
			String petUUID ){
			this.petUUID = petUUID;
	}
	
	@Override
	protected boolean readImpl() {
		petUUID = readString();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeString(petUUID);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_PET_FIRE;
	}
	
	@Override
	public String getTypeName() {
		return "CG_PET_FIRE";
	}

	public String getPetUUID(){
		return petUUID;
	}
		
	public void setPetUUID(String petUUID){
		this.petUUID = petUUID;
	}

	@Override
	public void execute() {
		PetHandlerFactory.getHandler().handlePetFire(this.getSession().getPlayer(), this);
	}
}