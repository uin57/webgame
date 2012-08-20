package com.pwrd.war.gameserver.pet.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.GCMessage;

/**
 * 增删除武将消息
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCPetDel extends GCMessage{
	
	/** 武将UUID */
	private String petUUid;

	public GCPetDel (){
	}
	
	public GCPetDel (
			String petUUid ){
			this.petUUid = petUUid;
	}

	@Override
	protected boolean readImpl() {
		petUUid = readString();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeString(petUUid);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_PET_DEL;
	}
	
	@Override
	public String getTypeName() {
		return "GC_PET_DEL";
	}

	public String getPetUUid(){
		return petUUid;
	}
		
	public void setPetUUid(String petUUid){
		this.petUUid = petUUid;
	}
}