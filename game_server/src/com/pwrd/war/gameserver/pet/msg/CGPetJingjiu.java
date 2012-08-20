package com.pwrd.war.gameserver.pet.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.CGMessage;
import com.pwrd.war.gameserver.pet.handler.PetHandlerFactory;

/**
 * 武将敬酒
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGPetJingjiu extends CGMessage{
	
	/** 敬酒id */
	private int id;
	
	public CGPetJingjiu (){
	}
	
	public CGPetJingjiu (
			int id ){
			this.id = id;
	}
	
	@Override
	protected boolean readImpl() {
		id = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(id);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_PET_JINGJIU;
	}
	
	@Override
	public String getTypeName() {
		return "CG_PET_JINGJIU";
	}

	public int getId(){
		return id;
	}
		
	public void setId(int id){
		this.id = id;
	}

	@Override
	public void execute() {
		PetHandlerFactory.getHandler().handlePetJingjiu(this.getSession().getPlayer(), this);
	}
}