package com.pwrd.war.gameserver.human.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.GCMessage;

/**
 * 玩家第一此进入场景，发给玩家的其他玩家信息列表
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCSceneRoleList extends GCMessage{
	
	/** 角色信息 */
	private com.pwrd.war.common.model.human.HumanInfo[] roleInfo;

	public GCSceneRoleList (){
	}
	
	public GCSceneRoleList (
			com.pwrd.war.common.model.human.HumanInfo[] roleInfo ){
			this.roleInfo = roleInfo;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		roleInfo = new com.pwrd.war.common.model.human.HumanInfo[count];
		for(int i=0; i<count; i++){
			com.pwrd.war.common.model.human.HumanInfo obj = new com.pwrd.war.common.model.human.HumanInfo();
			obj.setRoleUUID(readString());
			obj.setName(readString());
			obj.setCamp(readInteger());
			obj.setSex(readInteger());
			obj.setVocation(readInteger());
			obj.setLevel(readInteger());
			obj.setSceneId(readString());
			obj.setLineNo(readInteger());
			obj.setX(readInteger());
			obj.setY(readInteger());
			obj.setToX(readInteger());
			obj.setToY(readInteger());
			obj.setHumanStatus(readInteger());
			obj.setWeaponSn(readString());
			obj.setBodySn(readString());
			obj.setXiulianSymbolId(readInteger());
			obj.setXiulianCollectTimes(readInteger());
			obj.setXiulianAllCollectTimes(readInteger());
			roleInfo[i] = obj;
		}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeShort(roleInfo.length);
		for(int i=0; i<roleInfo.length; i++){
			writeString(roleInfo[i].getRoleUUID());
			writeString(roleInfo[i].getName());
			writeInteger(roleInfo[i].getCamp());
			writeInteger(roleInfo[i].getSex());
			writeInteger(roleInfo[i].getVocation());
			writeInteger(roleInfo[i].getLevel());
			writeString(roleInfo[i].getSceneId());
			writeInteger(roleInfo[i].getLineNo());
			writeInteger(roleInfo[i].getX());
			writeInteger(roleInfo[i].getY());
			writeInteger(roleInfo[i].getToX());
			writeInteger(roleInfo[i].getToY());
			writeInteger(roleInfo[i].getHumanStatus());
			writeString(roleInfo[i].getWeaponSn());
			writeString(roleInfo[i].getBodySn());
			writeInteger(roleInfo[i].getXiulianSymbolId());
			writeInteger(roleInfo[i].getXiulianCollectTimes());
			writeInteger(roleInfo[i].getXiulianAllCollectTimes());
		}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SCENE_ROLE_LIST;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SCENE_ROLE_LIST";
	}

	public com.pwrd.war.common.model.human.HumanInfo[] getRoleInfo(){
		return roleInfo;
	}

	public void setRoleInfo(com.pwrd.war.common.model.human.HumanInfo[] roleInfo){
		this.roleInfo = roleInfo;
	}	
}