package com.pwrd.war.gameserver.human.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.GCMessage;

/**
 * 玩家信息发生变化，告诉场景内其他人
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCSceneUpdateRole extends GCMessage{
	
	/** 角色信息 */
	private com.pwrd.war.common.model.human.HumanInfo roleInfo;

	public GCSceneUpdateRole (){
	}
	
	public GCSceneUpdateRole (
			com.pwrd.war.common.model.human.HumanInfo roleInfo ){
			this.roleInfo = roleInfo;
	}

	@Override
	protected boolean readImpl() {
		roleInfo = new com.pwrd.war.common.model.human.HumanInfo();
					roleInfo.setRoleUUID(readString());
							roleInfo.setName(readString());
							roleInfo.setCamp(readInteger());
							roleInfo.setSex(readInteger());
							roleInfo.setVocation(readInteger());
							roleInfo.setLevel(readInteger());
							roleInfo.setSceneId(readString());
							roleInfo.setLineNo(readInteger());
							roleInfo.setX(readInteger());
							roleInfo.setY(readInteger());
							roleInfo.setToX(readInteger());
							roleInfo.setToY(readInteger());
							roleInfo.setHumanStatus(readInteger());
							roleInfo.setWeaponSn(readString());
							roleInfo.setBodySn(readString());
							roleInfo.setXiulianSymbolId(readInteger());
							roleInfo.setXiulianCollectTimes(readInteger());
							roleInfo.setXiulianAllCollectTimes(readInteger());
				return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeString(roleInfo.getRoleUUID());
		writeString(roleInfo.getName());
		writeInteger(roleInfo.getCamp());
		writeInteger(roleInfo.getSex());
		writeInteger(roleInfo.getVocation());
		writeInteger(roleInfo.getLevel());
		writeString(roleInfo.getSceneId());
		writeInteger(roleInfo.getLineNo());
		writeInteger(roleInfo.getX());
		writeInteger(roleInfo.getY());
		writeInteger(roleInfo.getToX());
		writeInteger(roleInfo.getToY());
		writeInteger(roleInfo.getHumanStatus());
		writeString(roleInfo.getWeaponSn());
		writeString(roleInfo.getBodySn());
		writeInteger(roleInfo.getXiulianSymbolId());
		writeInteger(roleInfo.getXiulianCollectTimes());
		writeInteger(roleInfo.getXiulianAllCollectTimes());
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SCENE_UPDATE_ROLE;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SCENE_UPDATE_ROLE";
	}

	public com.pwrd.war.common.model.human.HumanInfo getRoleInfo(){
		return roleInfo;
	}
		
	public void setRoleInfo(com.pwrd.war.common.model.human.HumanInfo roleInfo){
		this.roleInfo = roleInfo;
	}
}