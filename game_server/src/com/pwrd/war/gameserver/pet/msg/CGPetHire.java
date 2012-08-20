package com.pwrd.war.gameserver.pet.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.CGMessage;
import com.pwrd.war.gameserver.pet.handler.PetHandlerFactory;

/**
 * 招募武将
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGPetHire extends CGMessage{
	
	/** 酒馆 */
	private String pub;
	/**  */
	private String petSn;
	
	public CGPetHire (){
	}
	
	public CGPetHire (
			String pub,
			String petSn ){
			this.pub = pub;
			this.petSn = petSn;
	}
	
	@Override
	protected boolean readImpl() {
		pub = readString();
		petSn = readString();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeString(pub);
		writeString(petSn);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_PET_HIRE;
	}
	
	@Override
	public String getTypeName() {
		return "CG_PET_HIRE";
	}

	public String getPub(){
		return pub;
	}
		
	public void setPub(String pub){
		this.pub = pub;
	}

	public String getPetSn(){
		return petSn;
	}
		
	public void setPetSn(String petSn){
		this.petSn = petSn;
	}

	@Override
	public void execute() {
		PetHandlerFactory.getHandler().handlePetHire(this.getSession().getPlayer(), this);
	}
}