package com.pwrd.war.gameserver.item.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.GCMessage;

/**
 * 人物换装后发消息
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCSwitchEquip extends GCMessage{
	
	/** 角色ID */
	private String roleUUID;
	/** 武将ID */
	private String petUUID;
	/** 角色类型 */
	private short petType;
	/** 装备位置 */
	private short position;
	/** 整体ID */
	private int avatarId;
	/** 头发ID */
	private int hairId;
	/** 特征ID */
	private int featureId;
	/** 军帽ID */
	private int capId;

	public GCSwitchEquip (){
	}
	
	public GCSwitchEquip (
			String roleUUID,
			String petUUID,
			short petType,
			short position,
			int avatarId,
			int hairId,
			int featureId,
			int capId ){
			this.roleUUID = roleUUID;
			this.petUUID = petUUID;
			this.petType = petType;
			this.position = position;
			this.avatarId = avatarId;
			this.hairId = hairId;
			this.featureId = featureId;
			this.capId = capId;
	}

	@Override
	protected boolean readImpl() {
		roleUUID = readString();
		petUUID = readString();
		petType = readShort();
		position = readShort();
		avatarId = readInteger();
		hairId = readInteger();
		featureId = readInteger();
		capId = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeString(roleUUID);
		writeString(petUUID);
		writeShort(petType);
		writeShort(position);
		writeInteger(avatarId);
		writeInteger(hairId);
		writeInteger(featureId);
		writeInteger(capId);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SWITCH_EQUIP;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SWITCH_EQUIP";
	}

	public String getRoleUUID(){
		return roleUUID;
	}
		
	public void setRoleUUID(String roleUUID){
		this.roleUUID = roleUUID;
	}

	public String getPetUUID(){
		return petUUID;
	}
		
	public void setPetUUID(String petUUID){
		this.petUUID = petUUID;
	}

	public short getPetType(){
		return petType;
	}
		
	public void setPetType(short petType){
		this.petType = petType;
	}

	public short getPosition(){
		return position;
	}
		
	public void setPosition(short position){
		this.position = position;
	}

	public int getAvatarId(){
		return avatarId;
	}
		
	public void setAvatarId(int avatarId){
		this.avatarId = avatarId;
	}

	public int getHairId(){
		return hairId;
	}
		
	public void setHairId(int hairId){
		this.hairId = hairId;
	}

	public int getFeatureId(){
		return featureId;
	}
		
	public void setFeatureId(int featureId){
		this.featureId = featureId;
	}

	public int getCapId(){
		return capId;
	}
		
	public void setCapId(int capId){
		this.capId = capId;
	}
}