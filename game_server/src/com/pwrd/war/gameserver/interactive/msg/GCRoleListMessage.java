package com.pwrd.war.gameserver.interactive.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.GCMessage;

/**
 * 查看装备基本信息
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCRoleListMessage extends GCMessage{
	
	/** 玩家的UUID */
	private String playUUID;
	/** ${field.comment} */
	private com.pwrd.war.gameserver.interactive.domain.PetInfomation[] petInformation;

	public GCRoleListMessage (){
	}
	
	public GCRoleListMessage (
			String playUUID,
			com.pwrd.war.gameserver.interactive.domain.PetInfomation[] petInformation ){
			this.playUUID = playUUID;
			this.petInformation = petInformation;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		playUUID = readString();
		count = readShort();
		count = count < 0 ? 0 : count;
		petInformation = new com.pwrd.war.gameserver.interactive.domain.PetInfomation[count];
		for(int i=0; i<count; i++){
			com.pwrd.war.gameserver.interactive.domain.PetInfomation obj = new com.pwrd.war.gameserver.interactive.domain.PetInfomation();
			obj.setUuid(readString());
			obj.setPetSn(readString());
			obj.setName(readString());
			obj.setSex(readInteger());
			obj.setVocation(readInteger());
			obj.setSkeltonId(readString());
			obj.setLevel(readInteger());
			obj.setInBattle(readBoolean());
			petInformation[i] = obj;
		}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeString(playUUID);
		writeShort(petInformation.length);
		for(int i=0; i<petInformation.length; i++){
			writeString(petInformation[i].getUuid());
			writeString(petInformation[i].getPetSn());
			writeString(petInformation[i].getName());
			writeInteger(petInformation[i].getSex());
			writeInteger(petInformation[i].getVocation());
			writeString(petInformation[i].getSkeltonId());
			writeInteger(petInformation[i].getLevel());
			writeBoolean(petInformation[i].getInBattle());
		}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_Role_List_Message;
	}
	
	@Override
	public String getTypeName() {
		return "GC_Role_List_Message";
	}

	public String getPlayUUID(){
		return playUUID;
	}
		
	public void setPlayUUID(String playUUID){
		this.playUUID = playUUID;
	}

	public com.pwrd.war.gameserver.interactive.domain.PetInfomation[] getPetInformation(){
		return petInformation;
	}

	public void setPetInformation(com.pwrd.war.gameserver.interactive.domain.PetInfomation[] petInformation){
		this.petInformation = petInformation;
	}	
}