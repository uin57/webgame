package com.pwrd.war.gameserver.pet.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.CGMessage;
import com.pwrd.war.gameserver.pet.handler.PetHandlerFactory;

/**
 * 获取可见招募武将列表
 * 
 * @author CodeGenerator, don't modify this file please.
 */
public class CGPetHireList extends CGMessage{
	
	/** 酒馆 */
	private String pub;
	
	public CGPetHireList (){
	}
	
	public CGPetHireList (
			String pub ){
			this.pub = pub;
	}
	
	@Override
	protected boolean readImpl() {
		pub = readString();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeString(pub);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_PET_HIRE_LIST;
	}
	
	@Override
	public String getTypeName() {
		return "CG_PET_HIRE_LIST";
	}

	public String getPub(){
		return pub;
	}
		
	public void setPub(String pub){
		this.pub = pub;
	}

	@Override
	public void execute() {
		PetHandlerFactory.getHandler().handlePetHireList(this.getSession().getPlayer(), this);
	}
}