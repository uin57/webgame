package com.pwrd.war.gameserver.human.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.GCMessage;

/**
 * 返回成就列表
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCArchiveList extends GCMessage{
	
	/** 所有成就信息 */
	private com.pwrd.war.gameserver.human.domain.Archive[] properties;

	public GCArchiveList (){
	}
	
	public GCArchiveList (
			com.pwrd.war.gameserver.human.domain.Archive[] properties ){
			this.properties = properties;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		properties = new com.pwrd.war.gameserver.human.domain.Archive[count];
		for(int i=0; i<count; i++){
			com.pwrd.war.gameserver.human.domain.Archive obj = new com.pwrd.war.gameserver.human.domain.Archive();
			obj.setUuid(readString());
			obj.setHumanUUID(readString());
			obj.setType(readString());
			obj.setNumber(readInteger());
			obj.setTitle(readString());
			properties[i] = obj;
		}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeShort(properties.length);
		for(int i=0; i<properties.length; i++){
			writeString(properties[i].getUuid());
			writeString(properties[i].getHumanUUID());
			writeString(properties[i].getType());
			writeInteger(properties[i].getNumber());
			writeString(properties[i].getTitle());
		}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_ARCHIVE_LIST;
	}
	
	@Override
	public String getTypeName() {
		return "GC_ARCHIVE_LIST";
	}

	public com.pwrd.war.gameserver.human.domain.Archive[] getProperties(){
		return properties;
	}

	public void setProperties(com.pwrd.war.gameserver.human.domain.Archive[] properties){
		this.properties = properties;
	}	
}