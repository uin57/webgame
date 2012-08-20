package com.pwrd.war.gameserver.player.msg;

import com.pwrd.war.core.msg.MessageType;
import com.pwrd.war.gameserver.common.msg.GCMessage;

/**
 * 角色信息
 *
 * @author CodeGenerator, don't modify this file please.
 */
public class GCRoleInfo extends GCMessage{
	
	/** 角色的uuid */
	private String roleUUID;
	/** 角色名字 */
	private String name;
	/** 选择的性别 */
	private int sex;
	/** 选择的职业 */
	private int vocation;
	/** 选择的阵营  */
	private int camp;

	public GCRoleInfo (){
	}
	
	public GCRoleInfo (
			String roleUUID,
			String name,
			int sex,
			int vocation,
			int camp ){
			this.roleUUID = roleUUID;
			this.name = name;
			this.sex = sex;
			this.vocation = vocation;
			this.camp = camp;
	}

	@Override
	protected boolean readImpl() {
		roleUUID = readString();
		name = readString();
		sex = readInteger();
		vocation = readInteger();
		camp = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeString(roleUUID);
		writeString(name);
		writeInteger(sex);
		writeInteger(vocation);
		writeInteger(camp);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_ROLE_INFO;
	}
	
	@Override
	public String getTypeName() {
		return "GC_ROLE_INFO";
	}

	public String getRoleUUID(){
		return roleUUID;
	}
		
	public void setRoleUUID(String roleUUID){
		this.roleUUID = roleUUID;
	}

	public String getName(){
		return name;
	}
		
	public void setName(String name){
		this.name = name;
	}

	public int getSex(){
		return sex;
	}
		
	public void setSex(int sex){
		this.sex = sex;
	}

	public int getVocation(){
		return vocation;
	}
		
	public void setVocation(int vocation){
		this.vocation = vocation;
	}

	public int getCamp(){
		return camp;
	}
		
	public void setCamp(int camp){
		this.camp = camp;
	}
}