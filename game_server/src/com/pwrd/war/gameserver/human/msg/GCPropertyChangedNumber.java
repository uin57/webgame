package com.pwrd.war.gameserver.human.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.GCMessage;

/**
 * 用于发送数值类型的属性改变消息
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCPropertyChangedNumber extends GCMessage{
	
	/** 角色类型 */
	private short roleType;
	/** 角色UUID */
	private String roleUUID;
	/** 所有变化的属性 */
	private com.pwrd.war.core.util.KeyValuePair<Integer,Integer>[] properties;

	public GCPropertyChangedNumber (){
	}
	
	public GCPropertyChangedNumber (
			short roleType,
			String roleUUID,
			com.pwrd.war.core.util.KeyValuePair<Integer,Integer>[] properties ){
			this.roleType = roleType;
			this.roleUUID = roleUUID;
			this.properties = properties;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		roleType = readShort();
		roleUUID = readString();
		count = readShort();
		count = count < 0 ? 0 : count;
		properties = com.pwrd.war.core.util.KeyValuePair.newKeyValuePairArray(count);
		for(int i=0; i<count; i++){
			com.pwrd.war.core.util.KeyValuePair<Integer,Integer> obj = new com.pwrd.war.core.util.KeyValuePair<Integer,Integer>();
			obj.setKey(readInteger());
			obj.setValue(readInteger());
			properties[i] = obj;
		}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeShort(roleType);
		writeString(roleUUID);
		writeShort(properties.length);
		for(int i=0; i<properties.length; i++){
			writeInteger(properties[i].getKey());
			writeInteger(properties[i].getValue());
		}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_PROPERTY_CHANGED_NUMBER;
	}
	
	@Override
	public String getTypeName() {
		return "GC_PROPERTY_CHANGED_NUMBER";
	}

	public short getRoleType(){
		return roleType;
	}
		
	public void setRoleType(short roleType){
		this.roleType = roleType;
	}

	public String getRoleUUID(){
		return roleUUID;
	}
		
	public void setRoleUUID(String roleUUID){
		this.roleUUID = roleUUID;
	}

	public com.pwrd.war.core.util.KeyValuePair<Integer,Integer>[] getProperties(){
		return properties;
	}

	public void setProperties(com.pwrd.war.core.util.KeyValuePair<Integer,Integer>[] properties){
		this.properties = properties;
	}	
}